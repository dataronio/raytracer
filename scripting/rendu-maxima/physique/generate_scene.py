#!/usr/bin/env python2
# -*- coding: utf8 -*
from physics import *
import os.path
import multiprocessing
from random import uniform

current_dir = os.path.dirname(os.path.abspath(__file__))
scene = open(os.path.join(current_dir, 'scene')).read()
dest_dir = raw_input('Répertoire des images : ')
dt = float(raw_input('Delta : '))
max_time = float(raw_input('Temps Max : '))
nbr_sphere = int(raw_input('Nombre de sphère : '))


class Texture(object):
    fields = ('k_diffuse', 'k_refraction', 'k_reflection', 'brightness', 'k_specular', 'refractive_index')

    def __init__(self, k_diffuse=(1.0, 1.0, 1.0), k_refraction=(0, 0, 0), k_reflection=0.0, brightness=30.0, k_specular=1.0, refractive_index=1.0):
        for f in Texture.fields:
            setattr(self, f, locals()[f])

    def __repr__(self):
        return ', '.join('%s=%s' % (f, getattr(self, f)) for f in Texture.fields)

class SphereScene(Sphere):
    def __init__(self, texture, world, radius, pos=(0, 0, 0), mass=1.0, density=500.0): 
        Sphere.__init__(self, world, radius, pos, mass, density)
        self.texture = texture

    def __repr__(self):
        return "Sphere(center=%s, radius=%s, %s)" % (self.getPosition(), self.radius, self.texture)

# Fonction de collision
def collideCallback(c):
    c.setBounce(0.8)
    #c.setMu(5000)

world = World((0, 0, -9.81), collideCallback)

boules = []
for i in xrange(nbr_sphere):
    t = uniform(0, 1)
    pos = (uniform(-3, 3), uniform(-3, 3), uniform(3, 300))
    diffuse = (uniform(0, 1), uniform(0, 1), uniform(0, 1))
    if t > 0.8: # miroir
        boules.append(SphereScene(Texture((0, 0, 0), (0, 0, 0), 1), world, radius=0.5, pos=pos))
    elif t > 0.6: # transparence
        boules.append(SphereScene(Texture(diffuse, (0.3, 0.3, 0.3)), world, radius=0.5, pos=pos))
    else:
        boules.append(SphereScene(Texture(diffuse), world, radius=0.5, pos=pos))

sol = FixPlane(world, normal=(0, 0, 1), dist=-3)

# Génération des scènes
print "\nGenerating scenes..."

current_time = 0.0
i = 1
commands = []
while current_time < max_time:
    lines = [repr(b) for b in boules]

    si = '0'*(4-len(str(i))) + str(i)
    scene_file = os.path.join(dest_dir, '%s.scn' % si)
    output_file = os.path.join(dest_dir, si)
    open(scene_file, 'w+').write(scene + '\n' + '\n'.join(lines) + '\n')
    commands.append("java raytracer.RayTracer %s %s" % (scene_file, output_file))

    world.step(dt)
    i += 1
    current_time += dt

# Rendu
print "Parallel rendering..."

pool = multiprocessing.Pool(None)
r = pool.map_async(os.system, commands)
r.wait()
