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
     * @param eye_ le point où doit être placé l'œil.
     * @param origin_ l'origine du plan de l'écran.
     * @param abscissa_ le vecteur de l'abscisse, qui va jusqu'au bout de l'écran (origin_ + abscissa_ donne le bout de l'écran).
     * @param ordinate_ le vecteur de l'ordonnée, qui va jusqu'au bout de l'écran.
     * @param widthPixels_ la largeur de l'image en pixels.
     * @param heightPixels_ la hauteur de l'image en pixels.
     * @throws IllegalArgumentException si l'une des référence passée est nulle
     * ou si les tailles données sont négatives.
     */
    public Camera(
        Point3d eye_, Point3d origin_,
        Vector3d abscissa_, Vector3d ordinate_,
        int widthPixels_, int heightPixels_
    ) {
        if(eye_ == null
        || origin_ == null
        || abscissa_ == null
        || ordinate_ == null
        || widthPixels_ <= 0
        || heightPixels_ <= 0) {
            throw new IllegalArgumentException("Paramètre invalide");
        }

        eye = eye_;
        origin = origin_;
        abscissa = abscissa_;
        ordinate = ordinate_;
        widthPixels = widthPixels_;
        heightPixels = heightPixels_;
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
     */
    public Ray getRay(int x, int y) {
        Vector3d v = new Vector3d(screenPoint(x, y), eye);

        return new Ray(eye, v);
    }

    /**
     * Renvoie les coordonnées spatiales d'un point sur l'écran
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
