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

    public Panel(TuilesList tuilesList) {
        // On crée un plateau et des textures
        this.tuiles = tuilesList;

        tuilesWidth = tuilesList.columnsNumber();
        tuilesHeight = tuilesList.rowsNumber();

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
                    g.drawImage(textures.get(textureName), j * 120, i * 120, null);
                }

                ArrayList<Image> connexions = connexionsFromTuile(tuiles.get(i, j), g);
                for (Image connexion : connexions) {
                    g.drawImage(connexion, j * 120, i * 120, null);
                }

            }
        }
    }

    private ArrayList<Image> connexionsFromTuile(Tuile tuile, Graphics g) {
        ArrayList<Image> connexions = new ArrayList<>();
        String type = "";
        if (tuile.isPower()) {
            type += "1";
        } else {
            type += "0";
        }

        type += "C1";

        for (Connexion intConnexion : tuile.getConnexions()) {
            Image image = rotateImageByDegrees(textures.get(type), intConnexion.getValue() * 90);
            connexions.add(image);
        }
        return connexions;
    }

    private ArrayList<String> textureNameFromTuile(Tuile tuile) {
        String type = "";
        String board = "";

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
        int w = img.getWidth();
        int h = img.getHeight();

        BufferedImage rotated = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();

        int x = w / 2;
        int y = h / 2;

        at.rotate(Math.toRadians(angle), x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, this);

        return rotated;
    }
}
