import java.util.*;
import java.awt.Color;

/**
 * Class BasicObject
 * Classe abstraite qui représente un objet de base.
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
    public abstract double distance(Ray ray) throws DontIntersectException;

    /**
     * Calcule la normale à la surface de l'objet au point d'intersection du
     * rayon avec l'objet.
     * @return       Le vecteur normal unitaire.
     */
    // FIXME: On a réellement besoin de ça ?
    public abstract Ray normal(Ray ray) throws DontIntersectException;

    /**
     * @param        ray Le point de départ indique l'intersection.
     * @param        depth La profondeur de l'appel récursif.
     */
    public abstract double[] computeColor(Ray ray, Scene scene, int depth) throws DontIntersectException;
    /*
     * On appelle d'abord la fonction « normal » pour obtenir le vecteur normal.
     * Ensuite, on calcule la couleur du pixel, en appelant récursivement computeColor
     * si nécessaire.
     */

}
