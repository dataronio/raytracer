#!/usr/bin/env python2
# -*- coding: utf8 -*
import ode
import os.path
import multiprocessing

repertoire_courant = os.path.dirname(os.path.abspath(__file__))
scene = open(os.path.join(repertoire_courant, 'scene')).read()
repertoire_destination = raw_input('Répertoire des images : ')

# Temporaire
rayon = 0.5
densite = 500.0
masse = 1.0
position_initiale = (0, 0, 2)
dt = 0.04
temps_max= 4.0

# Création du monde
world = ode.World()
world.setGravity((0, 0, -9.81))
space = ode.Space()
contactgroup = ode.JointGroup()

# Fonction de collision
def near_callback(args, geom1, geom2):
    """Callback function for the collide() method.

    This function checks if the given geoms do collide and
    creates contact joints if they do.
    """

    # Check if the objects do collide
    contacts = ode.collide(geom1, geom2)

    # Create contact joints
    world, contactgroup = args
    for c in contacts:
        c.setBounce(1.0)
        #c.setMu(5000)
        j = ode.ContactJoint(world, contactgroup, c)
        j.attach(geom1.getBody(), geom2.getBody())

# Boule
boule = ode.Body(world)
m = ode.Mass()
m.setSphere(densite, rayon)
m.mass = masse
boule.setMass(m)
boule.setPosition(position_initiale)

boule_space = ode.GeomSphere(space, radius=rayon)
boule_space.setBody(boule)

# Sol
sol = ode.GeomPlane(space, (0, 0, 1), -3)

# Génération des scènes
print "\nGénération des scènes..."

temps_courant = 0.0
i = 1
commandes = []
while temps_courant < temps_max:
    ligne = "Sphere(center=%s, radius=%s)" % (boule.getPosition(), rayon)

    si = '0'*(4-len(str(i))) + str(i)
    fichier_scene = os.path.join(repertoire_destination, '%s.scn' % si)
    fichier_sortie = os.path.join(repertoire_destination, si)
    open(fichier_scene, 'w+').write(scene + '\n' + ligne + '\n')
    commandes.append("java raytracer.RayTracer %s %s" % (fichier_scene, fichier_sortie))

    # Génération d'un petit intervalle de temps
    space.collide((world, contactgroup), near_callback)
    world.step(dt)
    contactgroup.empty()

    i += 1
    temps_courant += dt

# Rendu
print "Rendu parallèlisé en cours..."

pool = multiprocessing.Pool(None)
r = pool.map_async(os.system, commandes)
r.wait()
