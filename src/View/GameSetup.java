package View;

import Model.Continent;
import Model.Player;
import Model.Territory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.awt.*;
import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * The GameSetup class is used to initialize all things prior to the Game being played.
 *
 * @author Erik Iuhas
 */
public class GameSetup {

    private final HashMap<String, Territory> world_map;
    private final String territory_CSV;
    private final ArrayList<Territory> unclaimed_territory;
    private final HashMap<String, Continent> continentMap;
    private final ArrayList<TerritoryButton> worldMapView;
    private BackgroundPanel background;
    private final String jsonPath;
    private String output_folder;
    private String output_subdirectory;
    private String gameName;


    /**
     * View.GameSetup is in charge of calling private methods which
     * distribute the troops to players and then begins to place
     * the troops randomly on the world_map
     * @param players a list of the players, provided by Game object
     */
    public GameSetup(ArrayList<Player> players,String jsonPath){
        territory_CSV = "/resources/TerritoryNeighbours.csv";

        world_map = new HashMap<>();
        continentMap = new HashMap<>();
        unclaimed_territory = new ArrayList<>();
        worldMapView = new ArrayList<>();
        this.jsonPath = jsonPath;
        set_neighboursJson();
        createSaveFolder();
        distribute_troops(players);
        }

    /**
     * By the rules of risk you can have 2-6 players, when there are 3 players
     * each player starts with 35 troops, this value lowers by five for each
     * player new player. 2 players is a special case and will be assigned 50 per person
     * @param players an array list of players
     */
    private void provide_troops(ArrayList<Player> players) {
        int distribute_val;
        if(players.size() == 2){distribute_val = 50;}
        else{distribute_val = 35 - 5 * (players.size() - 3);}
        for (Player x : players) {
            x.setDeployableTroops(distribute_val);
        }
    }

    /**
     * This method is in charge of iterating through the ArrayList of players and placing one troop on the unclaimed
     * territories. After claiming all territories each player begins to randomly distribute troops on their
     * own territories. The random placement is done by placing one troop at a time for a relatively even distributed
     * troop count.
     * @param players ArrayList of players
     */
    private void distribute_troops(ArrayList<Player> players){
        provide_troops(players);
        distributeTerritories(players);
        for(Player curr_player : players){
            ArrayList<Territory> territory_list = new ArrayList<>(curr_player.getTerritoriesOccupied().values());
            while(curr_player.getDeployableTroops() != 0){
                int troop_num = 1;
                Territory random_territory = select_random_territory(territory_list);
                random_territory.setTroops(random_territory.getTroops()+curr_player.placeDeployableTroops(troop_num));
            }
        }
        printWorldInfo();
    }

    /**
     * This method is used to distribute territories among the list of players.
     * @param players the arraylistof players
     */
    public void distributeTerritories(ArrayList<Player> players) {
        for(int i = 0; unclaimed_territory.size() != 0; i++){
            Player current_player = players.get(i%players.size());
            Territory current_territory = select_random_territory(unclaimed_territory);
            current_territory.setOccupant(current_player);
            current_territory.setTroops(current_player.placeDeployableTroops(1));
            current_player.addTerritory(current_territory.getTerritoryName(),current_territory);
            unclaimed_territory.remove(current_territory);
        }
    }

    /**
     * This method is used for randomly selecting a territory from a list.
     * @param list_of_territories a list of territories
     * @return return the randomly selected territory
     */
    private Territory select_random_territory(ArrayList<Territory> list_of_territories){
        Random number_generator = new Random();
        int territory_num = number_generator.nextInt(list_of_territories.size()) ;
        return list_of_territories.get(territory_num);
    }
    
    private void createSaveFolder(){
        try{
            String path =  this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            String decodedPath = URLDecoder.decode(path, "UTF-8");
            File chop_jar = new File(decodedPath);
            if(chop_jar.getName().contains(".jar")){
                decodedPath = decodedPath.replace(chop_jar.getName(),"");
            }
            System.out.println(decodedPath);
            output_folder = decodedPath + "output/";
            createFolder(output_folder);
            output_subdirectory = output_folder + gameName + "/";
            createFolder(output_subdirectory);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public String getGameName(){
        return gameName;
    }
    public String getOutput_subdirectory(){
        return output_subdirectory;
    }
    private void createFolder(String path){
        File file = new File(path);
        if(file.exists()){
            System.out.println("Directory already exists!");
        }else if(file.mkdir()){
            System.out.println("Directory created!");
        } else {
            System.out.println("Failed creating directory!");
        }
    }
    private void set_neighboursJson(){
        try {
            int total_territories = 0;
            JSONParser parser = new JSONParser();
            JSONObject map;
            if(jsonPath.contains("/resources/DefaultMap.json")){
                InputStreamReader jsonFile = new InputStreamReader(getClass().getResourceAsStream(jsonPath));
                map = (JSONObject) parser.parse(jsonFile);
                background = new BackgroundPanel((String) map.get("Background"));
            } else {
                FileReader jsonFile = new FileReader(jsonPath);
                map = (JSONObject) parser.parse(jsonFile);
                File newFile = new File(jsonPath);
                String mapPath = newFile.getPath().replace(newFile.getName(), "");
                background = new BackgroundPanel(mapPath + map.get("Background"));
            }
            gameName = (String) map.get("Name");
            for(Object obj_c : (JSONArray) map.get("Continents")){
                JSONObject continent = (JSONObject) obj_c;
                String continent_name = (String)continent.get("Continent");
                continentMap.put(continent_name, new Continent((String)continent.get("Continent"),(int) (long) continent.get("TroopBonus")));
                for(Object obj_t : (JSONArray) continent.get("Territories")){
                    JSONObject territory = (JSONObject) obj_t;
                    JSONArray neighbours = (JSONArray) territory.get("Neighbours");
                    String territory_name = (String) territory.get("Territory");
                    addToWorld(territory_name,continent_name);
                    for(Object neighbour_n:neighbours){
                        String neighbour_name = (String) neighbour_n;
                        addToWorld(neighbour_name,continent_name);
                        world_map.get(territory_name).addNeighbour(world_map.get(neighbour_name));
                    }
                    int X1 = (int) (long) territory.get("X1");
                    int Y1 = (int) (long) territory.get("Y1");
                    int X2 = (int) (long) territory.get("X2");
                    int Y2 = (int) (long) territory.get("Y2");
                    total_territories++;

                    TerritoryButton new_territory_view = new TerritoryButton(territory_name,X1,Y1,(X2-X1),(Y2-Y1),background);
                    world_map.get(territory_name).addTerritoryView(new_territory_view);

                    worldMapView.add(new_territory_view);

                }
            }
            for( Territory check_territory : world_map.values()){
                if(check_territory.debugLink().size() != total_territories){
                    System.out.println("Invalid");
                }
            }
            unclaimed_territory.addAll(world_map.values());

        } catch (Exception e){
            e.printStackTrace();
        }


    }

    private void addToWorld(String territory_name,String continent){
        if(world_map.get(territory_name) == null){
            Territory new_territory = new Territory(territory_name);
            new_territory.setContinentName(continent);
            world_map.put(territory_name,new_territory);
            continentMap.get(continent).addContinentTerritory(territory_name,new_territory);
        }
    }
    /**
     * Iterate through ArrayList generated by CSV to obtain neighbours for
     * territories.
     * @param territories ArrayList generated from TerritoryNeighbours.csv
     */
    private void set_neighbours(ArrayList<String[]> territories, Component parent){
        for(String[] temp_territory : territories){
            String continent_name = temp_territory[0];
            String territory_name = temp_territory[6];
            int x = Integer.parseInt(temp_territory[2]);
            int y = Integer.parseInt(temp_territory[3]);
            int width = Integer.parseInt(temp_territory[4]) - x;
            int height = Integer.parseInt(temp_territory[5]) - y;
            Territory added_territory = new Territory(territory_name);
            TerritoryButton territoryButton = new TerritoryButton(territory_name,x,y,width,height,parent);
            added_territory.addTerritoryView(territoryButton);
            worldMapView.add(territoryButton);
            world_map.put(territory_name,added_territory);
            createContinent(continent_name, added_territory, Integer.parseInt(temp_territory[1]));
        }
        linkNeighbours(territories);
        unclaimed_territory.addAll(world_map.values());
    }

    /**
     * This method is used to create a continent.
     *
     * @param continent_name the continent name
     * @param added_territory the territory to add
     * @param bonusTroops the amount of extra troops a continent gives if a player controls it
     */
    private void createContinent(String continent_name, Territory added_territory,int bonusTroops) {
        if (continentMap.get(continent_name) == null){
            continentMap.put(continent_name, new Continent(continent_name,bonusTroops));
        }
        added_territory.setContinentName(continent_name);
        continentMap.get(continent_name).addContinentTerritory(added_territory.getTerritoryName(),added_territory);
    }

    /**
     * This method is used to link territories that are connected.
     *
     * @param territories the arraylist of territories
     */
    private void linkNeighbours(ArrayList<String[]> territories) {
        for(String[] temp_territory : territories){
            String territory_name = temp_territory[6];
            ArrayList<String> temp_sub = new ArrayList<>(Arrays.asList(temp_territory));
            for(String temp_neighbours : temp_sub.subList(6,temp_sub.size())){
                world_map.get(territory_name).addNeighbour(world_map.get(temp_neighbours));
            }
        }
    }

    /**
     * Read the CSV file and obtain the Neighbours, it does this by reading file TerritoryNeighbour.csv and sorting it
     * into a String[].
     * @return list of territories
     */
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

    /**
     * Iterates through the world map territories and prints the current state of the world at the start of the game.
     */
    public void printWorldInfo(){
        for(Territory x : world_map.values()){
            x.updateView();
            x.print_info();
        }
    }
    public BackgroundPanel getBackground(){
        return background;
    }

    public String getOutput_folder(){
        return output_folder;
    }
    /**
     * After gameSetup ends it's final method is used for returning the world map. The gameSetup method could be used again
     * later if players would like to restart the game.
     * @return Hashmap of Territories
     */
    public HashMap<String, Territory> returnWorldMap(){
        return world_map;
    }

    /**
     * Getter for the world map of Territory Buttons.
     * @return ArrayList<TerritoryButton> the world map
     */
    public ArrayList<TerritoryButton> returnWorldMapView() { return worldMapView; }

    /**
     * Getter for the Continent map.
     * @return HashMap<String,Continent> the map of all continents
     */
    public HashMap<String,Continent> returnContinentMap() {
        return continentMap;
    }
}

