from objects import *
import math
import collections

class Mickey(ComplexObject):
    couleur_spheres = (0.7,0.2,0.4)
    couleur_yeux = (1, 1, 1)

    def __init__(self, center):
        center = center + 10*Z
        self.objects = [
                   Sphere(center, {'center': NULLP}, {'radius': 10, 'k_diffuse': self.couleur_spheres}),
                   Sphere(center, {'center': 11*Z + 11*Y}, {'radius': 6, 'k_diffuse': self.couleur_spheres}),
                   Sphere(center, {'center': 11*Z - 11*Y}, {'radius': 6, 'k_diffuse': self.couleur_spheres}),
                   Sphere(center, {'center': 4*Z - 8*X + 4*Y}, {'radius': 1, 'k_diffuse': self.couleur_yeux}),
                   Sphere(center, {'center': 4*Z - 8*X - 4*Y}, {'radius': 1, 'k_diffuse': self.couleur_yeux}),
                   Triangle(center, {'p1': - 3.2*Z - 5*Y - 9.5*X, 'p2': -3.2*Z + 5*Y - 9.5*X, 'p3': - 8.8*X - 5*Z}, {'k_diffuse': self.couleur_yeux}), 
                  ]


class SpotVertical(ComplexObject):
    couleur = (0.1,0.1,0.1)

    def __init__(self, center, largeur_faisceau=0.1):
        self.objects = [
                Light(center, {'pos': NULLP}, {'intensity': (0,0,0)}),
                Parallelogram(center, {'p1': Z+X+Y*largeur_faisceau, 'p2': -Z+Y*largeur_faisceau, 'p3': -X+Y*largeur_faisceau}, {'k_diffuse': self.couleur}),
                Parallelogram(center, {'p1': Z+X-Y*largeur_faisceau, 'p2': -Z-Y*largeur_faisceau, 'p3': -X-Y*largeur_faisceau}, {'k_diffuse': self.couleur}),
            ]
    
    def setIntensity(self, intensity):
        self.objects[0].setIntensity(intensity)


class PalkeoScene(Scene):
    CAMERA_ZOOM = 1

    def __init__(self):
        self.objects = collections.OrderedDict()

        couleur_scène = (0.2,0.2,0.2)
        couleur_sol = (0.5,0.5,0.5)

        self.objects['lumière ambiante'] = AmbientLights(0.03,0.03,0.03)

        self.objects['sol'] = Plane(Point(0,0,0), {'p1': NULLP, 'p2': X, 'p3': Y}, {'k_diffuse': couleur_sol})

        self.objects['caméra'] = Camera(Point(800,0,40), {'eye': NULLP, 'abscissa': Vector(0,4/3,0), 'ordinate': Vector(0,0,1), 'origin': Point(self.CAMERA_ZOOM,-2/3,-1/2)}, {'widthPixel': 800, 'heightPixel': 600})
        self.objects['caméra'].rotate(Y, math.pi/30)
        self.objects['caméra'].translate(80*Z-250*X)

        #self.objects['lumière'] = Light(Point(00,00,800), {'pos': NULLP}, {'intensity': (0.5,0.5,0.5)})


        self.objects['plateau_scène'] = Parallelogram(Point(900,-200,30), {'p1': NULLP, 'p2': 100*X, 'p3': 400*Y}, {'k_diffuse': couleur_scène})
        self.objects['plan_derrière_scène'] = Plane(Point(1000,0,0), {'p1': NULLP, 'p2': Z, 'p3': Y}, {'k_diffuse': couleur_scène})
        self.objects['avant_bas_scène'] = Parallelogram(Point(900,-200,0), {'p1': NULLP, 'p2': 30*Z, 'p3': 400*Y}, {'k_diffuse': couleur_scène})
        self.objects['plafond_scène'] = Parallelogram(Point(899,-200,200), {'p1': NULLP, 'p2': 101*X, 'p3': 400*Y}, {'k_diffuse': couleur_scène})
        self.objects['bord_plafond_scène'] = Parallelogram(Point(899,-200,200), {'p1': NULLP, 'p2': -20*Z, 'p3': 400*Y}, {'k_diffuse': couleur_scène})

        self.objects['mickey'] = Mickey(Point(930,0,30))

        self.objects['spot_vertical_public'] = SpotVertical(Point(898.99,0,195))

        self.objects['spot_vertical_public'].setIntensity((0.3,0.8,0.3))
        self.objects['spot_vertical_public'].rotate(Z,0.5)


if __name__ == '__main__':
    s = PalkeoScene()
    s.write('/tmp/test.scn')
