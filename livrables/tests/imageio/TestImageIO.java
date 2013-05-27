package tests.imageio;

import imageio.PPMImageWriterSpi;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.spi.IIORegistry;
import org.junit.*;

/** Test pour <tt>imageio.PPMWriter</tt> et <tt>imageio.PPMImageWriterSpi</tt>.
 *  @author Martin Carton
 */
public class TestImageIO {
    static {
        IIORegistry.getDefaultInstance()
                   .registerServiceProvider(new PPMImageWriterSpi());
    }

    @org.junit.Test
    public void simpleTest() { 
        BufferedImage bi = new BufferedImage(
            3, 3, BufferedImage.TYPE_INT_RGB
        );

        bi.setRGB(0, 0, 0xff0000);
        bi.setRGB(0, 1, 0xffff00);
        bi.setRGB(0, 2, 0xffffff);

        bi.setRGB(1, 0, 0x000000);
        bi.setRGB(1, 1, 0x00ff00);
        bi.setRGB(1, 2, 0x00ffff);

        bi.setRGB(2, 0, 0x000000);
        bi.setRGB(2, 1, 0x000000);
        bi.setRGB(2, 2, 0x0000ff);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            ImageIO.write(bi, "ppm", output);
        }
        catch (IOException e) {
            org.junit.Assert.fail(e.toString());
        }

        org.junit.Assert.assertEquals(
            output.toString(),
            "P3\n" +
            "3 3\n" +
            "255\n" +
            "255 0 0 0 0 0 0 0 0 \n" +
            "255 255 0 0 255 0 0 0 0 \n" +
            "255 255 255 0 255 255 0 0 255 \n"
        );
    }
}

