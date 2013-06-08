package raytracer;

/**
 * Classe qui représente un point en 3D
 */
public class Point3d extends Tuple3d {
    /** Constructeur par défaut */
    public Point3d() {
        super();
    }

    /** Constructeur
     * @param x
     * @param y
     * @param z
     */
    public Point3d(double x, double y, double z) {
        super(x, y, z);
    }

    /** Constructeur
     * @param xyz
     */
    public Point3d(double[] xyz) {
        super(xyz);
    }

    /** Constructeur par copie
     * @param other
     */
    public Point3d(Tuple3d other) {
        super(other);
    }

    /**
     * Translate ce vecteur selon <tt>other</tt>.
     * @param other
     * @return this
     */
    public Point3d add(Vector3d other)
    {
        set(other.add(this));
        return this;
    }

    /**
     * Retourne le vecteur formé par <tt>this</tt> et <tt>other</tt>
     * @param other
     */
    public Vector3d sub(Point3d other)
    {
        return new Vector3d(new Vector3d(this).sub(new Vector3d(other)));
    }
}

