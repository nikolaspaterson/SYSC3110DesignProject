import java.util.ArrayList;
import java.util.HashMap;

public class Territory {

    private String territory_name;
    private String continent_name;
    private String player_name;
    private int troops;
    private HashMap<String,Territory> neighbours;

    public Territory(String continent_name, String territory_name){
        this.continent_name = continent_name;
        this.territory_name = territory_name;
        this.neighbours = new HashMap<>();
    }
    public void set_neighbours(String territory_name, Territory territory_obj){
        neighbours.put(territory_name,territory_obj);
    }
    public void print_info(){
        System.out.println("Territory Name: " + territory_name );
        System.out.println("Continent Name: " + continent_name);
        System.out.println("Neighbours: " + neighbours.keySet().toString());
        System.out.println("================================================");
    }

}
