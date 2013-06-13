#!/usr/bin/env python2
import ode

class World(object):
    def __init__(self, gravity=(0, 0, -9.81), collideCallback=None):
        self.oWorld = ode.World()
        self.oWorld.setGravity(gravity)
        self.oSpace = ode.Space()
        self.oContactGroup = ode.JointGroup()
        self.collideCallback = collideCallback

    def step(self, dt):
        self.oSpace.collide(None, self.nearCallback)
        self.oWorld.step(dt)
        self.oContactGroup.empty()

    def nearCallback(self, args, geom1, geom2):
        contacts = ode.collide(geom1, geom2)

        for c in contacts:
            if self.collideCallback is not None:
                self.collideCallback(c)
            j = ode.ContactJoint(self.oWorld, self.oContactGroup, c)
            j.attach(geom1.getBody(), geom2.getBody())

class PhysicsObject(object):
    def __init__(self, world, pos=(0, 0, 0), mass=1.0, density = 500.0):
        assert type(world) is World

        self.world = world
        self.mass = mass
        self.density = density

        self.body = ode.Body(self.world.oWorld)
        self.body.setMass(self.createMass())
        self.body.setPosition(pos)
        
        self.geom = self.createGeom()
        self.geom.setBody(self.body)

    def setPosition(self, pos):
        self.body.setPosition(pos)

    def getPosition(self):
        return self.body.getPosition()

    def createMass(self):
        raise NotImplementedError()

    def createGeom(self):
        raise NotImplementedError()

class Sphere(PhysicsObject):
    def __init__(self, world, radius, pos=(0, 0, 0), mass=1.0, density = 500.0):
        self.radius = radius
        PhysicsObject.__init__(self, world, pos, mass, density)

    def createMass(self):
        m = ode.Mass()
        m.setSphere(self.density, self.radius)
        m.mass = self.mass
        return m

    def createGeom(self):
        return ode.GeomSphere(self.world.oSpace, radius=self.radius)

class FixPlane(PhysicsObject):
    def __init__(self, world, normal, dist):
        self.world = world
        self.normal = normal
        self.dist = dist

        self.geom = self.createGeom()

    def createGeom(self):
        return ode.GeomPlane(self.world.oSpace, self.normal, self.dist)
