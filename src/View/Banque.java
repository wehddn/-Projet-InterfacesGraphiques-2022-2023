package src.View;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Banque extends JPanel {

    public Banque() {
        JLabel title = new JLabel("Banque 1");
        title.setHorizontalAlignment(SwingConstants.CENTER); // center the label
        this.add(title, BorderLayout.CENTER);
        this.add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridBagLayout());
        JButton level1 = new JButton("Niveau 1");
        JButton level2 = new JButton("Niveau 2");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(level1, gbc);
        gbc.gridy++;
        panel.add(level2, gbc);

        level1.addActionListener(e->{
            View.switchPanel(2);
        });

        this.add(panel, BorderLayout.CENTER);
        this.add(panel, BorderLayout.CENTER);
        this.setVisible(true);
        this.setSize(400, 300);
    }
    
    
}
