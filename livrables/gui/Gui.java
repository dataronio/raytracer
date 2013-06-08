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

        // Mise en place des widgets
        tabbedPane = new JTabbedPane();
        JPanel buttonPane = new JPanel();
        JButton generateImageButton = new JButton("Voir l'image");
        JButton addButton = new JButton("Ajouter");
        JButton saveButton = new JButton("Enregister");
        text = new JTextArea();
        JScrollPane scrollText = new JScrollPane(text);

        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(generateImageButton);
        buttonPane.add(saveButton);
        buttonPane.add(addButton);

        Container container = this.window.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        container.add(tabbedPane);
        container.add(buttonPane);
        container.add(scrollText);

        // Ajout des onglets
        tabbedPane.addTab("Camera", new CameraTab());
        tabbedPane.addTab("Lumière Ambiante", new AmbientLightTab());
        tabbedPane.addTab("Lumière", new LightTab());
        tabbedPane.addTab("Cube", new CubeTab());
        tabbedPane.addTab("Plan", new PlaneTab());
        tabbedPane.addTab("Sphère", new SphereTab());
        tabbedPane.addTab("Triangle", new TriangleTab());

        // Ajout des actions
        generateImageButton.addActionListener(new GenerateImageAction(this));
        addButton.addActionListener(new AddAction());
        saveButton.addActionListener(new SaveAction());

        // Lecture du fichier
        try
        {
            String content = new Scanner(file).useDelimiter("\\Z").next();  
            text.setText(content);
        }
        catch(FileNotFoundException e) {}

        // Affichage
        this.window.setPreferredSize(new Dimension(600, 600));
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

    /** Retourne la scène actuelle, sous forme textuelle
     * @return La scène actuelle
     */
    public String getText()
    {
        return this.text.getText();
    }

    /** Retourne la fenêtre principale
     * @return La fenêtre principale
     */
    public JFrame getWindow()
    {
        return this.window;
    }

    /** Action qui permet d'enregistrer la scène */
    class SaveAction extends AbstractAction
    {
        public void actionPerformed(ActionEvent a)
        {
            try
            {
                FileWriter writer = new FileWriter(file);
                writer.write(text.getText());
                writer.close();
            }
            catch(IOException e)
            {
                JOptionPane.showMessageDialog(window, "Erreur : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
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
