package raytracer;

import imageio.PPMImageWriterSpi;
import java.awt.image.BufferedImage;
import javax.imageio.spi.IIORegistry;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

public class RayTracer {
    public static void main(String[] args) {
        if(args.length == 2) {
            File source = new File(args[0]);
            File output = new File(args[1]);
            Scanner scanner;

            try {
                scanner = new Scanner(source);
                Scene scene = FileReader.read(scanner);

                BufferedImage image = scene.generateImage();

                IIORegistry.getDefaultInstance()
                           .registerServiceProvider(new PPMImageWriterSpi());
                ImageIO.write(image, "png", output);
            }
            catch (FileNotFoundException | InvalidFormatException e) {
                System.out.println(
                    "Fichier \"" + args[0] + "\" illisible.\n" +
                    "Raison : " + e.toString()
                ); 
                System.exit(1);
            }
            catch (IOException e) {
                System.out.println("Erreur : " + e.toString());
                System.exit(1);
            }
        }
        else {
            System.out.println(
                "Usage: java Raytracer nomFichierSource nomFichierDestination"
            );
            System.exit(1);
        }
    }
}

