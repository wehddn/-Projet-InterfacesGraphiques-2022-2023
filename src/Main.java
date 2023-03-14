package src;

import javax.swing.JFrame;

import src.Controller.Controller;
import src.View.Panel;

public class Main {
    public static void main(String[] args) {
        // On crée un plateau et une vue, et on attribue tous les deux au contrôleur qui
        // va les gerer
        Plateau plateau = new Plateau();
        System.out.println(plateau);
        Panel view = new Panel(plateau.getTuiles());
        new Controller(plateau, view);

        JFrame frame = new JFrame("My Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(view);
        frame.pack();
        frame.setVisible(true);
    }
}
