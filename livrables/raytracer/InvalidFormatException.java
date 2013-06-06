package raytracer;

public class InvalidFormatException extends Exception {
    public InvalidFormatException(String s) {
        super(s);
    }

    public InvalidFormatException(String s, Throwable t) {
        super(s, t);
    }
}
