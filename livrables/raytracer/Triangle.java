package raytracer;

/**
 * Représente un triangle par ses trois sommets.
 */
public class Triangle extends TriPointed {
    /**
     * Construit un Triangle. Les paramètres ne sont pas copiés.
     */
    public Triangle(Texture texture, Point3d P0, Point3d P1, Point3d P2) {
        super(texture, P0, P1, P2);
    }
    
    /**
     * Vérifie si le dernier rayon intersecte cet objet.
     */
    @Override
    protected boolean checkDontIntersect() {
        return lastSol == null
            || lastSol.x < 0. || lastSol.x > 1.
            || lastSol.y < 0. || lastSol.y > 1-lastSol.x;
    }
}

