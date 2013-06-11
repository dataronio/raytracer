package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.awt.EventQueue;
import raytracer.*;
import java.awt.image.BufferedImage;

/**
 * Action qui génère un rendu d'une scène en parallèle.
 */
public class GenerateImageAction extends AbstractAction {
    private Gui gui;

    public GenerateImageAction(Gui gui) {
        this.gui = gui;
    }

    /**
     * @param event Ignoré.
     */
    public void actionPerformed(ActionEvent event) {
        Thread thread = new Thread(new InvokeRenderer(gui));
        thread.setDaemon(true);
        thread.start();

        JOptionPane.showMessageDialog(
            null,
            "La génération de l'image est lancée,\n"
        +   "l'image sera affiché quand elle sera terminée.",
            "Génération en cours",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}

/**
 * Lance la génération de l'image en parallèle.
 */
class InvokeRenderer implements Runnable {
    private Gui gui;

    public InvokeRenderer(Gui gui_) {
        gui = gui_;
    }

    public void run() {
        try
        {
            Scanner scanner = new Scanner(this.gui.getText());
            Scene scene = raytracer.FileReader.read(scanner);
            java.util.List<BufferedImage> images = scene.generateImages();

            for(BufferedImage image : images)
            {
                JFrame frame = new JFrame("Visionnage du rendu");
                frame.getContentPane().setLayout(new FlowLayout());
                frame.getContentPane().add(new JLabel(new ImageIcon(image)));
                frame.pack();
                frame.setVisible(true);
            }
        }
        catch (InvalidFormatException e) {
            error("Fichier incorrect :\n" + e.toString());
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

