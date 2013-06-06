import java.util.*;
import java.util.regex.*;

/**
 * Class FileReader
 */
public class FileReader {
    // capture Nom et params dans une expression de la forme "Nom(params)"
    static final Pattern namePattern
        = Pattern.compile("(\\w+)[\\t ]*\\((.+)\\)");

    // capture nom et valeur dans une chaine de la forme "nom = valeur"
    static final Pattern nameValuePattern
        = Pattern.compile("[\\t ]*(\\w+)[\\t ]*=[\\t ]*(.*)[\\t ]*");

    /**
     * Construit une Scene en parsant le Scanner donné en entré.
     * @throw IllegalStateException si le scanner est fermé.
     * @throw InvalidFormatException si le scanner ne respecte pas le format.
     */
    public static Scene read(java.util.Scanner scanner)
    throws IllegalStateException, InvalidFormatException {
        Vector<BasicObject> objects = new Vector<BasicObject>();
        Camera camera = null;
        double light = 1.0; // todo : intervalle de la lumière ambiante ?

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine().toLowerCase();

            Matcher m = namePattern.matcher(line);

            if(!m.matches()) {
                throw new InvalidFormatException(
                    "Format invalide : ligne \"" + line + "\" non-valide"
                );
            }

            if(m.group(1).equals("Camera")) {
                if(camera == null) {
                    camera = buildCamera(parseParams(m.group(2)));
                }
                else {
                    throw new InvalidFormatException(
                        "Format invalide : deux caméras présentes"
                    );
                }
            }
            else {
                objects.add(buildObject(m.group(1), parseParams(m.group(2))));
            }
        }

        if(camera == null) {
            throw new InvalidFormatException(
                "Format invalide : caméra manquante"
            );
        }

        return new Scene(objects, camera, light);
    }

    /**
     * Découpe une chaine de caractère selon les ',' en respectant les
     * paires de parenthèses.
     */
    private static Vector<String> split(String params)
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
    private static HashMap<String, String> parseParams(String params)
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
    private static double[] parse3Array(String params)
    throws InvalidFormatException {
        if(!params.startsWith("(") || !params.endsWith(")")) {
            throw new InvalidFormatException(
                "Format invalide : point ou vecteur mal parenthèsé"
            );
        }

        String[] xyz = params.substring(1, params.length()-2).split(",");

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

    /**
     * Convertie une chaine de la forme "#RRGGB" en couleur.
     */
    private static java.awt.Color parseColor(String s)
    throws InvalidFormatException {
        Throwable t = null;

        try {
            if(s.length() == 7 && s.charAt(0) == '#') {
                return new java.awt.Color(
                    Integer.parseInt(s.substring(1, 3)),
                    Integer.parseInt(s.substring(3, 5)),
                    Integer.parseInt(s.substring(5, 7))
                );
            }
        }
        catch (NumberFormatException e) {
            t = e;
        }

        throw new InvalidFormatException(
            "Format invalide : couleur invalide, format : #RRGGBB", t
        );
    }

    private static Texture buildTexture(HashMap<String, String> params)
    throws InvalidFormatException {
        Texture t = new Texture();

        if(params.containsKey("transparency")) {
            t.transparency = Double.parseDouble(params.get("transparency"));
        }
        if(params.containsKey("absorbance")) {
            t.absorbance = parse3Array(params.get("absorbance"));
        }
        if(params.containsKey("reflectance")) {
            t.reflectance = parse3Array(params.get("reflectance"));
        }
        if(params.containsKey("refractiveindex")) {
            t.refractiveIndex = parse3Array(params.get("refractiveindex"));
        }
        if(params.containsKey("color")) {
            t.color = parseColor(params.get("color"));
        }

        return t;
    }

    private static Camera buildCamera(HashMap<String, String> params)
    throws InvalidFormatException {
        String[] required = {
            "eye", "origin", "abscissa", "ordinate",
            "widthPixel", "heightPixel"
        };

        if(!params.keySet().containsAll(Arrays.asList(required))) {
            throw new InvalidFormatException(
                "Format invalide : paramètre manquant pour Camera"
            );
        }

        int width, height;

        try {
            width = Integer.parseInt(params.get("widthPixel"));
            height = Integer.parseInt(params.get("heightPixel"));
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
            new Point3d(parse3Array(params.get("eye"))),
            new Point3d(parse3Array(params.get("origin"))),
            new Vector3d(parse3Array(params.get("absissa"))),
            new Vector3d(parse3Array(params.get("ordinate"))),
            width, height
        );
    }

    private static
    BasicObject buildObject(String name, HashMap<String, String> params)
    throws InvalidFormatException {
        Texture texture = buildTexture(params);

        if(name.equals("sphere")) {
            return buildSphere(params, texture);
        }

        throw new InvalidFormatException("Objet inconnu :" + name);
    }

    private static
    BasicObject buildSphere(HashMap<String, String> params, Texture texture)
    throws InvalidFormatException {
        if(!params.containsKey("center") || !params.containsKey("radius")) {
            throw new InvalidFormatException(
                "Format invalide : paramètre manquant pour Camera"
            );
        }

        return new Sphere(
            texture,
            new Point3d(parse3Array(params.get("center"))),
            Double.parseDouble(params.get("radius"))
        );
    }
}

