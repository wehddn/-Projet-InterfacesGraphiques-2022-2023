package src.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Banque extends JPanel {

    public Banque() {
        JLabel title = new JLabel("Banque 1");
       

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        this.add(title,c);
        c.insets = new Insets(30,0,0,0);  

        JButton level1 = new JButton("Niveau 1");
        c.gridx = 0;
        this.add(level1, c);
        c.insets = new Insets(10,100,10,100);  
        JButton level2 = new JButton("Niveau 2");
        c.gridx = 0;
        this.add(level2, c);
       

        level1.addActionListener(e->{
            View.switchPanel(3);
        });
        

        this.setVisible(true);
        this.setSize(400, 300);
    }
    
    
}
