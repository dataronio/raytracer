
import java.util.*;
import java.awt.Color;


/**
 * Class Scene
 * Représente une scène, c'est à dire la caméra et les objets que contient la
 * scène.
 */
public class Scene
{
    private List<BasicObject> objects;
    private Camera camera;
    private double[] ambientLight;
    private List<Light> lights;
  
    public Scene (List<BasicObject> objects_, List<Light> lights_, Camera camera_, double[] ambientLight_)
    {
        objects = objects_;
        camera = camera_;
        ambientLight = ambientLight_;
        lights = lights_;
    };
  
    public List<Light> getLights()
    {
        return lights;
    }

    /**
     * Set the value of ambientLight
     * @param newVar the new value of ambientLight
     */
    public void setAmbientLight ( double[] newVar )
    {
        ambientLight = newVar;
    }

    /**
     * Get the value of ambientLight
     * @return the value of ambientLight
     */
    public double getAmbientLight (int i)
    {
        return ambientLight[i];
    }

    /**
     * @return       java.awt.Color
     * @param        ray
     * @param        depth
     */
    public java.awt.Color rayColor( Ray ray, int depth )
    {
        // on trouve l'objet pour lequel la méthode distance() est la plus faible
        // on appelle la méthode computeColor dessus.
        // voilà :)
        return null;
    }


    public void generateImage()
    {
        for(int x = 0; x < camera.getWidthPixels(); x++)
        {
            for(int y = 0; y < camera.getHeightPixels(); y++)
            {
                //image[x][y] = rayColor(camera.getRay(x, y), 0);
            }
        }
    }

}
