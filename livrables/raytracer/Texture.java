package raytracer;

public class Texture {
    public double[] absorbance = {1.0, 1.0, 1.0};
    public double[] color = {1.0, 1.0, 1.0}; // blanc par défaut
    public double[] reflectance = {0.0, 0.0, 0.0};
    public double[] refractance = {0.0, 0.0, 0.0};
    public double[] refractiveIndex = {1.0, 1.0, 1.0};

    public Texture() {}
}
