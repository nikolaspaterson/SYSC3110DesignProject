package JSONModels;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.awt.*;

public class JsonAIPlayer extends  JsonPlayer{

    private final JSONObject ai_player;
    private boolean attacking;

    public JsonAIPlayer(){
        ai_player = new JSONObject();
    }

    public JsonAIPlayer(JSONObject load){
        ai_player = new JSONObject();
        setPlayer_json();
        loadJson(load);
    }

    @Override
    public void loadJson(JSONObject player){
        setPlayer((String) player.get("Name"));
        setTotalTroops((int) (long)player.get("TotalTroops"));
        setDeployableTroops((int) (long) player.get("DeployableTroops"));
        setFortify((boolean) player.get("Fortify"));
        setTerritories((JSONArray) player.get("OccupiedTerritories"));
        setColor(new Color((int) (long) player.get("Color")));
        setPlayerIndex((int) (long)player.get("PlayerIndex"));
        setInGame((boolean) player.get("InGame"));
        setAttacking((boolean) player.get("Attacking"));
        setIconPath((String) player.get("FilePath"));
    }

    @SuppressWarnings("unchecked")
    public void setAttacking(boolean attacking){
        this.attacking = attacking;
        ai_player.put("Attacking", attacking);
    }

    @SuppressWarnings("unchecked")
    public JSONObject getAi_player() {
        ai_player.putAll(getPlayer_json());
        return ai_player;
    }

    public boolean isAttacking() {
        return attacking;
    }
}
