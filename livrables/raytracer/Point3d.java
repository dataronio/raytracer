package raytracer;

public class Point3d extends Tuple3d {
    public Point3d() {
        super();
    }

    public Point3d(double x_, double y_, double z_) {
        super(x_, y_, z_);
    }

    public Point3d(double[] xyz) {
        super(xyz);
    }

    public Point3d(Tuple3d other) {
        super(other);
    }

    public Vector3d sub(Point3d other)
    {
        return new Vector3d(new Vector3d(this).sub(new Vector3d(other)));
    }

    public Point3d add(Vector3d other)
    {
        return new Point3d(other.add(this));
    }
}
