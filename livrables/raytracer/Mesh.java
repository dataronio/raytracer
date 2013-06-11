package raytracer;

import java.util.*;

/**
 * Object constitué d'un ensemble d'objets.
 * Capable de représenter des choses complexes.
 */
public class Mesh extends BasicObject {
    /** Liste des objects */
    private Set<BasicObject> objects;

    /** Conservé afin d'éviter de refaire les calculs à chaque fois. */
    private Ray lastRay;
    /** Conservé afin d'éviter de refaire les calculs à chaque fois. */
    private BasicObject lastObject;

    /**
     * Construit un Mesh qui n'est composé d'un ensemble vide mais non-null
     * d'objects.
     */
    public Mesh() {
        this(new HashSet<BasicObject>());
    };

    /**
     * Construit un Mesh à partir de l'ensemble de objects donné.
     */
    public Mesh(Set<BasicObject> t) {
        setObjects(t);
    }
  
    /**
     * @return L'ensemble d'objets (non copié).
     */
    public Set<BasicObject> getObjects() {
        return objects;
    }

    /** Modifie la liste des objects.
     * @param t Le nouvel ensemble de objects (non copié).
     */
    public void setObjects(Set<BasicObject> t) {
        lastRay = null;
        lastObject = null;
        objects = t;
    }

    @Override
    public double[] computeColor(Ray ray, Scene scene, int depth)
    throws DontIntersectException {
        return firstBasicObject(ray).computeColor(ray, scene, depth);
    }

    @Override
    public double distance(Ray ray)
    throws DontIntersectException {
        return firstBasicObject(ray).distance(ray);
    }

    /**
     * Renvoie le premier objet qui intersecte le rayon.
     */
    private BasicObject firstBasicObject(Ray ray)
    throws DontIntersectException {
        if(ray == lastRay) {
            if(lastObject == null) {
                throw new DontIntersectException();
            }

            return lastObject;
        }
        else {
            // Trouver l'objet qui a triangle.distance(ray) le plus petit.

            lastRay = ray;
            lastObject = null;

            double minDistance = Double.MAX_VALUE;
            for(BasicObject t : objects) {
                try {
                    double c = t.distance(ray);

                    if(minDistance > c && c > 0.0001) {
                        minDistance = c;
                        lastObject = t;
                    }
                }
                catch (DontIntersectException e) {
                    continue;
                }
            }

            if(lastObject == null) {
                throw new DontIntersectException();
            }

            return lastObject;
        }
    }
}

