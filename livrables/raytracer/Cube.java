package raytracer;

import java.util.Set;
import java.util.HashSet;

/**
 * Représente un cube. Un cube est un ensemble de 12 triangles isocèles.
 */
public class Cube extends Mesh {
    /**
     * Crée un cube à partir de 4 points copiés disposés de cette manière :
     * <pre>{@code .
     *     p2------+
     *    /|      /|
     *   / |     / |
     *  p1------p3 |
     *  |  +----|--+
     *  | /     | /
     *  |/      |/
     *  p4------+
     * }</pre>
     */
    public Cube(
        Texture texture,
        Point3d p1_, Point3d p2_, Point3d p3_, Point3d p4_
    ) 
    {
        /*     p2------p5
         *    /|      /|
         *   / |     / |
         *  p1------p3 |
         *  |  p8---|---
         *  | /     | /
         *  |/      |/
         *  p4-----p6
         */

        // les points sont copiés afin d'éviter que p5..p8 ne deviennent
        // incohérents
        Point3d p1 = new Point3d(p1_);
        Point3d p2 = new Point3d(p2_);
        Point3d p3 = new Point3d(p3_);
        Point3d p4 = new Point3d(p4_);

        Vector3d p1p3 = new Vector3d(p1, p3);

        Point3d p5 = new Point3d(p2);
        p5.add(p1p3);

        Point3d p6 = new Point3d(p4);
        p6.add(p1p3);

        Vector3d p1p4 = new Vector3d(p1, p4);

        Point3d p8 = new Point3d(p2);
        p8.add(p1p4);

        Set<BasicObject> triangles = new HashSet<BasicObject>(6);

        // up
        triangles.add(new Parallelogram(texture, p1, p3, p2));

        // down
        triangles.add(new Parallelogram(texture, p4, p8, p6));

        // front
        triangles.add(new Parallelogram(texture, p1, p4, p3));

        // bottom
        triangles.add(new Parallelogram(texture, p2, p8, p5));

        // left
        triangles.add(new Parallelogram(texture, p1, p2, p4));

        // right
        triangles.add(new Parallelogram(texture, p3, p5, p6));

        setObjects(triangles);
    }
}
