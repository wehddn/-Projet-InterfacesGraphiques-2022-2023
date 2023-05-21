package src.View;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class MyJButton extends JButton{

    public MyJButton(String text) {
        super(text);

        this.addActionListener(e -> {
            setBorder(null);
        });

        this.addMouseListener( new MouseListener() {

            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.WHITE)); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(null);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseClicked(MouseEvent e) {}
        } );
    }
    
}
