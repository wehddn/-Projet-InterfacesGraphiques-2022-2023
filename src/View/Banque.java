package src.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;

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
        c.insets = new Insets(30,0,10,0);  

        int i=0;
        File gr = new File("levels/level" + i + ".nrg");
        while(gr.exists()){

            JButton level = new JButton("Niveau "+i);
            c.gridx = 0;
            this.add(level, c);
            c.insets = new Insets(10,100,10,100); 

            int n=i;
            level.addActionListener(e->{
                View.openGameLevel(n);
            });
            
            i++;
            gr = new File("levels/level" + i + ".nrg");
        }

        JButton menu = new JButton("retour au menu ");
            c.gridx = 0;
            this.add(menu, c);
            c.insets = new Insets(10,100,10,100); 

            int n=i;
            menu.addActionListener(e->{
                View.switchPanel(0);
            });

    

        this.setVisible(true);
        this.setSize(400, 300);
    }
    
    
}
