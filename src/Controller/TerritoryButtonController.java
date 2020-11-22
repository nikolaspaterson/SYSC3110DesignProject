package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The Controller.AttackPopUpController class is used to change and update the View.AttackPopUp based which buttons are pressed.
 *
 * @author Ahmad El-Sammak
 * @author Erik Iuhas
 */
public class TerritoryButtonController extends MouseAdapter {

    private final Component parent;
    private final JPanel popup_info;

    /**
     * Class constructor for the TerritoryButtonController class.
     *
     * @param parent the parent
     * @param popup_info information about the territory in a popup
     */
    public TerritoryButtonController(Component parent, JPanel popup_info) {
        this.parent = parent;
        this.popup_info = popup_info;
    }

    /**
     * This method is used to add the popup info when the mouse hovers over the button
     *
     * @param me mouse hovering
     */
    public void mouseEntered(MouseEvent me) {
        parent.getParent().add(popup_info);
        parent.getParent().revalidate();
        parent.getParent().repaint();
    }

    /**
     * This method removes the popup info when the mouse moves away from the button
     *
     * @param me mouse moving away
     */
    public void mouseExited(MouseEvent me) {
        parent.getParent().remove(popup_info);
        parent.getParent().revalidate();
        parent.getParent().repaint();
    }
}
