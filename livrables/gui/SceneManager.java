package gui;
import java.awt.EventQueue;
import java.io.File;

/** Classe principale qui interprète les arguments et lance l'interface graphique
 *
 * @author Maxime Arthaud
 */
public class SceneManager
{
    public static void main(String[] args)
    {
        if(args.length > 1 || (args.length == 1 && args[0].equals("-h")))
        {
            System.out.println("usage: java gui.SceneManager [-h] [file]");
            System.out.println("");
            System.out.println("Gère un fichier de scène.");
            System.out.println("");
            System.out.println("arguments:");
            System.out.println("   file    Le fichier de scène. S'il n'existe pas, il sera créé.");
        }
        else
        {
            // le final est nécessaire pour y avoir accès dans la classe
            // anonyme ci-dessous
            final String filePath = args.length == 1 ? args[0] : null;

            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new Gui(filePath);
                }
            });
        }
    }
}
