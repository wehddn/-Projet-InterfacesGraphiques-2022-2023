package src.View;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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

    public boolean clickInCenter(int pointX, int pointY, int[] tileCoords) {
        int circleX;
        int circleY;
        int circleSide;

        if (tuiles.getType() == Type.HEX) {
            circleX = coords.get(tileCoords[1]).get(tileCoords[0])[0][0] + 8;
            circleY = coords.get(tileCoords[1]).get(tileCoords[0])[0][1] + 30;
            circleSide = 43;
        } else {
            circleX = tileCoords[0] * 120 + 27;
            circleY = tileCoords[1] * 120 + 27;
            circleSide = 65;
        }

        // Calculate the coordinates of the center of the circle
        double circleCenterX = circleX + (circleSide / 2);
        double circleCenterY = circleY + (circleSide / 2);

        // Calculate the distance between the point and the center of the circle
        double distance = Math.sqrt(Math.pow(pointX - circleCenterX, 2) + Math.pow(pointY - circleCenterY, 2));

        // If the distance is less than the radius of the circle (which is half the side
        // length), the point is inside the circle
        return distance < (circleSide / 2);
    }

    public Connexion getNearestConnexion(int pointX, int pointY, int[] tileCoords) {
        Integer[][] sides;
        if (tuiles.getType() == Type.HEX)
            sides = coords.get(tileCoords[1]).get(tileCoords[0]);
        else {
            int squareX = tileCoords[0] * 120;
            int squareY = tileCoords[1] * 120;
            int side = 120;
            sides = new Integer[4][4];
            sides[0] = new Integer[] { squareX, squareY, squareX + side, squareY };
            sides[1] = new Integer[] { squareX + side, squareY, squareX + side, squareY + side };
            sides[2] = new Integer[] { squareX + side, squareY + side, squareX, squareY + side };
            sides[3] = new Integer[] { squareX, squareY + side, squareX, squareY };

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
