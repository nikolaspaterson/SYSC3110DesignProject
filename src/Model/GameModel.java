package Model;

import Listener.UserStatusListener;
import Event.UserStatusEvent;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;


public class GameModel{

    private final ArrayList<Player> playerList;
    private Player currentPlayer;
    private int currentPlayerIndex;
    private HashMap<String, Continent> continentMap;
    private final ArrayList<GameState> gameState;
    private int gameStateIndex;
    private GameState currentState;
    private int outOfGame;
    private final ArrayList<Territory> commandTerritory;
    private HashMap<String, Territory> worldMap;
    private final int AISpeed = 10;
    private Timer aiTimer;
    private ArrayList<UserStatusListener> gameViews;

    /**
     * Constructor of the Gameview, it is called in Controller.PlayerSelectController and the game begins after the construction of the class.
     * @throws IOException handle a possible IOException
     */
    public GameModel() throws IOException {
        playerList = new ArrayList<>();
        currentPlayer = null;
        gameViews = new ArrayList<>();
        outOfGame = 0;
        gameState = new ArrayList<>();
        gameState.add(GameState.REINFORCE);
        gameState.add(GameState.ATTACK);
        gameState.add(GameState.FORTIFY);
        gameStateIndex = 0;
        aiTimer = new Timer("AI");
        currentState = GameState.REINFORCE;
        currentPlayerIndex = 0;
        commandTerritory = new ArrayList<>();
    }

    public void addView(UserStatusListener view){
        gameViews.add(view);
    }

    public void addPlayers(ArrayList<Player> players){
        playerList.addAll(players);
    }

    public void removeView(UserStatusListener view){
        gameViews.remove(view);
    }

    public void initializeAITimer() {
        if(currentPlayer instanceof AIPlayer) {
            aiTimer.scheduleAtFixedRate(new AITimer((AIPlayer) currentPlayer),AISpeed,AISpeed);
        }
    }

    public void getGameSetup(GameSetup setup) {
        continentMap = setup.returnContinentMap();
        worldMap = setup.returnWorldMap();
        currentPlayer = playerList.get(currentPlayerIndex);
        currentPlayer.playerBonus(continentMap);
        initializeAITimer();
        updateView();
    }

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
        aiTimer.cancel();
        aiTimer = new Timer();
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

    /**
     * This method is in charge of handling the switching of states and is called everytime the View.StatusBar nextButton
     * is pressed.
     * When reaching the end of state list it calls nextPlayer to load the next player and continue the game.
     */
    public void nextState(){
        clearCommandTerritory();
        if(gameStateIndex + 1 == 3){
            gameStateIndex = (gameStateIndex + 1) % gameState.size();
            currentState = gameState.get(gameStateIndex);
            nextPlayer();
        }else{
            gameStateIndex = (gameStateIndex + 1) % gameState.size();
            currentState = gameState.get(gameStateIndex);
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

    public Territory getFirstCommandTerritory(){
        return commandTerritory.get(0);
    }

    /**
     * Returns the size of the commandTerritory ArrayList
     * @return commandTerritory
     */
    public int getCommandTerritorySize(){
        return commandTerritory.size();
    }

    public Continent getContinent(Territory territory){
        return continentMap.get(territory.getContinentName());
    }

    public HashMap<String, Territory> getWorldMap() { return worldMap; }

    private void updateView(){
        for(UserStatusListener temp : gameViews){
            temp.updateUserStatus(new UserStatusEvent(this, currentPlayer, currentState));
        }
    }

}
