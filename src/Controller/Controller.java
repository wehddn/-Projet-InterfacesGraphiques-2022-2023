package src.Controller;

import java.awt.event.MouseEvent;

import javax.swing.event.*;

import src.Plateau;
import src.View.Panel;

public class Controller implements MouseInputListener{

    private Plateau plateau;
    private Panel view;

    public Controller(Plateau plateau, Panel view) {
        this.plateau = plateau;
        this.view = view;

        view.addMouseListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getX() + " " + e.getY());
        int x = view.getTuileX(e.getX());
        int y = view.getTuileY(e.getY());
        plateau.turn(y, x);
        System.out.println(plateau);
        view.setTuiles(plateau.getTuiles());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
