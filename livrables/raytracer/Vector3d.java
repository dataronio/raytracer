package raytracer;

/**
 * Représente un vecteur.
 * Certaines méthode retournent <tt>this</tt> après l'avoir modifier pour
 * pouvoir chaine les opérations.
 */
public class Vector3d extends Tuple3d {
    /** Constructeur le vecteur (0,0,0). */
    public Vector3d() {
        super();
    }

    public Vector3d(double x, double y, double z) {
        super(x, y, z);
    }

    /**
     * @throws IndexOutOfBoundsException si le tableau n'a pas au moins 3
     * éléments.
     */
    public Vector3d(double[] xyz) {
        super(xyz);
    }

    /**
     * Constructeur par copie.
     */
    public Vector3d(Tuple3d other) {
        super(other);
    }

    /**
     * Représente le vecteur AB ou B-A.
     */
    public Vector3d(Tuple3d B, Tuple3d A) {
        set(B.x-A.x, B.y-A.y, B.z-A.z);
    }

    /**
     * Retourne la norme du vecteur
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Retourne la norme au carré du vecteur
     */
    public double lengthSquared() {
        return dot(this);
    }

    /**
     * Effectue le produit scalaire entre <tt>this</tt> et <tt>other</tt>.
     */
    public double dot(Vector3d other) {
        return x*other.x + y*other.y + z*other.z;
    }

    /**
     * Effectue le produit vectoriel entre <tt>this</tt> et <tt>other</tt>.
     * Modifie cet object.
     * @return this
     */
    public Vector3d cross(Vector3d other) {
        set(
            y*other.z - z*other.y,
            z*other.x - x*other.z,
            x*other.y - y*other.x
        );
        return this;
    }

    /**
     * Multiplie ce vecteur par <tt>s</tt>.
     * @return this
     */
    public Vector3d scale(double s) {
        x *= s; y *= s; z *= s;
        return this;
    }

    /**
     * Réalise <tt>this = s*t</tt>.
     */
    public void scale(double s, Vector3d t) {
        set(new Vector3d(t).scale(s));
    }

    /**
     * Normalise ce vecteur.
     * @return this
     */
    public Vector3d normalize() {
        scale(1.d/length());
        return this;
    }

    /**
     * Retourne l'angle entre ce vecteur et other.
     */
    public double angle(Vector3d other) {
        return Math.acos(dot(other) / (length() * other.length()));
    }

    /**
     * Ajoute <tt>rhs</tt> à ce vecteur.
     * @return this
     */
    public Vector3d add(Tuple3d rhs) {
        x += rhs.x; y += rhs.y; z += rhs.z;
        return this;
    }

    /**
     * Réalise <tt>this = lhs+rhs</tt>.
     */
    public void add(Vector3d lhs, Vector3d rhs) {
        set(new Vector3d(lhs).add(rhs));
    }

    /**
     * Soustrait <tt>rhs</tt> à ce vecteur.
     * @return this
     */
    public Vector3d sub(Vector3d rhs) {
        x -= rhs.x; y -= rhs.y; z -= rhs.z;
        return this;
    }

    /**
     * Réalise <tt>this = lhs-rhs</tt>.
     */
    public void sub(Vector3d lhs, Vector3d rhs) {
        set(new Vector3d(lhs).sub(rhs));
    }

    /**
     * Symétrise ce vecteur par rapport à celui passé en paramètre.
     * @return this
     */
    public Vector3d symmetry(Vector3d other)
    {
        double dd = 2.d*dot(other);
        x = other.x*dd - x;
        y = other.y*dd - y;
        z = other.z*dd - z;
        return this;
    }

}

