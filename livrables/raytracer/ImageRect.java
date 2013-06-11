package raytracer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;

/**
 * Représente un rectangle qui contient une image.
 */
public class ImageRect extends Parallelogram {
    protected BufferedImage image;

    /**
     * Construit un rectangle avec image à partir de 3 points.
     * @param texture La texture du parallélogramme (non copiée).
     * @param image_path Chemin vers l'image.
     */
    public ImageRect(Texture texture, String image_path, Point3d P0, Point3d P1, Point3d P2) {
        super(texture, P0, P1, P2);

        try
        {
            image = ImageIO.read(new File(image_path));
        }
        catch (IOException e)
        {
            System.out.println("Warning : Unable to load image " + image_path + ", fallback to textured rendering.");
            image = null;
        }
    }

    /**
     * @return Un tableau avec les 3 composantes de couleur.
     * @param ray Le point de départ indique l'intersection.
     * @param scene La scène dans laquel est l'objet.
     * @param depth La profondeur de l'appel récursif.
     */
    public double[] computeColor(Ray ray, Scene scene, int depth) throws DontIntersectException
    {
        if(image == null)
            super.computeColor(ray, scene, depth);

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
                Color color = new Color(image.getRGB((int) lastSol.x / image.getWidth(), (int)lastSol.y / image.getHeight()));

                E[0] = logAdd(E[0], light.getIntensity(0) * angle_normal_light * (color.getRed()));
                E[1] = logAdd(E[1], light.getIntensity(1) * angle_normal_light * (color.getGreen()));
                E[2] = logAdd(E[2], light.getIntensity(2) * angle_normal_light * (color.getBlue()));
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

}
