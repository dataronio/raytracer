package gui;
import javax.swing.JFormattedTextField;
import java.text.Format;

/** Représente un champ d'un onglet
 *
 * @author Maxime Arthaud
 */
public class TabField
{
    /** L'identifiant du champ */
    private String identifier;

    /** L'étiquette du champ */
    private String label;

    /** Le champ texte */
    protected JFormattedTextField textField;

    /** Constructeur
     * @param identifier L'identifiant du champ
     * @param label L'étiquette du champ
     */
    public TabField(String identifier, String label)
    {
        this.identifier = identifier;
        this.label = label;
        this.textField = new JFormattedTextField();
    }

    /** Constructeur
     * @param identifier L'identifiant du champ
     * @param label L'étiquette du champ
     * @param format Le format du champ
     */
    public TabField(String identifier, String label, Format format)
    {
        this(identifier, label, format, null);
    }

    /** Constructeur
     * @param identifier L'identifiant du champ
     * @param label L'étiquette du champ
     * @param format Le format du champ
     * @param init La valeur initiale du champ
     */
    public TabField(String identifier, String label, Format format, Object init)
    {
        this.identifier = identifier;
        this.label = label;
        this.textField = new JFormattedTextField(format);
        if(init != null)
            this.textField.setValue(init);
    }

    /** Retourne l'identifiant du champ
     * @return L'identifiant du champ
     */
    public String getIdentifier()
    {
        return new String(this.identifier);
    }

    /** Retourne l'étiquette du champ
     * @return L'étiquette du champ
     */
    public String getLabel()
    {
        return new String(this.label);
    }

    /** Retourne le champ text */
    public JFormattedTextField getTextField()
    {
        return this.textField;
    }

    /** Retourne la valeur du champ
     * @return La valeur du champ
     */
    public String getValue()
    {
        return this.textField.getText();
    }

    /** Retourne la valeur à écrire dans le fichier */
    public String toString()
    {
        return getIdentifier() + "=" + getValue();
    }

    /** Retourne si la valeur actuelle est la valeur par défaut 
     * dans ce cas, l'écriture dans le fichier n'est pas necéssaire
     */
    public boolean isDefault()
    {
        return false;
    }
}
