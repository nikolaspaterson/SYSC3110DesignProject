package JSONModels;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * The JSONMapTerritory class is used to form the territory from the JSON file.
 */
public class JSONMapTerritory {

    private final ArrayList<String> neighbours;
    private final String name;
    private final int X1;
    private final int Y1;
    private final int X2;
    private final int Y2;

    /**
     * Class constructor for the JSONMapTerritory class.
     * @param territory the territory to load
     */
    public JSONMapTerritory(JSONObject territory) {
        neighbours = new ArrayList<>();
        JSONArray temp = (JSONArray) territory.get(JSONMapKeys.NEIGHBOURS.getKey());
        for(Object curr : temp) {
            neighbours.add(curr.toString());
        }
        name = territory.get(JSONMapKeys.TERRITORY.getKey()).toString();
        X1 = (int) (long) territory.get(JSONMapKeys.COORDINATE_X1.getKey());
        Y1 = (int) (long) territory.get(JSONMapKeys.COORDINATE_Y1.getKey());
        X2 = (int) (long) territory.get(JSONMapKeys.COORDINATE_X2.getKey());
        Y2 = (int) (long) territory.get(JSONMapKeys.COORDINATE_Y2.getKey());
    }

    public ArrayList<String> getNeighbours() {
        return neighbours;
    }

    public String getName() {
        return name;
    }

    public int getX1() {
        return X1;
    }

    public int getY1() {
        return Y1;
    }

    public int getX2() {
        return X2;
    }

    public int getY2() {
        return Y2;
    }
}
