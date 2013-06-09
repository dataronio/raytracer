package raytracer;

import imageio.PPMImageWriterSpi;
import java.awt.image.RenderedImage;
import javax.imageio.spi.IIORegistry;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

public class RayTracer {
    public static void main(String[] args) {
        if(args.length >= 2) {
            File source = new File(args[0]);
            Scanner scanner;

            try {
                // enregistre le format PPM auprès de Java.
                ImageIO.scanForPlugins();
                IIORegistry.getDefaultInstance()
                           .registerServiceProvider(new PPMImageWriterSpi());

                scanner = new Scanner(source);
                Scene scene = FileReader.read(scanner);

                List<RenderedImage> images = scene.generateImages();

                String format = args.length >= 3 ? args[2] : "png";

                if(images.size() > 0) {
                    saveImage(args[1], format, images.get(0));
                }
                else {
                    for(int i = 0; i < images.size(); ++i) {
                        saveImage(args[1], format, images.get(i));
                    }
                }
            }
            catch (FileNotFoundException e) {
                System.out.println(
                    "Fichier \"" + args[0] + "\" illisible.\n" +
                    "Raison : " + e.toString()
                ); 
                System.exit(1);
            }
            catch (InvalidFormatException e) {
                System.out.println(
                    "Fichier incorect.\n" +
                    "Raison : " + e.toString()
                ); 
                System.exit(1);
            }
        }
        else {
            System.out.println(
                "Usage: java raytracer.RayTracer source destination format\n"
            +   "\n"
            +   "Crée autant de fichiers que de caméras dans la source.\n"
            +   "Les fichiers sont nommés destination[nombre].format\n"
            +   "\n"
            +   "arguments :\n"
            +   "   source       Le fichier de scène.\n"
            +   "   destination  Le fichier de destination. "
            +                   "S'il n'existe pas, il sera créé. \n"
            +   "   format       Le format de fichier. Par défaut png."
            );
            System.exit(1);
        }
    }

    static private
    void saveImage(String name, String format, RenderedImage image) {
        try {
            File output = new File(name + "." + format);

            if(!output.exists()) {
                output.createNewFile();
            }

            ImageIO.write(image, format, output);
        }
        catch (IOException e) {
            System.out.println("Erreur : " + e.toString());
            System.exit(1);
        }
    }
}

