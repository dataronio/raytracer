
public class Light
{
    private Point3d position;
    private double[] intensity;

    public Light(Point3d position_, double[] rgbIntensity)
    {
        position = new Point3d(position_);
        intensity = new double[3];
        System.arraycopy(rgbIntensity, 0, intensity, 0, 3);
    }

    public Point3d getPosition()
    {
        return new Point3d(position);
    }

    /**
     * @param color la composante (0 = rouge, 1 = vert, 2 = bleu) voulue.
     * @throws IndexOutOfBoundsException si color < 0 ou color > 2.
     */
    public double getIntensity(int color)
    {
        return intensity[color];
    }

}
