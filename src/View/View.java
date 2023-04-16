package src.View;

import java.io.File;

import javax.swing.*;

import src.Controller.GameController;

//Dimensions fixes

public class View {

    static Menu menu;
    static JFrame frame = new JFrame("Energy");

    public View() {
        menu = new Menu();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(menu);
        frame.pack();
        frame.setVisible(true);
    }

    public static void switchPanel(int n){

        int i=0;
        File gr = new File("levels/level" + i + ".nrg");
        while(gr.exists()){
            i++; 
            gr = new File("levels/level" + i + ".nrg");

        }

        
            if(n==-3){
                frame.getContentPane().add(menu);
                frame.pack();
            }

            else if(n==-1){
                Banque banque = new Banque();
                frame.setContentPane(banque);
                frame.pack(); 
                
            }

            else if(n==-2){
                EditBanque ebanque = new EditBanque();
                frame.setContentPane(ebanque);
                frame.pack(); 
                
            }

            else if(n>=0 && n<i){
                GameController gameController = new GameController(n);
                frame.setContentPane(gameController.getView());
                frame.pack(); 
               
            }

        

    }
}
