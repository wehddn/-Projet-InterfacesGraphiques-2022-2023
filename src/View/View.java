package src.View;

import javax.swing.*;

import src.TuilesList;
import src.Controller.GameController;

//Dimensions fixes

public class View {

    static Menu menu;
    static JFrame frame = new JFrame("Energy");

    public View() {
        menu = new Menu();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(menu);
        frame.setSize(400  , 300);

        frame.setVisible(true);
    }

    public static void switchPanel(int n){
        switch(n){
            case 1:
                Banque banque = new Banque();
                frame.setContentPane(banque);
                frame.validate(); 
                break;
            case 2:
                //GameController
                //game = new GameView(GameController.createPlateau(n));          //n = doc du niveau n = 2 pour l'instant
                GameController gameController = new GameController(n);
                frame.setContentPane(gameController.getView());
                frame.validate(); 
                break;
            default:
                frame.getContentPane().add(menu);
                frame.setSize(400  , 300);
        }
    }
}
