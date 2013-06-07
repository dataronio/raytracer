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
    /** Contructeur par défaut
     * @param identifier L'identifiant du champ
     * @param label L'étiquette du champ
     */
    public Point3dTabField(String identifier, String label)
    {
        this(identifier, label, null);
    }

    /** Contructeur
     * @param identifier L'identifiant du champ
     * @param label L'étiquette du champ
     * @param init La valeur initiale du champ
     */
    public Point3dTabField(String identifier, String label, Double[] init)
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
        {
            init = new Double[3];
            for(int i=0; i<3; i++)
                init[i] = 0.0;
        }
        this.textField.setValue(init);
    }
}
