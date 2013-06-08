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

        fields.add(new DoubleTabField("brightness", "Brillance", 30.0, 30.0));
        fields.add(new DoubleTabField("k_specular", "Spéculaire", 1.0, 1.0));
        fields.add(new Point3dTabField("k_diffuse", "Composante diffuse", ones, ones));
        fields.add(new DoubleTabField("k_reflection", "Réflection", 0.0, 0.0));
        fields.add(new Point3dTabField("k_refraction", "Réfraction", zeros, zeros));
        fields.add(new DoubleTabField("refractive_index", "Indice de réfraction", 1.0, 1.0));
    }
}
