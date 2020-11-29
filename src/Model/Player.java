package Model;

import JSONModels.JSONPlayer;
import Listener.PlayerListener;
import Event.PlayerEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

    private String name;
    private HashMap<String, Territory> territoriesOccupied;
    private int deployableTroops;
    private int total_troops;
    private Icon player_icon;
    private Color player_color;
    private boolean inGame;
    private boolean fortifyStatus;
    private ArrayList<PlayerListener> playerListeners;
    private int playerNumber;
    private String filePath;

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
        fortifyStatus = true;
    }

    /**
     * Constructor for the Player class. This constructor is used to create the Player from the JSON file.
     * @param player the JSONObject
     * @param currentMap the current map
     */
    public Player(JSONObject player, HashMap<String,Territory> currentMap){
        JSONPlayer player_json = new JSONPlayer(player);
        name = player_json.getName();
        player_color = player_json.getColor();
        deployableTroops = player_json.getDeployableTroops();
        fortifyStatus = player_json.isFortifyStatus();
        playerNumber = player_json.getPlayerNumber();
        inGame = player_json.isInGame();
        total_troops = player_json.getTotal_troops();
        territoriesOccupied = new HashMap<>();
        initializeTerritories(player_json, currentMap);
        playerListeners = new ArrayList<>();
        if(player_json.getFilePath() != null) {
            filePath = player_json.getFilePath();
            player_icon = scaleImage(filePath);
        }
    }

    /**
     * This method is used to store the player's color and icon.
     *
     * @param player_color the color
     * @param player_icon the icon
     */
    public void addGuiInfo(Color player_color, ImageIcon player_icon, String filePath){
        this.player_color = player_color;
        this.player_icon = player_icon;
        this.filePath = filePath;
    }

    public String getFilePath() { return filePath; }

    public void setPlayerNumber(int number){
        playerNumber = number;
    }

    public int getPlayerNumber(){
        return playerNumber;
    }
    /**
     * PlayerBonus calculates how many troops each player will get at the start of their turn by checking how many territories
     * they own and weather or not they occupy an entire continent
     * @param continentMap continentMap
     */
    public void playerBonus(HashMap<String, Continent> continentMap){
        int troops = 0;
        for(Continent temp_continent : continentMap.values()){
            if(temp_continent.checkContinentOccupant(this)){
                troops += temp_continent.getBonusTroops();
            }
        }

        if ((this.getTerritoriesOccupied().size()) <= 9) {
            troops += 3;
        } else {
            troops += ((this.getTerritoriesOccupied().size()) / 3);
        }
        this.addDeployableTroops(troops);
    }

    /**
     * Getter for the player's icon.
     * @return Icon the player's icon
     */
    public Icon getPlayer_icon() {
        return player_icon;
    }

    /**
     * Getter for the player's color
     * @return Color the player's color
     */
    public Color getPlayer_color() {
        return player_color;
    }

    public void setPlayerListeners(ArrayList<PlayerListener> playerListeners) {
        this.playerListeners = playerListeners;
    }

    /**
     * This method is used to addPlayerListeners of the Model.
     * @param list the listener to be added.
     */
    public void addPlayerListener(PlayerListener list){
        playerListeners.add(list);
        updateListeners();
    }

    /**
     * This method is used to removePlayerListeners of the Model.
     * @param list the listener to be remove.
     */
    public void removePlayerListener(PlayerListener list){ playerListeners.remove(list); }

    public void removeAllPlayerListeners(){
        playerListeners.clear();
    }

    /**
     * This method used to know if I player is fortifying or not.
     * @return boolean fortify status
     */
    public boolean getFortifyStatus() { return fortifyStatus; }

    /**
     * Sets the fortify status for the player
     * @param fortifyStatus the status
     */
    public void setFortifyStatus(boolean fortifyStatus) { this.fortifyStatus = fortifyStatus; }

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
        fortifyStatus = true;
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

    /**
     * This method is used to subtract troops from the amount of deployable troops.
     * @param subtract the amount of troops to remove.
     */
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

    /**
     * This method is used to eliminate players from the game.
     */
    public void eliminatePlayer() {
        inGame = false;
        updateListeners();
    }

    /**
     * Getter for the status (if the player is eliminated from the game or not)
     * @return boolean status
     */
    public boolean getStatus(){ return inGame;}

    /**
     * Updates all the necessary labels and backgrounds that the view needs to change after an event.
     */
    public void updateListeners(){
        for(PlayerListener temp : playerListeners){
            temp.handlePlayerUpdate(new PlayerEvent(this,deployableTroops,total_troops,inGame));
        }
    }

    public int getTotal_troops() {
        return total_troops;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTerritoriesOccupied(HashMap<String, Territory> territoriesOccupied) {
        this.territoriesOccupied = territoriesOccupied;
    }

    public void setTotal_troops(int total_troops) {
        this.total_troops = total_troops;
    }

    public void setPlayer_icon(Icon player_icon) {
        this.player_icon = player_icon;
    }

    public void setPlayer_color(Color player_color) {
        this.player_color = player_color;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    /**
     * Saves the Player to the JSONPlayer
     * @return JSONObject the JSONPlayer
     */
    public JSONObject saveJSON(){
        JSONPlayer player_json = new JSONPlayer();
        player_json.setPlayer(name);
        player_json.setPlayerIndex(playerNumber);
        player_json.setColor(player_color);
        player_json.setDeployableTroops(deployableTroops);
        player_json.setFortify(fortifyStatus);
        player_json.setTotalTroops(total_troops);
        player_json.setType(getClass().getName());
        player_json.setInGame(inGame);
        player_json.setOccupiedTerritories(territoriesOccupied.keySet());
        player_json.setIconPath(filePath);
        return player_json.getPlayer_json();
    }

    /**
     * This method is used to initialize the Territories that the Player owns which is read from the JSONPlayer object
     * @param player_json JSONPlayer
     * @param currentMap the current map
     */
    private void initializeTerritories(JSONPlayer player_json, HashMap<String,Territory> currentMap) {
        JSONArray list_territories = player_json.getTerritories();
        for(Object territoryObj : list_territories){
            String territoryName = (String) territoryObj;
            territoriesOccupied.put(territoryName, currentMap.get(territoryName));
            currentMap.get(territoryName).setOccupant(this);
        }
    }

    /**
     * This method is used to scale an image
     * @param filename the image filename
     * @return ImageIcon the image
     */
    public ImageIcon scaleImage(String filename) {
        ImageIcon scaledImg = new ImageIcon(getClass().getResource(filename));
        Image img = scaledImg.getImage().getScaledInstance( 85, 85,  java.awt.Image.SCALE_SMOOTH );
        scaledImg = new ImageIcon(img);
        return scaledImg;
    }
}
