import java.util.HashMap;

public class Player {

    private String player_name;
    private int troop_count_dist;
    private HashMap<String,Territory> territories_occupied;

    public Player(String name){
        this.player_name = name;
        this.troop_count_dist = 0;
    }

    public void add_troops(int troop_count){
        this.troop_count_dist += troop_count;
    }

}
