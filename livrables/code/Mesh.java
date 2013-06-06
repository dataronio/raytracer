
import java.util.*;


/**
 * Class Mesh
 * Objet constitué d'un ensemble de triangles.
 * Capable de représenter des choses complexes.
 */
public class Mesh extends Object
{
    private Set<Triangle> triangles;
  
    public Mesh (Texture texture_)
    {
        super(texture_);
    };

    public Mesh (Texture texture_, Set<Triangle> t)
    {
        super(texture_);
        setTriangles(t);
    }
  
    /**
     * Get the list of triangles
     */
    public Set<Triangle> getTriangles ()
    {
        return triangles;
    }
    
    /**
     * Set the list of triangles
     */
    public void setTriangles(Set<Triangle> t)
    {
        triangles = t;
    }

    /**
     * @return       java.awt.Color
     * @param        ray Le point de départ indique l'intersection.
     * @param        scene
     * @param        depth La profondeur de l'appel récursif.
     */
    public java.awt.Color computeColor( Ray ray, Scene scene, int depth )
    {
        return firstTriangle(ray).computeColor(ray, scene, depth);
    }

    public Ray normal(Ray ray)
    {
        return firstTriangle(ray).normal(ray);
    }

    public double distance(Ray ray)
    {
        return firstTriangle(ray).distance(ray);
    }

    /**
     * Renvoie le premier triangle qui intersecte
     */
    private Triangle firstTriangle(Ray ray)
    {
        // Trouver le triangle qui a triangle.distance(ray) le plus petit.
    }

}

