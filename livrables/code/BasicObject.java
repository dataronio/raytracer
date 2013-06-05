
import java.util.*;
import java.awt.Color;


/**
 * Class BasicObject
 * Classe abstraite qui représente un objet de base, avec ses deux méthodes.
 */
abstract public class BasicObject {

    public BasicObject ()
    {
    };

    /**
     * Calcule la distance à l'objet, suivant le rayon depuis son origine.
     * @return       double
     * @param        ray
     */
    public abstract double distance(Ray ray);

    /**
     * Calcule la normale à la surface de l'objet au point d'intersection du rayon avec l'objet
     * @return       Le vecteur normal.
     * @param        ray
     */
    public abstract Vector3d normal(Ray ray);

    /**
     * @return       java.awt.Color
     * @param        ray Le point de départ indique l'intersection.
     * @param        scene
     * @param        depth La profondeur de l'appel récursif.
     */
    public abstract java.awt.Color computeColor( Ray ray, Scene scene, int depth );
    /*
     * On appelle d'abord la fonction « normal » pour obtenir le vecteur normal.
     * Ensuite, on calcule la couleur du pixel, en appelant récursivement computeColor
     * si nécessaire.
     */

}