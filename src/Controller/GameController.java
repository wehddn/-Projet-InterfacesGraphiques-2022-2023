package src.Controller;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import src.Model.Plateau;
import src.View.GamePanel;
import src.View.GameView;

public class GameController extends MouseInputAdapter {

    private static Plateau plateau;
    private static GameView gameView;
    private GamePanel editPanel;

    public GameController(int n) {
        plateau = new Plateau(n, false);
        editPanel = new GamePanel(plateau.getTuiles());
        gameView = new GameView(plateau.getTuiles());
        gameView.addMouseListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int[] coords = gameView.getTuileCoords(e.getX(), e.getY());
        if (coords != null) {
            plateau.turn(coords[1], coords[0]);
            gameView.setTuiles(plateau.getTuiles());
        }
    }

    public GamePanel getView() {
        return editPanel;
    }
}
