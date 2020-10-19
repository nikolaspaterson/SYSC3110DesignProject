package src;

import java.util.HashMap;

/**
 * The Territory class is responsible for containing all the important attributes of a territory in the game of Risk.
 *
 * @author aelsammak
 * @author dieuleparfait
 * @version 1.0
 */
public class Territory {

    private Player occupant;
    private int troops;
    private HashMap<String, Territory> neighbours;
    private String territoryName;
    private String continentName;

    /**
     * Class constructor for the Territory class. Sets the player who occupies the territory
     * @param occupant the player who occupies the territory.
     * @param territoryName the name of the territory.
     * @param continentName the continent that the territory is in.
     */
    public Territory(Player occupant, String territoryName, String continentName) {
        this.occupant = occupant;
        this.territoryName = territoryName;
        this.continentName = continentName;
        troops = 0;
        neighbours = new HashMap<>();
    }

    /**
     * Gets the continent name that this Territory is in.
     * @return String the continent name.
     */
    public String getContinentName() { return continentName; }

    /**
     * Gets the name of this Territory.
     * @return String the Territory name.
     */
    public String getTerritoryName() { return territoryName; }

    /**
     * Sets the number of troops in this Territory.
     * @param troops the number of troops.
     */
    public void setTroops(int troops) {
        this.troops = troops;
    }

    /**
     * Gets the number of troops in this Territory.
     * @return int the number of troops.
     */
    public int getTroops() {
        return troops;
    }

    /**
     * Adds a Territory as a neighbour of this Territory into a HashMap.
     * @param neighbour the neighbouring Territory.
     */
    public void addNeighbour(Territory neighbour){ neighbours.put(neighbour.getTerritoryName() ,neighbour); }

    /**
     * Gets the HashMap of neighbouring Territories for this Territory.
     * @return HashMap<String,Territory> the HashMap of neighbours.
     */
    public HashMap<String,Territory> getNeighbours() {
        return this.neighbours;
    }

    /**
     * Sets a new Player to occupy this Territory.
     * @param occupant the new Player to Occupy.
     */
    public void setOccupant(Player occupant) {
        this.occupant = occupant;
    }

    /**
     * Gets the Player that occupies this Territory.
     * @return Player the occupant.
     */
    public Player getOccupant() {
        return occupant;
    }

    /**
     * This method is used to check if this Territory is neighbours with the @param territoryToCheck.
     * @param territoryToCheck the territory to check.
     * @return boolean true if it is a neighbour, false if not.
     */
    public boolean isNeighbour(Territory territoryToCheck) {
        String terrToCheck = territoryToCheck.getTerritoryName();
        if(this.neighbours.containsKey(terrToCheck)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Combines the name of the Territory and lists all the neighbouring Territories.
     * @return String combination of the information above.
     */
    public String toString() {
        String output = "------>Territory Name: " + this.territoryName + "<------\n";
        output += "======Neighbouring Territories======\n";
        for(String str : neighbours.keySet()) {
            output += "              " + neighbours.get(str) + "\n";
        }
        return output;
    }
}
