package gui;
import javax.swing.*;
import java.awt.*;
import java.text.*;

/** Représente un champ pour une couleur
 *
 * @author Maxime Arthaud
 */
public class ColorTabField extends TabField
{
    /** Valeur par défaut
     * null s'il n'y en a pas
     */
    protected Color default_;

    /** Constructeur
     * @param identifier L'identifiant du champ
     * @param label L'étiquette du champ
     */
    public ColorTabField(String identifier, String label)
    {
        this(identifier, label, Color.WHITE, null);
    }
    
    /** Constructeur
     * @param identifier L'identifiant du champ
     * @param label L'étiquette du champ
     * @param init La valeur initiale
     */
    public ColorTabField(String identifier, String label, Color init)
    {
        this(identifier, label, init, null);
    }

    /** Constructeur
     * @param identifier L'identifiant du champ
     * @param label L'étiquette du champ
     * @param init La valeur initiale
     * @param default_ La valeur par défaut. null s'il n'y en a pas.
     */
    public ColorTabField(String identifier, String label, Color init, Color default_)
    {
        super(identifier, label);
        
        this.textField = new JFormattedTextField(new ColorFormat());
        this.textField.setValue(init);
        this.default_ = default_;
    }

    /** Retourne si la valeur actuelle est la valeur par défaut 
     * dans ce cas, l'écriture dans le fichier n'est pas necéssaire
     */
    @Override
    public boolean isDefault()
    {
        return this.default_ != null && this.default_.equals((Color)this.textField.getValue());
    }
}
