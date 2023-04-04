package src.View;

import javax.swing.*;

import src.TuilesList;
import src.Controller.Controller;

//Dimensions fixes

public class View {

    Game panel;
    static Menu menu;
    static JFrame frame = new JFrame("Energy");

    public View() {
        menu = new Menu();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(menu);
        frame.setSize(400  , 300);

        frame.setVisible(true);
    }

    public int[] getTuileCoords(int x, int y) {
        return panel.getTuileCoords(x, y);
    }

    public void setTuiles(TuilesList tuiles) {
        panel.setTuiles(tuiles);
    }

    public static void switchPanel(int n){
        switch(n){
            case 1:
                Banque banque = new Banque();
                frame.setContentPane(banque);
                frame.validate(); 
                break;
            case 2:
                Game game = new Game(Controller.createPlateau(n));          //n = doc du niveau n = 2 pour l'instant
                frame.setContentPane(game);
                frame.validate(); 
                break;
            default:
                frame.getContentPane().add(menu);
                frame.setSize(400  , 300);
        }
    }
}
