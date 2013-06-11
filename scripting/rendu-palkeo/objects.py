import math

class Point():
    def __init__(self, x, y, z):
        self.x, self.y, self.z = x, y, z

    def rotate(self, vector, angle):
        c = math.cos(angle)
        s = math.sin(angle)
        u = vector

        nx = (u.x**2 + (1 - u.x**2)*c)*self.x + (u.x*u.y*(1 - c) - u.z*s)*self.y + (u.x*u.z*(1 - c) + u.y*s)*self.z
        ny = (u.x*u.y*(1 - c) + u.z*s)*self.x + (u.y**2 + (1-u.y**2)*c)*self.y + (u.y*u.z*(1-c) - u.x*s)*self.z
        nz = (u.x*u.z*(1-c) - u.y*s)*self.x + (u.y*u.z*(1-c) + u.z*s)*self.y + (u.z**2 + (1-u.z**2)*c)*self.z

        self.x, self.y, self.z = nx, ny, nz
    
    def __sub__(self, other):
        return Point(self.x-other.x, self.y-other.y, self.z-other.z)

    def __add__(self, other):
        return Point(self.x+other.x, self.y+other.y, self.z+other.z)


class Vector(Point):
    pass


class Object():
    center = None # Le centre de l'objet, dans le référentiel global
    points = {} # Un dictionnaire avec les noms des points de l'objet et leurs valeurs dans le référentiel du center
    attrs = {} # Un dictionnaire avec les noms des propriétés et leurs valeurs

    def __init__(self, center, points, attrs):
        self.center, self.points, self.attrs = center, points, attrs

    def rotate_around(self, vector, angle):
        for key in self.points:
            self.points[key].rotate(vector, angle)

    def rotate(self, ax, ay, az):
        self.rotate_around(AXIS_X, ax)
        self.rotate_around(AXIS_Y, ay)
        self.rotate_around(AXIS_Z, az)

    def translate(self, vector):
        self.center += vector

    def __str__(self):
        s = self.__class__.__name__ + "("
        s += ', '.join(['%s=%s' % (k, v + self.center) for k, v in self.points.items()] + ['%s=%s' % t for t in self.attrs.items()])
        s += ")"
        return s


class ComplexObject():
    def __init__(self, center, objects):
        self.objects = objects
        for o in self.objects:
            for i in o.points.values():
                i.translate(center - i.center)
            o.center = center

    def rotate_around(self, vector, angle):
        for i in self.objects:
            i.rotate_around(vector, angle)

    def translate(self, vector):
        for i in self.objects:
            i.translate(vector)

    def __str__(self):
        return '\n'.join(str(i) for i in self.objects)

    @property
    def center(self):
        return self.objects[0].center


class Sphere(Object):
    pass

class Cube(Object):
    pass

class Triangle(Object):
    pass

class Parallelogram(Object):
    pass

class ImageRect(Object):
    pass

class Plane(Object):
    pass

class AmbientLight(Object):
    pass

class Light(Object):
    pass

AXIS_X = Vector(1, 0, 0)
AXIS_Y = Vector(0, 1, 0)
AXIS_Z = Vector(0, 0, 1)
NULL_VECTOR = Vector(0, 0, 0)
