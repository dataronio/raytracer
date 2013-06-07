package gui;
import javax.swing.*;
import java.text.*;

/** Représente un champ pour un entier
 *
 * @author Maxime Arthaud
 */
public class IntegerTabField extends TabField
{
    /** Valeur par défaut
     * null s'il n'y en a pas
     */
    protected Integer default_;

    /** Constructeur par défaut
     * @param identifier L'identifiant du champ
     * @param label L'étiquette du champ
     */
    public IntegerTabField(String identifier, String label)
    {
        this(identifier, label, 0, null);
    }
    
    /** Constructeur
     * @param identifier L'identifiant du champ
     * @param label L'étiquette du champ
     * @param init La valeur initiale
     */
    public IntegerTabField(String identifier, String label, int init)
    {
        this(identifier, label, init, null);
    }

    /** Constructeur
     * @param identifier L'identifiant du champ
     * @param label L'étiquette du champ
     * @param init La valeur initiale
     * @param default_ La valeur par défaut. null s'il n'y en a pas.
     */
    public IntegerTabField(String identifier, String label, int init, Integer default_)
    {
        super(identifier, label);
        
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);

        this.textField = new JFormattedTextField(format);
        this.textField.setValue(new Integer(init));
        this.default_ = default_;
    }

    /** Retourne si la valeur actuelle est la valeur par défaut 
     * dans ce cas, l'écriture dans le fichier n'est pas necéssaire
     */
    public boolean isDefault()
    {
        return this.default_ != null && this.default_ == ((Number)this.textField.getValue()).intValue();
    }
}
