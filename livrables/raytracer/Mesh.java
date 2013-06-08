package raytracer;

import java.util.*;

/**
 * Objet constitué d'un ensemble de triangles.
 * Capable de représenter des choses complexes.
 */
public class Mesh extends BasicObject {
    private Set<Triangle> triangles;

    // le dernier triangle intersecté est enregistré avec le rayon associé afin
    // d'éviter de le recalculer à chaque fois
    private Ray lastRay;
    private Triangle lastTriangle;
  
    public Mesh() {
        this(new HashSet<Triangle>());
    };

    public Mesh(Set<Triangle> t) {
        setTriangles(t);
    }
  
    /**
     * @return L'ensemble de triangle (non copié).
     */
    public Set<Triangle> getTriangles() {
        return triangles;
    }

    public void setTriangles(Set<Triangle> t) {
        lastRay = null;
        lastTriangle = null;
        triangles = t;
    }

    @Override
    public double[] computeColor(Ray ray, Scene scene, int depth)
    throws DontIntersectException {
        return firstTriangle(ray).computeColor(ray, scene, depth);
    }

    /**
     * Renvoie la distance au triangle le plus proche.
     */
    @Override
    public double distance(Ray ray)
    throws DontIntersectException {
        return firstTriangle(ray).distance(ray);
    }

    /**
     * Renvoie le premier triangle qui intersecte le rayon.
     */
    private Triangle firstTriangle(Ray ray)
    throws DontIntersectException {
        if(ray == lastRay) {
            if(lastTriangle == null) {
                throw new DontIntersectException();
            }

            return lastTriangle;
        }
        else {
            // Trouver le triangle qui a triangle.distance(ray) le plus petit.

            lastRay = ray;
            lastTriangle = null;

            double minDistance = Double.MAX_VALUE;
            for(Triangle t : triangles) {
                try {
                    double c = t.distance(ray);

                    if(minDistance > c && c > 0.0001) {
                        minDistance = c;
                        lastTriangle = t;
                    }
                }
                catch (DontIntersectException e) {
                    continue;
                }
            }

            if(lastTriangle == null) {
                throw new DontIntersectException();
            }

            return lastTriangle;
        }
    }
}

