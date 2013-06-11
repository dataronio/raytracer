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
        nz = (u.x*u.z*(1-c) - u.y*s)*self.x + (u.y*u.z*(1-c) + u.x*s)*self.y + (u.z**2 + (1-u.z**2)*c)*self.z

        self.x, self.y, self.z = nx, ny, nz
    
    def __sub__(self, other):
        return Point(self.x-other.x, self.y-other.y, self.z-other.z)

    def __add__(self, other):
        return Point(self.x+other.x, self.y+other.y, self.z+other.z)

    def __rmul__(self, other):
        return Point(self.x*other, self.y*other, self.z*other)
    
    def __mul__(self, other):
        return Point(self.x*other, self.y*other, self.z*other)

    def __neg__(self):
        return self*-1

    def __str__(self):
        return str((self.x, self.y, self.z))


class Vector(Point):
    pass


class Object():
    center = None # Le centre de l'objet, dans le référentiel global
    points = {} # Un dictionnaire avec les noms des points de l'objet et leurs valeurs dans le référentiel du center
    attrs = {} # Un dictionnaire avec les noms des propriétés et leurs valeurs

    def __init__(self, center, points, attrs):
        self.center, self.points, self.attrs = center, points, attrs

    def rotate(self, vector, angle):
        for key in self.points:
            self.points[key].rotate(vector, angle)

    def translate(self, vector):
        self.center += vector

    def __str__(self):
        s = self.__class__.__name__ + "("
        s += ', '.join(['%s=%s' % (k, v + (self.center if v.__class__ == Point else NULLP)) for k, v in self.points.items()] + ['%s=%s' % t for t in self.attrs.items()])
        s += ")"
        return s


class ComplexObject():
    def rotate(self, vector, angle):
        for i in self.objects:
            i.rotate(vector, angle)

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

class AmbientLights():
    def __init__(self, r, g, b):
        self.intensity = (r, g, b)
    def __str__(self):
        return "AmbientLights" + str(self.intensity)

class Light(Object):
    def setIntensity(self, intensity):
        self.attrs['intensity'] = intensity

class Camera(Object):
    pass


class Scene():
    def __init__(self):
        self.objects = {}

    def write(self, filename):
        open(filename, 'w+').write(str(self))

    def __str__(self):
        s = ''
        for name, content in self.objects.items():
            s += '// ' + name + '\n'
            s += str(content) + '\n'
            s += '\n'
        return s


X = Point(1, 0, 0)
Y = Point(0, 1, 0)
Z = Point(0, 0, 1)
NULLV = Vector(0, 0, 0)
NULLP = Point(0, 0, 0)

