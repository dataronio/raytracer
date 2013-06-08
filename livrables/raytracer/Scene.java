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
    private final int MAX_DEPTH = 10;

    /** Liste des objets */
    private List<BasicObject> objects;

    /** Caméra */
    private Camera camera;

    /** Lumière Ambiante */
    private double[] ambientLight;

    /** Liste des lumières */
    private List<Light> lights;
  
    /**
     * Constructeur par défaut
     *
     * @param objects_ La liste des objects
     * @param lights_ La liste des lumières
     * @param camera_ La caméra
     * @param ambientLight_ La lumière ambiante
     */
    public Scene (List<BasicObject> objects_, List<Light> lights_, Camera camera_, double[] ambientLight_)
    {
        objects = objects_;
        camera = camera_;
        ambientLight = ambientLight_;
        lights = lights_;
    }

    /** Retourne la liste des lumières
     * @return La liste des lumières
     */
    public List<Light> getLights()
    {
        return lights;
    }

    /** Retourne la liste des objets
     * @return La liste des objets
     */
    public List<BasicObject> getObjects()
    {
        return objects;
    }

    /**
     * Modifie la lumière ambiante
     * @param newVar La nouvelle valeur de la lumière ambiante
     */
    public void setAmbientLight(double[] newVar)
    {
        ambientLight = newVar;
    }

    /**
     * Retourne la lumière ambiante
     * @return La lumière ambiante
     */
    public double getAmbientLight(int i)
    {
        return ambientLight[i];
    }

    /**
     * Calcul la couleur du premier point qui intersecte le rayon
     *
     * @param ray Le rayon
     * @param depth La profondeur actuelle de la projection
     *
     * @return La couleur
     */
    public double[] rayColor(Ray ray, int depth)
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

        if(best_object == null || depth > MAX_DEPTH)
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

    /**
     * Génère le rendu de la scène
     * @return Le rendu de la scène
     */
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
