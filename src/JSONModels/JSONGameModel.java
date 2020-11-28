package JSONModels;

import Model.GameState;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONGameModel {

    private final JSONObject game_json;
    private JSONArray player_array;
    private JSONArray territory_array;
    private GameState gameState;
    private String gameName;
    private int currentPlayerIndex;

    public JSONGameModel(){
        game_json = new JSONObject();
    }

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

    public void loadJSON(JSONObject game_json) {
        player_array = (JSONArray) game_json.get("Players");
        territory_array = (JSONArray) game_json.get("Territories");
        setGameState((String)game_json.get("GameState"));
        gameName = (String) game_json.get("GameName");
        currentPlayerIndex = (int) (long) game_json.get("CurrentPlayer");
    }

    private void setGameState(String state) {
        switch (state) {
            case "REINFORCE" -> gameState = GameState.REINFORCE;
            case "ATTACK" -> gameState = GameState.ATTACK;
            case "FORTIFY" -> gameState = GameState.FORTIFY;
        }
    }

    @SuppressWarnings("unchecked")
    public void setPlayer_array(JSONArray player_array) {
        this.player_array = player_array;
        game_json.put("Players", player_array);
    }

    @SuppressWarnings("unchecked")
    public void setTerritory_array(JSONArray territory_array) {
        this.territory_array = territory_array;
        game_json.put("Territories",territory_array);
    }

    @SuppressWarnings("unchecked")
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        game_json.put("GameState",gameState.toString());
    }

    @SuppressWarnings("unchecked")
    public void setGameName(String gameName) {
        this.gameName = gameName;
        game_json.put("GameName",gameName);
    }

    @SuppressWarnings("unchecked")
    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
        game_json.put("CurrentPlayer", currentPlayerIndex);
    }
}
