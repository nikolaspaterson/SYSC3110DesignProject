package View;

import Model.Player;
import Model.Territory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Used to represent the Territory object as a JButton that will be placed on the map.
 *
 * @author Ahmad El-Sammak
 * @author Erik Iuhas
 */
public class TerritoryView extends JButton {

    private final JPanel popup_info;
    private final JLabel troop_count_label;
    private final JLabel occupant_name_label;
    private final Territory territory;

    /**
     * Constructor for the TerritoryView class.
     * @param territoryName the territory name
     * @param x x coordinate
     * @param y y coordinate
     * @param width the width
     * @param height the height
     * @param parent the component parent
     * @param territory the territory object
     */
    public TerritoryView(String territoryName, int x, int y, int width, int height, Component parent, Territory territory) {
        this.territory = territory;
        setBounds(x,y,width,height);
        popup_info = new JPanel();
        popup_info.setLayout(new BoxLayout(popup_info,BoxLayout.Y_AXIS));
        popup_info.setBounds(x+width,y,100,50);
        popup_info.setMaximumSize(new Dimension(100,50));
        JLabel territory_name_label = new JLabel(territoryName);
        territory_name_label.setFont(new Font("Arial",Font.BOLD,12));
        troop_count_label = new JLabel("Troops: " );
        occupant_name_label = new JLabel("");

        popup_info.add(territory_name_label);
        popup_info.add(troop_count_label);
        popup_info.add(occupant_name_label);

        this.addMouseListener(new MouseAdapter() {
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
        });
    }

    /**
     * Set the troop label to display the new troop count.
     * @param troops amount of troops
     */
    public void setTroopsLabel(int troops) {
        troop_count_label.setText("Troops: " + troops);
    }

    /**
     * Sets the occupant player of the territory to be on the JLabel.
     * @param occupant the occupying player
     */
    public void setOccupantLabel(Player occupant) {
        occupant_name_label.setText("Occ: "+occupant.getName());
    }

    /**
     * Gets the Territory object.
     * @return Territory
     */
    public Territory getTerritory() {
        return territory;
    }
}
