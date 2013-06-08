package raytracer;

public class Texture {
    public Texture() {}

    /* Composante spéculaire */
    public double brightness = 20; // plus ce nombre est grand, plus la composante spéculaire est restreinte à une zone faible (puissance du cosinus)
    public double k_specular = 1; // coefficient par lequel on multiplie la composante spéculaire

    /* Composante diffuse */
    public double[] k_diffuse = {1.0, 1.0, 1.0}; // coefficients par lesquels on multiplie la composante diffuse, un par couleur.

    /* Réflection */
    public double k_reflection = 0.; // coefficient par lequel on multiplie la composante réfléchie

    /* Réfraction */
    public double[] k_refraction = {0., 0., 0.}; // coefficient par lequel on multiplie la composante réfractée
    public double refractive_index = 1; // indice du milieu dans l'objet. 1 -> le même que l'air ambient.

}
