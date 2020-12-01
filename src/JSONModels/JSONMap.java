package JSONModels;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * The JSONMap class is used to form the Map from the JSON file.
 */
public class JSONMap {

    private final String name;
    private final String filePath;
    private final ArrayList<JSONContinent> continents;

    /**
     * Class constructor for the JSONMap class.
     * @param map the map
     */
    public JSONMap(JSONObject map) {
        name = map.get(JSONMapKeys.NAME.getKey()).toString();
        filePath = map.get(JSONMapKeys.BACKGROUND.getKey()).toString();
        System.out.println(filePath);
        continents = new ArrayList<>();
        for(Object continent : (JSONArray) map.get(JSONMapKeys.CONTINENTS.getKey())) {
            continents.add(new JSONContinent((JSONObject) continent));
            System.out.println();
        }
    }

    public String getName() {
        return name;
    }

    public String getFilePath() {
        return filePath;
    }

    public ArrayList<JSONContinent> getContinents() {
        return continents;
    }

}
