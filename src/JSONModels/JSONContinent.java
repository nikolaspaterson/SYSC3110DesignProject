package JSONModels;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class JSONContinent {

    private String name;
    private int troopBonus;
    private ArrayList<JSONMapTerritory> territories;

    @SuppressWarnings("unchecked")
    public JSONContinent(JSONObject continent) {
        name = continent.get("Continent").toString();
        troopBonus = (int) (long) continent.get("TroopBonus");
        territories = new ArrayList<>();
        for(Object territory : (JSONArray) continent.get("Territories")) {
            territories.add(new JSONMapTerritory((JSONObject) territory));
        }
    }

    public String getName() {
        return name;
    }

    public int getTroopBonus() {
        return troopBonus;
    }

    public ArrayList<JSONMapTerritory> getTerritories() {
        return territories;
    }


}
