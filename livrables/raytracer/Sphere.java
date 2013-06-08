package raytracer;

import java.util.*;

/**
 * Représente une sphère
 */
public class Sphere extends Object {
    /** Centre de la sphère */
    private Point3d center;

    /** Rayon de la sphère */
    private double radius;
  
    /** Constructeur
     * @param texture La texture de la sphère
     * @param center Le centre
     * @param radius Le rayon
     */
    public Sphere(Texture texture, Point3d center, double radius) {
        super(texture);
        this.center = center;
        this.radius = radius;
    };

    @Override
    public double distance(Ray ray) throws DontIntersectException {
        Vector3d SC = new Vector3d(center, ray.getOrigin());
        double dDotSC = ray.getDirection().dot(SC);
        double delta = radius*radius + dDotSC*dDotSC - SC.lengthSquared();

        if(delta < 0) {
            throw new DontIntersectException();
        }

        double t1 = dDotSC - Math.sqrt(delta);
        double t2 = dDotSC + Math.sqrt(delta);

        if(t1 >= 0) {
            return t1;
        }
        else {
            return t2;
        }
    }

    @Override
    public Ray normal(Ray ray) throws DontIntersectException {
        Vector3d td = ray.getDirection().scale(distance(ray));

        Point3d P = new Point3d(ray.getOrigin().add(td));

        if(isEntering(ray))
            return new Ray(P, new Vector3d(P, center));
        return new Ray(P, new Vector3d(center, P));
    }

    @Override
    public boolean isEntering(Ray ray) throws DontIntersectException {
        Vector3d SC = new Vector3d(center, ray.getOrigin());
        double dDotSC = ray.getDirection().dot(SC);
        double delta = radius*radius + dDotSC*dDotSC - SC.lengthSquared();

        if(delta < 0)
            throw new DontIntersectException();

        double distance = dDotSC - Math.sqrt(delta);

        return distance >= 0;
    }
}

