package raytracer;

import java.util.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.lang.RuntimeException;

/**
 * Représente une scène, c'est à dire la caméra, les lumières et les objets
 * que contient la scène.
 */
public class Scene
{
    /** Profondeur maximale du calcul */
    private final int MAX_DEPTH = 10;

    private List<BasicObject> objects;
    private List<Camera> cameras;
    private double[] ambientLight;
    private List<Light> lights;
  
    /**
     * Constructeur (les paramètres ne sont pas copiés).
     */
    public Scene (
        List<BasicObject> objects_, List<Light> lights_,
        List<Camera> cameras_, double[] ambientLight_
    ) {
        objects = objects_;
        cameras = cameras_;
        ambientLight = ambientLight_;
        lights = lights_;
    }

    /**
     * Retourne la liste des lumières.
     * @return La liste des lumières (non copiée).
     */
    public List<Light> getLights() {
        return lights;
    }

    /**
     * Retourne la liste des objets.
     * @return La liste des objets (non copiée).
     */
    public List<BasicObject> getObjects() {
        return objects;
    }

    /**
     * Modifie la lumière ambiante.
     * @param newVar La nouvelle valeur de la lumière ambiante.
     */
    public void setAmbientLight(double[] newVar) {
        ambientLight = newVar;
    }

    /**
     * Retourne une composante de la lumière ambiante.
     * @param i La composante voulue (0=rouge, 1=vert, 2=bleu).
     * @return La composante de la lumière ambiante voulue.
     */
    public double getAmbientLight(int i) {
        return ambientLight[i];
    }

    /**
     * Calcule la couleur du premier point qui intersecte le rayon.
     *
     * @param depth La profondeur actuelle de la projection
     * @param ignore_object Un éventuel objet que l'on va ignorer.
     * Peut être nul.
     *
     * @return La couleur
     */
    public double[] rayColor(Ray ray, int depth, BasicObject ignore_object) {
        if(depth > MAX_DEPTH)
        {
            System.out.println("Warning: maximum recursion depth exceeded.");
            return new double[]{0, 0, 0};
        }

        BasicObject best_object = null;
        double best_distance = Double.MAX_VALUE;

        // détermine le premier objet que croise le rayon.
        for(BasicObject object : objects) {
            if(object == ignore_object) {
                continue;
            }

            try {
                double d = object.distance(ray);

                if(d > 0 && d < best_distance) {
                    best_distance = d;
                    best_object = object;
                }
            }
            catch(DontIntersectException ex) {
                continue;
            }
        }

        if(best_object == null) {
            // black, if the ray goes to the infinite
            return new double[]{0, 0, 0};
        }

        try {
            return best_object.computeColor(ray, this, depth);
        }
        catch(DontIntersectException ex) {
            // ne devrait pas arriver.
            throw new java.lang.RuntimeException();
        }
    }

    /**
     * Génère le rendu de la scène pour chaque caméra.
     * @return La liste des images générée.
     */
    public List<BufferedImage> generateImages() {
        List<BufferedImage> list = new ArrayList<BufferedImage>();
        for(Camera camera : cameras) {
            list.add(generateImage(camera));
        }
        return list;
    }

    /**
     * Génère le rendu de la scène pour la caméra donnée.
     * @return Une image représentant la scène
     */
    private BufferedImage generateImage(Camera camera)
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
                double[] rgb = rayColor(camera.getRay(x, y), 0, null);

                if(rgb[0] > 1)
                {
                    System.out.println("Warning: red saturation at (" + x + "," + y + ").");
                    rgb[0] = 1;
                }
                if(rgb[1] > 1)
                {
                    System.out.println("Warning: green saturation at (" + x + "," + y + ").");
                    rgb[1] = 1;
                }
                if(rgb[2] > 1)
                {
                    System.out.println("Warning: blue saturation at (" + x + "," + y + ").");
                    rgb[2] = 1;
                }

                image.setRGB(x, height - y - 1, new Color((float)rgb[0], (float)rgb[1], (float)rgb[2]).getRGB());
            }
        }
        return image;
    }
}
