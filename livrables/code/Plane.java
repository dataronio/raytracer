import javax.vecmath.*;


/**
 * Représente un plan.
 */
public class Plane extends Object
{
    private Point3d p1;
    private Point3d p2;
    private Point3d p3;
  
    public Plane (Point3d p1_, Point3d p2_, Point3d p3_)
    {
        p1 = p1_;
        p2 = p2_;
        p3 = p3_;
    };

    public double distance(Ray ray)
    {
        // distance au projeté de l'origine du rayon sur le plan.
    }

    public Vector3d normal(Ray ray)
    {
        // vecteur normal au plan.
    }

}
