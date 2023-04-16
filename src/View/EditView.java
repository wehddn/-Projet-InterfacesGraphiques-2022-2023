package src.View;

import java.awt.Dimension;
import java.awt.Graphics;

import src.Connexion;
import src.TuilesList;
import src.Type;

public class EditView extends GameView {

    public EditView(TuilesList tuilesList) {
        super(tuilesList);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    //TODO circle -> ellipse
    public boolean clickInCenter(int pointX, int pointY, int[] tileCoords) {
        int rectX;
        int rectY;
        int rectWidth;
        int rectHeight;

        if (tuiles.getType() == Type.HEX) {
            rectX = coords.get(tileCoords[1]).get(tileCoords[0])[0][0] + textureWidth/4;
            rectY = coords.get(tileCoords[1]).get(tileCoords[0])[0][1] + textureHeight/4;
            rectWidth = textureWidth/2;
            rectHeight = textureHeight/2;
        } else {
            rectX = tileCoords[0] * textureWidth + textureWidth/4;
            rectY = tileCoords[1] * textureHeight + textureHeight/4;
            rectWidth = textureWidth/2;
            rectHeight = textureHeight/2;
        }

        double ellipseX = rectX + rectWidth / 2;
        double ellipseY = rectY + rectHeight / 2;
        double axisX = rectWidth / 2;
        double axisY = rectHeight / 2;
        double dx = (pointX - ellipseX) / axisX;
        double dy = (pointY - ellipseY) / axisY;
        return dx * dx + dy * dy < 1;
    }

    public Connexion getNearestConnexion(int pointX, int pointY, int[] tileCoords) {
        Integer[][] sides;
        if (tuiles.getType() == Type.HEX)
            sides = coords.get(tileCoords[1]).get(tileCoords[0]);
        else {
            int squareX = tileCoords[0] * textureWidth;
            int squareY = tileCoords[1] * textureHeight;
            int xside = textureWidth;
            int yside = textureHeight;
            sides = new Integer[4][4];
            sides[0] = new Integer[] { squareX, squareY, squareX + xside, squareY };
            sides[1] = new Integer[] { squareX + xside, squareY, squareX + xside, squareY + yside };
            sides[2] = new Integer[] { squareX + xside, squareY + yside, squareX, squareY + yside };
            sides[3] = new Integer[] { squareX, squareY + yside, squareX, squareY };

        }
        // Initialize variables to hold the closest distance and side number
        double closestDistance = Double.MAX_VALUE;
        Connexion closestSide = null;

        // Loop through each side of the hexagon
        for (int i = 0; i < tuiles.getTypeValue(); i++) {
            // Get the coordinates of the two endpoints of the current side
            int startX = sides[i][0];
            int startY = sides[i][1];
            int endX = sides[(i + 1) % tuiles.getTypeValue()][0];
            int endY = sides[(i + 1) % tuiles.getTypeValue()][1];

            // Calculate the distance between the point and the current side using the
            // formula for the distance between a point and a line segment
            double distance = Math
                    .abs((endY - startY) * pointX - (endX - startX) * pointY + endX * startY - endY * startX)
                    / Math.sqrt(Math.pow(endY - startY, 2) + Math.pow(endX - startX, 2));

            // If the distance is less than the closest distance so far, update the closest
            // distance and side number
            if (distance < closestDistance) {
                closestDistance = distance;
                closestSide = Connexion.intToEnum(i);
            }
        }

        // Return the side number of the closest side
        return closestSide;
    }

    public void updateFrame() {
        setUpSizes();
        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.revalidate();
        this.repaint();
    }
}
