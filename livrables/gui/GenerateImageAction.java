package gui;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/** Action qui génère un rendu d'une scène
 *
 * @author Maxime Arthaud
 */
public class GenerateImageAction extends AbstractAction
{
    /** La gui */
    Gui gui;

    /** Constructeur
     * @param gui La gui associée
     */
    public GenerateImageAction(Gui gui)
    {
        this.gui = gui;
    }

    /** Affiche une boite de dialogue qui indique une erreur
     * @param message Un message décrivant l'erreur
     */
    private void error(String message)
    {
        JOptionPane.showMessageDialog(this.gui.getWindow(), message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    /** Action
     * @param event L'événement
     */
    public void actionPerformed(ActionEvent event)
    {
        Runtime runtime = Runtime.getRuntime();
        File in, out;
       
        // Création de fichier temporaire
        try {
            in = File.createTempFile("raytracer_input", null);
            out = File.createTempFile("raytracer_output", ".png");
        }
        catch(IOException e) {
            error("Erreur lors de la création de fichiers temporaires :\n" + e.getMessage());
            return;
        }

        // Écriture de la scène dans le fichier in
        try {
            FileWriter writer = new FileWriter(in);
            writer.write(this.gui.getText());
            writer.close();
        }
        catch(IOException e) {
            error("Erreur lors de l'écriture dans un fichier temporaire :\n" + e.getMessage());
            return;
        }

        // Géneration du rendu
        Process process;
       
        try {
            process = runtime.exec("java raytracer.RayTracer " + in.getPath() + " " + out.getPath());
            int code = process.waitFor();
            
            if(code != 0) // Erreur de parsage
            {
                String content = new Scanner(process.getInputStream()).useDelimiter("\\Z").next();  
                error("Erreur lors de la génération du rendu :\n" + content);
                return;
            }

        }
        catch(IOException e) {
            error("Erreur lors de la génération du rendu :\n" + e.getMessage());
            return;
        }
        catch(InterruptedException e) { // Interruption du thread
            return;
        }

        // Affichage
        try {
            runtime.exec("eog " + out.getPath());
        }
        catch(IOException e) { // A priori, c'est quand eog n'est pas installé
            error("Erreur lors de l'affichage du rendu :\n" + e.getMessage());
            return;
        }
    }
}
