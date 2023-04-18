package src.View;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import src.Controller.TuilesList;

public class GamePanel extends JPanel {

    GameView gameView;

    public GamePanel(TuilesList tuilesList) {

        this.setBackground(Color.black);

        JPanel panelEdit = new JPanel();
        panelEdit.setLayout(new BorderLayout());

        InGameMenuPanel panelMenu = new InGameMenuPanel();

        gameView = new GameView(tuilesList);
        panelEdit.add(gameView);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(panelMenu);
        this.add(panelEdit);
    }

    public GameView getView() {
        return gameView;
    }
    
}

