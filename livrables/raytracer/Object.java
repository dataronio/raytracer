package raytracer;

import java.util.*;
import java.awt.Color;

/**
 * Objet simple, en un seul « bloc », qui contient donc des propriétés optiques
 * uniques et une géométrie simple.
 */
abstract public class Object extends BasicObject {

    protected Texture texture;

    /**
     * Crée un Object avec la texture donnée, qui n'est pas copiée.
     */
    public Object(Texture texture_) {
        texture = texture_;
    };

    /**
     * @return la texture de cet objet, pas une copie.
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * @return       Un tableau avec les 3 composantes de couleur.
     * @param        ray Le point de départ indique l'intersection.
     * @param        scene
     * @param        depth La profondeur de l'appel récursif.
     */
    public double[] computeColor( Ray ray, Scene scene, int depth ) throws DontIntersectException
    {
        Ray normal_ray = normal(ray);

        double[] E = {0, 0, 0};

        for(int i = 0; i < 3; i++)
            E[i] = scene.getAmbientLight(i)*texture.absorbance[i];

        Ray reflected_ray = new Ray(normal_ray.getOrigin(), ray.getDirection().symmetry(normal_ray.getDirection()).scale(-1));

        // réflection
        if(texture.refractance > 0)
        {
            double[] E2 = scene.rayColor(reflected_ray, depth + 1);

            for(int i = 0; i < 3; i++)
                E[i] = logAdd(E[i], E2[i] * texture.refractance);
        }
        // TODO
        // utiliser scene.rayColor() sur les nouveaux rayons à calculer, en incrémentant depth

        // réfraction
        // TODO
        // utiliser scene.rayColor() sur les nouveaux rayons à calculer, en incrémentant depth


        for(Light light : scene.getLights())
        {
            // composante diffuse
            Vector3d to_light = light.getPosition().sub(normal_ray.getOrigin());
            double angle_normal_light = Math.cos(normal_ray.getDirection().angle(to_light));
            Ray intersect_to_light = new Ray(normal_ray.getOrigin(), to_light);

            boolean intersect = false;
            for(BasicObject object: scene.getObjects())
            {
                try
                {
                    double d = object.distance(intersect_to_light);
                    //System.out.println(d + ", " + to_light.length());
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
            //intersect = false; // FIXME

            if(angle_normal_light > 0 && ! intersect)
            {
                for(int i = 0; i < 3; i++)
                {
                    E[i] = logAdd(E[i], light.getIntensity(i) * angle_normal_light * texture.absorbance[i]);
                }
            }

            // spéculaire

            double angle_reflected_light = Math.cos(reflected_ray.getDirection().angle(to_light));

            if(angle_reflected_light > 0 && ! intersect)
            {
                angle_reflected_light = Math.pow(angle_reflected_light, texture.brightness);

                for(int i = 0; i < 3; i++)
                {
                    E[i] = logAdd(E[i], light.getIntensity(i) * angle_reflected_light * texture.reflectance);
                }
            }
            
        }

        return E;
    }

    private double logAdd(double a, double b)
    {
        return 1 - (1 - b)*(1 - a);
    }
}
