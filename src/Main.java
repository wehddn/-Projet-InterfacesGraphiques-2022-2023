package src;

import src.View.*;

import java.io.File;

import src.Controller.GameController;
import src.View.GameView;
import src.View.View;

public class Main {

    public static void main(String[] args) {
        // On crée un plateau et une vue, et on attribue tous les deux au contrôleur qui
        // va les gerer
       /*  Plateau plateau = new Plateau();
        Panel panel = new Panel(plateau.getTuiles());
        new Controller(plateau, panel);

        new View(panel);*/

        new View();
    }
}
