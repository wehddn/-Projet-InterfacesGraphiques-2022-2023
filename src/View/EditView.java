package src.View;

import java.awt.Color;
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
        g.setColor(Color.cyan);
        g.drawOval(240+27, 0+27, 65, 65);
    }

    public boolean clickInCenter(int pointX, int pointY, int[] tileCoords) {
        int circleX;
        int circleY;
        int circleSide;
        
        if(tuiles.getType()==Type.HEX){
            circleX = coords.get(tileCoords[1]).get(tileCoords[0])[0][0] + 8;
            circleY = coords.get(tileCoords[1]).get(tileCoords[0])[0][1] + 30;
            circleSide = 43;
        }
        else{
            circleX = tileCoords[0]*120 + 27;
            circleY = tileCoords[1]*120 + 27;
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

    public Connexion getNearestConnexion(int x, int y, int[] tileCoords) {
        if(tuiles.getType()==Type.HEX)
            return getNearestConnexionHex(x, y, tileCoords);
        else
            return getNearestConnexionSqr(x, y, tileCoords);
    }

    public Connexion getNearestConnexionHex(int x, int y, int[] tileCoords) {
        Integer[][] hexagonCoords = coords.get(tileCoords[1]).get(tileCoords[0]);
        // Initialize variables to hold the closest distance and side number
        double closestDistance = Double.MAX_VALUE;
        Connexion closestSide = null;

        // Loop through each side of the hexagon
        for (int i = 0; i < 6; i++) {
            // Get the coordinates of the two endpoints of the current side
            int startX = hexagonCoords[i][0];
            int startY = hexagonCoords[i][1];
            int endX = hexagonCoords[(i + 1) % 6][0];
            int endY = hexagonCoords[(i + 1) % 6][1];

            // Calculate the distance between the point and the current side using the
            // formula for the distance between a point and a line segment
            double distance = Math.abs((endY - startY) * x - (endX - startX) * y + endX * startY - endY * startX)
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

    public static Connexion getNearestConnexionSqr(int pointX, int pointY, int[] tileCoords) {
        int squareX = tileCoords[0] * 120;
        int squareY = tileCoords[1] * 120;
        int side = 120;
        // Initialize variables to hold the closest distance and side number
        double closestDistance = Double.MAX_VALUE;
        Connexion closestSide = null;
    
        // Define the coordinates of the four sides of the square
        int[][] sides = {
            {squareX, squareY, squareX + side, squareY},
            {squareX + side, squareY, squareX + side, squareY + side},
            {squareX + side, squareY + side, squareX, squareY + side},
            {squareX, squareY + side, squareX, squareY}
        };
    
        // Loop through each side of the square
        for (int i = 0; i < 4; i++) {
            // Get the coordinates of the two endpoints of the current side using modulo arithmetic to wrap around to the first side for the last side
            int startX = sides[i][0];
            int startY = sides[i][1];
            int endX = sides[(i + 1) % 4][0];
            int endY = sides[(i + 1) % 4][1];
    
            // Calculate the distance between the point and the current side using the formula for the distance between a point and a line segment
            double distance = Math.abs((endY - startY) * pointX - (endX - startX) * pointY + endX * startY - endY * startX) / Math.sqrt(Math.pow(endY - startY, 2) + Math.pow(endX - startX, 2));
    
            // If the distance is less than the closest distance so far, update the closest distance and side number
            if (distance < closestDistance) {
                closestDistance = distance;
                closestSide = Connexion.intToEnum(i);
            }
        }
    
        // Return the side number of the closest side
        return closestSide;
    }
    

}
