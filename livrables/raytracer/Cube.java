package raytracer;


/**
 * Class Cube
 */
public class Cube extends Mesh {

    public Cube (Texture texture, Point3d p1, Point3d p2, Point3d p3, Point3d p4)
    {
        // on crée la liste de triangle que contient le cube à la construction, pour le reste c'est un mesh normal.

        super();

        Point3d p5 = new Point3d(); // p1 + p1p2 + p1p3
        p5.add(p3, p2);
        p5.sub(p5, p1);

        Point3d p6 = new Point3d(); // p1 + p1p2 + p1p4
        p6.add(p4, p2);
        p6.sub(p6, p1);

        Point3d p7 = new Point3d(); // p2 + p2p5 + p2p6
        p7.add(p5, p6);
        p7.sub(p7, p2);

        Point3d p8 = new Point3d(); // p1 + p1p3 + p1p4
        p8.add(p3, p4);
        p8.sub(p8, p1);

        addTriangle(new Triangle(texture, p1, p2, p5));
        addTriangle(new Triangle(texture, p1, p5, p3));

        addTriangle(new Triangle(texture, p4, p6, p7));
        addTriangle(new Triangle(texture, p4, p8, p7));

        addTriangle(new Triangle(texture, p1, p4, p6));
        addTriangle(new Triangle(texture, p4, p2, p6));

        addTriangle(new Triangle(texture, p3, p8, p7));
        addTriangle(new Triangle(texture, p3, p5, p7));

        addTriangle(new Triangle(texture, p2, p5, p6));
        addTriangle(new Triangle(texture, p7, p5, p6));

        addTriangle(new Triangle(texture, p1, p3, p4));
        addTriangle(new Triangle(texture, p8, p4, p3));

    };
}
