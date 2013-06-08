package imageio;

import java.util.Locale;
import javax.imageio.ImageWriter;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;

/** Classe pour enregistrer le format d'image PPM auprès de
 *  {@link javax.imageio}.
 *  Ne supporte aucune forme de métadonnée ou de conversion.
 *  <p>
 *  La classe doit être enregistrée par exemple avec :
 *  <code>IIORegistry.getDefaultInstance().registerServiceProvider(new PPMImageWriterSpi())</code>.
 *  @author Martin Carton
 */
public class PPMImageWriterSpi extends ImageWriterSpi {
    static final String vendorName = "n7";
    static final String version = "1.0";

    static final String[] names = { "ppm", "PPM" };
    static final String[] suffixes = { "ppm" };
    static final String[] MIMETypes = { "image/x-portable-pixmap" };

    static final String writerClassName = "imageio.PPMWriter";
    static final Class[] outputTypes = { ImageOutputStream.class };
    static final String[] readerSpiNames = null;

    static final boolean supportsStandardStreamMetadataFormat = false;
    static final String nativeStreamMetadataFormatName = null;
    static final String nativeStreamMetadataFormatClassName = null;
    static final String[] extraStreamMetadataFormatNames = null;
    static final String[] extraStreamMetadataFormatClassNames = null;
    static final boolean supportsStandardImageMetadataFormat = false;
    static final String nativeImageMetadataFormatName = null;
    static final String nativeImageMetadataFormatClassName = null;
    static final String[] extraImageMetadataFormatNames = null;
    static final String[] extraImageMetadataFormatClassNames = null;

    public PPMImageWriterSpi() {
        super(
            vendorName, version,
            names, suffixes, MIMETypes,
            writerClassName, outputTypes, readerSpiNames,
            supportsStandardStreamMetadataFormat,
            nativeStreamMetadataFormatName,
            nativeStreamMetadataFormatClassName,
            extraStreamMetadataFormatNames,
            extraStreamMetadataFormatClassNames,
            supportsStandardImageMetadataFormat,
            nativeImageMetadataFormatName,
            nativeImageMetadataFormatClassName,
            extraImageMetadataFormatNames,
            extraImageMetadataFormatClassNames
        );
    }

    public boolean canEncodeImage(ImageTypeSpecifier imageType) {
        return true;
    }

    public String getDescription(Locale locale) {
        return "Write PPM images.";
    }

    /** Crée une instance de {@link imageio.PPMWriter}.
     *  @see imageio.PPMWriter
     */
    public ImageWriter createWriterInstance(Object extension) {
        return new PPMWriter(this);
    }
}

