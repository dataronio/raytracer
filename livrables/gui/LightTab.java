package gui;
import java.awt.Color;

/** Onglet de création d'une lumière
 *
 * @author Maxime Arthaud
 */
public class LightTab extends Tab
{
    /** Constructeur par défaut 
     */
    public LightTab()
    {
        super("Light");
    }

    /** Met en place les widgets dans le layout */
    @Override
    protected void setupFields()
    {
        fields.add(new Point3dTabField("position", "Position"));
        fields.add(new ColorTabField("intensity", "Intensité", Color.WHITE));
    }
}
