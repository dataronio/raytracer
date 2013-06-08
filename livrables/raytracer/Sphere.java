package raytracer;

import java.util.*;

public class Sphere extends Object {
    private Point3d center;
    private double radius;
  
    public Sphere(Texture texture, Point3d center_, double radius_) {
        super(texture);
        center = center_;
        radius = radius_;
    };

    public double distance(Ray ray) throws DontIntersectException {
        Vector3d d = ray.getDirection().normalize();

        Vector3d SC = new Vector3d(center, ray.getOrigin());
        double dDotSC = d.dot(SC);
        double delta = radius*radius + dDotSC*dDotSC - SC.lengthSquared();

        if(delta >= 0) {
            double t1 = dDotSC - Math.sqrt(delta);
            double t2 = dDotSC + Math.sqrt(delta);

            if(t1 >= 0 && t2 >= 0) {
                return Math.min(t1, t2);
            }
            if(t1 >= 0) {
                return t1;
            }
            else { // t2 >= 0 ?
                return t2;
            }
        }
        else {
            throw new DontIntersectException();
        }
    }

    public Ray normal(Ray ray) throws DontIntersectException {
        double dist = distance(ray);
        Vector3d td = ray.getDirection().scale(dist);

        Point3d P = new Point3d(ray.getOrigin().add(td));

        return new Ray(P, new Vector3d(P, center));
    }
}

