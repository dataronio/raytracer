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

        // réflection
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
                if(object.intersects(intersect_to_light))
                {
                    intersect = true;
                    break;
                }
            }

            if(angle_normal_light > 0 && ! intersect)
            {
                for(int i = 0; i < 3; i++)
                    E[i] = logAdd(E[i], light.getIntensity(i) * angle_normal_light);
            }

            // composante spéculaire
            // TODO
        }

        return E;
    }

    private double logAdd(double a, double b)
    {
        return 1 - (1 - b)*(1 - a);
    }
}
