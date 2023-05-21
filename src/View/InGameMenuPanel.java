package src.View;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class InGameMenuPanel extends JPanel {

    public InGameMenuPanel() {
        JButton button = new MyJButton("Back");

        button.addActionListener(e -> {
            View.switchPanel(0);
        });

        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.add(button);
    }

}
