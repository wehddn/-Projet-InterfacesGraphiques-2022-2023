package src.View;

import javax.swing.*;

public class View {
    private JFrame frame = new JFrame("Energy");


    public View(Panel panel) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public View(){
        Menu menu= new Menu();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(menu);
        frame.setSize(400  , 300);

        frame.setVisible(true);

        
    }
}
