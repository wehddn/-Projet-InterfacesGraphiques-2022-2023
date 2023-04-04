package src.View;

import javax.swing.*;

import src.TuilesList;

public class View {

    Panel panel;
    Menu menu;

    public View() {
        JFrame frame = new JFrame("Energy");
        menu = new Menu();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(menu);
        frame.setSize(400  , 300);

        frame.setVisible(true);
    }

    public int[] getTuileCoords(int x, int y) {
        return panel.getTuileCoords(x, y);
    }

    public void setTuiles(TuilesList tuiles) {
        panel.setTuiles(tuiles);
    }
}
