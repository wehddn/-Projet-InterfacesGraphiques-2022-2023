package src.View;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import src.Controller.EditController;
import src.Controller.GameController;
import javax.swing.UIManager;

//Dimensions fixes

public class View {

    static Menu menu;
    static JFrame frame = new JFrame("Energy");

    public View() {
        
        UIManager.put("OptionPane.background", Color.BLACK);
        UIManager.put("Panel.background", Color.BLACK);

        UIManager.put("Button.background", Color.BLACK);
        UIManager.put("Button.border", BorderFactory.createEmptyBorder());
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.select", Color.BLACK);

        UIManager.put("Label.foreground", Color.WHITE);
        
        UIManager.put("OptionPane.messageForeground", Color.WHITE);

        menu = new Menu();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(menu);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static void switchPanel(int n) {
        switch (n) {
            case 1:
                Banque banque = new Banque();
                frame.setContentPane(banque);
                frame.pack();
                break;
            case 2:
                EditBanque ebanque = new EditBanque();
                frame.setContentPane(ebanque);
                frame.pack();
                break;
            default:
                frame.setContentPane(menu);
                frame.pack();
        }
    }

    public static void openGameLevel(int n) {
        GameController gameController = new GameController(n);
        frame.setContentPane(gameController.getView());
        frame.pack();
    }

    public static void openEditLevel(int n) {
        EditController editController = new EditController(n);
        frame.setContentPane(editController.getView());
        frame.pack();
    }
}
