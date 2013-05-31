package gui;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;

/** Interface Graphique pour la gestion de scène.
 * 
 * @author Maxime Arthaud
 */
public class Gui
{
    /** Fenêtre principale */
    private JFrame window;

    /** Scène à gérer */
    private Scene scene;

    /** Construire une interface de gestion de scène
     * @param scene la scène à gérer
     * @param windowTitle le titre de la scène
     */
    public Gui(Scene scene, String windowTitle)
    {
        this.scene = scene;

        // Création de la fenêtre
        this.window = new JFrame(windowTitle);

        // Création du menu
        JMenuBar bar = new JMenuBar();
        this.window.setJMenuBar(bar);

        JMenu fileMenu = new JMenu("Fichier");
        fileMenu.add(new QuitAction());

        // Affichage
        this.window.pack();
        this.window.setVisible(true);
    }

    /** Construire une interface de gestion de scène
     * @param scene la scène à gérer
     */
    public Gui(Scene scene)
    {
        this(scene, "Gestionnaire de Scène");
    }


    /** Action qui permet de fermer la fenêtre */
    class QuitAction extends AbstractAction
    {
        public QuitAction()
        {
            super("Quitter");
        }

        public void actionPerformed(ActionEvent a)
        {
            window.dispose();
        }
    }
}
