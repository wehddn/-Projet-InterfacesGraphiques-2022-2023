package src.Controller;

import java.awt.event.MouseEvent;

import javax.swing.event.*;

import src.Plateau;
import src.View.Panel;
import src.View.View;

public class Controller {

    private static Plateau plateau;
    private static View view;

    public Controller() {
        plateau = new Plateau();
        view = new View();
    }

    public static void turnTuile(int x, int y){
        int[] coords = view.getTuileCoords(x, y);
        if (coords != null) {
            plateau.turn(coords[1], coords[0]);
            view.setTuiles(plateau.getTuiles());
        }
    }
}
