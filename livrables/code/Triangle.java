
import java.util.*;
import javax.vecmath.*;


/**
 * Class Triangle
 */
public class Triangle extends Object
{

    private Point3d p1;
    private Point3d p2;
    private Point3d p3;
  
    public Triangle (Point3d a1, Point3d a2, Point3d a3)
    {
        p1 = a1;
        p2 = a2;
        p3 = a3;
    };

    public Vector3d normal(Ray ray)
    {
        // calculer la normale
    }

    public double distance(Ray ray)
    {
        // calculer la distance à l'origine du rayon
    }
  
}
