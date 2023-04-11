package src.View;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.awt.geom.AffineTransform;

import src.Composant;

import javax.swing.*;

import src.Convertion;
import src.Tuile;
import src.TuilesList;
import src.Type;

public class GameView extends JPanel {

    public TuilesList tuiles;
    protected int panelWidth;
    protected int panelHeight;
    private HashMap<String, BufferedImage> textures;
    private int tuilesWidth;
    private int tuilesHeight;
    private int textureWidth;
    protected int textureHeight;
    ArrayList<ArrayList<Integer[][]>> coords;

    public GameView(TuilesList tuilesList) {
        // On crée un plateau et des textures
        this.tuiles = tuilesList;

        setUpSizes();

        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.setBackground(Color.BLACK);

        textures = Convertion.parseTextures();

        if (tuiles.getType() == Type.HEX)
            setUpCooridnates();
    }

    protected void setUpSizes() {
        tuilesWidth = tuiles.columnsNumber();
        tuilesHeight = tuiles.rowsNumber();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenHeight = screenSize.getHeight()-tuiles.getType().getHeight();
        double screenWidth = screenSize.getWidth()-120;

        if (screenHeight < tuilesHeight * tuiles.getType().getHeight())
            textureHeight = (int) screenHeight / tuilesHeight;
        else
            textureHeight = tuiles.getType().getHeight();

        if (screenWidth < screenWidth * 120)
            textureWidth = (int) screenHeight / tuilesHeight;
        else
            textureWidth = 120;

        panelWidth = tuilesWidth * textureWidth;
        panelHeight = tuilesHeight * textureHeight;

        if (tuiles.getType() == Type.HEX) {
            panelWidth -= (tuilesWidth - 1) * (textureWidth / 4);
            panelHeight += textureHeight / 2;
        }
    }

    //On stocke les coordonnées des sommets de tous les hexagones
    //On les calcule par rapport à leurs dimensions
    private void setUpCooridnates() {
        coords = new ArrayList<>();
        for (int i = 0; i < tuilesHeight; i++) {
            ArrayList<Integer[][]> coordsRow = new ArrayList<>();
            for (int j = 0; j < tuilesWidth; j++) {
                Integer[][] coordsTuile = new Integer[6][2];
                if (j % 2 == 0) {
                    coordsTuile[0][1] = i * textureHeight;
                    coordsTuile[1][1] = i * textureHeight;
                    coordsTuile[2][1] = i * textureHeight + textureHeight / 2;
                    coordsTuile[3][1] = (i + 1) * textureHeight;
                    coordsTuile[4][1] = (i + 1) * textureHeight;
                    coordsTuile[5][1] = i * textureHeight + textureHeight / 2;
                } else {
                    //Les hexagones dans les colonnes impaires doivent être décalés de la moitié de la hauteur de la texture
                    coordsTuile[0][1] = i * textureHeight + textureHeight / 2;
                    coordsTuile[1][1] = i * textureHeight + textureHeight / 2;
                    coordsTuile[2][1] = (i + 1) * textureHeight;
                    coordsTuile[3][1] = (i + 1) * textureHeight + textureHeight / 2;
                    coordsTuile[4][1] = (i + 1) * textureHeight + textureHeight / 2;
                    coordsTuile[5][1] = (i + 1) * textureHeight;
                }

                coordsTuile[0][0] = textureWidth / 4 + j * textureWidth * 3 / 4;
                coordsTuile[1][0] = textureWidth * 3 / 4 + j * textureWidth * 3 / 4;
                coordsTuile[2][0] = textureWidth + j * textureWidth * 3 / 4;
                coordsTuile[3][0] = textureWidth * 3 / 4 + j * textureWidth * 3 / 4;
                coordsTuile[4][0] = textureWidth / 4 + j * textureWidth * 3 / 4;
                coordsTuile[5][0] = j * textureWidth * 3 / 4;

                coordsRow.add(coordsTuile);
            }
            coords.add(coordsRow);
        }
    }

    public void repaint() {
        super.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int drawX;
        int drawY;

        for (int i = 0; i < tuilesHeight; i++) {
            for (int j = 0; j < tuilesWidth; j++) {
                
                //Coordonnées de dessin de texture
                drawX = j * textureWidth;
                drawY = i * textureHeight;

                //Toutes les textures d'hexagones doivent être décalées horizontalement, ainsi que les textures dans les colonnes impaires verticalement
                if (tuiles.getType() == Type.HEX) {
                    drawX -= j * (textureWidth / 4);
                    if (j % 2 == 1)
                        drawY += textureHeight / 2;
                }

                ArrayList<String> composant = textureComposantFromTuile(tuiles.get(i, j));
                for (String textureName : composant) {
                    g.drawImage(textures.get(textureName), drawX, drawY, textureWidth, textureHeight, null);
                }

                ArrayList<Image> connexions = textureConnexionsFromTuile(tuiles.get(i, j));
                for (Image connexion : connexions) {
                    g.drawImage(connexion, drawX, drawY, textureWidth, textureHeight, null);
                }
            }
        }
    }

    private ArrayList<Image> textureConnexionsFromTuile(Tuile tuile) {
        ArrayList<Image> connexions = new ArrayList<>();

        ArrayList<Integer> edges = tuile.getIntConnexions();
        Collections.sort(edges);
        
        String type = tuiles.getType().toString();
        if (tuile.isPower()) {
            type += "1";
        } else {
            type += "0";
        }

        type += "C";
        Image image;

        //Le type de texture dépend du nombre de connexions dans la tuile
        switch (edges.size()) {
            case 0:
                break;
            case 1:
                // Connexion courte simple
                type += "1";
                image = rotateImageByDegrees(textures.get(type),
                        edges.get(0) * tuiles.getType().getRotateAngle());
                connexions.add(image);
                break;
            case 2:
                // S'il y a un composant, on dessine des connexions courtes
                if (tuile.getComposant() == Composant.EMPTY) {
                    connexions.add(getTextureConnexion(type, edges.get(0), edges.get(1)));
                } else
                    connexions.addAll(getTextureConnexionWithComposant(type, edges));
                break;
            default:
            // Nombre de connexions est supérieur à 2 :
            // Si toutes les connexions qu'on connecte sont voisins, on exclut la dernière connexion entre les points non adjacents
            int exclude = allConexionsNearby(edges);
                // S'il y a un composant, on dessine des connexions courtes
                if (tuile.getComposant() == Composant.EMPTY) {
                    for (int i = 0; i < edges.size(); i++) {
                        int j;

                        if (i == edges.size() - 1)
                            j = 0;
                        else
                            j = i + 1;

                        int diff = tuiles.getTypeValue()/2;

                        // On ne dessine pas une connexion directe entre les points opposés, et une connexion à partir du point exclu
                        if (Math.abs(edges.get(i) - edges.get(j)) != diff && i != exclude) {
                            if (edges.get(i) < edges.get(j)) {
                                connexions.add(getTextureConnexion(type, edges.get(i), edges.get(j)));
                            } else {
                                connexions.add(getTextureConnexion(type, edges.get(j), edges.get(i)));
                            }
                        }
                    }
                } else
                    connexions.addAll(getTextureConnexionWithComposant(type, edges));
        }
        return connexions;

    }

    // Texture de connexion pour 2 points
    private Image getTextureConnexion(String type, int firstConnexion, int secondConnexion) {
        Image image;
        int diff = secondConnexion - firstConnexion;

        // On trouve la texture en fonction de la distance entre les connexions
        switch (diff) {
            case 1:
            case 5:
                type += "2";
                break;
            case 2:
            case 4:
                type += "3";
                break;
            case 3:
                if(tuiles.getType() == Type.SQR)
                    type+="2";
                else
                    type+="4";
                break;
        }

        // On détermine combien de fois il faut tourner la texture
        int rotateTimes = 0;

        if (diff > tuiles.getTypeValue()/2)
            rotateTimes = secondConnexion;
        else
            rotateTimes = firstConnexion;

        image = rotateImageByDegrees(textures.get(type),
                rotateTimes * tuiles.getType().getRotateAngle());
        return image;
    }

    // On vérifie si toutes les connexions sont voisines et, si oui, renvoie la connexion pour laquelle il ne faut pas dessiner une texture
    private int allConexionsNearby(ArrayList<Integer> edges) {
        int j, currentConnexion, nextConnexion;

        int notNeighboorCount = 0;
        int notNeighboorIndex = -1;

        // Si le nombre de connexions correspond au nombre de côtés, tous les points sont adjacents
        if (edges.size() == tuiles.getTypeValue())
            return -1;

        // On vérifie la différence entre deux connexions adjacentes
        // Si tous les points sont adjacents, la différence entre le dernier et le premier ne sera pas égale à 1
        for (int i = 0; i < edges.size(); i++) {
            if (i == edges.size() - 1)
                j = 0;
            else
                j = i + 1;

            currentConnexion = edges.get(i);
            
            // Pour calculer la différence entre la connexion 0 et tout autre, on peut utiliser le nombre de côtés au lieu de 0
            if (edges.get(j) == 0)
                nextConnexion = tuiles.getTypeValue();
            else
                nextConnexion = edges.get(j);
            
            if (nextConnexion - currentConnexion != 1) {
                notNeighboorCount++;
                notNeighboorIndex = i;
            }
        }
        if (notNeighboorCount == 1)
            return notNeighboorIndex;
        else
            return -1;
    }

    private ArrayList<Image> getTextureConnexionWithComposant(String type, ArrayList<Integer> edges) {
        ArrayList<Image> connexions = new ArrayList<>();
        type += "1";
        for (Integer intConnexion : edges) {
            Image image = rotateImageByDegrees(textures.get(type),
                    intConnexion * tuiles.getType().getRotateAngle());
            connexions.add(image);
        }
        return connexions;
    }

    private ArrayList<String> textureComposantFromTuile(Tuile tuile) {
        String type = tuiles.getType().toString();
        String board = tuiles.getType().toString();

        if (tuile.isPower()) {
            type += "1";
            board += "1";
        } else {
            type += "0";
            board += "0";
        }

        type += tuile.getComposant();
        board += "E";

        ArrayList<String> result = new ArrayList<>();
        result.add(type);
        result.add(board);

        return result;
    }

    public int[] getTuileCoords(int x, int y) {
        if (tuiles.getType() == Type.SQR)
            return new int[] { (x / textureWidth), (y / textureHeight) };
        else
            return getHexCoords(x, y);
    }

    public int[] getHexCoords(int x, int y) {
        for (int i = 0; i < coords.size(); i++) {
            for (int j = 0; j < coords.get(i).size(); j++) {
                Integer[][] hexagonCoords = coords.get(i).get(j);
                if (isPointInsideHexagon(hexagonCoords, x, y)) {
                    return new int[] { j, i };
                }
            }
        }
        return null;
    }

    private boolean isPointInsideHexagon(Integer[][] hexagonCoords, int x, int y) {
        // On tilise l'algorithme de point dans le polygone pour déterminer si le point est à l'intérieur du hexagone
        int i, j;
        boolean c = false;
        for (i = 0, j = 6 - 1; i < 6; j = i++) {
            int xi = hexagonCoords[i][0];
            int yi = hexagonCoords[i][1];
            int xj = hexagonCoords[j][0];
            int yj = hexagonCoords[j][1];
            if (((yi > y) != (yj > y)) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi)) {
                c = !c;
            }
        }
        return c;
    }

    public void setTuiles(TuilesList tuiles) {
        this.tuiles = tuiles;
        repaint();
    }

    public BufferedImage rotateImageByDegrees(BufferedImage image, double angle) {
        angle = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h * cos + w * sin);
        GraphicsConfiguration gc = getGraphicsConfiguration();
        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(angle, w / 2, h / 2);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }
}
