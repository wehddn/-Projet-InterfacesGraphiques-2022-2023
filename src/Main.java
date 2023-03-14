package src;

import javax.swing.JFrame;

import src.Controller.Controller;
import src.View.Panel;

public class Main {
    public static void main(String[] args){
        Plateau plateau = new Plateau();
        System.out.println(plateau);
        Panel view = new Panel(plateau.getTuiles());
        Controller controller = new Controller(plateau, view);

        JFrame frame = new JFrame("My Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(view);
        frame.pack();
        frame.setVisible(true);
    }
}
