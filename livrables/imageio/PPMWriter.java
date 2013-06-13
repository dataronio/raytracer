package imageio;

import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.IOException;
import javax.imageio.IIOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;

/** Classe pour écrire des images.
 *  La classe devrait être utilisée au travers de {@link javax.imageio.ImageIO}.
 *  @author Martin Carton
 */
public class PPMWriter extends ImageWriter {
    /** Flux sur lequel l'image sera écrit.
     *  @see #setOutput(Object)
     */
    ImageOutputStream stream = null;

    public PPMWriter(ImageWriterSpi originatingProvider) {
        super(originatingProvider);
    }

    /** Les métadonnées ne sont pas supportées.
     *  @return null
     */
    public IIOMetadata convertImageMetadata(
        IIOMetadata inData,
        ImageTypeSpecifier imageType,
        ImageWriteParam param
    ) {
        return null;
    }

    /** Les métadonnées ne sont pas supportées.
     *  @return null
     */
    public IIOMetadata convertStreamMetadata(
        IIOMetadata inData, ImageWriteParam param
    ) {
        return null;
    }

    /** Les métadonnées ne sont pas supportées.
     *  @return null
     */
    public IIOMetadata getDefaultImageMetadata(
        ImageTypeSpecifier imageType, ImageWriteParam param
    ) {
        return null;
    }

    /** Les métadonnées ne sont pas supportées.
     *  @return null
     */
    public IIOMetadata getDefaultStreamMetadata(ImageWriteParam param) {
        return null;
    }

    /** Affecte le flux de sortie.
     *  @exception IllegalArgumentException si <tt>output</tt> n'est pas une
     *  instance de {@link javax.imageio.stream.ImageOutputStream}.
     */
    public void setOutput(Object output) {
        super.setOutput(output);

        if (output != null) {
            try {
                this.stream = (ImageOutputStream)output;
            }
            catch (ClassCastException e) {
                throw new IllegalArgumentException(
                    "output not an ImageOutputStream", e
                );
            }
        }
        else {
            this.stream = null;
        }
    }

    /** Écrit l'image <tt>image</tt> sur le flux <tt>streamMetadata</tt>.
     *  @exception javax.imageio.IIOException si l'écriture échoue.
     *  @param param Ignoré.
     */
    public void write(
        IIOMetadata streamMetadata,
        IIOImage image,
        ImageWriteParam param
    ) throws IIOException {
        RenderedImage im = image.getRenderedImage();
        Raster raster = im.getData();

        int width = im.getWidth();
        int height = im.getHeight();

        try {
            stream.writeBytes("P3\n");
            stream.writeBytes(Integer.toString(width));
            stream.writeByte(' ');
            stream.writeBytes(Integer.toString(height));
            stream.writeBytes("\n255\n");

            // le canal alpha est ignoré par ce format, mais si l'image en a un
            // il faut que ce tableau ait au moins 4 cases.
            int[] rgb = new int[4];
            for (int i = 0; i < height; ++i) {
                for (int j = 0; j < width; ++j) {
                    raster.getPixel(j, i, rgb);

                    stream.writeBytes(Integer.toString(rgb[0]));
                    stream.writeByte( ' ' );
                    stream.writeBytes(Integer.toString(rgb[1]));
                    stream.writeByte( ' ' );
                    stream.writeBytes(Integer.toString(rgb[2]));
                    stream.writeByte( ' ' );
                }

                stream.writeByte('\n');
            }
        }
        catch (IOException e) {
            throw new IIOException("Error when writting PPM image", e);
        }
    }
}

