package JSONModels;

import Listener.PlayerListener;
import Model.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.Set;

public class JsonPlayer {
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

    public JsonPlayer(){
        player_json = new JSONObject();
    }
    public void loadJson(JSONObject player){
        name = (String) player.get("Name");
        total_troops = (int) (long)player.get("TotalTroops");
        deployableTroops = (int) (long) player.get("DeployableTroops");
        fortifyStatus = (boolean) player.get("Fortify");
        territories = (JSONArray) player.get("OccupiedTerritories");
        color = new Color((int) (long) player.get("Color"));
        playerNumber = (int) (long)player.get("PlayerIndex");
        inGame = (boolean) player.get("InGame");
    }
    public void setPlayer(String player){
        name = player;
        player_json.put("Name",player);
    }
    public void setPlayerIndex(int playerNumber){
        this.playerNumber = playerNumber;
        player_json.put("PlayerIndex", playerNumber);
    }
    public void setColor(Color color){
        this.color = color;
        player_json.put("Color", color.getRGB());
    }
    public void setDeployableTroops(int deployableTroops){
        this.deployableTroops = deployableTroops;
        player_json.put("DeployableTroops", deployableTroops);
    }
    public void setFortify(boolean fortifyStatus){
        this.fortifyStatus = fortifyStatus;
        player_json.put("Fortify", fortifyStatus);
    }
    public void setTotalTroops(int total_troops){
        this.total_troops = total_troops;
        player_json.put("TotalTroops",total_troops);
    }
    public void setType(String type){
        this.type = type;
        player_json.put("Type",type);
    }
    public void setInGame(boolean inGame){
        this.inGame = inGame;
        player_json.put("InGame",inGame);
    }
    public void setOccupiedTerritories(Set<String> territories){
        JSONArray occupiedTerritories = new JSONArray();
        for(String temp_territories : territories){
            occupiedTerritories.add(temp_territories);
        }
        this.territories = occupiedTerritories;
        player_json.put("OccupiedTerritories",occupiedTerritories);
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
