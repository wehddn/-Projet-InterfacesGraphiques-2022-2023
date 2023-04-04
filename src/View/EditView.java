package src.View;

import java.awt.Color;
import java.awt.Graphics;

import src.Connexion;
import src.TuilesList;

public class EditView extends GameView {

    public EditView(TuilesList tuilesList) {
        super(tuilesList);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public boolean clickInCenter(int pointX, int pointY, int[] tileCoords) {

        int circleX = coords.get(tileCoords[1]).get(tileCoords[0])[0][0]+8;
        int circleY = coords.get(tileCoords[1]).get(tileCoords[0])[0][1]+30;
        int circleSide = 43;
        
            // Calculate the coordinates of the center of the circle
            double circleCenterX = circleX + (circleSide / 2);
            double circleCenterY = circleY + (circleSide / 2);
            
            // Calculate the distance between the point and the center of the circle
            double distance = Math.sqrt(Math.pow(pointX - circleCenterX, 2) + Math.pow(pointY - circleCenterY, 2));
            
            // If the distance is less than the radius of the circle (which is half the side length), the point is inside the circle
            return distance < (circleSide / 2);
    }

    public Connexion getNearestConnexion(int x, int y, int[] tileCoords) {
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
        
        // Calculate the distance between the point and the current side using the formula for the distance between a point and a line segment
        double distance = Math.abs((endY - startY) * x - (endX - startX) * y + endX * startY - endY * startX) / Math.sqrt(Math.pow(endY - startY, 2) + Math.pow(endX - startX, 2));
        
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
