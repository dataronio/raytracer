
public class Light
{
    private Point3d position;
    private double[] intensity;

    public Light(Point3d position_, double intensityR, double intensityG, double intensityB)
    {
        position = new Point3d(position_);
        intensity = {intensityR, intensityG, intensityB};
    }

    public Point3d getPosition()
    {
        return new Point3d(position);
    }

    public double getIntensity(int color)
    {
        assert(color >= 0 && color < 3);
        return intensity[color];
    }

}
