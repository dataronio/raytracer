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
            double angle_normal_light = normal_ray.getDirection().angle(light.getPosition().sub(normal_ray.getOrigin()));

            for(int i = 0; i < 3; i++)
                E[i] = logAdd(E[i], light.getIntensity(i) * (Math.cos(angle_normal_light) ));

            // composante spéculaire
            // TODO
        }
    }

    private double logAdd(double a, double b)
    {
        return 1 - (1 - b)*(1 - a);
    }
}
