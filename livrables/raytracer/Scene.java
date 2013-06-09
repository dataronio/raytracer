package raytracer;

import java.util.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.lang.RuntimeException;

/**
 * Représente une scène, c'est à dire la caméra, les lumières et les objets
 * que contient la scène.
 */
public class Scene
{
    /** Profondeur maximale du calcul */
    private final int MAX_DEPTH = 10;

    /** Liste des objets */
    private List<BasicObject> objects;

    /** Caméra */
    private Camera camera;

    /** Lumière ambiante */
    private double[] ambientLight;

    /** Liste des lumières */
    private List<Light> lights;
  
    /**
     * Constructeur (les paramètres ne sont pas copiés).
     *
     * @param objects La liste des objects
     * @param lights La liste des lumières
     * @param camera La caméra
     * @param ambientLight La lumière ambiante
     */
    public Scene (List<BasicObject> objects, List<Light> lights, Camera camera, double[] ambientLight)
    {
        this.objects = objects;
        this.camera = camera;
        this.ambientLight = ambientLight;
        this.lights = lights;
    }

    /**
     * Retourne la liste des lumières.
     * @return La liste des lumières (non copiée).
     */
    public List<Light> getLights()
    {
        return lights;
    }

    /**
     * Retourne la liste des objets.
     * @return La liste des objets (non copiée).
     */
    public List<BasicObject> getObjects()
    {
        return objects;
    }

    /**
     * Modifie la lumière ambiante.
     * @param newVar La nouvelle valeur de la lumière ambiante.
     */
    public void setAmbientLight(double[] newVar)
    {
        ambientLight = newVar;
    }

    /**
     * Retourne une composante de la lumière ambiante
     * @param i La composante voulue
     * @return La composante de la lumière ambiante voulue
     */
    public double getAmbientLight(int i)
    {
        return ambientLight[i];
    }

    /**
     * Calcule la couleur du premier point qui intersecte le rayon.
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
     * Génère le rendu de la scène.
     * @return Une image représentant la scène
     */
    public RenderedImage generateImage()
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
