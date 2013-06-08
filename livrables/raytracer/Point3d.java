package raytracer;

/**
 * Classe qui représente un point en 3D
 */
public class Point3d extends Tuple3d {
    /** Constructeur le point (0,0,0). */
    public Point3d() {
        super();
    }

    public Point3d(double x, double y, double z) {
        super(x, y, z);
    }

    /**
     * @throws IndexOutOfBoundsException si le tableau n'a pas au moins 3
     * éléments.
     */
    public Point3d(double[] xyz) {
        super(xyz);
    }

    /** Constructeur par copie
     */
    public Point3d(Tuple3d other) {
        super(other);
    }

    /**
     * Translate ce vecteur selon <tt>other</tt>.
     * @return this
     */
    public Point3d add(Vector3d other) {
        set(other.add(this));
        return this;
    }

    /**
     * Construit un vecteur égal à other-this.
     */
    public Vector3d sub(Point3d other) {
        return new Vector3d(other, this);
    }
}

