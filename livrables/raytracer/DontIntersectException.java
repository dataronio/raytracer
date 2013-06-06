package raytracer;

/**
 * Exception lancée lorsqu'un rayon n'intersecte pas un object.
 */
public class DontIntersectException extends Exception {
    public DontIntersectException() {
        super("Pas d'intersection");
    }
}
