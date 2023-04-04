package src.Controller;

import java.awt.event.MouseEvent;

import javax.swing.event.*;

import src.Plateau;
import src.TuilesList;
import src.View.Game;
import src.View.View;

public class Controller {

    private static Plateau plateau;
    private static View view;

    public Controller() {
        view = new View();
    }

    public static void turnTuile(int x, int y){
        int[] coords = view.getTuileCoords(x, y);
        if (coords != null) {
            plateau.turn(coords[1], coords[0]);
            view.setTuiles(plateau.getTuiles());
        }
    }

    public static TuilesList createPlateau(int n) {
        plateau = new Plateau(n);

        return plateau.getTuiles();
    }
}
