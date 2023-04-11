package src.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EditBanque extends Banque{
    public EditBanque() {
        super();
        setLayout(new GridLayout(0, 2)); 
        JButton modif = new JButton("Modifier");

        
        /*for (int i = 1; i <= 10; i++) {
            JButton button = new JButton("Modifier");
            add(button);
        }*/
    }
    

}
