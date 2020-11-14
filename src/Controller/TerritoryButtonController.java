package Controller;

import Model.Territory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TerritoryButtonController extends MouseAdapter {

    private Component parent;
    private JPanel popup_info;

    public TerritoryButtonController(Component parent, JPanel popup_info) {
        this.parent = parent;
        this.popup_info = popup_info;
    }

    public void mouseEntered(MouseEvent me) {
        parent.getParent().add(popup_info);
        parent.getParent().revalidate();
        parent.getParent().repaint();
    }
    public void mouseExited(MouseEvent me) {
        parent.getParent().remove(popup_info);
        parent.getParent().revalidate();
        parent.getParent().repaint();
    }
}
