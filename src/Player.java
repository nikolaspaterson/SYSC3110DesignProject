import java.util.HashMap;

public class Player {

    private String player_name;
    private int troop_count_dist;
    private HashMap<String,Territory> territories_occupied;

    public Player(String name){
        this.territories_occupied = new HashMap<>();
        this.player_name = name;
        this.troop_count_dist = 0;
    }
    public int getTroop_count_dist(){
        return troop_count_dist;
    }
    public void add_troops(int troop_count){
        this.troop_count_dist += troop_count;
    }
    public int place_troop(int troop_count){
        if(troop_count_dist - troop_count >= 0){
            troop_count_dist -= troop_count;
            return troop_count;
        }
        else{
            int temp = troop_count_dist;
            troop_count_dist = 0;
            return temp;
        }

    }
    public void add_territory(Territory terr){
        territories_occupied.put(terr.getName(),terr);
    }
    public String returnName(){
        return player_name;
    }
    public HashMap<String,Territory> getTerritories_occupied(){
        return territories_occupied;
    }
}
