package raytracer;

public class Plane extends Object {
    private Point3d P0;
    private Point3d P1;
    private Point3d P2;

    // pour éviter de refaire les calculs entre l'appel à distance et l'appel à 
    // normal
    private Ray lastRay;
    private double lastGamma;
    private Point3d lastPoint;
    private Vector3d lastN;
  
    public Plane(Texture texture, Point3d P0_, Point3d P1_, Point3d P2_) {
        super(texture);
        P0 = P0_;
        P1 = P1_;
        P2 = P2_;
        lastPoint = null;
    };

    public double distance(Ray ray) throws DontIntersectException {
        update(ray);

        return lastGamma;
    }

    public Ray normal(Ray ray) throws DontIntersectException {
        update(ray);

        return new Ray(lastPoint, lastN);
    }

    private void update(Ray ray) throws DontIntersectException {
        if(lastRay != ray) {
            Vector3d P01 = new Vector3d(P1, P0);
            Vector3d P02 = new Vector3d(P2, P0);
            Vector3d d = ray.getDirection().scale(-1.);
            Vector3d b = new Vector3d(ray.getOrigin(), P0);

            Matrix m = new Matrix(P01, P02, d);

            lastRay = ray;
            Vector3d sol = m.solve(b);
            if(sol == null) {
                lastPoint = null;
                throw new DontIntersectException();
            }

            lastGamma = sol.z;
            lastN = P01.cross(P02);
            lastPoint = ray.getOrigin()
                           .add(ray.getDirection().scale(lastGamma));
        }
        else if(lastPoint == null) {
            throw new DontIntersectException();
        }
    }
}
