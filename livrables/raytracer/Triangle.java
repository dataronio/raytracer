package raytracer;

import java.util.*;

/**
 * Représente un triangle par ses trois sommets.
 */
public class Triangle extends Object {
    /** Points du triangle */
    private Point3d P0;
    private Point3d P1;
    private Point3d P2;

    /** Conservé pour éviter de refaire les calculs */
    private Ray lastRay;
    /** Conservé pour éviter de refaire les calculs */
    protected Vector3d lastSol;
    /** Conservé pour éviter de refaire les calculs */
    private Vector3d lastN;
    /** Conservé pour éviter de refaire les calculs */
    private Point3d lastPoint;
  
    /**
     * Construit un Triangle. Les paramètres ne sont pas copiés.
     */
    public Triangle(Texture texture, Point3d P0_, Point3d P1_, Point3d P2_) {
        super(texture);
        P0 = P0_;
        P1 = P1_;
        P2 = P2_;
        lastSol = null;
    }

    @Override
    public double distance(Ray ray) throws DontIntersectException {
        update(ray);

        return lastSol.z;
    }

    @Override
    public Ray normal(Ray ray) throws DontIntersectException {
        update(ray);

        return new Ray(lastPoint, lastN);
    }

    /**
     * Met à jour l'état interne du triangle si le rayon a changé depuis le
     * dernier appel.
     */
    private void update(Ray ray) throws DontIntersectException {
        if(lastRay != ray) {
            Vector3d P01 = new Vector3d(P1, P0);
            Vector3d P02 = new Vector3d(P2, P0);
            Vector3d d = ray.getDirection().scale(-1.);
            Vector3d b = new Vector3d(ray.getOrigin(), P0);

            Matrix m = new Matrix(P01, P02, d);

            lastRay = ray;
            lastSol = m.solve(b);
            if(checkDontIntersect()) {
                lastSol = null;
                throw new DontIntersectException();
            }

            lastN = P01.cross(P02);

            // oriente la normale.
            if(lastN.dot(ray.getDirection()) > 0)
                lastN.scale(-1.d);

            lastPoint = ray.getOrigin()
                           .add(ray.getDirection().scale(lastSol.z));
        }
        else if(lastSol == null) {
            throw new DontIntersectException();
        }
    }

    /**
     * Vérifie si le dernier rayon intersecte cet objet.
     */
    protected boolean checkDontIntersect() {
        return lastSol == null
        ||  lastSol.x < 0. || lastSol.x > 1.
             || lastSol.y < 0. || lastSol.y > 1-lastSol.x;
    }

    /**
     * Renvoie <tt>true</tt> si le dernier rayon intersecte cet objet.
     */
    public boolean isEntering(Ray ray) throws DontIntersectException {
        if(checkDontIntersect()) {
            throw new DontIntersectException();
        }
        else {
            return true;
        }
    }
}

