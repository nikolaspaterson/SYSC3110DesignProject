package Controller;

import View.MapSelectScreen;
import View.PlayerSelectView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapSelectController implements ActionListener {

    private final MapSelectScreen mapSelectScreen;

    public MapSelectController(MapSelectScreen mapSelectScreen) {
        this.mapSelectScreen = mapSelectScreen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().contains("resources")) {
            mapSelectScreen.dispose();
            new PlayerSelectView(e.getActionCommand());
        } else {
            if(mapSelectScreen.checkCustomMapValidity(e.getActionCommand())) {
                mapSelectScreen.dispose();
                new PlayerSelectView(e.getActionCommand());
            } else {
                JOptionPane.showMessageDialog(mapSelectScreen, "ERROR! CUSTOM MAP IS MISSING LINKS!");
            }
        }
    }
}
