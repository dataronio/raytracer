package raytracer;

import java.util.Set;
import java.util.HashSet;

/**
 * Représente un cube. Un cube est un ensemble de 12 triangles isocèles.
 */
public class Cube extends Mesh {
    /**
     * Crée un cube à partir de 4 points disposés de cette manière :
     * <code>
     *     p2------+
     *    /|      /|
     *   / |     / |
     *  p1------p3 |
     *  |  +----|--+
     *  | /     | /
     *  |/      |/
     *  p4------+
     * </code>
     */
    public Cube(
        Texture texture,
        Point3d p1, Point3d p2, Point3d p3, Point3d p4
    ) {
        /*     +-------p5
         *    /|      /|
         *   / |     / |
         *  +-------+  |
         *  |  p8---|--p7
         *  | /     | /
         *  |/      |/
         *  +------p6
         */
        super(texture);

        Vector3d p1p3 = new Vector3d(p3, p1);

        Point3d p5 = new Point3d(p2);
        p5.add(p1p3);

        Point3d p6 = new Point3d(p4);
        p6.add(p1p3);

        Vector3d p1p4 = new Vector3d(p4, p1);

        Point3d p7 = new Point3d(p5);
        p7.add(p1p4);

        Point3d p8 = new Point3d(p2);
        p8.add(p1p4);

        Set<Triangle> triangles = new HashSet<Triangle>(12);

        // up
        triangles.add(new Triangle(texture, p1, p2, p3));
        triangles.add(new Triangle(texture, p5, p2, p3));

        // down
        triangles.add(new Triangle(texture, p4, p6, p8));
        triangles.add(new Triangle(texture, p7, p6, p8));

        // front
        triangles.add(new Triangle(texture, p1, p4, p6));
        triangles.add(new Triangle(texture, p1, p3, p6));

        // bottom
        triangles.add(new Triangle(texture, p2, p8, p7));
        triangles.add(new Triangle(texture, p2, p5, p7));

        // left
        triangles.add(new Triangle(texture, p1, p8, p2));
        triangles.add(new Triangle(texture, p1, p8, p4));

        // right
        triangles.add(new Triangle(texture, p3, p5, p7));
        triangles.add(new Triangle(texture, p3, p6, p7));

        setTriangles(triangles);
    }
}
