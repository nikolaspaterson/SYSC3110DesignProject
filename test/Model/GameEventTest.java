package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;

public class GameEventTest {

    private Player player1;
    private Player player2;
    private Territory t1;
    private Territory t2;
    private Territory t3;
    private Territory t4;
    private Territory t5;
    private Territory t6;
    private Territory t7;
    private Territory t8;
    private Territory t9;
    private GameEvent gameEvent;
    private MockGameEvent mockEvent;
    private MockDice fakeDice;

    @Before
    public void setUp() {
        player1 = new Player("Fred");
        player2 = new Player("John");

        t1 = new Territory("Alaska");
        t2 = new Territory("Alberta");
        t3 = new Territory("CentralAmerica");
        t4 = new Territory("EasternUnitedStates");
        t5 = new Territory("Greenland");
        t6 = new Territory("NorthwestTerritory");
        t7 = new Territory("Ontario");
        t8 = new Territory("Quebec");
        t9 = new Territory("WesternUnitedStates");

        player1.addTerritory(t6.getTerritoryName(), t6);
        t6.setOccupant(player1);
        t6.addNeighbour(t1);
        t6.addNeighbour(t2);
        t6.addNeighbour(t5);
        t6.addNeighbour(t7);

        player1.addTerritory(t2.getTerritoryName(), t2);
        t2.setOccupant(player1);
        t2.addNeighbour(t1);
        t2.addNeighbour(t6);
        t2.addNeighbour(t7);
        t2.addNeighbour(t9);

        player1.addTerritory(t9.getTerritoryName(), t9);
        t9.setOccupant(player1);
        t9.addNeighbour(t2);
        t9.addNeighbour(t3);
        t9.addNeighbour(t4);
        t9.addNeighbour(t7);

        player1.addTerritory(t8.getTerritoryName(), t8);
        t8.setOccupant(player1);
        t8.addNeighbour(t4);
        t8.addNeighbour(t5);
        t8.addNeighbour(t7);

        player2.addTerritory(t5.getTerritoryName(), t5);
        t5.setOccupant(player2);
        t5.addNeighbour(t6);
        t5.addNeighbour(t7);
        t5.addNeighbour(t8);

        player2.addTerritory(t1.getTerritoryName(), t1);
        t1.setOccupant(player2);
        t1.addNeighbour(t2);
        t1.addNeighbour(t6);

        player2.addTerritory(t7.getTerritoryName(), t7);
        t7.setOccupant(player2);
        t7.addNeighbour(t2);
        t7.addNeighbour(t4);
        t7.addNeighbour(t5);
        t7.addNeighbour(t6);
        t7.addNeighbour(t8);
        t7.addNeighbour(t9);

        player2.addTerritory(t4.getTerritoryName(), t4);
        t4.setOccupant(player2);
        t4.addNeighbour(t3);
        t4.addNeighbour(t7);
        t4.addNeighbour(t8);
        t4.addNeighbour(t9);

        player2.addTerritory(t3.getTerritoryName(), t3);
        t3.setOccupant(player2);
        t3.addNeighbour(t4);
        t3.addNeighbour(t9);

        t1.setTroops(5);
        t2.setTroops(5);
        t3.setTroops(5);
        t4.setTroops(5);
        t5.setTroops(5);
        t6.setTroops(5);
        t7.setTroops(5);
        t8.setTroops(1);
        t9.setTroops(5);

        player1.setDeployableTroops(7);
        gameEvent = new GameEvent(player1);
        mockEvent = new MockGameEvent(player1);
        fakeDice = mockEvent.getMockDice();
    }

    @After
    public void tearDown() {
       player1 = null;
       player2 = null;
       t1 = null;
       t2 = null;
       t3 = null;
       t4 = null;
       t5 = null;
       t6 = null;
       t7 = null;
       t8 = null;
       t9 = null;
    }

    @Test
    public void testSuccessfulReinforce() {
        System.out.println("Successful Reinforce");
        gameEvent.reinforce(t6, 6);
        gameEvent.reinforce(t2, 1);
        assertEquals(11, t6.getTroops());
        assertEquals(6, t2.getTroops());
    }

    @Test
    public void testUnsuccessfulReinforce() {
        System.out.println("\nFailed Reinforce - Trying to reinforce more troops than the player's deployable troop number");
        gameEvent.reinforce(t6, 10);
        assertEquals(5, t6.getTroops());

        System.out.println( "\nFailed Reinforce  - Negative troops as input");
        gameEvent.reinforce(t6, -2);
        assertEquals(5, t6.getTroops());

        System.out.println("\nFailed Reinforce - Model.Player does NOT occupy the Model.Territory");
        gameEvent.reinforce(t1, 2);
        assertEquals(5, t1.getTroops());
    }

    @Test
    public void testSuccessfulAttack() {
        // Set mocked outcome - Defender loses one troop
        fakeDice.setNextAttackOutcome(AttackResult.D1);

        System.out.println("\nSuccessful Attack - AttackingT has 3 troops and rolls with 1 die.");
        mockEvent.attack(t2, t1, 1);
        assertEquals(5, t2.getTroops());
        assertEquals(4, t1.getTroops());

        // Set mocked outcome - Attacker loses one troop and Defender loses one troop
        fakeDice.setNextAttackOutcome(AttackResult.A1D1);

        System.out.println("\nSuccessful Attack - AttackingT has 3 troops and rolls with 2 dice.");
        mockEvent.attack(t2, t1, 2);
        assertEquals(4, t2.getTroops());
        assertEquals(3, t1.getTroops());

        System.out.println("\nSuccessful Attack - AttackingT has 4 troops and rolls with 3 dice.");
        mockEvent.attack(t2, t1, 3);
        assertEquals(3, t2.getTroops());
        assertEquals(2, t1.getTroops());
    }

    @Test
    public void testUnsuccessfulAttack() {
        // Set mocked outcome - Attacker loses one troop and Defender loses one troop
        fakeDice.setNextAttackOutcome(AttackResult.A1D1);

        System.out.println("\nUnsuccessful Attack - AttackingT and DefendingT are NOT neighbours.");
        mockEvent.attack(t2, t5, 1);
        assertEquals(5, t2.getTroops());
        assertEquals(5, t5.getTroops());

        System.out.println("\nUnsuccessful Attack - AttackingT and DefendingT have the SAME occupant.");
        mockEvent.attack(t2, t6, 1);
        assertEquals(5, t2.getTroops());
        assertEquals(5, t6.getTroops());

        System.out.println("\nUnsuccessful Attack - AttackingT player wants to roll with more dice than possible.");
        mockEvent.attack(t8, t5, 3);
        assertEquals(1, t8.getTroops());
        assertEquals(5, t5.getTroops());

        System.out.println("\nUnsuccessful Attack - AttackingT player wants to roll with negative/zero dice.");
        mockEvent.attack(t2, t7, -1);
        assertEquals(5, t1.getTroops());
        assertEquals(5, t2.getTroops());

        System.out.println("\nUnsuccessful Attack - AttackingT is not owned by the player who created the GameEvent.");
        mockEvent.attack(t1, t2, 1);
        assertEquals(5, t1.getTroops());
        assertEquals(5, t2.getTroops());
    }

    @Test
    public void testSuccessfulFortify() {
        System.out.println("\nSuccessful Fortify - Territory1 and Territory2 are NEIGHBOURING and owned by the same player.");
        gameEvent.fortify(t2, t6, 1);
        assertEquals(4, t2.getTroops());
        assertEquals(6, t6.getTroops());
    }

    @Test
    public void testSuccessfulLinkedFortify() {
        System.out.println("\nSuccessful Fortify - Territory1 and Territory2 are neighbouring, owned by the same player and are CONNECTED but not NEIGHBOURING.");
        gameEvent.fortify(t6, t9, 2);
        assertEquals(3, t6.getTroops());
        assertEquals(7, t9.getTroops());
    }

    @Test
    public void testUnSuccessfulLinkedFortify() {
        System.out.println("\nUnsuccessful Fortify - Territory1 and Territory2 are neighbouring, NOT owned by the same player and are CONNECTED but not NEIGHBOURING.");
        gameEvent.fortify(t2, t8, 2);
        assertEquals(5, t2.getTroops());
        assertEquals(1, t8.getTroops());
    }

    @Test
    public void testUnsuccessfulFortify() {
        System.out.println("\nUnsuccessful Fortify - Territory1 and Territory2 are not owned by the same player.");
        gameEvent.fortify(t2, t1, 1);
        assertEquals(5, t2.getTroops());
        assertEquals(5, t1.getTroops());

        System.out.println("\nUnsuccessful Fortify - negative amount of troops.");
        gameEvent.fortify(t2, t6, -1);
        assertEquals(5, t1.getTroops());
        assertEquals(5, t2.getTroops());

        System.out.println("\nUnsuccessful Fortify - move ALL the troops in your first territory.");
        gameEvent.fortify(t2, t6, 5);
        assertEquals(5, t1.getTroops());
        assertEquals(5, t2.getTroops());

        System.out.println("\nUnsuccessful Fortify - Territory1 and Territory2 are NOT neighbouring BUT are owned by the same player.");
        gameEvent.fortify(t2, t8, 1);
        assertEquals(5, t2.getTroops());
        assertEquals(1, t8.getTroops());
    }

    @Test
    public void testBonusTroops() {
        Continent australia = new Continent("Australia");
        Continent asia = new Continent("Asia");
        Continent europe = new Continent("Europe");
        Continent africa = new Continent("Africa");
        Continent na = new Continent("NorthAmerica");
        Continent sa = new Continent("SouthAmerica");

        asia.addContinentTerritory(t1.getTerritoryName(), t1);
        europe.addContinentTerritory(t1.getTerritoryName(), t1);
        africa.addContinentTerritory(t1.getTerritoryName(), t1);
        na.addContinentTerritory(t1.getTerritoryName(), t1);
        sa.addContinentTerritory(t1.getTerritoryName(), t1);

        australia.addContinentTerritory(t2.getTerritoryName(), t2);

        HashMap<String, Continent> continentHashMap = new HashMap<>();
        continentHashMap.put(australia.getContinentName(), australia);
        continentHashMap.put(asia.getContinentName(), asia);
        continentHashMap.put(europe.getContinentName(), europe);
        continentHashMap.put(africa.getContinentName(), africa);
        continentHashMap.put(na.getContinentName(), na);
        continentHashMap.put(sa.getContinentName(), sa);

        // Players owns less than 9 territories (gains 3 troops) and controls Australia for the continent bonus of 2 troops.
        player1.playerBonus(continentHashMap);
        assertEquals(12, player1.getDeployableTroops());
    }
}