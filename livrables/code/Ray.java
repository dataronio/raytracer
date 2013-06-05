
import java.util.*;


/**
 * Class Ray
 */
public class Ray {

    private Point3d origin;
    private Vector3d direction;
  
    /**
     * Construit un nouveau rayon.
     * @param origin_ l'origine du rayon.
     * @param direction_ la direction du rayon.
     */
    public Ray(Point3d origin_, Vector3d direction_)
    {
        origin = new Point3d(origin_);
        direction = new Vector3d(direction_);
    };

    public Point3d getOrigin()
    {
      return new Point3d(origin);
    }

    public Vector3d getDirection()
    {
        return new Vector3d(direction);
    }

}
