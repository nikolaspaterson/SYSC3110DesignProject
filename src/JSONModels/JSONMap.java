package JSONModels;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class JSONMap {

    private String name;
    private String filePath;
    private ArrayList<JSONContinent> continents;

    public JSONMap(JSONObject map) {
        name = map.get("Name").toString();
        filePath = map.get("Background").toString();
        System.out.println(filePath);
        continents = new ArrayList<>();
        for(Object continent : (JSONArray) map.get("Continents")) {
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
