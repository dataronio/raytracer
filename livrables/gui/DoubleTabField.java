package gui;
import javax.swing.*;
import java.text.*;

/** Représente un champ pour un double
 *
 * @author Maxime Arthaud
 */
public class DoubleTabField extends TabField
{
    /** Valeur par défaut
     * null s'il n'y en a pas
     */
    protected Double default_;

    /** Valeur très petite pour la comparaison de double */
    public static double EPSILON = 0.0000001;

    /** Constructeur par défaut
     * @param identifier L'identifiant du champ
     * @param label L'étiquette du champ
     */
    public DoubleTabField(String identifier, String label)
    {
        this(identifier, label, 0.0, null);
    }
    
    /** Constructeur
     * @param identifier L'identifiant du champ
     * @param label L'étiquette du champ
     * @param init La valeur initiale
     */
    public DoubleTabField(String identifier, String label, double init)
    {
        this(identifier, label, init, null);
    }

    /** Constructeur
     * @param identifier L'identifiant du champ
     * @param label L'étiquette du champ
     * @param init La valeur initiale
     * @param default_ La valeur par défaut. null s'il n'y en a pas.
     */
    public DoubleTabField(String identifier, String label, double init, Double default_)
    {
        super(identifier, label);
        
        DecimalFormat format = new DecimalFormat();
        format.setMinimumFractionDigits(1);

        this.textField = new JFormattedTextField(format);
        this.textField.setValue(new Double(init));
        this.default_ = default_;
    }

    /** Retourne si la valeur actuelle est la valeur par défaut 
     * dans ce cas, l'écriture dans le fichier n'est pas necéssaire
     */
    public boolean isDefault()
    {
        if(this.default_ == null)
            return false;
        else
        {
            double val = ((Number)this.textField.getValue()).doubleValue();
            return Math.abs(val - this.default_) < EPSILON;
        }
    }
}
