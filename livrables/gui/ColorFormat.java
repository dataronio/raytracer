package gui;
import java.text.*;
import java.awt.Color;

/** Format d'une couleur : #RRGGBB
 *
 * @author Maxime Arthaud
 */
public class ColorFormat extends Format
{
    /**
     * Ecrit la couleur sous forme de chaine de caractères.
     *
     * @param obj La couleur à formater
     * @param toAppendTo Le buffer où notre chaine va être écrite (ignoré)
     * @param pos La position du champ (ignoré)
     *
     * @return La couleur sous forme de chaine de caractères
     */
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos)
    {
        Color c = (Color)obj;
        StringBuffer s = new StringBuffer("#");
        s.append(Integer.toHexString(c.getRGB()).substring(2));
        return s;
    }

    /**
     * Parse une chaine de caractères et retourne une couleur.
     *
     * @param source La chaine de caractères
     * @param pos un objet ParsePosition avec des informations de parsage
     *
     * @return La couleur, ou null en cas d'erreur
     */
    public Object parseObject(String source, ParsePosition pos)
    {
        int i = pos.getIndex();

        if(source.length() - i < 7)
            return null;
        else
        {
            String hex = source.substring(i + 1, i + 7);
            try 
            {
                int rgb = Integer.parseInt(hex, 16);
                pos.setIndex(i + 7);
                return new Color(rgb);
            }
            catch(NumberFormatException e)
            {
                return null;
            }
        }
    }
}
