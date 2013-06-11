package raytracer;

/**
 * Représente un objet représentable par trois points.
 */
public abstract class TriPointed extends Object {
    protected Point3d P0;
    protected Point3d P1;
    protected Point3d P2;

    /** Conservé pour éviter de refaire les calculs */
    protected Ray lastRay;
    /** Conservé pour éviter de refaire les calculs */
    protected Vector3d lastSol;
    /** Conservé pour éviter de refaire les calculs */
    protected Vector3d lastN;
    /** Conservé pour éviter de refaire les calculs */
    protected Point3d lastPoint;
  
    /**
     * Construit un objet représenté par 3 points.
     * Les paramètres ne sont pas copiés.
     */
    public TriPointed(Texture texture, Point3d P0_, Point3d P1_, Point3d P2_) {
        super(texture);
        P0 = P0_;
        P1 = P1_;
        P2 = P2_;
        lastSol = null;
    }

    @Override
    public double distance(Ray ray) throws DontIntersectException {
        update(ray);

        return lastSol.z;
    }

    @Override
    public Ray normal(Ray ray) throws DontIntersectException {
        update(ray);

        return new Ray(lastPoint, lastN);
    }
    /**
     * Le rayon « entre » dans l'objet s'il est dans la direction opposée à
     * P0P1^P0P2. 
     */
    public boolean isEntering(Ray ray) throws DontIntersectException {
        if(checkDontIntersect()) {
            throw new DontIntersectException();
        }
        else {
            Vector3d P01 = new Vector3d(P0, P1);
            Vector3d P02 = new Vector3d(P0, P2);
            return P01.cross(P02).dot(ray.getDirection()) < 0;
        }
    }

    /**
     * Vérifie si le dernier rayon intersecte cet objet.
     */
    abstract protected boolean checkDontIntersect();

    /**
     * Met à jour l'état interne de l'objet si le rayon a changé depuis le
     * dernier appel.
     */
    private void update(Ray ray) throws DontIntersectException {
        if(lastRay != ray) {
            Vector3d P01 = new Vector3d(P0, P1);
            Vector3d P02 = new Vector3d(P0, P2);
            Vector3d d = ray.getDirection().scale(-1.);
            Vector3d b = new Vector3d(P0, ray.getOrigin());

            Matrix m = new Matrix(P01, P02, d);

            lastRay = ray;
            lastSol = m.solve(b);
            if(checkDontIntersect()) {
                lastSol = null;
                throw new DontIntersectException();
            }

            lastN = P01.cross(P02);

            // oriente la normale.
            if(lastN.dot(ray.getDirection()) > 0) {
                lastN.scale(-1.d);
            }

            lastPoint = ray.getOrigin()
                           .add(ray.getDirection().scale(lastSol.z));
        }
        else if(lastSol == null) {
            throw new DontIntersectException();
        }
    }


}

