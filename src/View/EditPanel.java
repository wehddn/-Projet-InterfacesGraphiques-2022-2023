package src.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import src.TuilesList;
import src.Controller.EditController;


public class EditPanel extends JPanel {

    EditView editView;

    public EditPanel(TuilesList tuilesList) {
        this.setLayout(new BorderLayout());
        List<JPanel> panels = createPanels();
        this.add(panels.get(0), BorderLayout.NORTH);
        this.add(panels.get(1), BorderLayout.WEST);
        this.add(panels.get(2), BorderLayout.SOUTH);
        this.add(panels.get(3), BorderLayout.EAST);

        editView = new EditView(tuilesList);
        this.add(editView);
    }

    public List<JPanel> createPanels() {
        List<JPanel> panels = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            final int side = i;

            JPanel panel = new JPanel(new GridBagLayout());

            JButton add = new JButton("+");
            JButton delete = new JButton("-");

            add.addActionListener(e -> {
                EditController.modifyPlateau(side, 1);
                editView.updateFrame();
            });

            delete.addActionListener(e -> {
                EditController.modifyPlateau(side, 0);
                editView.updateFrame();
            });

            panel.add(add, new GridBagConstraints());
            panel.add(delete, new GridBagConstraints());
            panel.setBackground(Color.black);
            panels.add(panel);
        }
        return panels;
    }

    public EditView getView() {
        return editView;
    }
    
}
