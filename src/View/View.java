package src.View;

import javax.swing.*;

public class View {

    public View(Panel view) {
        JFrame frame = new JFrame("Energy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(view);
        frame.pack();
        frame.setVisible(true);
    }
}
