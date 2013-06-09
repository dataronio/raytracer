package gui;

/** Onglet de création d'un cube
 *
 * @author Maxime Arthaud
 */
public class CubeTab extends ObjectTab
{
    /** Constructeur par défaut 
     */
    public CubeTab()
    {
        super("Cube");
    }

    /** Met en place les widgets dans le layout */
    @Override
    protected void setupFields()
    {
        fields.add(new Point3dTabField("p1", "Point 1"));
        fields.add(new Point3dTabField("p2", "Point 2"));
        fields.add(new Point3dTabField("p3", "Point 3"));
        fields.add(new Point3dTabField("p4", "Point 4"));
        super.setupFields();
    }
}
