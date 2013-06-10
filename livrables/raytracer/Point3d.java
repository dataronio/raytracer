package raytracer;

/**
 * Classe qui représente un point en 3D
 */
public class Point3d extends Tuple3d {
    /** Constructeur le point (0,0,0). */
    public Point3d() {
        super();
    }

    public Point3d(double x_, double y_, double z_) {
        super(x_, y_, z_);
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
        set(x+other.x, y+other.y, z+other.z);
        return this;
    }

    /**
     * Construit un vecteur égal à this-other.
     */
    public Vector3d sub(Point3d other) {
        return new Vector3d(other, this);
    }
}

