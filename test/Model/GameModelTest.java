package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;

public class GameModelTest {

    private ArrayList<Player> players;
    private Player p1;
    private Player p2;
    private Player p3;
    private GameModel gameModel;

    @Before
    public void setup(){
        players = new ArrayList<>();
        p1 = new Player("p1");
        p2 = new Player("p2");
        p3 = new Player("p3");

        players.add(p1);
        players.add(p2);
        players.add(p3);

        p1.addTerritory("1",new Territory("1"));
        p2.addTerritory("2",new Territory("2"));
        p3.addTerritory("3",new Territory("3"));

        gameModel = new GameModel();

    }

    @Test
    public void addPlayersTest(){
        gameModel.addPlayers(players);
        assertEquals(3,gameModel.getPlayers().size());
    }

    @Test
    public void stateTest(){
        gameModel.addPlayers(players);
        assertEquals(GameState.REINFORCE, gameModel.getCurrentState());
        gameModel.nextState();
        assertEquals(GameState.ATTACK, gameModel.getCurrentState());
        gameModel.nextState();
        assertEquals(GameState.FORTIFY, gameModel.getCurrentState());
    }

    @Test
    public void nextPlayerTest(){
        gameModel.addPlayers(players);
        gameModel.getWorld(new HashMap<>(),new HashMap<>());
        gameModel.nextPlayer();
        assertEquals(p2,gameModel.getCurrentPlayer());
    }

    @After
    public void teardown(){
        players = null;
        p1 = null;
        p2 = null;
        p3 = null;
        gameModel = null;
    }
}
