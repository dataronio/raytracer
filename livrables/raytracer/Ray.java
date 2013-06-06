package raytracer;

import java.util.*;

/**
 * Classe représentant un rayon optique par une origine et une direction.
 */
public class Ray {
    private Point3d origin;
    private Vector3d direction;
  
    /**
     * Construit un nouveau rayon.
     * @param origin_ l'origine du rayon (copiée).
     * @param direction_ la direction du rayon (copiée).
     */
    public Ray(Point3d origin_, Vector3d direction_)
    {
        origin = new Point3d(origin_);
        direction = new Vector3d(direction_);
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
