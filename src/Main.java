package src;

import src.Controller.Controller;
import src.View.Panel;
import src.View.View;

public class Main {

    // TO DO: demander pour les fenetres menu / plateau
    public static void main(String[] args) {
        // On crée un plateau et une vue, et on attribue tous les deux au contrôleur qui
        // va les gerer
        Plateau plateau = new Plateau();
        Panel panel = new Panel(plateau.getTuiles());
        new Controller(plateau, panel);

        View view = new View(panel);

    }
}
