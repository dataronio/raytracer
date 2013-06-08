package raytracer;

public class Camera {
    private Point3d eye;
    private Point3d origin;
    private Vector3d abscissa;
    private Vector3d ordinate;
    private int widthPixels;
    private int heightPixels;
    
    /**
     * Construit une caméra.
     * @param eye le point où doit être placé l'œil.
     * @param origin l'origine du plan de l'écran.
     * @param abscissa le vecteur de l'abscisse, qui va jusqu'au bout de l'écran (origin + abscissa donne le bout de l'écran).
     * @param ordinate le vecteur de l'ordonnée, qui va jusqu'au bout de l'écran.
     * @param widthPixels la largeur de l'image en pixels.
     * @param heightPixels la hauteur de l'image en pixels.
     * @throws IllegalArgumentException si l'une des référence passée est nulle
     * ou si les tailles données sont négatives.
     */
    public Camera(
        Point3d eye, Point3d origin,
        Vector3d abscissa, Vector3d ordinate,
        int widthPixels, int heightPixels
    ) {
        if(eye == null
        || origin == null
        || abscissa == null
        || ordinate == null
        || widthPixels <= 0
        || heightPixels <= 0) {
            throw new IllegalArgumentException("Paramètre invalide");
        }

        this.eye = eye;
        this.origin = origin;
        this.abscissa = abscissa;
        this.ordinate = ordinate;
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;
    };
  
    /**
     * Retourne la largeur de l'image en pixels.
     */
    public int getWidthPixels ( ) {
        return widthPixels;
    }

    /**
     * Retourne la hauteur de l'image en pixels.
     */
    public int getHeightPixels ( ) {
        return heightPixels;
    }

    /**
     * Renvoie le rayon qui passe par l'œil et qui se dirige vers le pixel de
     * l'écran dont les coordonnées sont passées en paramètre.
     * @param x
     * @param y
     */
    public Ray getRay(int x, int y) {
        Vector3d v = new Vector3d(screenPoint(x, y), eye);

        return new Ray(eye, v);
    }

    /**
     * Renvoie les coordonnées spatiales d'un point sur l'écran
     * @param x
     * @param y
     */
    private Point3d screenPoint(int x, int y)
    {
        Vector3d scaledAbscissa
            = new Vector3d(abscissa).scale(x / (double)widthPixels);

        Vector3d scaledOrdinate
            = new Vector3d(ordinate).scale(y / (double)heightPixels);

        Point3d p = new Point3d(origin);
        p.add(scaledAbscissa).add(scaledOrdinate);

        return p;
    }
}
