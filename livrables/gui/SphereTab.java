package gui;

/** Onglet de création d'une sphère
 *
 * @author Maxime Arthaud
 */
public class SphereTab extends ObjectTab
{
    /** Constructeur par défaut 
     */
    public SphereTab()
    {
        super("Sphere");
    }

    /** Met en place les widgets dans le layout */
    @Override
    protected void setupFields()
    {
        fields.add(new Point3dTabField("center", "Centre"));
        fields.add(new DoubleTabField("radius", "Rayon"));
        super.setupFields();
    }
}
