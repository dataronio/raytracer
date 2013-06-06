package gui;
import javax.swing.*;
import java.awt.*;
import java.util.*;

/** Classe qui représente un onglet 
 *
 * @author Maxime Arthaud
 */
abstract public class Tab extends JPanel
{
    /** Nom de l'onglet */
    protected String name;

    /** Liste des champs */
    protected java.util.List<TabField> fields;

    /** Constructeur par défaut 
     * @param name Le nom de l'onglet
     */
    public Tab(String name)
    {
        this.name = name;
        this.fields = new LinkedList<TabField>();

        this.setupFields();
        
        // Mise en place dans le layout
        setLayout(new GridLayout(this.fields.size(), 2));
        for(TabField f : this.fields)
        {
            add(new JLabel(f.getLabel() + " :"));
            add(f.getTextField());
        }
    }

    /** Met en place les champs */
    abstract protected void setupFields();

    /** Retourne le nom de l'onglet
     * @return le nom de l'onglet
     */
    public String getName()
    {
        return name;
    }

    /** Retourne la ligne à écrire dans le fichier */
    public String toString()
    {
        String s = new String();
        for(TabField f : this.fields)
        {
            if(!f.isDefault())
            {
                if(!s.isEmpty())
                    s += ", ";
                
                s += f.toString();
            }
        }

        return name + "(" + s + ")";
    }
}
