package raytracer;

/** Classe représentant un tuple 3D */
public class Tuple3d {
    public double x, y, z;

    /** Constructeur par défaut */
    public Tuple3d() {
        this(0, 0, 0);
    }

    /** 
     * @throws IndexOutOfBoundsException si le tableau n'a pas au moins 3
     * éléments.
     */
    public Tuple3d(double[] xyz) throws IndexOutOfBoundsException {
        this(xyz[0], xyz[1], xyz[2]);
    }

    public Tuple3d(double x_, double y_, double z_) {
        set(x_, y_, z_);
    }

    /**
     * Constructeur de copie.
     */
    public Tuple3d(Tuple3d other) {
        this(other.x, other.y, other.z);
    }

    /**
     * Modifie ce tuple.
     */
    public void set(double x_, double y_, double z_) {
        x = x_;y = y_; z = z_;
    }

    /**
     * Modifie ce tuple à partir du tuple donné.
     */
    public void set(Tuple3d other) {
        set(other.x, other.y, other.z);
    }

    /**
     * Retourne une chaine de la forme "(x, y, z)".
     */
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    };
}
