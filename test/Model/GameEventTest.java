package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class GameEventTest {

    private Player player1;
    private Player p1;
    private Player p2;

    private Territory t1;
    private Territory t2;
    private Territory t3;
    private Territory t4;
    private Territory t5;
    private Territory t6;
    private Territory temp1;
    private Territory temp2;
    private Territory temp3;

    @Before
    public void setUp() {
        p1 = new Player("Fred");
        p2 = new Player("John");

        t1 = new Territory("Ontario");
        t2 = new Territory("Quebec");
        t3 = new Territory("Egypt");
        t4 = new Territory("Scandinavia");
        t5 = new Territory("Greenland");
        t6 = new Territory("Wonderland");

        p1.addTerritory(t1.getTerritoryName(), t1);
        t1.setOccupant(p1);
        t1.addNeighbour(t2);
        t1.addNeighbour(t5);
        t1.addNeighbour(t6);

        p1.addTerritory(t2.getTerritoryName(), t2);
        t2.setOccupant(p2);
        t2.addNeighbour(t1);
        t2.addNeighbour(t5);

        p1.addTerritory(t4.getTerritoryName(), t4);
        t4.setOccupant(p1);

        p2.addTerritory(t3.getTerritoryName(), t3);
        t3.setOccupant(p2);

        p2.addTerritory(t5.getTerritoryName(), t5);
        t5.setOccupant(p2);
        t5.addNeighbour(t2);
        t5.addNeighbour(t1);

        p1.addTerritory(t6.getTerritoryName(),t6);
        t6.setOccupant(p1);
        t6.addNeighbour(t1);
    }

    /**
     * This method is used to set up the territories and necessary troops to test troop movement for fortifying in milestone 3.
     */
    public void setUpForLinkedFortify() {
        temp1 = new Territory("Ontario");
        temp2 = new Territory("Quebec");
        temp3 = new Territory("Egypt");

        player1 = new Player("P1");
        player1.addTerritory(temp1.getTerritoryName(), temp1);
        player1.addTerritory(temp2.getTerritoryName(), temp2);
        player1.addTerritory(temp3.getTerritoryName(), temp3);

        temp1.setOccupant(player1);
        temp2.setOccupant(player1);
        temp3.setOccupant(player1);

        temp1.addNeighbour(temp2);
        temp2.addNeighbour(temp1);
        temp2.addNeighbour(temp3);
        temp3.addNeighbour(temp2);

        temp1.setTroops(3);
        temp2.setTroops(1);
        temp3.setTroops(4);
    }

    @After
    public void tearDown() {
       p1 = null;
       p2 = null;

       t1 = null;
       t2 = null;
       t3 = null;
       t4 = null;
       t5 = null;
       t6 = null;
    }

    @Test
    public void testSuccessfulReinforce() {
        p1.setDeployableTroops(2);
        t1.setTroops(3);

        GameEvent gameEvent = new GameEvent(p1);

        System.out.println("Successful Reinforce");
        // Successful reinforce
        gameEvent.reinforce(t1, 2);
        assertEquals(5, t1.getTroops());
    }

    @Test
    public void testUnsuccessfulReinforce() {
        p1.setDeployableTroops(3);
        p2.setDeployableTroops(3);

        t1.setTroops(3);
        t2.setTroops(1);

        GameEvent gameEvent = new GameEvent(p1);

        System.out.println("\nFailed Reinforce - Trying to reinforce more troops than the player's deployable troop number");
        // Failed Reinforce - Trying to reinforce more troops than the player's deployable troop number
        gameEvent.reinforce(t1, 4);
        assertEquals(3, t1.getTroops());

        System.out.println( "\nFailed Reinforce  - Negative troops as input");
        // Failed Reinforce  - Negative troops as input
        gameEvent.reinforce(t1, -2);
        assertEquals(3, t1.getTroops());

        System.out.println("\nFailed Reinforce - Model.Player does NOT occupy the Model.Territory");
        // Failed Reinforce - Model.Player does NOT occupy the Model.Territory
        gameEvent.reinforce(t2, 2);
        assertEquals(1, t2.getTroops());
    }

    @Test
    public void testSuccessfulAttack() {
        MockGameEvent gameEvent = new MockGameEvent(p1);
        MockDice fakeDice = gameEvent.getMockDice();

        // Set mocked outcome - Defender loses one troop
        fakeDice.setNextAttackOutcome(AttackResult.D1);

        System.out.println("\nSuccessful Attack - AttackingT has 3 troops and rolls with 1 die.");
        // Successful Attack - Using mocked outcome
        t1.setTroops(3);
        t2.setTroops(3);
        gameEvent.attack(t1, t2, 1);
        assertEquals(3, t1.getTroops());
        assertEquals(2, t2.getTroops());

        // Set mocked outcome - Attacker loses one troop and Defender loses one troop
        fakeDice.setNextAttackOutcome(AttackResult.A1D1);

        System.out.println("\nSuccessful Attack - AttackingT has 3 troops and rolls with 2 dice.");
        // Successful Attack - Using mocked outcome
        t1.setTroops(3);
        t2.setTroops(3);
        gameEvent.attack(t1, t2, 2);
        assertEquals(2, t1.getTroops());
        assertEquals(2, t2.getTroops());

        System.out.println("\nSuccessful Attack - AttackingT has 4 troops and rolls with 3 dice.");
        // Successful Attack - Using mocked outcome
        t1.setTroops(4);
        t2.setTroops(3);
        gameEvent.attack(t1, t2, 3);
        assertEquals(3, t1.getTroops());
        assertEquals(2, t2.getTroops());
    }

    @Test
    public void testUnsuccessfulAttack() {
        MockGameEvent gameEvent = new MockGameEvent(p1);
        MockDice fakeDice = gameEvent.getMockDice();

        // Set mocked outcome - Attacker loses one troop and Defender loses one troop
        fakeDice.setNextAttackOutcome(AttackResult.A1D1);

        System.out.println("\nUnsuccessful Attack - AttackingT and DefendingT are NOT neighbours.");
        // Unsuccessful Attack
        t1.setTroops(2);
        t3.setTroops(2);
        gameEvent.attack(t1, t3, 1);
        assertEquals(2, t1.getTroops());
        assertEquals(2, t3.getTroops());

        System.out.println("\nUnsuccessful Attack - AttackingT and DefendingT have the SAME occupant.");
        // Unsuccessful Attack
        t1.setTroops(2);
        t3.setOccupant(p1);
        t3.setTroops(2);
        gameEvent.attack(t1, t3, 1);
        assertEquals(2, t1.getTroops());
        assertEquals(2, t3.getTroops());

        System.out.println("\nUnsuccessful Attack - AttackingT player wants to roll with more dice than possible.");
        // Unsuccessful Attack
        t1.setTroops(2);
        t2.setTroops(2);
        gameEvent.attack(t1, t2, 3);
        assertEquals(2, t1.getTroops());
        assertEquals(2, t2.getTroops());

        System.out.println("\nUnsuccessful Attack - AttackingT player wants to roll with negative/zero dice.");
        // Unsuccessful Attack
        t1.setTroops(2);
        t2.setTroops(2);
        gameEvent.attack(t1, t2, -1);
        assertEquals(2, t1.getTroops());
        assertEquals(2, t2.getTroops());

        System.out.println("\nUnsuccessful Attack - AttackingT is not owned by the player who created the GameEvent.");
        // Unsuccessful Attack
        t1.setTroops(2);
        t2.setTroops(2);

        gameEvent.attack(t2, t1, 1);
        assertEquals(2, t1.getTroops());
        assertEquals(2, t2.getTroops());
    }

    @Test
    public void testSuccessfulFortify() {
        t1.setTroops(4);
        t6.setTroops(4);

        GameEvent gameEvent = new GameEvent(p1);

        System.out.println("\nSuccessful Fortify - Territory1 and Territory2 are NEIGHBOURING and owned by the same player.");
        // Successful Fortify
        gameEvent.fortify(t1, t6, 1);
        assertEquals(3, t1.getTroops());
        assertEquals(5, t6.getTroops());
    }

    @Test
    public void testSuccessfulLinkedFortify() {
        setUpForLinkedFortify();
        GameEvent gameEvent1 = new GameEvent(player1);
        System.out.println("\nSuccessful Fortify - Territory1 and Territory2 are neighbouring, owned by the same player and are CONNECTED but not NEIGHBOURING.");
        // Successful Fortify
        gameEvent1.fortify(temp1, temp3, 2);
        assertEquals(1, temp1.getTroops());
        assertEquals(6, temp3.getTroops());
    }

    @Test
    public void testUnSuccessfulLinkedFortify() {
        setUpForLinkedFortify();
        player1.removeTerritory(temp2.getTerritoryName());

        Player player2 = new Player("P2");
        player2.addTerritory(temp2.getTerritoryName(), temp2);
        temp2.setOccupant(player2);

        GameEvent gameEvent1 = new GameEvent(player1);

        System.out.println("\nUnsuccessful Fortify - Territory1 and Territory2 are neighbouring, NOT owned by the same player and are CONNECTED but not NEIGHBOURING.");
        // Unsuccessful Fortify
        gameEvent1.fortify(temp1, temp3, 2);
        assertEquals(3, temp1.getTroops());
        assertEquals(4, temp3.getTroops());
    }

    @Test
    public void testUnsuccessfulFortify() {
        t1.setTroops(4);
        t2.setTroops(4);
        t3.setTroops(4);
        t4.setTroops(4);
        t5.setTroops(4);

        GameEvent gameEvent = new GameEvent(p1);

        System.out.println("\nUnsuccessful Fortify - Territory1 and Territory2 are not owned by the same player.");
        // Unsuccessful Fortify
        t1.setTroops(4);
        t2.setTroops(4);
        gameEvent.fortify(t1, t3, 1);
        assertEquals(4, t1.getTroops());
        assertEquals(4, t3.getTroops());

        System.out.println("\nUnsuccessful Fortify - Territory1 and Territory2 are neighbouring BUT is NOT owned by the same player.");
        // Unsuccessful Fortify
        gameEvent.fortify(t1, t5, 1);
        assertEquals(4, t1.getTroops());
        assertEquals(4, t5.getTroops());

        System.out.println("\nUnsuccessful Fortify - negative amount of troops.");
        // Unsuccessful Fortify
        gameEvent.fortify(t1, t2, -1);
        assertEquals(4, t1.getTroops());
        assertEquals(4, t2.getTroops());

        System.out.println("\nUnsuccessful Fortify - move ALL the troops in your first territory.");
        // Unsuccessful Fortify
        gameEvent.fortify(t1, t2, 4);
        assertEquals(4, t1.getTroops());
        assertEquals(4, t2.getTroops());

        System.out.println("\nUnsuccessful Fortify - Territory1 and Territory2 are NOT neighbouring BUT are owned by the same player.");
        // Unsuccessful Fortify
        gameEvent.fortify(t1, t4, 1);
        assertEquals(4, t1.getTroops());
        assertEquals(4, t4.getTroops());
    }

    @Test
    public void testBonusTroops() {
        Continent australia = new Continent("Australia");
        Continent asia = new Continent("Asia");
        Continent europe = new Continent("Europe");
        Continent africa = new Continent("Africa");
        Continent na = new Continent("NorthAmerica");
        Continent sa = new Continent("SouthAmerica");

        Territory tempTerritory = new Territory("Quantum");
        Player other = new Player("JimmyNeutron");
        other.addTerritory(tempTerritory.getTerritoryName(), tempTerritory);
        tempTerritory.setOccupant(other);
        asia.addContinentTerritory(tempTerritory.getTerritoryName(), tempTerritory);
        europe.addContinentTerritory(tempTerritory.getTerritoryName(), tempTerritory);
        africa.addContinentTerritory(tempTerritory.getTerritoryName(), tempTerritory);
        na.addContinentTerritory(tempTerritory.getTerritoryName(), tempTerritory);
        sa.addContinentTerritory(tempTerritory.getTerritoryName(), tempTerritory);

        HashMap<String, Continent> continentHashMap = new HashMap<>();
        continentHashMap.put(australia.getContinentName(), australia);
        continentHashMap.put(asia.getContinentName(), asia);
        continentHashMap.put(europe.getContinentName(), europe);
        continentHashMap.put(africa.getContinentName(), africa);
        continentHashMap.put(na.getContinentName(), na);
        continentHashMap.put(sa.getContinentName(), sa);

        Territory t1 = new Territory("NorthAustralia");
        Territory t2 = new Territory("SouthAustralia");
        australia.addContinentTerritory(t1.getTerritoryName(), t1);
        australia.addContinentTerritory(t2.getTerritoryName(), t2);

        Player p1 = new Player("Player1");
        p1.addTerritory(t1.getTerritoryName(), t1);
        p1.addTerritory(t2.getTerritoryName(), t2);

        t1.setOccupant(p1);
        t2.setOccupant(p1);

        // Players owns less than 9 territories (gains 3 troops) and controls Australia for the continent bonus of 2 troops.
        p1.playerBonus(continentHashMap);
        assertEquals(5, p1.getDeployableTroops());
    }

}