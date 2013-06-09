package gui;

/** Onglet de création de la caméra
 *
 * @author Maxime Arthaud
 */
public class CameraTab extends Tab
{
    /** Constructeur par défaut 
     */
    public CameraTab()
    {
        super("Camera");
    }

    /** Met en place les widgets dans le layout */
    @Override
    protected void setupFields()
    {
        fields.add(new Point3dTabField("eye", "Position de l'oeil"));
        fields.add(new Point3dTabField("origin", "Origine de l'écran"));
        fields.add(new Point3dTabField("abscissa", "Axe des abscisses"));
        fields.add(new Point3dTabField("ordinate", "Axe des ordonnées"));
        fields.add(new IntegerTabField("widthPixel", "Largeur en pixels", 100));
        fields.add(new IntegerTabField("heightPixel", "Hauteur en pixels", 100));
    }
}
