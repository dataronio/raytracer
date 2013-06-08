package raytracer;

/**
 * Exception lancée lors d'une erreur lors du parsage d'un fichier
 */
public class InvalidFormatException extends Exception {
    /** Constructeur
     * @param s un message d'erreur
     */
    public InvalidFormatException(String s) {
        super(s);
    }

    /** Constructeur
     * @param s un message d'erreur
     * @param t l'exception source
     */
    public InvalidFormatException(String s, Throwable t) {
        super(s, t);
    }
}
