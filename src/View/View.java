package src.View;

import javax.swing.*;

public class View {
    public View(Panel panel) {
        JFrame frame = new JFrame("Energy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public View(){
        
    }
}
