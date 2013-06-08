package raytracer;

import java.util.*;
import java.awt.Color;

/**
 * Objet simple, en un seul « bloc », qui contient donc des propriétés optiques
 * uniques et une géométrie simple.
 */
abstract public class Object extends BasicObject {

    /** Texture de l'objet */
    protected Texture texture;

    /**
     * Crée un Object avec la texture donnée, qui n'est pas copiée.
     * @param texture
     */
    public Object(Texture texture) {
        this.texture = texture;
    };

    /**
     * @return la texture de l'objet (non copiée).
     */
    public Texture getTexture() {
        return texture;
    }


    /**
     * Calcule la normale à la surface de l'objet au point d'intersection du
     * rayon avec l'objet.
     * @return Le vecteur normal unitaire
     */
    public abstract Ray normal(Ray ray) throws DontIntersectException;

    /**
     * Indique si le rayon rentre dans l'objet ou en sort
     * @param ray Le rayon
     */
    public abstract boolean isEntering(Ray ray) throws DontIntersectException;


    /**
     * @return Un tableau avec les 3 composantes de couleur
     * @param ray Le point de départ indique l'intersection
     * @param scene La scène
     * @param depth La profondeur de l'appel récursif
     */
    public double[] computeColor(Ray ray, Scene scene, int depth) throws DontIntersectException
    {
        Ray normal_ray = normal(ray);

        Ray reflected_ray = new Ray(normal_ray.getOrigin(), ray.getDirection().symmetry(normal_ray.getDirection()).scale(-1));

        // la couleur finale du rayon
        double[] E = {0, 0, 0};


        // composante ambiante

        for(int i = 0; i < 3; i++)
            E[i] = scene.getAmbientLight(i) * texture.k_diffuse[i];


        // réflection

        if(texture.k_reflection > 0)
        {
            double[] E2 = scene.rayColor(reflected_ray, depth + 1);

            for(int i = 0; i < 3; i++)
                E[i] = logAdd(E[i], E2[i] * texture.k_reflection);
        }


        // réfraction
        
        if(texture.k_refraction[0] > 0 || texture.k_refraction[1] > 0 || texture.k_refraction[2] > 0)
        {
            double coef = normal_ray.getDirection().dot(ray.getDirection());

            double refindex = texture.refractive_index;
            if(! isEntering(ray))
                refindex = 1 / refindex;

            coef += Math.sqrt(Math.pow(refindex, 2) + Math.pow(coef, 2) - 1);
           
            Ray refracted_ray = new Ray(normal_ray.getOrigin(), normal_ray.getDirection().scale(coef*-1).add(ray.getDirection()));

            double[] E2 = scene.rayColor(refracted_ray, depth + 1);

            for(int i = 0; i < 3; i++)
                E[i] = logAdd(E[i], E2[i] * texture.k_refraction[i]);
        }


        // éclairement

        for(Light light : scene.getLights())
        {
            Vector3d to_light = light.getPosition().sub(normal_ray.getOrigin());
            Ray intersect_to_light = new Ray(normal_ray.getOrigin(), to_light);

            
            // on regarde si il y a un objet qui masque la lumière

            boolean intersect = false;
            for(BasicObject object: scene.getObjects())
            {
                try
                {
                    double d = object.distance(intersect_to_light);
                    if(0.00001 < d && d < to_light.length())
                    {
                        intersect = true;
                        break;
                    }
                }
                catch(DontIntersectException ex)
                {
                }
            }
            if(intersect)
                continue;


            // composante diffuse

            double angle_normal_light = Math.cos(normal_ray.getDirection().angle(to_light));

            if(angle_normal_light > 0)
            {
                for(int i = 0; i < 3; i++)
                {
                    E[i] = logAdd(E[i], light.getIntensity(i) * angle_normal_light * texture.k_diffuse[i]);
                }
            }


            // composante spéculaire

            double angle_reflected_light = Math.cos(reflected_ray.getDirection().angle(to_light));

            if(angle_reflected_light > 0)
            {
                angle_reflected_light = Math.pow(angle_reflected_light, texture.brightness);

                for(int i = 0; i < 3; i++)
                {
                    E[i] = logAdd(E[i], light.getIntensity(i) * angle_reflected_light * texture.k_specular);
                }
            }
            
        }

        return E;
    }

    /** Addition de couleur
     * @param a
     * @param b
     */
    private double logAdd(double a, double b)
    {
        return 1 - (1 - b)*(1 - a);
    }
}
