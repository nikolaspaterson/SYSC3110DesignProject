package Controller;

import View.MapSelectScreen;
import View.PlayerSelectView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The MapSelectController class is used to send the PlayerSelectView the correct map based on which map was selected by the user.
 *
 * @author Ahmad El-Sammak
 */
public class MapSelectController implements ActionListener {

    private final MapSelectScreen mapSelectScreen;

    /**
     * Class constructor for the MapSelectController class.
     *
     * @param mapSelectScreen the map select screen
     */
    public MapSelectController(MapSelectScreen mapSelectScreen) {
        this.mapSelectScreen = mapSelectScreen;
    }

    /**
     * Performs an action based on which button triggered the event.
     *
     * @param e the event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().contains(JButtonActionCommands.RESOURCES.getCommand())) {
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
