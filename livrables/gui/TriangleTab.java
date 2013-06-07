package gui;

/** Onglet de création d'un triangle
 *
 * @author Maxime Arthaud
 */
public class TriangleTab extends ObjectTab
{
    /** Constructeur par défaut 
     */
    public TriangleTab()
    {
        super("Triangle");
    }

    /** Met en place les widgets dans le layout */
    protected void setupFields()
    {
        fields.add(new Point3dTabField("p1", "Point 1"));
        fields.add(new Point3dTabField("p2", "Point 2"));
        fields.add(new Point3dTabField("p3", "Point 3"));
        super.setupFields();
    }
}
