package gui;

/** Onglet de création de la lumière ambiante
 *
 * @author Maxime Arthaud
 */
public class AmbientLightTab extends Tab
{
    /** Champ pour l'intensité */
    Point3dTabField intensity;

    /** Constructeur par défaut 
     */
    public AmbientLightTab()
    {
        super("AmbientLight");
    }

    /** Met en place les widgets dans le layout */
    @Override
    protected void setupFields()
    {
        Double[] init = {0.1, 0.1, 0.1};
        intensity = new Point3dTabField("intensity", "Intensité", init);
        fields.add(intensity);
    }

    /** Retourne la ligne à écrire dans le fichier */
    @Override
    public String toString()
    {
        return name + intensity.getValue();
    }
}
