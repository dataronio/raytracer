
import java.util.*;
import java.awt.Color;


/**
 * Class Object
 * Objet simple, en un seul « bloc », qui contient donc des propriétés optiques
 * uniques et une géométrie simple.
 */
abstract public class Object extends BasicObject {

    private java.awt.Color color;
    private double transparency;
    private double absorbance;
    private double reflectance;
    private double refractiveIndex;

    public Object () {};

    /**
     * Set the value of color
     * @param newVar the new value of color
     */
    private void setColor ( java.awt.Color newVar )
    {
        color = newVar;
    }

    /**
     * Get the value of color
     * @return the value of color
     */
    private java.awt.Color getColor ( )
    {
        return color;
    }

    /**
     * Set the value of transparency
     * @param newVar the new value of transparency
     */
    private void setTransparency ( double newVar )
    {
        transparency = newVar;
    }

    /**
     * Get the value of transparency
     * @return the value of transparency
     */
    private double getTransparency ( )
    {
        return transparency;
    }

    /**
     * Set the value of absorbance
     * @param newVar the new value of absorbance
     */
    private void setAbsorbance ( double newVar )
    {
        absorbance = newVar;
    }

    /**
     * Get the value of absorbance
     * @return the value of absorbance
     */
    private double getAbsorbance ()
    {
        return absorbance;
    }

    /**
     * Set the value of reflectance
     * @param newVar the new value of reflectance
     */
    private void setReflectance ( double newVar )
    {
        reflectance = newVar;
    }

    /**
     * Get the value of reflectance
     * @return the value of reflectance
     */
    private double getReflectance ( )
    {
        return reflectance;
    }

    /**
     * Set the value of refractiveIndex
     * @param newVar the new value of refractiveIndex
     */
    private void setRefractiveIndex ( double newVar )
    {
      refractiveIndex = newVar;
    }

    /**
     * Get the value of refractiveIndex
     * @return the value of refractiveIndex
     */
    private double getRefractiveIndex ()
    {
        return refractiveIndex;
    }

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
