package src.View;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.geom.AffineTransform;
import src.Connexion;

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

        if(tuiles.getType() == Type.HEX)
            setUpCooridnates();        
    }

    private void setUpCooridnates() {
        coords = new ArrayList<>();
        for(int i = 0; i<tuilesHeight; i++){
            ArrayList<Integer[][]> coordsRow = new ArrayList<>();
            for(int j = 0; j<tuilesWidth; j++){
                Integer[][] coordsTuile = new Integer[6][2];
                if (j % 2 == 0){
                    coordsTuile[0][1] = i*textureHeight;
                    coordsTuile[1][1] = i*textureHeight;
                    coordsTuile[2][1] = i*textureHeight + textureHeight/2;
                    coordsTuile[3][1] = (i+1)*textureHeight;
                    coordsTuile[4][1] = (i+1)*textureHeight;
                    coordsTuile[5][1] = i*textureHeight + textureHeight/2;
                }
                else{
                    coordsTuile[0][1] = i*textureHeight + textureHeight/2;
                    coordsTuile[1][1] = i*textureHeight + textureHeight/2;
                    coordsTuile[2][1] = (i+1)*textureHeight;
                    coordsTuile[3][1] = (i+1)*textureHeight + textureHeight/2;
                    coordsTuile[4][1] = (i+1)*textureHeight + textureHeight/2;
                    coordsTuile[5][1] = (i+1)*textureHeight;
                }

                coordsTuile[0][0] = textureWidth/4+j*textureWidth*3/4;
                coordsTuile[1][0] = textureWidth*3/4+j*textureWidth*3/4;
                coordsTuile[2][0] = textureWidth+j*textureWidth*3/4;
                coordsTuile[3][0] = textureWidth*3/4+j*textureWidth*3/4;
                coordsTuile[4][0] = textureWidth/4+j*textureWidth*3/4;
                coordsTuile[5][0] = j*textureWidth*3/4;
                
                coordsRow.add(coordsTuile);
            }  
            coords.add(coordsRow); 
        }
    }

    public void repaint() {
        super.repaint();
    }

    // Pour chaque tuile, on crée une clé pour trouver la texture dans le
    // dictionnaire
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int drawX;
        int drawY;

        for (int i = 0; i < tuilesHeight; i++) {
            for (int j = 0; j < tuilesWidth; j++) {
                drawX = j * textureWidth;
                drawY = i * textureHeight;

                if (tuiles.getType() == Type.HEX) {
                    drawX -= j * (textureWidth / 4);
                    if (j % 2 == 1)
                        drawY += textureHeight / 2;
                }

                ArrayList<String> texturesName = textureNameFromTuile(tuiles.get(i, j));
                for (String textureName : texturesName) {
                    g.drawImage(textures.get(textureName), drawX, drawY, null);
                }

                ArrayList<Image> connexions = connexionsFromTuile(tuiles.get(i, j), g);
                for (Image connexion : connexions) {
                    g.drawImage(connexion, drawX, drawY, null);
                }
            }
        }
    }

    private ArrayList<Image> connexionsFromTuile(Tuile tuile, Graphics g) {
        ArrayList<Image> connexions = new ArrayList<>();
        String type = tuiles.getType().toString();
        if (tuile.isPower()) {
            type += "1";
        } else {
            type += "0";
        }

        type += "C1";
        for (Connexion intConnexion : tuile.getConnexions()) {
            Image image = rotateImageByDegrees(textures.get(type),
                    intConnexion.getValue() * tuiles.getType().getRotateAngle());
            connexions.add(image);
        }
        return connexions;
    }

    private ArrayList<String> textureNameFromTuile(Tuile tuile) {
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
                    return new int[] {j, i};
                }
            }
        }
        return null;
    }
    
    private boolean isPointInsideHexagon(Integer[][] hexagonCoords, int x, int y) {
        // Use the point-in-polygon algorithm to determine if the point is inside the hexagon
        int numPoints = hexagonCoords.length;
        int i, j;
        boolean c = false;
        for (i = 0, j = numPoints - 1; i < numPoints; j = i++) {
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
