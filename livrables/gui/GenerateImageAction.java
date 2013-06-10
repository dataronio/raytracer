package gui;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.awt.EventQueue;

/**
 * Action qui génère un rendu d'une scène en parallèle.
 */
public class GenerateImageAction extends AbstractAction {
    Gui gui;

    public GenerateImageAction(Gui gui) {
        this.gui = gui;
    }

    /**
     * @param event Ignoré.
     */
    public void actionPerformed(ActionEvent event) {
        JOptionPane.showMessageDialog(
            null, "Génération en cours",
            "La génération de l'image est lancée,"
        +   "l'image sera affiché quand elle sera terminée.",
            JOptionPane.INFORMATION_MESSAGE
        );
        Thread thread = new Thread(new InvokeRenderer(gui));
        thread.setDaemon(true);
        thread.start();
    }
}

/**
 * Lance la génération de l'image en parallèle.
 */
class InvokeRenderer implements Runnable {
    Gui gui;

    public InvokeRenderer(Gui gui_) {
        gui = gui_;
    }

    public void run() {
        Runtime runtime = Runtime.getRuntime();
        File in;
       
        // Création de fichier temporaire
        try {
            in = File.createTempFile("raytracer_input", null);
        }
        catch(IOException e) {
            error("Erreur lors de la création du fichier temporaire :\n"
                    + e.getMessage());
            return;
        }

        // Écriture de la scène dans le fichier in
        try {
            FileWriter writer = new FileWriter(in);
            writer.write(this.gui.getText());
            writer.close();
        }
        catch(IOException e) {
            error("Erreur lors de l'écriture dans un fichier temporaire :\n"
                    + e.getMessage());
            return;
        }
        // Géneration du rendu
        Process process;
       
        try {
            process = runtime.exec(
                "java raytracer.RayTracer " + in.getPath() + " " + in.getPath()
            );
            int code = process.waitFor();
            
            if(code != 0) { // Erreur de parsage
                String content
                    = new Scanner(process.getInputStream()).useDelimiter("\\Z")
                                                           .next();  
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
            // todo: s'il y a plusieurs caméras le nom de fichier est numéroté
            // et il faut afficher toutes les images
            runtime.exec("eog " + in.getPath() + ".png");
        }
        catch(IOException e) { // A priori, c'est quand eog n'est pas installé
            error("Erreur lors de l'affichage du rendu :\n" + e.getMessage());
            return;
        }
    }

    /**
     * Affiche une boite de dialogue qui indique une erreur.
     * @param message Un message décrivant l'erreur.
     */
    private void error(String message) {
        JOptionPane.showMessageDialog(
            gui.getWindow(), message, "Erreur", JOptionPane.ERROR_MESSAGE
        );
    }
}

