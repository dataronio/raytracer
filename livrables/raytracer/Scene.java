package raytracer;


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
    public double[] rayColor( Ray ray, int depth )
    {
        BasicObject best_object = null;
        double best_distance = 0;

        for(BasicObject object : objects)
        {
            try
            {
                double d = object.distance(ray);

                if(d < best_distance || best_object == null)
                {
                    best_distance = d;
                    best_object = object;
                }
            }
            catch(DontIntersectException ex)
            {
            }
        }

        if(best_object == null)
            return {0, 0, 0} // black, if the ray goes to the infinite

        return best_object.computeColor(ray, this, depth);
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
