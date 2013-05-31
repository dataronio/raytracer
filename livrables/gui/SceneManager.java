package gui;

/** Classe principale qui interprète les arguments et lance l'interface graphique
 *
 * @author Maxime Arthaud
 */
public class SceneManager
{
    public static void main(String[] args)
    {
        if(args.length == 0)
        {
            System.out.println("usage: java gui.SceneManager file");
            System.out.println("");
            System.out.println("Gère un fichier de scène.");
            System.out.println("");
            System.out.println("arguments:");
            System.out.println("   file    Le fichier de scène. S'il n'existe pas, il sera créé.");
        }
        else
        {
            String file = args[0];
        }
    }
}
