package gui;
import java.awt.Color;

/** Onglet de création d'un objet.
 *
 * @author Maxime Arthaud
 */
public class ObjectTab extends Tab
{    
    /** Constructeur 
     * @param name Le nom de l'onglet
     */
    public ObjectTab(String name)
    {
        super(name);
    }

    /** Met en place les widgets dans le layout */
    protected void setupFields()
    {
        Double[] zeros = {0.0, 0.0, 0.0};
        Double[] ones = {1.0, 1.0, 1.0};

        fields.add(new ColorTabField("color", "Couleur", Color.WHITE, Color.WHITE));
        fields.add(new Point3dTabField("transparency", "Transparence", zeros, zeros));
        fields.add(new Point3dTabField("absorbance", "Absorbance", ones, ones));
        fields.add(new Point3dTabField("reflectance", "Réflectance", zeros, zeros));
        fields.add(new Point3dTabField("refractiveIndex", "Indice de réfraction", ones, ones));
    }
}
