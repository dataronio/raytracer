package tests.maths;

import raytracer.Vector3d;
import raytracer.Tuple3d;
import raytracer.Point3d;
import org.junit.*;

/** Test pour <tt>Vector3d</tt> et <tt>Point3d</tt>.
 *  @author Korantin Auguste
 */
public class TestVecteurs {
    static public void assertEquals(Tuple3d a, Tuple3d b) {
        if(a != null && b != null) {
            Assert.assertEquals(a.x, b.x, 0.00001);
            Assert.assertEquals(a.y, b.y, 0.00001);
            Assert.assertEquals(a.z, b.z, 0.00001);
        }
        else {
            Assert.assertTrue(a == null);
            Assert.assertTrue(b == null);
        }
    }

    @org.junit.Test
    public void test1() { 
        Vector3d vx = new Vector3d(1, 0, 0);
        Vector3d vy = new Vector3d(0, 1, 0);
        Vector3d vz = new Vector3d(0, 0, 1);

        Vector3d vid = new Vector3d(vx).add(vy).add(vz);
        assertEquals(vid, new Vector3d(1,1,1));

        Vector3d p = new Vector3d(vx);
        assertEquals(p.symmetry(vx), vx);

        Vector3d p = new Vector3d(vx);
        assertEquals(p.symmetry(vy), new Vector3d(vx).scale(-1));

        Vector3d p = new Vector3d(vx);
        assertEquals(p.symmetry(vz), new Vector3d(vx).scale(-1));
    }
}

