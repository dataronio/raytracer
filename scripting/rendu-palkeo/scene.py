from objects import *
import math
import collections
import random
import copy

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

    def jump(self, t, période, amplitude=20):
        self.translate(amplitude*abs(math.cos(t*math.pi/période))*Z)


class CubeDisco(ComplexObject):
    def __init__(self, center, size=30, rotateMul=1):
        self.objects = [
                Cube(center, {'p1': (size/2)*(-X-Y-Z), 'p2': size*(-X-Y+Z), 'p3': size*(-X+Y-Z), 'p4': size*(X-Y-Z)}, {'k_diffuse': (0,0,0), 'k_specular': 0, 'k_reflection': 1})
            ]
        self.rotate(Y,math.pi*rotateMul/4)
        self.rotate(X,math.pi*rotateMul/4)


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


class Confetti(ComplexObject):
    def __init__(self, center, size):
        self.objects = [
                Parallelogram(center, {'p1': (-X-Y)*(size/2), 'p2': X*size, 'p3': Y*size}, {'k_diffuse': (random.random(), random.random(), random.random())})
            ]
        self.rotate(X,random.random()*math.pi*6)
        self.rotate(Y,random.random()*math.pi*6)
        self.rotate(Z,random.random()*math.pi*6)
        self.vr = (random.uniform(-1,1), random.uniform(-1,1), random.uniform(-1,1))
        v = 200/10
        self.vd = (random.uniform(-0.5,0.5)*v, random.uniform(-0.5,0.5)*v, random.uniform(-1,-0.8)*v)

    def todelete(self):
        return self.center.z < -1 or self.center.x > 1001 or (self.center.z < 29 and self.center.x > 901)

    def fall(self, interval):
        self.rotate(X, self.vr[0] * interval)
        self.rotate(Y, self.vr[1] * interval)
        self.rotate(Z, self.vr[2] * interval)
        self.translate(X * self.vd[0] * interval)
        self.translate(Y * self.vd[1] * interval)
        self.translate(Z * self.vd[2] * interval)

class Confettis(ComplexObject):
    def __init__(self, center, size):
        self.gencenter = center
        self.size = size
        self.objects = []

    def create(self):
        self.objects.append(Confetti(self.gencenter, self.size))

    def delete(self):
        self.objects = [i for i in self.objects if not i.todelete()]
    
    def fall(self, interval):
        for o in self.objects:
            o.fall(interval)


class Spectateur(ComplexObject):
    XMIN = 550
    XMAX = 870
    YMIN = -220
    YMAX = 220
    RAYON = 10

    def __init__(self, scène, dephasage=None):
        couleur = (random.random(), random.random(), random.random())

        if dephasage is None:
            self.dephasage = random.gauss(0,0.05)
        else:
            self.dephasage = dephasage

        while True:
            x = random.uniform(self.XMIN, self.XMAX)
            y = random.uniform(self.YMIN, self.YMAX)
            i = 0
            ok = True
            for s in scène.iter("spectateur_"):
                if math.sqrt((s.center.x - x)**2 + (s.center.y - y)**2) <= 2*self.RAYON:
                    ok = False
                    break
            if ok:
                break

        center = Point(x, y, self.RAYON)

        self.objects = [
                Sphere(center, {'center': NULLP}, {'radius': self.RAYON, 'k_diffuse': couleur})
            ]

    def jump(self, t, période, amplitude=None):
        if amplitude is None:
            amplitude = self.RAYON*2
        self.translate(amplitude*abs(math.cos((t+self.dephasage)*math.pi/période))*Z)


class PalkeoScene(Scene):
    CAMERA_ZOOM = 1
    NB_SPECTATEURS = 100
    TOTAL_TIME = 180
    FPS = 24
    PERIODE = 180/385
    HEIGHT = 768
    TIME_START = 0
    TIME_STOP = 182

    def iter(self, basename):
        i = 0
        while True:
            try:
                yield self.objects['%s%s' % (basename, i)]
            except KeyError:
                break
            i += 1

    def __init__(self):
        self.objects = collections.OrderedDict()

        couleur_scène = (0.2,0.2,0.2)
        couleur_sol = (0.4,0.3,0.3)

        self.objects['lumière ambiante'] = AmbientLights(0.03,0.03,0.03)

        self.objects['sol'] = Plane(Point(0,0,0), {'p1': NULLP, 'p2': X, 'p3': Y}, {'k_diffuse': couleur_sol, 'brightness': 1, 'k_specular': 0})

        self.objects['caméra'] = Camera(Point(380,0,120), {'eye': NULLP, 'abscissa': Vector(0,4/3,0), 'ordinate': Vector(0,0,1), 'origin': Point(self.CAMERA_ZOOM,-2/3,-1/2)}, {'widthPixel': 4*self.HEIGHT//3, 'heightPixel': self.HEIGHT})
        #self.objects['caméra'] = Camera(Point(800,200,120), {'eye': NULLP, 'abscissa': Vector(0,4/3,0), 'ordinate': Vector(0,0,1), 'origin': Point(self.CAMERA_ZOOM,-2/3,-1/2)}, {'widthPixel': 800, 'heightPixel': 600})

        self.objects['caméra'].rotate(Y, math.pi/30)
        #self.objects['caméra'].rotate(Z, -1)

        self.objects['plateau_scène'] = Parallelogram(Point(900,-200,30), {'p1': NULLP, 'p2': 100*X, 'p3': 400*Y}, {'k_diffuse': couleur_scène})
        self.objects['plan_derrière_scène'] = Plane(Point(1000,0,0), {'p1': NULLP, 'p2': Z, 'p3': Y}, {'k_diffuse': couleur_scène})
        self.objects['avant_bas_scène'] = Parallelogram(Point(900,-200,0), {'p1': NULLP, 'p2': 30*Z, 'p3': 400*Y}, {'k_diffuse': couleur_scène})
        self.objects['plafond_scène'] = Parallelogram(Point(899,-200,200), {'p1': NULLP, 'p2': 101*X, 'p3': 400*Y}, {'k_diffuse': couleur_scène})
        self.objects['bord_plafond_scène'] = Parallelogram(Point(899,-200,200), {'p1': NULLP, 'p2': -20*Z, 'p3': 400*Y}, {'k_diffuse': couleur_scène})

        self.objects['image'] = ImageRect(Point(999.999,-88,60), {'p1': NULLP, 'p2': 176*Y, 'p3': 110*Z}, {'image': '"/home/kauguste/deadmau5.jpg"'})

        self.objects['mickey'] = Mickey(Point(940,0,30))

        self.objects['table'] = Cube(Point(920,-15,30), {'p1': NULLP, 'p2': 30*Y, 'p3': 10*Z, 'p4': 10*X}, {'k_diffuse': couleur_scène})

        self.objects['cube_disco_0'] = CubeDisco(Point(950,-100,120))
        self.objects['cube_disco_1'] = CubeDisco(Point(950,100,120), rotateMul=-1)

        self.objects['faisceau_public_0'] = SpotVertical(Point(898.99,-100,195))
        self.objects['faisceau_public_1'] = SpotVertical(Point(898.99,100,195))
        self.objects['faisceau_public_0'].setIntensity((0.8,0.5,0.3))
        self.objects['faisceau_public_1'].setIntensity((0.3,0.5,0.5))

        self.objects['spot_public_0'] = Light(Point(898.99,-180,195), {'pos': NULLP}, {'intensity': (0,0,0)})
        self.objects['spot_public_2'] = Light(Point(898.99,0,195), {'pos': NULLP}, {'intensity': (0,0,0)})
        self.objects['spot_public_1'] = Light(Point(898.99,180,195), {'pos': NULLP}, {'intensity': (0,0,0)})

        self.objects['spot_scène_0'] = Light(Point(899.01,-180,195), {'pos': NULLP}, {'intensity': (0,0,0)})
        self.objects['spot_scène_1'] = Light(Point(899.01,-60,195), {'pos': NULLP}, {'intensity': (0,0,0)})
        self.objects['spot_scène_2'] = Light(Point(899.01,60,195), {'pos': NULLP}, {'intensity': (0,0,0)})
        self.objects['spot_scène_3'] = Light(Point(899.01,180,195), {'pos': NULLP}, {'intensity': (0,0,0)})

        self.objects['spot_haut_1'] = Light(Point(0,0,900), {'pos': NULLP}, {'intensity': (0.05,0.05,0.05)})
        self.objects['spot_haut_2'] = Light(Point(900,0,1500), {'pos': NULLP}, {'intensity': (0.05,0.05,0.05)})

        self.objects['confettis'] = Confettis(Point(950,0,200), 1.6)

        for i in range(self.NB_SPECTATEURS):
            self.objects['spectateur_%s' % i] = Spectateur(self)


    def frame(self, t):
        coef_spots_scène = max(min((t-4)/8, 1), 0)
        coef_spots_public = max(min((t-12)/40, 1), 0)

        for s in self.iter('spot_public_'):
            c_avant = abs(math.sin((t-1/self.FPS)*0.5*math.pi/self.PERIODE))
            c_apres = abs(math.sin((t+1/self.FPS)*0.5*math.pi/self.PERIODE))
            c = abs(math.sin(t*0.5*math.pi/self.PERIODE))
            if not hasattr(s, 'color') or c_avant > c < c_apres:
                s.color = (random.random(), random.random(), random.random())
            s.setIntensity(tuple(i*c*coef_spots_public for i in s.color))

        for i, s in enumerate(self.iter('spot_scène_')):
            phi = math.pi*i/4
            c_avant = abs(math.cos(phi + (t-1/self.FPS)*0.5*math.pi/self.PERIODE))
            c_apres = abs(math.cos(phi + (t+1/self.FPS)*0.5*math.pi/self.PERIODE))
            c = abs(math.cos(phi + t*0.5*math.pi/self.PERIODE))
            if not hasattr(s, 'color') or c_avant > c < c_apres:
                s.color = (random.random(), random.random(), random.random())
            s.setIntensity(tuple(i*c*coef_spots_scène for i in s.color))

        if 39 < t < 69 or 100 < t < 132 or t > 163:
            self.objects['confettis'].create()
        self.objects['confettis'].fall(1/self.FPS)
        self.objects['confettis'].delete()

        s = copy.deepcopy(self)

        for i in s.iter('spectateur_'):
            i.jump(t, self.PERIODE)


        s.objects['faisceau_public_0'].rotate(Z,math.pi*math.sin(t*math.pi/(self.PERIODE*3))/2)
        s.objects['faisceau_public_1'].rotate(Z,-math.pi*math.sin(t*math.pi/(self.PERIODE*3))/2)
        if t < 12:
            del s.objects['faisceau_public_0']
            del s.objects['faisceau_public_1']

        s.objects['cube_disco_0'].rotate(Z,t*math.pi/5)
        s.objects['cube_disco_1'].rotate(Z,-t*math.pi/5)

        if t > 26:
            s.objects['mickey'].rotate(Z, math.pi*math.cos(t*math.pi/self.PERIODE)/4)
        if t > 88:
            s.objects['mickey'].jump(t, self.PERIODE)

        if t > 161:
            s.objects['caméra'].translate(X*((t-161)*370/11))

        return s


    def render(self, t_from = TIME_START, t_to = TIME_STOP):
        t = 0
        i = 0
        while t <= t_to:
            f = self.frame(t)
            if t >= t_from:
                f.write('/tmp/rendu/%s%s.scn' % ('0'*(5-len(str(i))),i))
            t += 1/self.FPS
            i += 1



if __name__ == '__main__':
    s = PalkeoScene()
    s.render()

