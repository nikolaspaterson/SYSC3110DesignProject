package src;

import java.util.HashMap;

/**
 * The Player class is responsible for containing important attributes that every player should have in the game of Risk.
 *
 * @author aelsammak
 * @author dieuleparfait
 * @version 1.0
 */
public class Player {

    private int deployableTroops;
    private String name;
    private HashMap<String, Territory> territoriesOccupied;

    /**
     * Class constructor for the Player class. Sets the name of the player and initializes the HashMap which will store what territory the player occupies.
     * @param name the name of the player.
     */
    public Player(String name) {
        this.name = name;
        territoriesOccupied = new HashMap<String, Territory>();
    }

    /**
     * Getter for the hashmap containing all the territories that the player occupies.
     * @return HashMap<String,Territory> The territories Occupied.
     */
    public HashMap<String, Territory> getTerritoriesOccupied() { return territoriesOccupied; }

    /**
     * Sets the amount of deployable troops the player can use during their reinforcement.
     * @param deployableTroops number of deployable troops.
     */
    public void setDeployableTroops(int deployableTroops) {
        this.deployableTroops = deployableTroops;
    }

    /**
     * Gets the amount of deployable troops the player can use during their reinforcement.
     * @return int number of deployable troops.
     */
    public int getDeployableTroops() {
        return deployableTroops;
    }

    /**
     * Increments the number of troops in a specified territory.
     * @param territory the territory where troops will be added.
     * @param numTroops the number of troops to add.
     */
    public void incrementTroops(Territory territory, int numTroops) {
        territory.setTroops(territory.getTroops() + numTroops);
    }

    /**
     * Decrements the number of troops in a specified territory.
     * @param territory the territory where troops will be removed.
     * @param numTroops the number of troops to remove.
     */
    public void decrementTroops(Territory territory, int numTroops) {
        territory.setTroops(territory.getTroops() - numTroops);
    }

    /**
     * Adds a territory to the HashMap of territories occupied by the player.
     * @param territoryName the name of the territory.
     * @param territory the territory object itself.
     */
    public void addTerritory(String territoryName, Territory territory) {
        territoriesOccupied.put(territoryName, territory);
    }

    /**
     * Removes a territory from the HashMap of territories occupied by the player.
     * @param territory the territory to remove.
     */
    public void removeTerritory(String territory) {
        territoriesOccupied.remove(territory);
    }

    /**
     * Combines the Player's Name, all their Territory's owned and how many troops are in each Territory.
     * @return String combination of information above.
     */
    public String toString() {
        String output = "------>Player: " + this.name + "<------\n";
        for(String str : territoriesOccupied.keySet()) {
            output += "======Territory Owned======\n";
            output += "         " + str + "\n";
            output += "======Troops Owned======\n";
            output += "            " + territoriesOccupied.get(str).getTroops();
        }
        return output + "\n";
    }
}
