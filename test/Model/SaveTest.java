package Model;

import View.GameSetup;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SaveTest {

    private MockSave gameModel;
    private ArrayList<Player> players;
    private Player player1;
    private Player player2;
    private Territory territory1;
    private Territory territory2;

    @Before
    public void setUp(){
        player1 = new Player("Player1");
        player1.setPlayer_color(Color.RED);
        player1.setDeployableTroops(297);
        player1.setPlayerNumber(0);

        player2 = new Player("Player2");
        player2.setPlayer_color(Color.RED);
        player2.setDeployableTroops(12);
        player2.setPlayerNumber(1);

        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        territory1 = new Territory("Ottawa");
        territory2 = new Territory("OtherOttawa");
        territory1.setOccupant(player1);
        territory2.setOccupant(player2);
        territory1.setTroops(4);
        territory2.setTroops(4);

        gameModel = new MockSave();
        gameModel.addPlayers(players);
        gameModel.getWorld(new HashMap<>(),new HashMap<>());
        gameModel.setGameName("GameTest");
    }

    @Test
    public void testSave() throws IOException, ParseException {
        gameModel.saveAction("", "Testname");
        File json_file = new File("Testname.json");
        FileReader load_file = new FileReader(json_file);
        JSONParser parser = new JSONParser();
        JSONObject loadedJSON = (JSONObject) parser.parse(load_file);
        GameModel loadModel = new GameModel(loadedJSON, gameModel);
        assertEquals(300,loadModel.getCurrentPlayer().getDeployableTroops());
    }

    @Test
    public void testLoadMap(){
        GameSetup setup = new GameSetup(players, "/resources/DefaultMap.json");
        GameModel model = new GameModel();
        model.addPlayers(players);
        model.getWorld(setup.returnContinentMap(),setup.returnWorldMap());
        assertEquals(GameState.REINFORCE, model.getCurrentState());
        assertTrue(model.getWorldMap().containsKey("Ontario"));
    }
}