package JSONModels;

import Model.GameState;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonGameModel {

    private JSONObject game_json;
    private JSONArray player_array;
    private JSONArray territory_array;
    private GameState gameState;
    private String gameName;
    private int currentPlayerIndex;

    public JsonGameModel(){
        game_json = new JSONObject();
    }
    public JsonGameModel(JSONObject load){
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
        switch ((String)game_json.get("GameState")){
            case ("REINFORCE")-> gameState = GameState.REINFORCE;
            case ("ATTACK")-> gameState = GameState.ATTACK;
            case ("FORTIFY")-> gameState = GameState.FORTIFY;
        }
        gameName = (String) game_json.get("GameName");
        currentPlayerIndex = (int) (long) game_json.get("CurrentPlayer");
    }

    public void setPlayer_array(JSONArray player_array) {
        this.player_array = player_array;
        game_json.put("Players", player_array);
    }

    public void setTerritory_array(JSONArray territory_array) {
        this.territory_array = territory_array;
        game_json.put("Territories",territory_array);
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        game_json.put("GameState",gameState.toString());
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
        game_json.put("GameName",gameName);
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
        game_json.put("CurrentPlayer", currentPlayerIndex);
    }
}
