package gui;

/** Onglet de création d'un plan
 *
 * @author Maxime Arthaud
 */
public class PlaneTab extends ObjectTab
{
    /** Constructeur par défaut 
     */
    public PlaneTab()
    {
        super("Plane");
    }

    /** Met en place les widgets dans le layout */
    @Override
    protected void setupFields()
    {
        fields.add(new Point3dTabField("p1", "Point 1"));
        fields.add(new Point3dTabField("p2", "Point 2"));
        fields.add(new Point3dTabField("p3", "Point 3"));
        super.setupFields();
    }
}
