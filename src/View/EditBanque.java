package src.View;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import javax.swing.BoxLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EditBanque extends JPanel {
    public EditBanque() {
        JLabel title = new JLabel("Banque 1");
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        this.add(title, c);
        c.insets = new Insets(30, 0, 10, 0);

        int i = 1;
        File gr = new File("levels/level" + i + ".nrg");
        while (gr.exists()) {
            JButton level = new JButton("Niveau " + i);
            JButton edit = new JButton("Edit " + i);
            c.gridx = 0;
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.add(level);
            panel.add(edit);
            this.add(panel, c);
            c.insets = new Insets(10, 100, 10, 100);

            int n = i;
            level.addActionListener(e -> {
                View.openGameLevel(n);
            });

            edit.addActionListener(e -> {
                View.openEditLevel(n);
            });

            i++;
            gr = new File("levels/level" + i + ".nrg");
        }

        JButton menu = new JButton("retour au menu ");
        c.gridx = 0;
        this.add(menu, c);
        c.insets = new Insets(10, 100, 10, 100);

        menu.addActionListener(e -> {
            View.switchPanel(0);
        });

        this.setVisible(true);
        this.setSize(400, 300);
    }

}
