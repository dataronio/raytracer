
import java.util.*;
import java.awt.Color;


/**
 * Class Object
 * Objet simple, en un seul « bloc », qui contient donc des propriétés optiques
 * uniques et une géométrie simple.
 */
abstract public class Object extends BasicObject {

    private Texture texture;

    public Object (Texture texture_) {
        texture = new Texture(texture_);
    };

    /**
     * @return       java.awt.Color
     * @param        ray Le point de départ indique l'intersection.
     * @param        scene
     * @param        depth La profondeur de l'appel récursif.
     */
    public java.awt.Color computeColor( Ray ray, Scene scene, int depth )
    {
    }

}
