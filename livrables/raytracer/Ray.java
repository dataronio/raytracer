package raytracer;

import java.util.*;

/**
 * Classe représentant un rayon optique par une origine et une direction.
 */
public class Ray {
    /** L'origine du rayon */
    private Point3d origin;

    /** La direction du rayon */
    private Vector3d direction;
  
    /**
     * Construit un nouveau rayon.
     * @param origin l'origine du rayon (copiée).
     * @param direction la direction du rayon (copiée).
     */
    public Ray(Point3d origin, Vector3d direction)
    {
        this.origin = new Point3d(origin);
        this.direction = new Vector3d(direction).normalize();
    };

    /**
     * Retourne une copie de l'origine de ce rayon.
     */
    public Point3d getOrigin() {
      return new Point3d(origin);
    }

    /**
     * Retourne une copie de la direction de ce rayon.
     */
    public Vector3d getDirection() {
        return new Vector3d(direction);
    }
}
