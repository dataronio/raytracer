package raytracer;

/** Classe représentant un tuple 3D */
public class Tuple3d {
    public double x, y, z;

    /** Constructeur par défaut */
    public Tuple3d() {
        this(0, 0, 0);
    }

    /** Constructeur
     * @param xyz
     * @throws IndexOutOfBoundsException si le tableau n'a pas au moins 3
     * éléments.
     */
    public Tuple3d(double[] xyz) throws IndexOutOfBoundsException {
        this(xyz[0], xyz[1], xyz[2]);
    }

    /** Constructeur
     * @param x
     * @param y
     * @param z
     */
    public Tuple3d(double x, double y, double z) {
        set(x, y, z);
    }

    /** Constructeur de copie
     * @param other
     */
    public Tuple3d(Tuple3d other) {
        this(other.x, other.y, other.z);
    }

    /** Modifieur
     * @param x
     * @param y
     * @param z
     */
    public void set(double x, double y, double z) {
        this.x = x; this.y = y; this.z = z;
    }

    /** Assignation
     * @param other
     */
    public void set(Tuple3d other) {
        set(other.x, other.y, other.z);
    }

    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    };
}
