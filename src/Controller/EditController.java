package src.Controller;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import src.Connexion;
import src.Plateau;
import src.View.EditView;
import src.View.GameView;

public class EditController extends MouseInputAdapter {

    private static Plateau plateau;
    private static EditView gameView;

    public EditController(int n) {
        plateau = new Plateau(n);
        gameView = new EditView(plateau.getTuiles());
        gameView.addMouseListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int[] coords = gameView.getTuileCoords(e.getX(), e.getY());
        if(coords==null)
            return;

        if(gameView.clickInCenter(e.getX(), e.getY(), coords)){
            plateau.toggleComposant(coords);
            gameView.repaint();
        }
        else{
            Connexion connexion = gameView.getNearestConnexion(e.getX(), e.getY(), coords);
            plateau.toggleConnexion(coords, connexion);
            gameView.repaint();
        }
    }

    public GameView getView() {
        return gameView;
    }
}
