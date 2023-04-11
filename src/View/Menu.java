package src.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

//setPrefered... et pack le frame de View

public class Menu extends JPanel{
    public Menu(){
        JLabel title = new JLabel("Energy");
        title.setHorizontalAlignment(SwingConstants.CENTER); // center the label
        this.setPreferredSize(new Dimension(400, 300));
        this.add(title, BorderLayout.NORTH);

        this.setLayout(new GridBagLayout());
        JButton banque1 = new JButton("Banque 1");
        JButton banque2 = new JButton("Banque 2");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(banque1, gbc);
        gbc.gridy++;
        this.add(banque2, gbc);

        banque1.addActionListener(e->{
            View.switchPanel(1);
        });

        banque2.addActionListener(e->{
            View.switchPanel(2);
        });
    }
    
}
