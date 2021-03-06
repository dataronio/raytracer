package raytracer;

import java.util.*;
import java.awt.Color;

/**
 * Objet simple, en un seul « bloc », qui contient donc des propriétés optiques
 * uniques et une géométrie simple.
 */
abstract public class Object extends BasicObject {

    /** Texture de l'objet. */
    protected Texture texture;

    /**
     * Crée un Object avec la texture donnée, qui n'est pas copiée.
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
     * @return Le vecteur normal unitaire.
     */
    public abstract Ray normal(Ray ray) throws DontIntersectException;

    /**
     * Indique si le rayon rentre dans l'objet ou en sort.
     */
    public abstract boolean isEntering(Ray ray) throws DontIntersectException;

    /**
     * @return Un tableau avec les 3 composantes de couleur.
     * @param ray Le point de départ indique l'intersection.
     * @param scene La scène dans laquel est l'objet.
     * @param depth La profondeur de l'appel récursif.
     */
    public double[] computeColor(Ray ray, Scene scene, int depth) throws DontIntersectException
    {
        Ray normal_ray = normal(ray);
        Vector3d normal_ray_direction = normal_ray.getDirection();
        assert(normal_ray_direction.dot(ray.getDirection()) <= 0); // la normale doit être correctement orientée

        Point3d normal_ray_origin = normal_ray.getOrigin();
        Vector3d ray_direction = ray.getDirection();
        Ray reflected_ray = new Ray(normal_ray_origin, ray_direction.symmetry(normal_ray_direction).scale(-1));

        // la couleur finale du rayon
        double[] E = {0, 0, 0};


        // composante ambiante

        for(int i = 0; i < 3; i++)
            E[i] = scene.getAmbientLight(i) * texture.k_diffuse[i];


        // réflection

        if(texture.k_reflection > 0)
        {
            double[] E2 = scene.rayColor(reflected_ray, depth + 1, this);

            for(int i = 0; i < 3; i++)
                E[i] = logAdd(E[i], E2[i] * texture.k_reflection);
        }


        // réfraction
        
        if(texture.k_refraction[0] > 0 || texture.k_refraction[1] > 0 || texture.k_refraction[2] > 0)
        {
            double coef = normal_ray_direction.dot(ray_direction);

            double refindex = texture.refractive_index;
            if(! isEntering(ray))
                refindex = 1 / refindex;

            coef += Math.sqrt(refindex*refindex + coef*coef - 1.d);
           
            Ray refracted_ray = new Ray(normal_ray_origin, normal_ray_direction.scale(-coef).add(ray_direction));

            double[] E2 = scene.rayColor(refracted_ray, depth + 1, this);

            for(int i = 0; i < 3; i++)
                E[i] = logAdd(E[i], E2[i] * texture.k_refraction[i]);
        }


        // éclairement

        for(Light light : scene.getLights())
        {
            Vector3d to_light = light.getPosition().sub(normal_ray_origin);

            // on regarde si il y a un objet qui masque la lumière
            if(to_light.dot(normal_ray_direction) <= 0) // ça peut éventuellement être l'objet lui-même !
                continue;

            Ray intersect_to_light = new Ray(normal_ray_origin, to_light);

            boolean intersect = false;
            for(BasicObject object: scene.getObjects())
            {
                /* FIXME !
                 * Dans certains cas, il serait possible que l'objet se fasse de l'ombre à lui-même !
                 * Donc ceci n'est pas adapté.
                 * On peut l'enlever, mais il faut alors mettre un epsilon, et non 0, dans le if après le calcul de la distance (dans le try qui suit). Et je ne suis pas certain que ça résoudrait le problème :
                 * Ça pourrait renvoyer 0.00001 (l'endroit en cours), alors qu'il y a une vraie intersection valide plus loin...
                 */
                if(object == this)
                    continue;

                try
                {
                    double d = object.distance(intersect_to_light);
                    if(0 < d && d < to_light.length())
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

            double angle_normal_light = Math.cos(normal_ray_direction.angle(to_light));

            if(angle_normal_light > 0)
            {
                for(int i = 0; i < 3; i++)
                {
                    E[i] = logAdd(E[i], light.getIntensity(i) * angle_normal_light * texture.k_diffuse[i]);
                }
            }


            // composante spéculaire

            Vector3d reflected_ray_direction = reflected_ray.getDirection();
            double angle_reflected_light = reflected_ray_direction.dot(to_light) / (reflected_ray_direction.length() * to_light.length());

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

    /**
     * Addition de couleur
     */
    protected double logAdd(double a, double b)
    {
        return 1 - (1 - b)*(1 - a);
    }
}
