
import java.util.*;
import javax.vecmath.*;

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
        origin = origin_.clone();
        direction = direction_.clone();
    };

    public Point3d getOrigin()
    {
      return origin.clone();
    }

    public Vector3d getDirection()
    {
        return direction.clone();
    }

}
