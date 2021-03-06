package JSONModels;

import Model.GameState;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * This class is used to form the GameModel from the JSON file.
 */
public class JSONGameModel {

    private final JSONObject game_json;
    private JSONArray player_array;
    private JSONArray territory_array;
    private GameState gameState;
    private String gameName;
    private int currentPlayerIndex;

    /**
     * Class constructor for the JSONGameModel class.
     */
    public JSONGameModel(){
        game_json = new JSONObject();
    }

    /**
     * Class constructor for the JSONGameModel class.
     *
     * @param load the load
     */
    public JSONGameModel(JSONObject load){
        loadJSON(load);
        game_json = load;
    }

    public JSONObject getGame_json() {
        return game_json;
    }

    public JSONArray getPlayer_array() {
        return player_array;
    }

    public JSONArray getTerritory_array() {
        return territory_array;
    }

    public GameState getGameState() {
        return gameState;
    }

    public String getGameName() {
        return gameName;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * This method is used to load the AIPlayer's from JSON object.
     * @param game_json the game in JSONObject
     */
    public void loadJSON(JSONObject game_json) {
        player_array = (JSONArray) game_json.get(JSONGameModelKeys.PLAYERS.getKey());
        territory_array = (JSONArray) game_json.get(JSONGameModelKeys.TERRITORIES.getKey());
        setGameState((String) game_json.get(JSONGameModelKeys.GAME_STATE.getKey()));
        gameName = (String) game_json.get(JSONGameModelKeys.NAME.getKey());
        currentPlayerIndex = (int) (long) game_json.get(JSONGameModelKeys.CURRENT_PLAYER.getKey());
    }

    /**
     * Sets the GameState according to what state the JSON file was saved at.
     * @param state the state of the game
     */
    private void setGameState(String state) {
        if (state.equals(GameState.REINFORCE.toString())) {
            gameState = GameState.REINFORCE;
        } else if (state.equals(GameState.ATTACK.toString())) {
            gameState = GameState.ATTACK;
        } else if (state.equals(GameState.FORTIFY.toString())) {
            gameState = GameState.FORTIFY;
        }
    }

    /**
     * sets the player array.
     * @param player_array the player array.
     */
    @SuppressWarnings("unchecked")
    public void setPlayer_array(JSONArray player_array) {
        this.player_array = player_array;
        game_json.put(JSONGameModelKeys.PLAYERS.getKey(), player_array);
    }

    /**
     * sets the territory array.
     * @param territory_array the territory array.
     */
    @SuppressWarnings("unchecked")
    public void setTerritory_array(JSONArray territory_array) {
        this.territory_array = territory_array;
        game_json.put(JSONGameModelKeys.TERRITORIES.getKey(),territory_array);
    }

    /**
     * sets the GameState.
     * @param gameState the state of the game.
     */
    @SuppressWarnings("unchecked")
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        game_json.put(JSONGameModelKeys.GAME_STATE.getKey(),gameState.toString());
    }

    /**
     * Sets the name of the game
     * @param gameName the game name
     */
    @SuppressWarnings("unchecked")
    public void setGameName(String gameName) {
        this.gameName = gameName;
        game_json.put(JSONGameModelKeys.NAME.getKey(),gameName);
    }

    /**
     * sets the current players index.
     * @param currentPlayerIndex the player's turn
     */
    @SuppressWarnings("unchecked")
    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
        game_json.put(JSONGameModelKeys.CURRENT_PLAYER.getKey(), currentPlayerIndex);
    }
}
