#!/usr/bin/env python2
# -*- coding: utf8 -*
import math

class Point():
    def __init__(self, x, y, z):
        self.x, self.y, self.z = x, y, z

    def __sub__(self, other):
        return Point(self.x - other.x, self.y - other.y, self.z - other.z)

    def __add__(self, other):
        return Point(self.x + other.x, self.y + other.y, self.z + other.z)

    def __mul__(self, other):
        return Point(self.x*other, self.y*other, self.z*other)

    def __rmul__(self, other):
        return Point(self.x*other, self.y*other, self.z*other)

    def __neg__(self):
        return self*-1

    def __str__(self):
        return str((self.x, self.y, self.z))

    def __repr__(self):
        return str(self)

    def dotproduct(self, other):
        return self.x*other.x + self.y*other.y + self.z*other.z

    def length(self):
        return math.sqrt(self.dotproduct(self))

    def angle(self, other):
        if other.length() > 0 and self.length() > 0:
            return math.acos(self.dotproduct(other) / self.length() / other.length())
        else:
            raise Exception('Point.angle() ne peut pas être utilisé avec un vecteur nul')

X = Point(1, 0, 0)
Y = Point(0, 1, 0)
Z = Point(0, 0, 1)
POINT_NUL = Point(0, 0, 0)

def camera(center, abs, ord, see, focale, width, height):
    cs = see - center
    sc = -cs
    screen = center + (focale / cs.length()) * cs

    theta = Point(1, 0, 0).angle(Point(sc.x, sc.y, 0))
    if sc.y < 0:
        theta = 2*math.pi - theta
    phi = Point(sc.x, sc.y, 0).angle(sc)
    if sc.z < 0:
        phi = 2*math.pi - phi

    etheta = Point(-math.sin(theta), math.cos(theta), 0)
    ephi = Point(-math.sin(phi)*math.cos(theta), -math.sin(phi)*math.sin(theta), math.cos(phi))

    return 'Camera(eye=%s, origin=%s, abscissa=%s, ordinate=%s, widthPixel=%s, heightPixel=%s)' % (
        center, screen - (abs/2.0) * etheta - (ord/2.0) * ephi,
        abs * etheta, ord * ephi,
        width, height)

def spot(center, see, color, angle_horizontal=0.3, angle_vertical=0.3, focale=1, spot_color=(0,0,0)):
    cs = see - center
    sc = -cs
    screen = center + (focale / cs.length()) * cs

    theta = Point(1, 0, 0).angle(Point(sc.x, sc.y, 0))
    if sc.y < 0:
        theta = 2*math.pi - theta
    phi = Point(sc.x, sc.y, 0).angle(sc)
    if sc.z < 0:
        phi = 2*math.pi - phi

    etheta = Point(-math.sin(theta), math.cos(theta), 0)
    ephi = Point(-math.sin(phi)*math.cos(theta), -math.sin(phi)*math.sin(theta), math.cos(phi))

    x = focale * math.tan(angle_horizontal)
    y = focale * math.tan(angle_vertical)
    topleft = screen + y * ephi - x * etheta
    topright = screen + y * ephi + x * etheta
    bottomleft = screen - y * ephi - x * etheta
    bottomright = screen - y * ephi + x * etheta

    lines = []
    lines.append('Triangle(p1=%s, p2=%s, p3=%s, k_diffuse=%s)' % (center, topleft, topright, spot_color))
    lines.append('Triangle(p1=%s, p2=%s, p3=%s, k_diffuse=%s)' % (center, topright, bottomright, spot_color))
    lines.append('Triangle(p1=%s, p2=%s, p3=%s, k_diffuse=%s)' % (center, bottomright, bottomleft, spot_color))
    lines.append('Triangle(p1=%s, p2=%s, p3=%s, k_diffuse=%s)' % (center, bottomleft, topleft, spot_color))
    lines.append('Light(pos=%s, intensity=%s)' % (center + 0.001 / cs.length() * cs, color))
    return lines
