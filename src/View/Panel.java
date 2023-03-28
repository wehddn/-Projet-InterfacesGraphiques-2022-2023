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

public class Panel extends JPanel {

    private TuilesList tuiles;
    private int panelWidth;
    private int panelHeight;
    private HashMap<String, BufferedImage> textures;
    private int tuilesWidth;
    private int tuilesHeight;
    private int textureWidth;
    private int textureHeight;
    ArrayList<ArrayList<Integer[][]>> coords;

    public Panel(TuilesList tuilesList) {
        // On crée un plateau et des textures
        this.tuiles = tuilesList;

        tuilesWidth = tuilesList.columnsNumber();
        tuilesHeight = tuilesList.rowsNumber();

        textureWidth = 120;
        textureHeight = tuiles.getType().getHeight();

        panelWidth = tuilesWidth * 120;
        panelHeight = tuilesHeight * textureHeight;

        if (tuiles.getType() == Type.HEX) {
            panelWidth -= (tuilesWidth - 1) * (textureWidth / 4);
            panelHeight += textureHeight / 2;
        }

        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.setBackground(Color.BLACK);

        textures = Convertion.parseTextures();

        if (tuiles.getType() == Type.HEX)
            setUpCooridnates();
    }

    //On stocke les coordonnées des sommets de tous les hexagones
    //Les sommets de tous les hexagones peuvent être calculés par rapport à leurs dimensions
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
                    g.drawImage(textures.get(textureName), drawX, drawY, null);
                }

                ArrayList<Image> connexions = connexionsFromTuile(tuiles.get(i, j));
                for (Image connexion : connexions) {
                    g.drawImage(connexion, drawX, drawY, null);
                }
            }
        }
    }

    private ArrayList<Image> connexionsFromTuile(Tuile tuile) {
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

        switch (edges.size()) {
            case 0:
                break;
            case 1:
                type += "1";
                image = rotateImageByDegrees(textures.get(type),
                        edges.get(0) * tuiles.getType().getRotateAngle());
                connexions.add(image);
                break;
            case 2:
                if (tuile.getComposant() == Composant.EMPTY) {
                    connexions.add(getTextureConnexion(type, edges.get(0), edges.get(1)));
                } else
                    connexions.addAll(getTextureConnexionWithComposant(type, edges));
                break;

            default:
            int exclude = allConexionsNearby(edges);
                if (tuile.getComposant() == Composant.EMPTY) {
                    for (int i = 0; i < edges.size(); i++) {
                        int j;

                        if (i == edges.size() - 1)
                            j = 0;
                        else
                            j = i + 1;

                        int diff = tuiles.getTypeValue()/2;

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

    private Image getTextureConnexion(String type, int firstConnexion, int secondConnexion) {
        Image image;
        int diff = secondConnexion - firstConnexion;

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
                type += tuiles.getTypeValue()/2;
                break;
        }

        int rotateTimes = 0;

        if (diff > tuiles.getTypeValue()/2)
            rotateTimes = secondConnexion;
        else
            rotateTimes = firstConnexion;

        image = rotateImageByDegrees(textures.get(type),
                rotateTimes * tuiles.getType().getRotateAngle());
        return image;
    }

    private int allConexionsNearby(ArrayList<Integer> edges) {
        int j, currentConnexion, nextConnexion;

        int notNeighboorCount = 0;
        int notNeighboorIndex = -1;

        if (edges.size() == tuiles.getTypeValue())
            return -1;

        for (int i = 0; i < edges.size(); i++) {
            if (i == edges.size() - 1)
                j = 0;
            else
                j = i + 1;

            currentConnexion = edges.get(i);
            
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
            return new int[] { (x / 120), (y / 120) };
        else
            return getHexCoords(x, y);
    }

    public int[] getHexCoords(int x, int y) {
        for (int i = 0; i < coords.size(); i++) { // loop through rows of hexagons
            for (int j = 0; j < coords.get(i).size(); j++) { // loop through columns of hexagons
                Integer[][] hexagonCoords = coords.get(i).get(j);
                if (isPointInsideHexagon(hexagonCoords, x, y)) {
                    return new int[] { j, i };
                }
            }
        }
        return null;
    }

    private boolean isPointInsideHexagon(Integer[][] hexagonCoords, int x, int y) {
        // Use the point-in-polygon algorithm to determine if the point is inside the
        // hexagon
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

    public BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {
        BufferedImage rotated = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();

        int x = textureWidth / 2;
        int y = textureHeight / 2;

        at.rotate(Math.toRadians(angle), x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, this);

        return rotated;
    }
}
