package raytracer;

import java.util.*;
import java.util.regex.*;

/**
 * Class FileReader
 */
public class FileReader {
    private FileReader() {}

    /**
     * Construit une Scene en parsant le Scanner donné en entré.
     * @return La scène créée
     * @throws IllegalStateException si le scanner est fermé.
     * @throws InvalidFormatException si le scanner ne respecte pas le format.
     */
    public static Scene read(java.util.Scanner scanner)
    throws IllegalStateException, InvalidFormatException {
        Vector<BasicObject> objects = new Vector<BasicObject>();
        Vector<Light> lights = new Vector<Light>();
        List<Camera> cameras = new ArrayList<Camera>();
        double[] ambientLights = new double[]{0.1, 0.1, 0.1};

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine().toLowerCase();

            Matcher m = Utils.commentPattern.matcher(line);

            if(m.matches()) {
                continue;
            }

            m = Utils.namePattern.matcher(line);

            if(!m.matches()) {
                throw new InvalidFormatException(
                    "Format invalide : ligne \"" + line + "\" non-valide"
                );
            }

            if(m.group(1).equals("camera")) {
                cameras.add(buildCamera(Utils.parseParams(m.group(2))));
            }
            else if(m.group(1).equals("light")) {
                lights.add(buildLight(Utils.parseParams(m.group(2))));
            }
            else if(m.group(1).equals("ambientlights")) {
                ambientLights = Utils.parse3Array(m.group(2));
            }
            else {
                objects.add(
                    buildObject(m.group(1), Utils.parseParams(m.group(2)))
                );
            }
        }

        if(cameras.isEmpty()) {
            throw new InvalidFormatException(
                "Format invalide : caméra manquante"
            );
        }

        return new Scene(objects, lights, cameras, ambientLights);
    }

    /**
     * Construit une Texture à partir des paramètres donnés. Les valeurs par
     * défaut pour les paramètres non-passés sont spécifiées dans Texture.java.
     */
    private static Texture buildTexture(HashMap<String, String> params)
    throws InvalidFormatException {
        Texture t = new Texture();

        try {
            if(params.containsKey("brightness")) {
                t.brightness = Double.parseDouble(params.get("brightness"));
            }
            if(params.containsKey("k_specular")) {
                t.k_specular = Double.parseDouble(params.get("k_specular"));
            }
            if(params.containsKey("k_diffuse")) {
                t.k_diffuse = Utils.parse3ArrayPar(params.get("k_diffuse"));
            }
            if(params.containsKey("k_reflection")) {
                t.k_reflection = Double.parseDouble(params.get("k_reflection"));
            }
            if(params.containsKey("k_refraction")) {
                t.k_refraction
                    = Utils.parse3ArrayPar(params.get("k_refraction"));
            }
            if(params.containsKey("refractive_index")) {
                t.refractive_index
                    = Double.parseDouble(params.get("refractive_index"));
            }
        }
        catch (NumberFormatException e) {
            throw new InvalidFormatException(
                "Format invalide : nombre invalide", e
            );
        }

        return t;
    }

    /**
     * Construit une caméra à partir des paramètres donnés.
     * Une exception est lancé s'il en manque un.
     */
    private static Camera buildCamera(HashMap<String, String> params)
    throws InvalidFormatException {
        String[] required = {
            "eye", "origin", "abscissa", "ordinate",
            "widthpixel", "heightpixel"
        };

        if(!params.keySet().containsAll(Arrays.asList(required))) {
            throw new InvalidFormatException(
                "Format invalide : paramètre manquant pour Camera"
            );
        }

        int width, height;

        try {
            width = Integer.parseInt(params.get("widthpixel"));
            height = Integer.parseInt(params.get("heightpixel"));
        }
        catch (NumberFormatException e) {
            throw new InvalidFormatException(
                "Format invalide : hauteur ou largeur de Camera invalide", e
            );
        }

        if(width <= 0 || height <= 0) {
            throw new InvalidFormatException(
                "Format invalide : hauteur ou largeur de Camera négative"
            );
        }

        return new Camera(
            new Point3d(Utils.parse3ArrayPar(params.get("eye"))),
            new Point3d(Utils.parse3ArrayPar(params.get("origin"))),
            new Vector3d(Utils.parse3ArrayPar(params.get("abscissa"))),
            new Vector3d(Utils.parse3ArrayPar(params.get("ordinate"))),
            width, height
        );
    }

    /**
     * Construit une Light à partir des paramètres donnés.
     * pos et intensity sont obligatoires.
     */
    private static
    Light buildLight(HashMap<String, String> params)
    throws InvalidFormatException {
        if(!params.containsKey("pos") || !params.containsKey("intensity")) {
            throw new InvalidFormatException(
                "Format invalide : paramètre manquant pour Light"
            );
        }

        return new Light(
            new Point3d(Utils.parse3ArrayPar(params.get("pos"))),
            Utils.parse3ArrayPar(params.get("intensity"))
        );
    }

    /**
     * Liste des ObjectsBuilders connus.
     */
    private static ObjectBuilder[] objectBuilders = new ObjectBuilder[]{
        new SphereBuilder(),
        new PlaneBuilder(),
        new CubeBuilder(),
        new TriangleBuilder()
    };

    /**
     * Construit un objet du type donné par name à partir des paramètres
     * donnés en utilisant les ObjectBuilders.
     */
    private static
    BasicObject buildObject(String name, HashMap<String, String> params)
    throws InvalidFormatException {
        Texture texture = buildTexture(params);

        for(ObjectBuilder b : objectBuilders) {
            if(name.equals(b.objectName())) {
                return b.build(params, texture);
            }
        }


        throw new InvalidFormatException("Objet inconnu :" + name);
    }
}

/**
 * Les classes permettant de créer les objects doivent implémenter cette
 * interface.
 */ 
interface ObjectBuilder {
    /** Retourne le nom de l'objet que peut construire ce builder. */
    public String objectName();

    /** Construit un objet.
     * @param params la table des paramètres
     * @param texture la texture de l'objet
     * @return l'objet créé
     */
    public BasicObject build(HashMap<String, String> params, Texture texture)
        throws InvalidFormatException;
}

class SphereBuilder implements ObjectBuilder {
    public String objectName() { return "sphere"; }

    public BasicObject build(HashMap<String, String> params, Texture texture)
    throws InvalidFormatException {
        if(!params.containsKey("center") || !params.containsKey("radius")) {
            throw new InvalidFormatException(
                "Format invalide : paramètre manquant pour Sphere"
            );
        }

        return new Sphere(
            texture,
            new Point3d(Utils.parse3ArrayPar(params.get("center"))),
            Double.parseDouble(params.get("radius"))
        );
    }
}

class PlaneBuilder implements ObjectBuilder {
    public String objectName() { return "plane"; }

    public BasicObject build(HashMap<String, String> params, Texture texture)
    throws InvalidFormatException {
        if(!params.containsKey("p1")
        || !params.containsKey("p2")
        || !params.containsKey("p3")) {
            throw new InvalidFormatException(
                "Format invalide : point manquant pour Plan"
            );
        }

        return new Plane(
            texture,
            new Point3d(Utils.parse3ArrayPar(params.get("p1"))),
            new Point3d(Utils.parse3ArrayPar(params.get("p2"))),
            new Point3d(Utils.parse3ArrayPar(params.get("p3")))
        );
    }
}

class TriangleBuilder implements ObjectBuilder {
    public String objectName() { return "triangle"; }

    public BasicObject build(HashMap<String, String> params, Texture texture)
    throws InvalidFormatException {
        if(!params.containsKey("p1")
        || !params.containsKey("p2")
        || !params.containsKey("p3")) {
            throw new InvalidFormatException(
                "Format invalide : point manquant pour Triangle"
            );
        }

        return new Triangle(
            texture,
            new Point3d(Utils.parse3ArrayPar(params.get("p1"))),
            new Point3d(Utils.parse3ArrayPar(params.get("p2"))),
            new Point3d(Utils.parse3ArrayPar(params.get("p3")))
        );
    }
}

class CubeBuilder implements ObjectBuilder {
    public String objectName() { return "cube"; }

    public BasicObject build(HashMap<String, String> params, Texture texture)
    throws InvalidFormatException {
        if(!params.containsKey("p1")
        || !params.containsKey("p2")
        || !params.containsKey("p3")
        || !params.containsKey("p4")) {
            throw new InvalidFormatException(
                "Format invalide : point manquant pour Cube"
            );
        }

        return new Cube(
            texture,
            new Point3d(Utils.parse3ArrayPar(params.get("p1"))),
            new Point3d(Utils.parse3ArrayPar(params.get("p2"))),
            new Point3d(Utils.parse3ArrayPar(params.get("p3"))),
            new Point3d(Utils.parse3ArrayPar(params.get("p4")))
        );
    }
}

/** 
 * Contient des méthodes utiles à tout les ObjectBuilders.
 */
class Utils {
    // capture Nom et params dans une expression de la forme
    // "Nom(params) // commentaire optionnel"
    public static final Pattern namePattern
        = Pattern.compile("(\\w+)[\\t ]*\\((.+)\\)([\\t ]*//.+)?");

    // capture nom et valeur dans une chaine de la forme "nom = valeur"
    public static final Pattern nameValuePattern
        = Pattern.compile("[\\t ]*(\\w+)[\\t ]*=[\\t ]*(.*)[\\t ]*");

    // matche les lignes de commentaire
    public static final Pattern commentPattern
        = Pattern.compile("([\\t ]*//.*)?");

    /**
     * Découpe une chaine de caractère selon les ',' en respectant les
     * paires de parenthèses.
     */
    public static Vector<String> split(String params)
    throws InvalidFormatException {
        int par = 0;
        int begin = 0;
        Vector<String> v = new Vector<String>();

        for(int i = 0; i < params.length(); ++i) {
            switch(params.charAt(i)) {
                case '(':
                    ++par;
                    break;
                case ')':
                    if(par == 0) {
                        throw new InvalidFormatException(
                            "Format invalide : parenthèse \')\' sauvage"
                        );
                    }
                    --par;
                    break;
                case ',':
                    if(par == 0) {
                        v.add(params.substring(begin, i));
                        begin = i+1;
                    }
            }
        }

        if(par != 0) {
            throw new InvalidFormatException(
                "Format invalide : parenthèse non fermée"
            );
        }

        v.add(params.substring(begin));

        return v;
    }

    /**
     * Découpe une chaine de la forme "nom1=valeur1, nom2=valeur2, ..."
     * en une table associative T telle que :
     *   T["nom1"] = "valeur1"
     *   T["nom2"] = "valeur2"
     *   ...
     */
    public static HashMap<String, String> parseParams(String params)
    throws InvalidFormatException {
        Vector<String> list = split(params);
        HashMap<String, String> result = new HashMap<String, String>();

        for(String c : list) {
            Matcher m = nameValuePattern.matcher(c);

            if(!m.matches()) {
                throw new InvalidFormatException(
                    "Format invalide : \"" + c + "\""
                );
            }

            String name = m.group(1);
            if(result.containsKey(name)) {
                throw new InvalidFormatException(
                    "Format invalide : \"" + name + "\" apparait 2 fois"
                );
            }

            result.put(name, m.group(2));
        }

        return result;
    }

    /**
     * Crée un tableau de 3 doubles à partir d'une chaine de la forme "(x,y,z)"
     */
    public static double[] parse3ArrayPar(String params)
    throws InvalidFormatException {
        if(!params.startsWith("(") || !params.endsWith(")")) {
            throw new InvalidFormatException(
                "Format invalide : point ou vecteur mal parenthèsé"
            );
        }

        return parse3Array(params.substring(1, params.length()-1));
    }

    /**
     * Crée un tableau de 3 double à partir d'une chaine de la forme "x, y, z".
     */
    public static double[] parse3Array(String params)
    throws InvalidFormatException {
        String[] xyz = params.split(",");

        if(xyz.length != 3) {
            throw new InvalidFormatException(
                "Format invalide : point ou vecteur n'a pas 3 coordonnées"
            );
        }

        try {
            return new double[]{
                Double.parseDouble(xyz[0]),
                Double.parseDouble(xyz[1]),
                Double.parseDouble(xyz[2])
            };
        }
        catch(NumberFormatException e) {
            throw new InvalidFormatException(
                "Format invalide : coordonnée invalide", e
            );
        }
    }
}

