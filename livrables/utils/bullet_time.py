#!/usr/bin/python
import math, os, multiprocessing

RATIO = 1

scène = open(input("Scène : ")).readlines()
répertoire_destination = input("Répertoire des images : ")

height = int(input("Taille : "))
width = height * RATIO

centre_x = int(input("Centre x : "))
centre_y = int(input("Centre y : "))
centre_z = int(input("Centre z : "))
rayon = int(input("Rayon : "))
nb_images = int(input("Nombre d'images : "))

scène_sans_caméra = [i for i in scène if not i.lower().startswith("camera")]

step = 2*math.pi/nb_images

angle = -step
i = 0
commands = []

print("Generating scenes...")

while angle < 2*math.pi:
    angle += step
    i += 1

    pos_milieu_écran = (centre_x - rayon*math.cos(angle), centre_y - rayon*math.sin(angle), centre_z)

    vecteur_abscisse = (math.cos(angle + math.pi/2), math.sin(angle + math.pi/2), 0)
    vecteur_ordonnée = (0, 0, -1)

    pos_debut_écran = (pos_milieu_écran[0] - vecteur_abscisse[0]/2, pos_milieu_écran[1] - vecteur_abscisse[1]/2, pos_milieu_écran[2] - vecteur_ordonnée[2]/2)

    pos_œil = (centre_x - (rayon + 1)*math.cos(angle), centre_y - (rayon + 1)*math.sin(angle), centre_z)

    ligne_caméra = "Camera(eye=%s, origin=%s, abscissa=%s, ordinate=%s, widthPixel=%s, heightPixel=%s)" % (repr(pos_œil), repr(pos_debut_écran), repr(vecteur_abscisse), repr(vecteur_ordonnée), width, height)

    scène = scène_sans_caméra + [ligne_caméra]

    si = '0'*(4-len(str(i))) + str(i)

    fichier_scène = os.path.join(répertoire_destination, '%s.scn' % si)
    open(fichier_scène, 'w+').write('\n'.join(scène) + '\n')
    commands.append("java raytracer.RayTracer %s %s" % (fichier_scène, os.path.join(répertoire_destination, si)))

print("Parallel rendering...")

pool = multiprocessing.Pool(None)
r = pool.map_async(os.system, commands)
r.wait()

