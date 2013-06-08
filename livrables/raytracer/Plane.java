package raytracer;

/**
 * Représente un plan par trois points. Un plan est vu comme un triangle
 * infini.
 */
public class Plane extends Triangle {

    /**
     * Construit un plan à partir de 3 points.
     * @param texture La texture du plan (non copiée).
     */
    public Plane(Texture texture, Point3d P0, Point3d P1, Point3d P2) {
        super(texture, P0, P1, P2);
    }

    @Override
    protected boolean checkDontIntersect() {
        return lastSol == null;
    }
}

