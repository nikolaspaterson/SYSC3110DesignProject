import java.util.ArrayList;

public class Game {


    public static void main(String[] args) {
        ArrayList<Player> player_list = new ArrayList<>();
        player_list.add(new Player("Franky"));
        player_list.add(new Player("Freddy"));
        player_list.add(new Player("Fawney"));
        player_list.add(new Player("Obama"));
        GameSetup setup = new GameSetup(player_list);
    }

}
