public class Vector3d extends Tuple3d {
    public Vector3d() {
        super();
    }

    public Vector3d(double x_, double y_, double z_) {
        super(x_, y_, z_);
    }

    public Vector3d(Tuple3d other) {
        super(other);
    }

    /**
     * Représente le vecteur AB ou B-A.
     */
    public Vector3d(Tuple3d B, Tuple3d A) {
        set(new Vector3d(B).sub(A));
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
}

