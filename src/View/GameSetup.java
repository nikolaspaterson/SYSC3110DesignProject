package View;

import JSONModels.JSONContinent;
import JSONModels.JSONMap;
import JSONModels.JSONMapTerritory;
import Model.Continent;
import Model.Player;
import Model.Territory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * The GameSetup class is used to initialize all things prior to the Game being played.
 *
 * @author Erik Iuhas
 */
public class GameSetup {

    private final HashMap<String, Territory> world_map;
    private final ArrayList<Territory> unclaimed_territory;
    private final HashMap<String, Continent> continentMap;
    private final ArrayList<TerritoryButton> worldMapView;
    private BackgroundPanel background;
    private final String jsonPath;
    private String output_subdirectory;
    private String gameName;
    private final static int TWO_PLAYER_TROOP_COUNT = 50;
    private final static int THREE_PLAYER_TROOP_COUNT = 35;
    private final static int REMOVE_TROOP_THRESHOLD = 5;

    /**
     * View.GameSetup is in charge of calling private methods which
     * distribute the troops to players and then begins to place
     * the troops randomly on the world_map
     * @param players a list of the players, provided by Game object
     */
    public GameSetup(ArrayList<Player> players,String jsonPath){
        world_map = new HashMap<>();
        continentMap = new HashMap<>();
        unclaimed_territory = new ArrayList<>();
        worldMapView = new ArrayList<>();
        this.jsonPath = jsonPath;
        setupGameFromJSON();
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
        if(players.size() == 2){distribute_val = TWO_PLAYER_TROOP_COUNT;}
        else{distribute_val = THREE_PLAYER_TROOP_COUNT - REMOVE_TROOP_THRESHOLD * (players.size() - 3);}
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

    /**
     * This method is used to create a save folder.
     */
    private void createSaveFolder(){
        try{
            String path =  this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
            File chop_jar = new File(decodedPath);
            if(chop_jar.getName().contains(FilePath.JAR_FILE_SIGNATURE.getPath())){
                decodedPath = decodedPath.replace(chop_jar.getName(),"");
            }
            String output_folder = decodedPath + "output/";
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

    /**
     * This method is used to create a folder.
     * @param path the path
     */
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

    /**
     * This method is used to load the game from the JSON file selected by the user.
     */
    private void setupGameFromJSON(){
        try {
            JSONParser parser = new JSONParser();
            JSONMap map;
            if(jsonPath.contains(FilePath.DEFAULT_MAP_JSON.getPath())){
                InputStreamReader jsonFile = new InputStreamReader(getClass().getResourceAsStream(jsonPath));
                map = new JSONMap((JSONObject) parser.parse(jsonFile));
                background = new BackgroundPanel(map.getFilePath());
            } else {
                FileReader jsonFile = new FileReader(jsonPath);
                map = new JSONMap((JSONObject) parser.parse(jsonFile));
                File newFile = new File(jsonPath);
                String mapPath = newFile.getPath().replace(newFile.getName(), "");
                background = new BackgroundPanel(mapPath + map.getFilePath());
            }
            initializeMap(map);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method is used to set up the entire map from the information being parsed in the JSON file.
     * @param map the JSONMap
     */
    private void initializeMap(JSONMap map) {
        gameName = map.getName();
        for(JSONContinent continent : map.getContinents()){
            continentMap.put(continent.getName(), new Continent(continent.getName(), continent.getTroopBonus()));
            for(JSONMapTerritory territory : continent.getTerritories()){
                addToWorld(territory.getName(),continent.getName());
                for(String neighbour: territory.getNeighbours()){
                    addToWorld(neighbour,continent.getName());
                    world_map.get(territory.getName()).addNeighbour(world_map.get(neighbour));
                }
                initializeTerritoryButtons(territory);
            }
        }
        unclaimed_territory.addAll(world_map.values());
    }

    /**
     * This method is used to set up all Territory Buttons with the position specified in the JSON that is being parsed.
     * @param territory The Territory from the JSON file
     */
    private void initializeTerritoryButtons(JSONMapTerritory territory) {
        int X1 = territory.getX1();
        int Y1 = territory.getY1();
        int width = territory.getX2() - X1;
        int height = territory.getY2() - Y1;
        TerritoryButton new_territory_view = new TerritoryButton(territory.getName(),X1,Y1,width,height,background);
        world_map.get(territory.getName()).addTerritoryView(new_territory_view);
        worldMapView.add(new_territory_view);
    }

    /**
     * This method is used to add territories and their corresponding continent they are contained in to the world map.
     * @param territory_name the territory name
     * @param continent the continent
     */
    private void addToWorld(String territory_name, String continent){
        if(world_map.get(territory_name) == null){
            Territory new_territory = new Territory(territory_name);
            new_territory.setContinentName(continent);
            world_map.put(territory_name,new_territory);
            continentMap.get(continent).addContinentTerritory(territory_name,new_territory);
        }
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

