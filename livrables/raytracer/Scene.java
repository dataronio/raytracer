package raytracer;

import java.util.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.lang.RuntimeException;

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

    public List<BasicObject> getObjects()
    {
        return objects;
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

                if(d > 0.000001 && (d < best_distance || best_object == null))
                {
                    best_distance = d;
                    best_object = object;
                }
            }
            catch(DontIntersectException ex)
            {
            }
        }

        if(best_object == null || depth > 3)
            return new double[]{0, 0, 0}; // black, if the ray goes to the infinite

        try
        {
            return best_object.computeColor(ray, this, depth);
        }
        catch(DontIntersectException ex)
        {
            throw new java.lang.RuntimeException();
        }
    }


    public BufferedImage generateImage()
    {
        int width  = camera.getWidthPixels();
        int height = camera.getHeightPixels();
        BufferedImage image = new BufferedImage(
            width, height, BufferedImage.TYPE_INT_RGB
        );

        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                double[] rgb = rayColor(camera.getRay(x, y), 0);
                image.setRGB(x, y, new Color((float)rgb[0], (float)rgb[1], (float)rgb[2]).getRGB());
            }
        }
        return image;
    }
}

