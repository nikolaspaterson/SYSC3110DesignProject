package Model;

import java.util.HashMap;

/**
 * @author Erik Iuhas
 */
public class Continent {

    private final HashMap<String, Territory> continentTerritory;
    private final String continentName;

    /**
     * Model.Continent object stores all the territories withing that continent. This is used for calculation of the troop
     * bonus in View.GameView which is called at the start of everyone's turn
     * @param continentName the name of the continent
     */
    public Continent(String continentName){
        this.continentName = continentName;
        continentTerritory = new HashMap<>();
    }

    /**
     * Adds territory to continentTerritory HashMap
     * @param territory_name Name of territory
     * @param territory Model.Territory Object
     */
    public void addContinentTerritory(String territory_name, Territory territory){
        continentTerritory.put(territory_name,territory);
    }

    /**
     * Get the HashMap of Territories that are in the Model.Continent
     * @return The map of territories in Model.Continent
     */
    public HashMap<String, Territory> getContinentTerritory() {
        return continentTerritory;
    }

    /**
     * Returns the name of the Model.Continent
     * @return String of continent name
     */
    public String getContinentName() {
        return continentName;
    }

    /**
     * The purpose of the method returns is to return true if the player owns all territories in the continent.
     * @param player checks if a player is occupying a territory with this input
     * @return Checks if the player owns all the territories in the continent and returns True if they do
     */
    public Boolean checkContinentOccupant(Player player){
        for(Territory temp_territory : continentTerritory.values()){
            if (!player.equals(temp_territory.getOccupant())){
                return false;
            }
        }
        return true;
    }
}
