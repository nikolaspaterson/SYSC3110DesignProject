package JSONModels;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class JSONMapTerritory {

    private ArrayList<String> neighbours;
    private String name;
    private int X1;
    private int Y1;
    private int X2;
    private int Y2;

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
