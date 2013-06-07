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
                if(!output.exists()) {
                    output.createNewFile();
                } 

                scanner = new Scanner(source);
                Scene scene = FileReader.read(scanner);

                BufferedImage image = scene.generateImage();

                ImageIO.scanForPlugins();
                IIORegistry.getDefaultInstance()
                           .registerServiceProvider(new PPMImageWriterSpi());

                String format = "png";
                List<String> splitOutputFile
                    = Arrays.asList(args[1].split("\\."));

                if(splitOutputFile.size() >= 2
                && Arrays.asList(ImageIO.getWriterFileSuffixes())
                                        .contains(splitOutputFile.get(1))
                ) {
                    format = splitOutputFile.get(1);
                }

                ImageIO.write(image, format, output);
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

