package src.View;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import src.Convertion;
import src.Plateau;
import src.Tuile;

public class Panel extends JPanel {
    
    private ArrayList<ArrayList<Tuile>> tuiles;
    private int panelWidth;
    private int panelHeight;
    private HashMap<String, BufferedImage> textures;
    private int tuilesWidth;
    private int tuilesHeight; 

    public Panel(ArrayList<ArrayList<Tuile>> tuiles) {
        this.tuiles = tuiles;

        tuilesWidth = tuiles.get(0).size();
        tuilesHeight = tuiles.size();

        panelWidth = tuilesWidth*120;
        panelHeight = tuilesHeight*120;
        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.setBackground(Color.BLACK);

        textures = Convertion.parseTextures();
    }

    public void repaint() {
        super.repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        for(int i = 0; i<tuilesHeight; i++){
            for(int j = 0; j<tuilesWidth; j++){
                ArrayList<String> texturesName = textureNameFromTuile(tuiles.get(i).get(j));
                for (String textureName : texturesName) {
                    g.drawImage(textures.get(textureName), j*120, i*120, null);   
                }

                ArrayList<Image> connexions = connexionsFromTuile(tuiles.get(i).get(j));
                for (Image connexion : connexions) {
                    g.drawImage(connexion, j*120, i*120, null);   
                }

            }
        }
    }

    private ArrayList<Image> connexionsFromTuile(Tuile tuile) {
        ArrayList<Image> connexions = new ArrayList<>();
        String type = "";
        if (tuile.isPower()){
            type+="1";
        }
        else{
            type+="0";
        }

        type+="C1";

        for (Integer intConnexion : tuile.getConnexions()) {
            connexions.add(textures.get(type));
        }
        return connexions;
    }

    private ArrayList<String> textureNameFromTuile(Tuile tuile) {
        String type = "";
        String board = "";

        if (tuile.isPower()){
            type+="1";
            board+="1";
        }
        else{
            type+="0";
            board+="0";
        }

        type+=tuile.getComposant();
        board+="E";

        ArrayList<String> result = new ArrayList<>();
        result.add(type);
        result.add(board);

        return result;
    }

    public Integer getTuileX(int x) {
        return x/120;
    }

    public int getTuileY(int y) {
        return y/120;
    }

    public void setTuiles(ArrayList<ArrayList<Tuile>> tuiles) {
        this.tuiles = tuiles;
        repaint();
    }
}
