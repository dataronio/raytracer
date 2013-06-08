package raytracer;

public class Matrix {
    /** Les colonnes de la matrice */
    private Vector3d c1, c2, c3;

    /**
     * Construit la matrice représentée par les colonnes (c1 c2 c3)
     */
    public Matrix(Vector3d c1, Vector3d c2, Vector3d c3) {
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
    }

    /** Retourne le déterminant de la matrice */
    public double determinant() {
        return c1.x*c2.y*c3.z
             + c1.y*c2.z*c3.x
             + c1.z*c2.x*c3.y
             - c1.z*c2.y*c3.x
             - c1.x*c2.z*c3.y
             - c1.y*c2.x*c3.z;
    }

    /**
     * Résoud le système this*X = B et retourne X.
     * @return La solution du système, ou null si la matrice n'est pas
     * inversible.
     */
    public Vector3d solve(Vector3d B) {
        Vector3d X = new Vector3d();
        double det = determinant();
        if(det == 0) {
            return null;
        }
        else {
            X.x = new Vector3d(
                c2.y*c3.z-c3.y*c2.z,
                c3.x*c2.z-c2.x*c3.z,
                c2.x*c3.y-c3.x*c2.y
            ).dot(B);

            X.y = new Vector3d(
                c3.y*c1.z-c1.y*c3.z,
                c1.x*c3.z-c3.x*c1.z,
                c3.x*c1.y-c1.x*c3.y
            ).dot(B);

            X.z = new Vector3d(
                c1.y*c2.z-c2.y*c1.z,
                c2.x*c1.z-c1.x*c2.z,
                c1.x*c2.y-c2.x*c1.y
            ).dot(B);

            return X.scale(1/det);
        }
    }
}

