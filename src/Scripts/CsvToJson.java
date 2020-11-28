package Scripts;

import Model.Continent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CsvToJson {

    private String territory_CSV = "/resources/TerritoryNeighbours.csv";

    public CsvToJson() throws IOException {
        JSONObject outputJson = createJSONObject(read_csv());
        String output_path = "C:\\Users\\eriki\\OneDrive\\SoftwareEng3\\Sysc3110\\RiskAssignment\\SYSC3110DesignProject\\src\\resources\\DefaultMap.json";
        File output_file = new File(output_path);
        if(output_file.createNewFile()){
            System.out.println("File created");
        } else{
            System.out.println("File existed");
        }
        FileWriter writer = new FileWriter(output_file);
        writer.write(outputJson.toString());
        writer.close();
    }

    private ArrayList<String[]> read_csv(){
        String row;
        ArrayList<String[]> territory_list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(this.territory_CSV)));
            reader.readLine(); //Skip first line
            while((row = reader.readLine()) != null){
                String[] territories = row.split(",");
                territory_list.add(territories);
            }
        } catch (FileNotFoundException e){
            System.out.println("Error: File not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return territory_list;
    }
    private JSONObject createJSONObject(ArrayList<String[]> territories) {


        HashMap<String, JSONObject> continentObj  = new HashMap<>();


        for (String[] temp_territory : territories) {
            System.out.println(temp_territory);
            JSONArray newContinents;

            String continent_name = temp_territory[0];
            if(continentObj.get(continent_name) == null){
                JSONObject curr_continentObj = new JSONObject();
                continentObj.put(continent_name,curr_continentObj);
                curr_continentObj.put("Continent", continent_name);
                curr_continentObj.put("TroopBonus", Integer.valueOf(temp_territory[1]));
                curr_continentObj.put("Territories",new JSONArray());
                newContinents = (JSONArray) continentObj.get(continent_name).get("Territories");

            }else{

                newContinents = (JSONArray) continentObj.get(continent_name).get("Territories");

            }
            JSONObject newTerritory = new JSONObject();
            String territory_name = temp_territory[6];
            int x1 = Integer.parseInt(temp_territory[2]);
            int y1 = Integer.parseInt(temp_territory[3]);
            int x2 = Integer.parseInt(temp_territory[4]);
            int y2 = Integer.parseInt(temp_territory[5]);

            JSONArray neighbours = new JSONArray();

            ArrayList<String> temp_sub = new ArrayList<>(Arrays.asList(temp_territory));
            for(String temp_neighbours : temp_sub.subList(7,temp_sub.size())){
                neighbours.add(temp_neighbours);
            }

            newTerritory.put("Territory", territory_name);
            newTerritory.put("Neighbours",neighbours);
            newTerritory.put("X1",x1);
            newTerritory.put("Y1",y1);
            newTerritory.put("X2",x2);
            newTerritory.put("Y2",y2);

            newContinents.add(newTerritory);
        }
        JSONObject map = new JSONObject();
        JSONArray continents = new JSONArray();
        for(JSONObject temp_continent : continentObj.values()){
            continents.add(temp_continent);
        }
        map.put("Background", "/resources/Map.png");
        map.put("Name" , "DefaultMap");
        map.put("Continents", continents);


        return map;

    }

    public static void main(String[] args) throws IOException {
        new CsvToJson();
    }

}

