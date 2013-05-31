package gui;
import java.util.*;

/** Classe abstraite représentant une scène.
 *
 * @author Maxime Arthaud
 */
abstract public class Scene
{
    /** La liste des objets de la scène */
    protected List<SceneObject> objects;

    // TOTO : Ajouter la caméra


    /** Constructeur par défaut */
    public Scene()
    {
        this.objects = new LinkedList<SceneObject>();
    }
    
    /** Ajoute un objet à la scène.
     * @param object l'objet à ajouter
     */
    public void add(SceneObject object)
    {
        this.objects.add(object);
    }

    /** Supprime un objet de la scène.
     * @param object l'objet à supprimer
     */
    public void delete(SceneObject object)
    {
        this.objects.removeAll(object);
    }

    /** Enregistre la scène */
    public void save();
}
