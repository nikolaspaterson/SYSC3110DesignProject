package JSONModels;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.awt.*;
import java.util.Set;

/**
 * This class is used to form the Player from the JSON file
 */
public class JSONPlayer {

    private JSONObject player_json;
    private String name;
    private int playerNumber;
    private Color color;
    private int deployableTroops;
    private boolean fortifyStatus;
    private int total_troops;
    private String type;
    private boolean inGame;
    private JSONArray territories;
    private String filePath;

    /**
     * Class constructor for the JSONPlayer class.
     */
    public JSONPlayer(){
        player_json = new JSONObject();
    }

    /**
     * Class constructor for the JSONPlayer class.
     * @param load the load
     */
    public JSONPlayer(JSONObject load){
        loadJson(load);
        player_json = load;
    }

    /**
     * This method is used to load the Player from JSON object.
     * @param player the player in a JSON Object
     */
    public void loadJson(JSONObject player){
        name = (String) player.get(JSONPlayerKeys.NAME.getKey());
        total_troops = (int) (long)player.get(JSONPlayerKeys.TOTAL_TROOPS.getKey());
        deployableTroops = (int) (long) player.get(JSONPlayerKeys.DEPLOYABLE_TROOPS.getKey());
        fortifyStatus = (boolean) player.get(JSONPlayerKeys.FORTIFY_STATUS.getKey());
        territories = (JSONArray) player.get(JSONPlayerKeys.OCCUPIED_TERRITORIES.getKey());
        color = new Color((int) (long) player.get(JSONPlayerKeys.PLAYER_COLOR.getKey()));
        playerNumber = (int) (long)player.get(JSONPlayerKeys.PLAYER_NUMBER.getKey());
        inGame = (boolean) player.get(JSONPlayerKeys.PLAYER_STATUS.getKey());
        filePath = (String) player.get(JSONPlayerKeys.PLAYER_ICON.getKey());
    }

    public void setPlayer_json(){
        player_json = new JSONObject();
    }

    @SuppressWarnings("unchecked")
    public void setIconPath(String filePath) {
        this.filePath = filePath;
        player_json.put(JSONPlayerKeys.PLAYER_ICON.getKey(), filePath);
    }

    public String getFilePath() { return filePath; }

    @SuppressWarnings("unchecked")
    public void setPlayer(String player){
        name = player;
        player_json.put(JSONPlayerKeys.NAME.getKey(),player);
    }

    @SuppressWarnings("unchecked")
    public void setPlayerIndex(int playerNumber){
        this.playerNumber = playerNumber;
        player_json.put(JSONPlayerKeys.PLAYER_NUMBER.getKey(), playerNumber);
    }

    @SuppressWarnings("unchecked")
    public void setColor(Color color){
        this.color = color;
        player_json.put(JSONPlayerKeys.PLAYER_COLOR.getKey(), color.getRGB());
    }

    @SuppressWarnings("unchecked")
    public void setDeployableTroops(int deployableTroops){
        this.deployableTroops = deployableTroops;
        player_json.put(JSONPlayerKeys.DEPLOYABLE_TROOPS.getKey(), deployableTroops);
    }

    @SuppressWarnings("unchecked")
    public void setFortify(boolean fortifyStatus){
        this.fortifyStatus = fortifyStatus;
        player_json.put(JSONPlayerKeys.FORTIFY_STATUS.getKey(), fortifyStatus);
    }

    @SuppressWarnings("unchecked")
    public void setTotalTroops(int total_troops){
        this.total_troops = total_troops;
        player_json.put(JSONPlayerKeys.TOTAL_TROOPS.getKey(), total_troops);
    }

    @SuppressWarnings("unchecked")
    public void setType(String type){
        this.type = type;
        player_json.put(JSONPlayerKeys.TYPE.getKey(), type);
    }

    @SuppressWarnings("unchecked")
    public void setInGame(boolean inGame){
        this.inGame = inGame;
        player_json.put(JSONPlayerKeys.PLAYER_STATUS.getKey(), inGame);
    }

    @SuppressWarnings("unchecked")
    public void setOccupiedTerritories(Set<String> territories){
        JSONArray occupiedTerritories = new JSONArray();
        occupiedTerritories.addAll(territories);
        this.territories = occupiedTerritories;
        player_json.put(JSONPlayerKeys.OCCUPIED_TERRITORIES.getKey(), occupiedTerritories);
    }

    public void setTerritories(JSONArray territories) {
        this.territories = territories;
    }

    public String getName() {
        return name;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Color getColor() {
        return color;
    }

    public int getDeployableTroops() {
        return deployableTroops;
    }

    public boolean isFortifyStatus() {
        return fortifyStatus;
    }

    public int getTotal_troops() {
        return total_troops;
    }

    public String getType() {
        return type;
    }

    public boolean isInGame() {
        return inGame;
    }

    public JSONArray getTerritories() {
        return territories;
    }

    public JSONObject getPlayer_json(){
        return player_json;
    }

}
