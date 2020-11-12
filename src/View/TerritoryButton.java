package View;

import Controller.TerritoryButtonController;
import Listener.TerritoryView;
import Model.Player;
import Event.TerritoryEvent;

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
public class TerritoryButton extends JButton implements TerritoryView {

    private final JPanel popup_info;
    private final JLabel troop_count_label;
    private final JLabel occupant_name_label;
    private String territoryName;

    /**
     * Constructor for the TerritoryView class.
     * @param territoryName the territory name
     * @param x x coordinate
     * @param y y coordinate
     * @param width the width
     * @param height the height
     * @param parent the component parent
     */
    public TerritoryButton(String territoryName, int x, int y, int width, int height, Component parent) {
        this.territoryName = territoryName;
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
        TerritoryButtonController tbc = new TerritoryButtonController(parent, popup_info);
        this.addMouseListener(tbc);
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


    public String getTerritoryName() { return territoryName; }

    @Override
    public void handleTerritoryUpdate(TerritoryEvent event) {
        this.setOccupantLabel(event.getOccupant());
        this.setBackground(event.getColor());
        this.setTroopsLabel(event.getTroops());
        this.setText(String.valueOf(event.getTroops()));
    }
}
