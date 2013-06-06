package gui;

/** Onglet de création d'un objet.
 *
 * @author Maxime Arthaud
 */
public class ObjectTab extends Tab
{    
    /** Constructeur par défaut 
     * @param name Le nom de l'onglet
     */
    public ObjectTab(String name)
    {
        super(name);
    }

    /** Met en place les widgets dans le layout */
    protected void setupFields()
    {
        fields.add(new DoubleTabField("transparency", "Transparence", 0.0, new Double(0.0)));
        fields.add(new DoubleTabField("absorbance", "Absorbance", 1.0, new Double(1.0)));
        fields.add(new DoubleTabField("reflectance", "Réflectance", 0.0, new Double(0.0)));
        fields.add(new DoubleTabField("refractiveIndex", "Indice de réfraction", 1.0, new Double(1.0)));
    }
}
