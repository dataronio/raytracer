
import java.util.*;
import java.awt.Color;


/**
 * Class Object
 * Objet simple, en un seul « bloc », qui contient donc des propriétés optiques
 * uniques et une géométrie simple.
 */
abstract public class Object extends BasicObject {

    private Texture texture;

    public Object (Texture texture_) {
        texture = new Texture(texture_);
    };

    /**
     * Set the value of texture
     * @param newVar the new value of texture
     */
    public void setTexture (Texture texture_)
    {
        texture = texture_;
    }

    /**
     * Get the value of texture
     * @return the value of texture
     */
    public Texture getTexture ()
    {
        return texture;
    }

    /**
     * @return       java.awt.Color
     * @param        ray Le point de départ indique l'intersection.
     * @param        scene
     * @param        depth La profondeur de l'appel récursif.
     */
    public java.awt.Color computeColor( Ray ray, Scene scene, int depth )
    {
        Ray normal_ray = normal(ray);

        double[] E = {0, 0, 0};

        for(int i = 0; i < 3; i++)
            E[i] = scene.getAmbientLight(i)*texture.absorbance[i];

        // réflection
        // TODO

        // réfraction
        // TODO

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
