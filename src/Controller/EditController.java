package src.Controller;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import src.Connexion;
import src.Plateau;
import src.View.EditPanel;
import src.View.EditView;

public class EditController extends MouseInputAdapter {

    private static Plateau plateau;
    private static EditView editView;
    EditPanel editPanel;

    public EditController(int n) {
        plateau = new Plateau(n);
        editPanel = new EditPanel(plateau.getTuiles());
        editView = editPanel.getView();
        editView.addMouseListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int[] coords = editView.getTuileCoords(e.getX(), e.getY());
        if(coords==null)
            return;

        if(editView.clickInCenter(e.getX(), e.getY(), coords)){
            plateau.toggleComposant(coords);
            editView.repaint();
        }
        else{
            Connexion connexion = editView.getNearestConnexion(e.getX(), e.getY(), coords);
            plateau.toggleConnexion(coords, connexion);
            editView.repaint();
        }
    }

    public EditPanel getView() {
        return editPanel;
    }

    //enums
    public static void modifyPlateau(int side, int action) {
        plateau.modifyData(side, action);
    }
}
