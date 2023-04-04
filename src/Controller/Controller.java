package src.Controller;

import java.awt.event.MouseEvent;

import javax.swing.event.*;

import src.Plateau;
import src.View.Panel;
import src.View.View;

public class Controller implements MouseInputListener {

    private Plateau plateau;
    //private Panel view;
    private View view;

    /*public Controller(Plateau plateau, Panel view) {
        this.plateau = plateau;
        this.view = view;


        view.addMouseListener(this);
    }*/

    public Controller(){
        view= new View();
        plateau = new Plateau();


    }



    @Override
    public void mousePressed(MouseEvent e) {
       /*  int[] coords = view.getTuileCoords(e.getX(), e.getY());
        if (coords != null) {
            plateau.turn(coords[1], coords[0]);
            view.setTuiles(plateau.getTuiles());
        }*/
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
        

    }

}
