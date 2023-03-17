package src;

import javax.swing.JFrame;

import src.Controller.Controller;
import src.View.Panel;
import src.View.View;

public class Main {
    public static void main(String[] args) {
        // On crée un plateau et une vue, et on attribue tous les deux au contrôleur qui
        // va les gerer
        Plateau plateau = new Plateau();
        System.out.println(plateau);
        Panel view = new Panel(plateau.getTuiles());
        new Controller(plateau, view);
        View v = new View(view);

    }
}
