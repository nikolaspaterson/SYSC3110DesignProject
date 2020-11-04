package Model;

import View.PlayerView;
import View.TerritoryView;
import java.awt.*;
import java.util.HashMap;
import java.util.Timer;

/**
 * The Model.Territory class is responsible for containing all the important attributes of a territory in the game of Risk.
 *
 * @author Ahmad El-Sammak
 * @author Erik Iuhas
 */
public class Territory {

    private Player occupant;
    private int troops;
    private final HashMap<String, Territory> neighbours;
    private final String territoryName;

    private TerritoryView territoryView;
    private Color default_color;
    private Timer blinking_yours;
    private Timer blinking_theirs;

    /**
     * Class constructor for the Model.Territory class. Sets the player who occupies the territory
     * @param territoryName the name of the territory.
     */
    public Territory(String territoryName) {
        this.territoryName = territoryName;
        troops = 0;
        neighbours = new HashMap<>();
        blinking_yours = new Timer("flash_yours");
        blinking_theirs = new Timer("flash_theirs");

    }
    public void setTerritoryView(String territoryName,int x, int y, int width, int height,Component parent){
        territoryView = new TerritoryView(territoryName, x, y, width, height, parent,this);
    }
    public TerritoryView getTerritoryView() {
        return territoryView;
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
        return neighbours.containsKey(terrToCheck);
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
            TerritoryView tempView = temp.getTerritoryView();
            tempView.setBackground(temp.getDefault_color());
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

    public void updateView(){
        default_color = occupant.getPlayerView().getPlayer_color();
        territoryView.setOccupantLabel(occupant);
        territoryView.setBackground(occupant.getPlayerView().getPlayer_color());
        territoryView.setTroopsLabel(troops);
        territoryView.setText(String.valueOf(troops));
    }
}
