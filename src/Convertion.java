package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

//TODO
//ajouter type
//Prendre le type de 1 ligne et mettre dans tuile?
//Ou stocker dans plateau?
public class Convertion {

    public static TuilesList parseFile(Integer fileNumber) {
        boolean start = true;
        TuilesList tuiles = new TuilesList();

        File gr = new File("levels/level" + fileNumber + ".nrg");

        try (Scanner scanFile = new Scanner(gr)) {
            scanFile.useDelimiter(System.getProperty("line.separator"));
            while (scanFile.hasNext()) {
                String string = scanFile.nextLine();
                if (!start) {
                    tuiles.add(Convertion.parseString(string));
                } else {
                    start = false;
                    String stringType = string.substring(string.length() - 1);
                    switch (stringType) {
                        case "S":
                            tuiles.setType(Type.SQR);
                            break;
                        case "H":
                            tuiles.setType(Type.HEX);
                            break;
                        default:
                            throw new Exception("Wrong file");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Exception " + e);
        }

        return tuiles;
    }

    public static ArrayList<Tuile> parseString(String input) {
        ArrayList<Tuile> result = new ArrayList<Tuile>();
        String[] symbols = input.split(" ");
        Composant composant = null;
        ArrayList<Connexion> connexions = new ArrayList<>();
        for (String symbol : symbols) {
            if (isLetter(symbol)) {
                if (composant != null) {
                    result.add(new Tuile(composant, connexions));
                }
                composant = getComposantBySymbol(symbol);
                connexions = new ArrayList<>();
            } else if (isNumber(symbol)) {
                connexions.add(Connexion.intToEnum(Integer.parseInt(symbol)));
            }
        }
        if (composant != null) {
            result.add(new Tuile(composant, connexions));
        }
        return result;
    }

    private static Composant getComposantBySymbol(String symbol) {
        switch (symbol) {
            case "S":
                return Composant.SOURCE;
            case "L":
                return Composant.LAMPE;
            case "W":
                return Composant.WIFI;
            case ".":
                return Composant.EMPTY;
            default:
                return null; // TODO catch
        }
    }

    private static boolean isLetter(String s) {
        return s.length() == 1 && (s.equals(".") || s.equals("S") || s.equals("L") || s.equals("W"));
    }

    private static boolean isNumber(String s) {
        try {
            Integer i = Integer.parseInt(s);
            if (i >= 0 && i <= 6)
                return true;
            else
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static HashMap<String, BufferedImage> parseTextures() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("tuiles/tuiles.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<String, BufferedImage> textures = new HashMap<String, BufferedImage>();

        int textureWidth = 120;
        int textureHeight = 120;
        int startColumn = 0;
        int endColumn = 3;

        addTextures(image, textureWidth, textureHeight, startColumn, endColumn, textures, Type.SQR);

        textureWidth = 120;
        textureHeight = 104;
        startColumn = 3;
        endColumn = 7;

        addTextures(image, textureWidth, textureHeight, startColumn, endColumn, textures, Type.HEX);

        return textures;
    }

    private static void addTextures(BufferedImage image, int textureWidth,
            int textureHeight, int startColumn, int endColumn, HashMap<String, BufferedImage> textures, Type type) {
        int cellWidth = 120;
        int cellHeight = 120;
        int counter = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = startColumn; j < endColumn; j++) {
                int x = j * cellWidth;
                int y = i * cellHeight;

                BufferedImage texture = new BufferedImage(cellWidth, cellWidth, BufferedImage.TYPE_INT_ARGB);

                for (int k = 0; k < textureHeight; k++) {
                    for (int l = 0; l < textureWidth; l++) {
                        int color = image.getRGB(x + l, y + k);
                        texture.setRGB(l, k, color);
                    }
                }
                String key = setNom(counter, type);
                if (key != null)
                    textures.put(key, texture);
                counter++;
            }
        }
    }

    private static String setNom(int counter, Type type) {
        String res = type.toString();

        if (counter >= 0 && counter < type.getTexturesCount())
            res += "0";
        else
            res += "1";

        if (type == Type.SQR)
            switch (counter % type.getTexturesCount()) {
                case 0:
                    return res += "E";
                case 3:
                    if (counter > type.getTexturesCount())
                        return res += Composant.SOURCE;
                case 4:
                    return res += Composant.WIFI;
                case 5:
                    return res += Composant.LAMPE;
                case 6:
                    return res += "C1";
                case 7:
                    return res += "C2";
                case 8:
                    return res += "C3";
                default:
                    return null;

            }
        else
            switch (counter % type.getTexturesCount()) {
                case 0:
                    return res += "E";
                case 4:
                    if (counter > type.getTexturesCount())
                        return res += Composant.SOURCE;
                case 5:
                    return res += Composant.WIFI;
                case 6:
                    return res += Composant.LAMPE;
                case 8:
                    return res += "C1";
                case 9:
                    return res += "C2";
                case 10:
                    return res += "C3";
                case 11:
                    return res += "C4";
                default:
                    return null;

            }
    }
}
