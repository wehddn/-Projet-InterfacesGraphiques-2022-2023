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

public class Panel extends JPanel {

    private TuilesList tuiles;
    private int panelWidth;
    private int panelHeight;
    private HashMap<String, BufferedImage> textures;
    private int tuilesWidth;
    private int tuilesHeight;
    private int textureWidth;
    private int textureHeight;

    public Panel(TuilesList tuilesList) {
        // On crée un plateau et des textures
        this.tuiles = tuilesList;

        tuilesWidth = tuilesList.columnsNumber();
        tuilesHeight = tuilesList.rowsNumber();

        textureWidth = 120;
        textureHeight = tuiles.getType().getHeight();

        panelWidth = tuilesWidth * 120;
        panelHeight = tuilesHeight * 120;
        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.setBackground(Color.BLACK);

        textures = Convertion.parseTextures();
    }

    public void repaint() {
        super.repaint();
    }

    // Pour chaque tuile, on crée une clé pour trouver la texture dans le
    // dictionnaire
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < tuilesHeight; i++) {
            for (int j = 0; j < tuilesWidth; j++) {
                ArrayList<String> texturesName = textureNameFromTuile(tuiles.get(i, j));
                for (String textureName : texturesName) {
                    g.drawImage(textures.get(textureName), j * textureWidth, i * textureHeight, null);
                }

                ArrayList<Image> connexions = connexionsFromTuile(tuiles.get(i, j), g);
                for (Image connexion : connexions) {
                    g.drawImage(connexion, j * textureWidth, i * textureHeight, null);
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
            Image image = rotateImageByDegrees(textures.get(type), intConnexion.getValue() * tuiles.getType().getRotateAngle());
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

    public Integer getTuileX(int x) {
        return x / 120;
    }

    public int getTuileY(int y) {
        return y / 120;
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
