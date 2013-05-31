
import java.util.*;
import javax.vecmath.*;


/**
 * Class Sphere
 */
public class Sphere extends Object
{
    private Point3d center;
    private int radius;
  
    public Sphere (Point3d center_, int radius_)
    {
        center = center_;
        radius = radius_;
    };

    public double distance(Ray ray)
    {
        // pas si évident que ça en a l'air....
    }

    public Vector3d normal(Ray ray)
    {
        // ça par contre, ça peut se faire plutôt facilement si on a fait distance.
    }
  
}
