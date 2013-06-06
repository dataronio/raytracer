public class Vector3d extends Tuple3d {
    public Vector3d() {
        super();
    }

    public Vector3d(double x_, double y_, double z_) {
        super(x_, y_, z_);
    }

    public Vector3d(double[] xyz) {
        super(xyz);
    }

    public Vector3d(Tuple3d other) {
        super(other);
    }

    /**
     * Représente le vecteur AB ou B-A.
     */
    public Vector3d(Tuple3d B, Tuple3d A) {
        set(new Vector3d(B).sub(new Vector3d(A)));
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return dot(this);
    }

    public double dot(Vector3d other) {
        return x*other.x + y*other.y + z*other.z;
    }

    public Vector3d cross(Vector3d other) {
        set(
            y*other.z - z*other.y,
            z*other.x - x*other.z,
            x*other.y - y*other.x
        );
        return this;
    }

    public Vector3d scale(double s) {
        x *= s; y *= s; z *= s;
        return this;
    }

    public void scale(double s, Vector3d t) {
        set(new Vector3d(t).scale(s));
    }

    public Vector3d normalize() {
        scale(1/length());
        return this;
    }

    public double angle(Vector3d other)
    {
        return Math.acos(dot(other) / (length() * other.length()));
    }

    public Vector3d add(Vector3d rhs) {
        x += rhs.x; y += rhs.y; z += rhs.z;
        return this;
    }

    public void add(Vector3d lhs, Vector3d rhs) {
        set(new Vector3d(lhs).add(rhs));
    }

    public Vector3d sub(Vector3d rhs) {
        x -= rhs.x; y -= rhs.y; z -= rhs.z;
        return this;
    }

    public void sub(Vector3d lhs, Vector3d rhs) {
        set(new Vector3d(lhs).sub(rhs));
    }

}

