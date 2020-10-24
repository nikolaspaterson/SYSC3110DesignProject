
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
        territoriesOccupied = new HashMap<>();
    }
    public String getName(){
        return name;
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

    public void addDeployableTroops(int deployableTroops) {
        this.deployableTroops += deployableTroops;
    }

    /**
     * Gets the amount of deployable troops the player can use during their reinforcement.
     * @return int number of deployable troops.
     */
    public int getDeployableTroops() {
        return deployableTroops;
    }


    public int placeDeployableTroops(int troop_count) {
        if (deployableTroops - troop_count >= 0) {
            deployableTroops -= troop_count;
            return troop_count;
        } else {
            int temp = deployableTroops;
            deployableTroops = 0;
            return temp;
        }
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
     * This method is used to calculate the number of troops the player will receive based on how many territories and continents they own.
     */
    public void troopsReceived() {
        int result = 0;

        if ((this.getTerritoriesOccupied().size()) <= 9) {
            result = 3;
        } else {
            result = ((this.getTerritoriesOccupied().size()) / 3);
        }

        result += troopContinentBonus();
        this.addDeployableTroops(result);
    }

    /**
     * This method is used to calculate the amount of extra bonus troops the player gets if they control one or more continent(s).
     * @return the number of bonus troops.
     */
    public int troopContinentBonus() {
        int[] continents = new int[6];

        if (this.getTerritoriesOccupied().size() > 0) {
            for (Territory territory : this.getTerritoriesOccupied().values()) {
                String continentName = territory.getContinentName();

                switch (continentName) {
                    case "Asia":
                        continents[0]++;
                        break;
                    case "Australia":
                        continents[1]++;
                        break;
                    case "Europe":
                        continents[2]++;
                        break;
                    case "Africa":
                        continents[3]++;
                        break;
                    case "South America":
                        continents[4]++;
                        break;
                    case "North America":
                        continents[5]++;
                        break;
                }
            }
        }

        int troops = 0;

        if (continents[0] == 12) troops += 7; // Asia Bonus
        if (continents[1] == 4) troops += 2; // Australia Bonus
        if (continents[2] == 7) troops += 5; // Europe Bonus
        if (continents[3] == 6) troops += 3; // Africa Bonus
        if (continents[4] == 4) troops += 2; // South America Bonus
        if (continents[5] == 9) troops += 5; // North America Bonus

        return troops;
    }

    /**
     * Combines the Player's Name, all their Territory's owned and how many troops are in each Territory.
     * @return String combination of information above.
     */
    public String toString() {
        String output = "------>Player: " + this.name + "<------\n";
        for(String str : territoriesOccupied.keySet()) {
            output += "\n======TerritoryOwned:Troops======\n";
            output += "         " + str + " : " + territoriesOccupied.get(str).getTroops() + "\n";
        }
        return output;
    }
}
