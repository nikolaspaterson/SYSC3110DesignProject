import java.util.ArrayList;
import java.util.HashMap;

public class Territory {

    private String territory_name;
    private String continent_name;
    private Player player;
    private int troops;
    private HashMap<String,Territory> neighbours;

    public Territory(String continent_name, String territory_name){
        this.continent_name = continent_name;
        this.territory_name = territory_name;
        this.neighbours = new HashMap<>();
        this.troops = 0;
    }
    public void set_neighbour(String territory_name, Territory territory_obj){
        neighbours.put(territory_name,territory_obj);
    }
    public void print_info(){
        System.out.println("Territory Name: " + territory_name );
        System.out.println("Continent Name: " + continent_name);
        System.out.println("Neighbours: " + neighbours.keySet().toString());
        System.out.println("Owner: " + player.returnName());
        System.out.println("Troop Count: " + troops);
        System.out.println("================================================");
    }
    public void setPlayer(Player new_owner, int troop_count){
        player = new_owner;
        troops = troop_count;
    }
    public String getName(){
        return territory_name;
    }
    public String getPlayerString(){
        return player.returnName();
    }
    public void setTroops(int troop_count){
        troops += troop_count;
    }

}
