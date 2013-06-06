public class Tuple3d {
    public double x, y, z;

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

    public Tuple3d(Tuple3d other) {
        this(other.x, other.y, other.z);
    }

    public Tuple3d add(Tuple3d rhs) {
        x += rhs.x; y += rhs.y; z += rhs.z;
        return this;
    }

    public void add(Tuple3d lhs, Tuple3d rhs) {
        set(new Tuple3d(lhs).add(rhs));
    }

    public Tuple3d sub(Tuple3d rhs) {
        x -= rhs.x; y -= rhs.y; z -= rhs.z;
        return this;
    }

    public void sub(Tuple3d lhs, Tuple3d rhs) {
        set(new Tuple3d(lhs).sub(rhs));
    }

    public void set(double x_, double y_, double z_) {
        x = x_; y = y_; z = z_;
    }

    public void set(Tuple3d other) {
        set(other.x, other.y, other.z);
    }
}
