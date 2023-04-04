package src.View;

import javax.swing.*;

import src.TuilesList;

public class View {

    Panel panel;
    Menu menu;

    public View() {
        Menu menu = new Menu();
        JFrame frame = new JFrame("Energy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(menu);
        frame.pack();
        frame.setVisible(true);
    }

    public int[] getTuileCoords(int x, int y) {
        return panel.getTuileCoords(x, y);
    }

    public void setTuiles(TuilesList tuiles) {
        panel.setTuiles(tuiles);
    }
}
