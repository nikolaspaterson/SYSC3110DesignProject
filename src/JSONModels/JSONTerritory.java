package JSONModels;

import org.json.simple.JSONObject;

public class JSONTerritory {

    private final JSONObject territory_json;
    private String territoryName;
    private int troops;

    public JSONTerritory(){
        this.territory_json = new JSONObject();
    }

    public JSONTerritory(JSONObject load){
        loadJSON(load);
        this.territory_json = load;
    }

    public JSONObject getTerritory_json() {
        return territory_json;
    }

    public String getTerritoryName() {
        return territoryName;
    }

    public int getTroops() {
        return troops;
    }

    public void loadJSON(JSONObject territory_json) {
        territoryName = (String) territory_json.get("Territory");
        troops = (int) (long) territory_json.get("Troops");
    }

    @SuppressWarnings("unchecked")
    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
        territory_json.put("Territory",territoryName);
    }

    @SuppressWarnings("unchecked")
    public void setTroops(int troops) {
        this.troops = troops;
        territory_json.put("Troops",troops);
    }
}
