package gui;
import javax.swing.*;
import java.text.*;
import java.util.Locale;

/** Représente un champ pour un Point en 3D
 *
 * @author Maxime Arthaud
 */
public class Point3dTabField extends TabField
{
    /** Valeur par défaut
     * null s'il n'y en a pas
     */
    protected Double[] default_;

    /** Valeur très petite pour la comparaison de double */
    public static double EPSILON = 0.0000001;

    /** Contructeur
     * @param identifier L'identifiant du champ
     * @param label L'étiquette du champ
     */
    public Point3dTabField(String identifier, String label)
    {
        this(identifier, label, null, null);
    }

    /** Constructeur
     * @param identifier L'identifiant du champ
     * @param label L'étiquette du champ
     * @param init La valeur initiale
     */
    public Point3dTabField(String identifier, String label, Double[] init)
    {
        this(identifier, label, init, null);
    }

    /** Contructeur
     * @param identifier L'identifiant du champ
     * @param label L'étiquette du champ
     * @param init La valeur initiale du champ
     * @param default_ La valeur par défaut. null s'il n'y en a pas.
     */
    public Point3dTabField(String identifier, String label, Double[] init, Double[] default_)
    {
        super(identifier, label);

        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(false);
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.'); // ne marche pas pour une raison inconnue, du coup on met Locale.ENGLISH plus bas
        df.setDecimalFormatSymbols(dfs);
        String pattern = df.toPattern();

        MessageFormat format = new MessageFormat("({0,number," + pattern + "}, {1,number," + pattern + "}, {2,number," + pattern + "})", Locale.ENGLISH);
        this.textField = new JFormattedTextField(format);
        
        if(init == null)
            init = new Double[]{0.0, 0.0, 0.0};
        this.textField.setValue(init);
        this.default_ = default_;
    }

    /** Retourne si la valeur actuelle est la valeur par défaut 
     * dans ce cas, l'écriture dans le fichier n'est pas necéssaire
     */
    @Override
    public boolean isDefault()
    {
        if(this.default_ == null)
            return false;
        else
        {
            Object[] val = (Object[]) this.textField.getValue();
            double distance = Math.sqrt(
                    Math.pow(((Number)val[0]).doubleValue() - this.default_[0], 2.0) 
                    + Math.pow(((Number)val[1]).doubleValue() - this.default_[1], 2.0)
                    + Math.pow(((Number)val[2]).doubleValue() - this.default_[2], 2.0)
            );
            return distance < EPSILON;
        }
    }
}
