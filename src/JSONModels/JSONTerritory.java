package JSONModels;

import org.json.simple.JSONObject;

/**
 * This class is used to form the Territory from the JSON file.
 */
public class JSONTerritory {

    private final JSONObject territory_json;
    private String territoryName;
    private int troops;

    /**
     * Class constructor for the JSONTerritory class.
     */
    public JSONTerritory(){
        this.territory_json = new JSONObject();
    }

    /**
     * Class constructor for the JSONTerritory class.
     * @param load the load
     */
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

    /**
     * This method is used to load the Territory from JSON object.
     * @param territory_json the territory JSONObject
     */
    public void loadJSON(JSONObject territory_json) {
        territoryName = (String) territory_json.get(JSONMapKeys.TERRITORY.getKey());
        troops = (int) (long) territory_json.get(JSONMapKeys.TROOPS.getKey());
    }

    @SuppressWarnings("unchecked")
    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
        territory_json.put(JSONMapKeys.TERRITORY.getKey(),territoryName);
    }

    @SuppressWarnings("unchecked")
    public void setTroops(int troops) {
        this.troops = troops;
        territory_json.put(JSONMapKeys.TROOPS.getKey(),troops);
    }
}
