package gui;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/** Interface Graphique pour la gestion de scène.
 * 
 * @author Maxime Arthaud
 */
public class Gui
{
    /** Fenêtre principale */
    private JFrame window;

    /** Le widget contenant les onglets */
    private JTabbedPane tabbedPane;

    /** Fichier scène à gérer */
    private File file;

    /** La zone de texte */
    private JTextArea text;

    /** Construire une interface de gestion de scène
     * @param file le fichier scène à gérer
     * @param windowTitle le titre de la scène
     */
    public Gui(File file, String windowTitle)
    {
        this.file = file;

        // Création de la fenêtre
        this.window = new JFrame(windowTitle);

        // Création du menu
        JMenuBar bar = new JMenuBar();
        this.window.setJMenuBar(bar);

        JMenu fileMenu = new JMenu("Fichier");
        fileMenu.add(new QuitAction());

        // Mise en place des widgets
        tabbedPane = new JTabbedPane();
        JButton addButton = new JButton("Ajouter");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        text = new JTextArea();
        JScrollPane scrollText = new JScrollPane(text);

        Container container = this.window.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        container.add(tabbedPane);
        container.add(addButton);
        container.add(scrollText);

        // Ajout des onglets
        tabbedPane.addTab("Test", new ObjectTab("Test"));

        // Ajout des actions
        addButton.addActionListener(new AddAction());

        // Affichage
        this.window.setPreferredSize(new Dimension(600, 400));
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.pack();
        this.window.setVisible(true);
    }

    /** Construire une interface de gestion de scène
     * @param file le fichier scène à gérer
     */
    public Gui(File file)
    {
        this(file, "Gestionnaire de Scène");
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

    /** Action qui permet d'ajouter un objet dans la scène */
    class AddAction extends AbstractAction
    {
        public void actionPerformed(ActionEvent a)
        {
            String line = tabbedPane.getSelectedComponent().toString();
            
            if(!text.getText().isEmpty())
                line = "\n" + line;

            text.append(line);
        }
    }
}
