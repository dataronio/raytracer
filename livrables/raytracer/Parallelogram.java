package raytracer;

import java.awt.image.BufferedImage;

/**
 * Représente un parallélogramme par trois points.
 */
public class Parallelogram extends TriPointed {
    private BufferedImage image;

    /**
     * Construit un parallélogramme à partir de 3 points.
     * @param texture La texture du parallélogramme (non copiée).
     * @param image_ Une image à appliquer sur la surface (peut être null).
     */
    public Parallelogram(
        Texture texture, BufferedImage image_,
        Point3d P0, Point3d P1, Point3d P2
    ) {
        super(texture, P0, P1, P2);
        image = image_;
    }

    /**
     * Construit un parallélogramme à partir de 3 points.
     * @param texture La texture du parallélogramme (non copiée).
     */
    public Parallelogram(Texture texture, Point3d P0, Point3d P1, Point3d P2) {
        this(texture, null, P0, P1, P2);
    }

    @Override
    protected boolean checkDontIntersect() {
        return lastSol == null
            || lastSol.x < 0. || lastSol.x > 1.
            || lastSol.y < 0. || lastSol.y > 1.;
    }
}

