package JSONModels;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * This class is used to form the Continents from the JSON file.
 */
public class JSONContinent {

    private final String name;
    private final int troopBonus;
    private final ArrayList<JSONMapTerritory> territories;

    /**
     * Class constructor for the JSONContinent class.
     * @param continent the continent
     */
    public JSONContinent(JSONObject continent) {
        name = continent.get(JSONContinentKeys.NAME.getKey()).toString();
        troopBonus = (int) (long) continent.get(JSONContinentKeys.TROOP_BONUS.getKey());
        territories = new ArrayList<>();
        for(Object territory : (JSONArray) continent.get(JSONContinentKeys.TERRITORIES.getKey())) {
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
