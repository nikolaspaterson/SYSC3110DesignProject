package Model;

import Listener.PlayerListener;
import View.PlayerView;
import Event.PlayerEvent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Model.Player class is responsible for containing important attributes that every player should have in the game of Risk.
 *
 * @author Ahmad El-Sammak
 * @author Erik Iuhas
 */
public class Player {

    private final String name;
    private final HashMap<String, Territory> territoriesOccupied;
    private int deployableTroops;
    private int total_troops;
    private Icon player_icon;
    private Color player_color;
    private boolean inGame;

    private ArrayList<PlayerListener> playerListeners;

    /**
     * Class constructor for the Model.Player class. Sets the name of the player and initializes the HashMap which will store what territory the player occupies.
     * @param name the name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.total_troops = 0;
        territoriesOccupied = new HashMap<>();
        playerListeners = new ArrayList<>();
        inGame = true;
    }


    public void addGuiInfo(Color player_color, ImageIcon player_icon){
        this.player_color = player_color;
        this.player_icon = player_icon;
    }

    public Icon getPlayer_icon() {
        return player_icon;
    }

    public Color getPlayer_color() {
        return player_color;
    }


    public void addPlayerListener(PlayerListener list){
        playerListeners.add(list);
    }
    public void removePlayerListener(PlayerListener list){
        playerListeners.remove(list);
    }
    /**
     * Gets the PlayerView
     * @return PlayerView
     */

    /**
     * Gets the player's name.
     * @return String - Players name
     */
    public String getName(){
        return name;
    }

    /**
     * Getter for the hashmap containing all the territories that the player occupies.
     * @return HashMap<String,Model.Territory> The territories Occupied.
     */
    public HashMap<String, Territory> getTerritoriesOccupied() { return territoriesOccupied; }

    /**
     * Adds the deployableTroops on top of the existing deployableTroops.
     * This accounts for bonus troops awarded to the player at the beginning of a round.
     * @param deployableTroops number of deployable troops.
     */
    public void addDeployableTroops(int deployableTroops) {
        this.deployableTroops += deployableTroops;
        addTotal(deployableTroops);
        updateListeners();
    }

    /**
     * This method is used to add to the total troops that the player throughout all territories.
     * @param troops the number of troops to add.
     */
    public void addTotal(int troops) {
        total_troops += troops;
        updateListeners();
    }

    /**
     * Gets the amount of deployable troops the player can use during their reinforcement.
     * @return int number of deployable troops.
     */
    public int getDeployableTroops() {
        return deployableTroops;
    }

    /**
     * Sets the amount of deployable troops the player can use during their reinforcement.
     * @param deployableTroops number of deployable troops.
     */
    public void setDeployableTroops(int deployableTroops) {
        this.deployableTroops = deployableTroops;
        addTotal(deployableTroops);
        updateListeners();
    }

    public void subtractDeployableTroops(int subtract){
        this.deployableTroops -= subtract;
        updateListeners();
    }

    /**
     * Decrements the troops that are available to deploy and returns the value that it was decremented by.
     * @param troop_count number of troops
     * @return int - The amount of deployable troops.
     */
    public int placeDeployableTroops(int troop_count) {
        if (deployableTroops - troop_count >= 0) {
            deployableTroops -= troop_count;
            updateListeners();
            return troop_count;
        } else {
            int temp = deployableTroops;
            deployableTroops = 0;
            updateListeners();
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
        if(territoriesOccupied.size() == 0){
            eliminatePlayer();
        }


    }

    /**
     * Combines the Model.Player's Name, all their Model.Territory's owned and how many troops are in each Model.Territory.
     * @return String combination of information above.
     */
    public String toString() {
        String output = "------>Model.Player: " + this.name + "<------\n";
        String enemy;
        String ally;
        for(Territory temp_territory : territoriesOccupied.values()) {
            output += "\n======TerritoryOwned:Troops======\n";
            output += "         " + temp_territory.getTerritoryName() + " : " + temp_territory.getTroops() + "\n";
            enemy = "Neighbouring Enemy Territories: ";
            ally = "Neighbouring Ally Territories: ";
            for(Territory owners : temp_territory.getNeighbours().values()){
                if(owners.getOccupant().equals(this)) {
                    ally += owners.getTerritoryName() + ": " + owners.getTroops() + ", ";
                }else{
                    enemy += owners.getTerritoryName() + " : " + owners.getTroops() + ", ";
                }
            }
            ally = ally.substring(0,ally.length() -2) + "\n";
            enemy = enemy.substring(0,enemy.length() -2) + "\n";
            output += ally + enemy;
        }
        return output;
    }


    public void eliminatePlayer() {
        inGame = false;
        updateListeners();
    }

    public boolean getStatus(){ return inGame;}

    /**
     * Updates all the necessary labels and backgrounds that the view needs to change after an event.
     */
    public void updateListeners(){
        for(PlayerListener temp : playerListeners){
            temp.handlePlayerUpdate(new PlayerEvent(this,deployableTroops,total_troops,inGame));
        }
    }

}
