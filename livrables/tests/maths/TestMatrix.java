package tests.maths;

import raytracer.Vector3d;
import raytracer.Matrix;
import org.junit.*;

/** Test pour <tt>Matrix3d</tt>.
 *  @author Martin Carton
 */
public class TestMatrix {
    static public void assertEquals(Vector3d a, Vector3d b) {
        if(a != null && b != null) {
            Assert.assertEquals(a.x, b.x, 0.0001);
            Assert.assertEquals(a.y, b.y, 0.0001);
            Assert.assertEquals(a.z, b.z, 0.0001);
        }
        else {
            Assert.assertTrue(a == null);
            Assert.assertTrue(b == null);
        }
    }

    @org.junit.Test
    public void test1() { 
        Matrix m = new Matrix(
            new Vector3d(1., 0., 0.),
            new Vector3d(0., 1., 0.),
            new Vector3d(0., 0., 1.)
        );

        Vector3d b = new Vector3d(2., 3., 7.);

        Assert.assertEquals(m.determinant(), 1., 0.0001);
        assertEquals(m.solve(b), b);
    }

    @org.junit.Test
    public void test2() { 
        Matrix m = new Matrix(
            new Vector3d(1., 0., 0.),
            new Vector3d(0., 0., 1.),
            new Vector3d(0., 1., 0.)
        );

        Vector3d b = new Vector3d(2., 3., 7.);
        Vector3d br = new Vector3d(2., 7., 3.);

        Assert.assertEquals(m.determinant(), -1., 0.0001);
        assertEquals(m.solve(b), br);
    }

    @org.junit.Test
    public void test3() { 
        Matrix m = new Matrix(
            new Vector3d(1., 0., 0.),
            new Vector3d(2., 4., 0.),
            new Vector3d(3., 5., 6.)
        );

        Vector3d b = new Vector3d(2., 3., 4.);
        Vector3d br = new Vector3d(1./6., -1./12., 2./3.);

        Assert.assertEquals(m.determinant(), 24., 0.0001);
        assertEquals(m.solve(b), br);
    }

    @org.junit.Test
    public void test4() { 
        Matrix m = new Matrix(
            new Vector3d(1., 0., 7.),
            new Vector3d(2., 5., 8.),
            new Vector3d(3., 6., 9.)
        );

        Vector3d b = new Vector3d(2., 3., 4.);
        Vector3d br = new Vector3d(0., -1.0, 4./3.);

        Assert.assertEquals(m.determinant(), -24., 0.0001);
        assertEquals(m.solve(b), br);
    }

    @org.junit.Test
    public void test5() { 
        Matrix m = new Matrix(
            new Vector3d(1., 0., 7.),
            new Vector3d(2., 5., 0.),
            new Vector3d(0., 6., 9.)
        );

        Vector3d b = new Vector3d(2., 3., 4.);
        Vector3d br = new Vector3d(0.651163, 0.674419, -0.062016);

        Assert.assertEquals(m.determinant(), 129., 0.0001);
        assertEquals(m.solve(b), br);
    }
}

