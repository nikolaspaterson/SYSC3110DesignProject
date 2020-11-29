package Model;

import Event.UserStatusEvent;
import Event.SaveEvent;
import JSONModels.JSONGameModel;
import JSONModels.JSONTerritory;
import Listener.SaveListener;
import Listener.UserStatusListener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class is used to handle all the logic of the Game.
 */
public class GameModel{

    private final ArrayList<Player> playerList;
    private Player currentPlayer;
    private int currentPlayerIndex;
    private HashMap<String, Continent> continentMap;
    private GameState currentState;
    private int outOfGame;
    private final ArrayList<Territory> commandTerritory;
    private HashMap<String, Territory> worldMap;
    private Timer aiTimer;
    private final ArrayList<UserStatusListener> gameViews;
    private String gameName;
    private final ArrayList<SaveListener> saveView;

    /**
     * Constructor of the Gameview, it is called in Controller.PlayerSelectController and the game begins after the construction of the class.
     */
    public GameModel() {
        playerList = new ArrayList<>();
        currentPlayer = null;
        gameViews = new ArrayList<>();
        outOfGame = 0;
        aiTimer = new Timer("AI");
        currentState = GameState.REINFORCE;
        currentPlayerIndex = 0;
        commandTerritory = new ArrayList<>();
        saveView = new ArrayList<>();
    }

    /**
     * Constructor for the GameModel class. This constructor is used to create the GameModel from the JSON file.
     * @param load the JSONObject
     * @param oldGame the old game model
     */
    public GameModel(JSONObject load, GameModel oldGame){
        JSONGameModel game_json = new JSONGameModel(load);
        for(Player temp : oldGame.getPlayers()){
            temp.removeAllPlayerListeners();
        }
        gameName = game_json.getGameName();
        playerList = new ArrayList<>();
        continentMap = new HashMap<>();
        worldMap = new HashMap<>();
        gameViews = new ArrayList<>();
        gameViews.addAll(oldGame.removeListeners());
        outOfGame = 0;
        aiTimer = new Timer("AI");
        currentPlayerIndex = game_json.getCurrentPlayerIndex();
        continentMap.putAll(oldGame.getContinentMap());
        worldMap.putAll(oldGame.getWorldMap());
        prepareTerritoriesJSON(game_json);
        preparePlayersJSON(game_json);
        commandTerritory = new ArrayList<>();
        currentPlayer = playerList.get(currentPlayerIndex);
        currentState = game_json.getGameState();
        saveView = new ArrayList<>();
        saveView.addAll(oldGame.removeAllSaveView());
        initializeAITimer();
        updateView();
    }

    public void setGameName(String name){
        gameName = name;
    }

    /**
     * This method is used to add UserStatuslisteners of the model.
     * @param view the Listener to add
     */
    public void addView(UserStatusListener view){
        gameViews.add(view);
    }

    /**
     * Removes all listeners of the model and returns it.
     * @return ArrayList<UserStatusListener> the old gameview list of listeners
     */
    public ArrayList<UserStatusListener> removeListeners(){
        ArrayList<UserStatusListener> duplicate = new ArrayList<>(gameViews);
        gameViews.clear();
        return duplicate;
    }

    /**
     * This method is used to add saveListener to the model.
     * @param listener save Listener
     */
    public void addSaveView(SaveListener listener){
        saveView.add(listener);
    }

    /**
     * This method is used to remove saveListener to the model.
     * @param listener saveListener
     */
    public void removeSaveView(SaveListener listener){
        saveView.remove(listener);
    }

    /**
     * This method is used to remove all saveListener to the model.
     * @return Arraylist of listeners
     */
    public ArrayList<SaveListener> removeAllSaveView(){
        ArrayList<SaveListener> temp = new ArrayList<>(saveView);
        saveView.clear();
        return temp;
    }

    /**
     * Method that saves game state
     * @param output_path location where file is saved
     */
    public void saveAction(String output_path) {
        try {
            JSONObject save_file = saveJSON();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-HH-mm-ss");
            Date new_date = new Date();
            String fileName = gameName + dateFormat.format(new_date)+".json";
            FileWriter writer = new FileWriter(output_path + fileName);
            String value = save_file.toString();
            writer.write(value);
            writer.close();
            updateSaveView(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to add all players in the game into an ArrayList.
     * @param players the player ArrayList
     */
    public void addPlayers(ArrayList<Player> players){
        playerList.addAll(players);
    }

    /**
     * This method is used to remove UserStatuslisteners of the model.
     * @param view the Listener to remove
     */
    public void removeView(UserStatusListener view){
        gameViews.remove(view);
    }

    public HashMap<String, Continent> getContinentMap(){
        return continentMap;
    }

    /**
     * This method is used to check if the Player is an AIPlayer and if so, to start adding the delay to all the AIPlayer's moves.
     */
    public void initializeAITimer() {
        if(currentPlayer instanceof AIPlayer) {
            int AISpeed = 100;
            aiTimer.scheduleAtFixedRate(new AITimer((AIPlayer) currentPlayer), AISpeed, AISpeed);
        }
    }

    /**
     * Gets the models for continentMap and worldMap and initializes the game to start.
     * @param continentMap continentMap
     * @param worldMap worldMap
     */
    public void getWorld(HashMap<String,Continent> continentMap,HashMap<String,Territory> worldMap) {
        this.continentMap = continentMap;
        this.worldMap = worldMap;
        currentPlayer = playerList.get(currentPlayerIndex);
        currentPlayer.playerBonus(continentMap);
        initializeAITimer();
        updateView();
    }

    /**
     * Getter for the player list
     * @return ArrayList<Player> the player list
     */
    public ArrayList<Player> getPlayers(){
        return playerList;
    }

    /**
     * This method is for switching the characters and occurs when the game reaches the end of the game state arrayList
     * A method there counts out of game to see if there is one person remaining to declare a winner.
     * The game will only end after the player ends their last turn.
     */
    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % playerList.size();
        currentPlayer = playerList.get(currentPlayerIndex);
        stopAITimer();
        if(currentPlayer.getTerritoriesOccupied().size() == 0){
            outOfGame++;
            nextPlayer();
        }else if(outOfGame == playerList.size()-1){
            currentState = GameState.WINNING;
        }else{
            outOfGame = 0;
            currentPlayer.playerBonus(continentMap);
            initializeAITimer();
        }
    }

    public void stopAITimer(){
        aiTimer.cancel();
        aiTimer = new Timer();
    }

    /**
     * This method is in charge of handling the switching of states and is called everytime the View.StatusBar nextButton
     * is pressed.
     * When reaching the end of state list it calls nextPlayer to load the next player and continue the game.
     */
    public void nextState(){
        clearCommandTerritory();
        switch (currentState){
            case FORTIFY -> {
                currentState = GameState.REINFORCE;
                nextPlayer();
            }
            case REINFORCE -> currentState = GameState.ATTACK;
            case ATTACK -> currentState = GameState.FORTIFY;
        }
        updateView();
    }

    /**
     * Method returns String, to check what the current state of the game
     * @return returns either Reinforce, Attack, or Fortify
     */
    public GameState getCurrentState(){
        return currentState;
    }

    /**
     * It returns the player object of the player who's turn it currently is
     * @return The player that is currently in use
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * This method is in charge of adding territories to the commandTerritory ArrayList and also setting the background
     * of that colour to be darker to indicate to the player that they have selected that territory
     * @param new_territory the new territory to add
     */
    public void addCommandTerritory(Territory new_territory){
        Color new_color = new_territory.getColor();
        new_territory.addColor(new_color.darker());
        commandTerritory.add(new_territory);
    }

    /**
     * This method is in charge of resetting the colour and timer of the Territories in commandTerritory ArrayList
     */
    public void clearCommandTerritory(){
        for(Territory x : commandTerritory){
            Color color_fix = x.getColor().brighter();
            x.addColor(color_fix);
        }
        commandTerritory.clear();
    }

    /**
     * Returns the ArrayList for currently selected territories which are then loaded into the PopUp methods for
     * Attack, Reinforce and Fortify.
     * @return list of the currently selected territories
     */
    public ArrayList<Territory> getCommandTerritory(){
        return commandTerritory;
    }

    /**
     * This method is used to get the first CommandTerritory in the list.
     * @return Territory first CommandTerritory
     */
    public Territory getFirstCommandTerritory(){
        return commandTerritory.get(0);
    }

    /**
     * Returns the size of the commandTerritory ArrayList
     * @return int the size.
     */
    public int getCommandTerritorySize(){
        return commandTerritory.size();
    }

    /**
     * Returns the continent that the Territory being passed in is contained in.
     * @param territory the territory to check
     * @return Continent the continent
     */
    public Continent getContinent(Territory territory){
        return continentMap.get(territory.getContinentName());
    }

    /**
     * Getter for the WorldMap.
     * @return HashMap<String, Territory> the world map.
     */
    public HashMap<String, Territory> getWorldMap() { return worldMap; }

    /**
     * This method is used to loop through every UserStatusListener that is listening to this and
     * create UserStatusEvents that will notify the view of the changes
     */
    private void updateView(){
        for(UserStatusListener temp : gameViews){
            temp.updateUserStatus(new UserStatusEvent(this, currentPlayer, currentState));
        }
    }

    /**
     * This method is used to loop through every UserStatusListener that is listening to this and
     * create UserStatusEvents that will notify the view of the changes
     * @param fileName name of file
     */
    public void updateSaveView(String fileName){
        for(SaveListener temp : saveView){
            temp.updateSave(new SaveEvent(this,fileName));
        }
    }

    /**
     * Saves the model to the JSONGameModel
     * @return JSONObject the game model
     */
    public JSONObject saveJSON(){
        JSONGameModel game_json = new JSONGameModel();
        game_json.setGameState(currentState);
        game_json.setGameName(gameName);
        game_json.setCurrentPlayerIndex(currentPlayerIndex);
        savingPlayer(game_json, true);
        savingPlayer(game_json, false);
        return game_json.getGame_json();
    }

    /**
     * Saves the list of players or the Territories in an JSONArray to the JSONGameModel.
     * @param game_json the JSONGameModel
     * @param savePlayer true if saving Player array to JSONGameModel, else save Territory array to JSONGameModel.
     */
    @SuppressWarnings("unchecked")
    private void savingPlayer(JSONGameModel game_json, boolean savePlayer) {
        JSONArray savingArr = new JSONArray();
        if(savePlayer) {
            for(Player temp_player : playerList){
                savingArr.add(temp_player.saveJSON());
            }
            game_json.setPlayer_array(savingArr);
        } else {
            for(Territory temp_territory : worldMap.values()){
                savingArr.add(temp_territory.saveJSON());
            }
            game_json.setTerritory_array(savingArr);
        }
    }

    /**
     * This method is used to set up all the territories for the JSONGameModel.
     * @param game_json the JSONGameModel
     */
    private void prepareTerritoriesJSON(JSONGameModel game_json){
        JSONArray territoryList = game_json.getTerritory_array();
        HashMap<String, Set<String>> old_links = new HashMap<>();
        for(Object territoryObj : territoryList){
            JSONTerritory temp_territory  = new JSONTerritory((JSONObject) territoryObj);
            String territory_name = temp_territory.getTerritoryName();
            Territory oldTerritory = worldMap.get(territory_name);
            old_links.put(territory_name,oldTerritory.getNeighbours().keySet());
            worldMap.replace(territory_name,new Territory(temp_territory.getTerritory_json(),oldTerritory));
        }
        for(String territory_names : old_links.keySet()){
            worldMap.get(territory_names).updateLink(old_links.get(territory_names),worldMap);
        }
        for(Continent continent : continentMap.values()){
            continent.updateTerritories(worldMap);
        }
    }

    /**
     * This method is used to set up all the players for the JSONGameModel.
     * @param game_json the JSONGameModel
     */
    private void preparePlayersJSON(JSONGameModel game_json){
        JSONArray players = game_json.getPlayer_array();
        for(Object playerObj : players){
            JSONObject temp_player  = (JSONObject) playerObj;
            String type = (String) temp_player.get("Type");
            if(type.contains("AIPlayer")){
                playerList.add(new AIPlayer(temp_player,worldMap,this));
            }else{
                playerList.add(new Player(temp_player,worldMap));
            }
        }
    }
}
