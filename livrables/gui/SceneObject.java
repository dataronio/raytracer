package gui;
import java.awt.Color;

/** Cette classe représente un objet de scène.
 *
 * @author Maxime Arthaud
 */
abstract public class SceneObject
{
    Color color;
    double transparency;
    double absorbance;
    double reflectance;
    double refractiveIndex;

    /** Constructeur par défaut */
    public class SceneObject()
    {
        this.color = Color.WHITE;
        this.transparency = 0.0;
        this.absorbance = 0.0;
        this.refectance = 0.0;
        this.refractiveIndex = 0.0;
    }
    
    /** Exporte l'objet sous un format texte pour un fichier de scène.
     * @return l'objet sous un format texte
     */
    public String export()
    {
        return this.class.name  + "(" + this.exportParams() + ")";
    }

    /** Exporte les paramètres de l'objet sous un format texte.
     * @return les paramètres sous un format texte
     */
    protected String exportParams()
    {
        return ""; // TODO
    }
}
