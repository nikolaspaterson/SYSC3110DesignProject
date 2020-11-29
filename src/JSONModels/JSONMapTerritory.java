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
        JSONArray temp = (JSONArray) territory.get("Neighbours");
        for(Object curr : temp) {
            neighbours.add(curr.toString());
        }
        name = territory.get("Territory").toString();
        X1 = (int) (long) territory.get("X1");
        Y1 = (int) (long) territory.get("Y1");
        X2 = (int) (long) territory.get("X2");
        Y2 = (int) (long) territory.get("Y2");
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
