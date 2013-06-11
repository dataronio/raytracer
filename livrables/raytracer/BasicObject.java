package raytracer;

import java.util.*;

/**
 * Classe abstraite qui représente un objet de base.
 */
abstract public class BasicObject {

    /**
     * Calcule la distance à l'objet, suivant le rayon depuis son origine.
     * @throws DontIntersectException si le rayon ne passe par par cet objet.
     */
    public abstract double distance(Ray ray) throws DontIntersectException;

    /**
     * Détermine la couleur du rayon partant de cette objet.
     * @param ray Le point de départ indique l'intersection.
     * @param depth La profondeur de l'appel récursif.
     * @throws DontIntersectException si le rayon ne passe par par cet objet.
     */
    public abstract double[] computeColor(Ray ray, Scene scene, int depth)
        throws DontIntersectException;
    /*
     * On appelle d'abord la fonction « normal » pour obtenir le vecteur normal.
     * Ensuite, on calcule la couleur du pixel, en appelant récursivement computeColor
     * si nécessaire.
     */
}

