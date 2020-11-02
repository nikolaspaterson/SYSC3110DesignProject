package Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Timer;

/**
 * The Model.Territory class is responsible for containing all the important attributes of a territory in the game of Risk.
 *
 * @author Ahmad El-Sammak
 * @author Erik Iuhas
 */
public class Territory extends JButton {

    private Player occupant;
    private int troops;
    private final HashMap<String, Territory> neighbours;
    private final String territoryName;

    private JPanel popup_info;
    private final JLabel troop_count_label;
    private final JLabel territory_name_label;
    private final JLabel occupant_name_label;

    private Color default_color;
    private Timer blinking_yours;
    private Timer blinking_theirs;

    /**
     * Class constructor for the Model.Territory class. Sets the player who occupies the territory
     * @param territoryName the name of the territory.
     */
    public Territory(String territoryName,int x, int y, int width, int height,Component parent) {
        this.territoryName = territoryName;
        this.setBounds(x,y,width,height);
        troops = 0;
        neighbours = new HashMap<>();
        blinking_yours = new Timer("flash_yours");
        blinking_theirs = new Timer("flash_theirs");

        popup_info = new JPanel();
        popup_info.setLayout(new BoxLayout(popup_info,BoxLayout.Y_AXIS));
        popup_info.setBounds(x+width,y,100,50);
        popup_info.setMaximumSize(new Dimension(100,50));
        territory_name_label = new JLabel(territoryName);
        territory_name_label.setFont(new Font("Arial",Font.BOLD,12));
        troop_count_label = new JLabel("Troops: " + troops);
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

    public Territory(String territoryName){
        this.territoryName = territoryName;
        neighbours = new HashMap<>();
        occupant_name_label = new JLabel();
        territory_name_label = new JLabel();
        troop_count_label = new JLabel();
    }

    /**
     * Gets the Occupant's color.
     * @return Color the color of the player
     */
    public Color getDefault_color(){ return default_color;}

    /**
     * Gets the name of this Model.Territory.
     * @return String the Model.Territory name.
     */
    public String getTerritoryName() { return territoryName; }

    /**
     * Sets the number of troops in this Model.Territory.
     * @param troops the number of troops.
     */
    public void setTroops(int troops) {
        this.troops = troops;
        troop_count_label.setText("Troops: " + troops);
        this.setText(String.valueOf(troops));
    }

    /**
     * Gets the number of troops in this Model.Territory.
     * @return int the number of troops.
     */
    public int getTroops() {
        return troops;
    }

    /**
     * This method is used to remove troops and update the JLabel on the Model.Territory object as a result of an attack.
     * @param value the value to remove
     */
    public void removeTroops(int value) {
        troops += (value);
        occupant.addTotal(value);
        troop_count_label.setText("Troops: " + troops);
        this.setText(String.valueOf(troops));
    }

    /**
     * Adds a Model.Territory as a neighbour of this Model.Territory into a HashMap.
     * @param neighbour the neighbouring Model.Territory.
     */
    public void addNeighbour(Territory neighbour){ neighbours.put(neighbour.getTerritoryName() ,neighbour); }

    /**
     * Gets the HashMap of neighbouring Territories for this Model.Territory.
     * @return HashMap<String,Model.Territory> the HashMap of neighbours.
     */
    public HashMap<String,Territory> getNeighbours() {
        return this.neighbours;
    }

    /**
     * Sets a new Model.Player to occupy this Model.Territory.
     * @param occupant the new Model.Player to Occupy.
     */
    public void setOccupant(Player occupant) {
        this.occupant = occupant;
        occupant_name_label.setText("Occ: "+occupant.getName());
        this.setBackground(occupant.getPlayer_color());
        default_color = occupant.getPlayer_color();
    }

    /**
     * Gets the Model.Player that occupies this Model.Territory.
     * @return Model.Player the occupant.
     */
    public Player getOccupant() {
        return occupant;
    }

    /**
     * This method is used to check if this Model.Territory is neighbours with the @param territoryToCheck.
     * @param territoryToCheck the territory to check.
     * @return boolean true if it is a neighbour, false if not.
     */
    public boolean isNeighbour(Territory territoryToCheck) {
        String terrToCheck = territoryToCheck.getTerritoryName();
        return this.neighbours.containsKey(terrToCheck);
    }

    /**
     * Combines the name of the Model.Territory and lists all the neighbouring Territories.
     * Prints String combination of the information above.
     */
    public void print_info(){
        System.out.println("Model.Territory Name: " + territoryName );
        System.out.println("Neighbours: " + neighbours.keySet().toString());
        System.out.println("Owner: " + occupant.getName());
        System.out.println("Troop Count: " + troops);
        System.out.println("================================================");
    }

    /**
     * This method is used to stop the timer and stop the flashing of the valid territories that the player can attack.
     */
    public void cancel_timer(){
        blinking_yours.cancel();
        blinking_theirs.cancel();
        blinking_yours = new Timer();
        blinking_theirs = new Timer();
        for(Territory temp : neighbours.values()){
            temp.setBackground(temp.getDefault_color());
        }
    }

    /**
     * This method creates a timer object which is used to flash the valid territory that the player can attack.
     */
    public void activateTimer(){
        blinking_yours.scheduleAtFixedRate(new FlashTimerTask(getDefault_color(),getNeighbours(),1),0,1000);
        blinking_theirs.scheduleAtFixedRate(new FlashTimerTask(getDefault_color(),getNeighbours(),0),500,1000);
    }

    /**
     * Creates a String that displays the territory name as well as its neighbouring territories
     * @return String - The string in the description
     */
    public String toString() {
        String output = "------>Model.Territory Name: " + this.territoryName + "<------\n";
        output += "======Neighbouring Territories======\n";

        for(String str : neighbours.keySet()) {
            output += "              " + neighbours.get(str).getTerritoryName() + "\n";
        }
        output += "==================";
        return output;
    }
}
