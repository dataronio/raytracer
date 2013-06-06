package raytracer;

public class DontIntersectException extends Exception {
    public DontIntersectException() {
        super("Pas d'intersection");
    }
}
