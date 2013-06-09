package raytracer;


public class Light
{
    /** Position de la lumière */
    private Point3d position;

    /** Intensité lumineuse */
    private double[] intensity;

    /** Constructeur
     * @param position La position (copié)
     * @param rgbIntensity L'intensité (copié)
     */
    public Light(Point3d position_, double[] rgbIntensity)
    {
        position = new Point3d(position_);
        intensity = new double[3];
        System.arraycopy(rgbIntensity, 0, intensity, 0, 3);
    }

    /** Retourne la position */
    public Point3d getPosition()
    {
        return new Point3d(position);
    }

    /**
     * Retourne l'intensité d'une couleur voulue
     * @param color la composante (0 = rouge, 1 = vert, 2 = bleu) voulue.
     * @throws IndexOutOfBoundsException si color < 0 ou color > 2.
     */
    public double getIntensity(int color)
    {
        return intensity[color];
    }

}
