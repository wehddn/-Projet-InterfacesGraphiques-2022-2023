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

    public static ArrayList<ArrayList<Tuile>> parseFile(Integer fileNumber) {
        boolean start = true;
        ArrayList<ArrayList<Tuile>> tuiles = new ArrayList<>();
        int intType = 0;

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
                            intType = 4;
                            break;
                        case "H":
                            intType = 6;
                            break;
                        default:
                            throw new Exception("Wrong file"); // TODO catch
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Exception " + e);
        }

        for (ArrayList<Tuile> tuileRow : tuiles) {
            for (Tuile tuile : tuileRow) {
                tuile.setType(intType);
            }
        }
        return tuiles;
    }

    public static ArrayList<Tuile> parseString(String input) {
        ArrayList<Tuile> result = new ArrayList<Tuile>();
        String[] symbols = input.split(" ");
        String composant = null;
        ArrayList<Integer> connexions = new ArrayList<>();
        for (String symbol : symbols) {
            if (isLetter(symbol)) {
                if (composant != null) {
                    result.add(new Tuile(composant, connexions));
                }
                composant = symbol;
                connexions = new ArrayList<>();
            } else if (isNumber(symbol)) {
                connexions.add(Integer.parseInt(symbol));
            }
        }
        if (composant != null) {
            result.add(new Tuile(composant, connexions));
        }
        return result;
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

        int texture4Size = 120;

        HashMap<String, BufferedImage> textures = new HashMap<String, BufferedImage>();

        int counter = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                int x = j * texture4Size;
                int y = i * texture4Size;

                BufferedImage texture = new BufferedImage(texture4Size, texture4Size, BufferedImage.TYPE_INT_ARGB);

                for (int k = 0; k < texture4Size; k++) {
                    for (int l = 0; l < texture4Size; l++) {
                        int color = image.getRGB(x + l, y + k);
                        texture.setRGB(l, k, color);
                    }
                }
                String key = setNom(counter);
                if (key != null)
                    textures.put(key, texture);
                counter++;
            }
        }

        System.out.println(textures.keySet());
        return textures;
    }

    private static String setNom(int counter) {
        String res = "";
        if (counter >= 0 && counter < 9)
            res += "0";
        else
            res += "1";

        if(counter==12)
            return res+="S";

        switch (counter % 9) {
            case 0:
                return res += ".";
            case 4:
                return res += "W";
            case 5:
                return res += "L";
            case 6:
                return res += "C1";
            case 7:
                return res += "C2";
            case 8:
                return res += "C3";
            default:
                return null;

        }
    }
}
