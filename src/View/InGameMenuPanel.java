package src.View;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class InGameMenuPanel extends JPanel {

    public InGameMenuPanel() {
        this.setBackground(Color.black);
        JButton button = new JButton("Back");

        button.addActionListener(e -> {
            View.switchPanel(0);
        });

        this.setLayout(new FlowLayout(FlowLayout.LEFT));
                
        this.add(button);
    }
    
}
