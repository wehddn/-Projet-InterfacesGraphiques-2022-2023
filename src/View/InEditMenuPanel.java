package src.View;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import src.Controller.EditController;

public class InEditMenuPanel extends JPanel {

    public InEditMenuPanel() {
        this.setBackground(Color.black);
        JButton back = new JButton("Back");

        back.addActionListener(e -> {
            if (EditController.checkWin()) {
                int result = JOptionPane.showConfirmDialog(this,
                        "Le niveau est dans une position gagnante. \n" +
                                "Vous aller quitter le niveau. Êtes-vous sûr de vouloir enregistrer les modifications?",
                        "Confirmation",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                switch (result) {
                    case JOptionPane.YES_OPTION:
                        EditController.updateFile();
                        View.switchPanel(0);
                        break;
                    case JOptionPane.NO_OPTION:
                        View.switchPanel(0);
                        break;
                    default:
                        break;
                }
            } else {
                int result = JOptionPane.showConfirmDialog(this,
                        "Le niveau n'est pas dans la position gagnante.\n" +
                                "Si vous quittez, vous perdrez toutes les modifications.\n" +
                                "Êtes-vous sûr de vouloir sortir?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                switch (result) {
                    case JOptionPane.YES_OPTION:
                        View.switchPanel(0);
                        break;
                    default:
                        break;
                }
            }
        });

        JButton type = new JButton("Switch type");
        type.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "La modification de la géométrie effacera le plateau de jeu\n" +
                            "Êtes-vous sûr de vouloir continuer?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);
            switch (result) {
                case JOptionPane.YES_OPTION:
                    EditController.switchGeometry();
                    break;
                default:
                    break;
            }
        });

        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.add(back);
        this.add(type);
    }
}