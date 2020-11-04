import Model.AttackResult;
import Model.GameEvent;
import Model.Player;
import Model.Territory;
import junit.framework.TestCase;

class GameEventTest extends TestCase {

    private Player p1;
    private Player p2;

    private Territory t1;
    private Territory t2;
    private Territory t3;
    private Territory t4;
    private Territory t5;

    public void setUp() {
        p1 = new Player("Fred");
        p2 = new Player("John");

        t1 = new Territory("Ontario");
        t2 = new Territory("Quebec");
        t3 = new Territory("Egypt");
        t4 = new Territory("Scandinavia");
        t5 = new Territory("Greenland");
    }

    @org.junit.jupiter.api.Test
    void reinforce() {
        setUp();
        p1.setDeployableTroops(3);
        p2.setDeployableTroops(3);

        p1.addTerritory(t1.getTerritoryName(), t1);
        t1.setOccupant(p1);
        t1.setTroops(3);

        p2.addTerritory(t2.getTerritoryName(), t2);
        t2.setOccupant(p2);
        t2.setTroops(1);

        p2.addTerritory(t3.getTerritoryName(), t3);
        t3.setOccupant(p2);
        t3.setTroops(2);

        ////////////////////////////////////////////
        GameEvent gameEvent = new GameEvent(p1);

        System.out.println("Successful Reinforce");
        // Successful reinforce
        gameEvent.reinforce(t1, 2);
        assertEquals(5, t1.getTroops());

        System.out.println("\nFailed Reinforce - Trying to reinforce more troops than the player's deployable troop number");
        // Failed Reinforce - Trying to reinforce more troops than the player's deployable troop number
        p1.setDeployableTroops(3);
        gameEvent.reinforce(t1, 4);
        assertEquals(5, t1.getTroops());

        System.out.println( "\nFailed Reinforce  - Negative troops as input");
        // Failed Reinforce  - Negative troops as input
        gameEvent.reinforce(t1, -2);
        assertEquals(5, t1.getTroops());

        System.out.println("\nFailed Reinforce - Model.Player does NOT occupy the Model.Territory");
        // Failed Reinforce - Model.Player does NOT occupy the Model.Territory
        gameEvent.reinforce(t2, 2);
        assertEquals(1, t2.getTroops());

    }

    @org.junit.jupiter.api.Test
    void attack() {
        setUp();
        p1.addTerritory(t1.getTerritoryName(), t1);
        t1.setOccupant(p1);
        t1.addNeighbour(t2);

        p2.addTerritory(t2.getTerritoryName(), t2);
        t2.setOccupant(p2);
        t2.addNeighbour(t1);

        p2.addTerritory(t3.getTerritoryName(), t3);
        t3.setOccupant(p2);

        ////////////////////////////////////////////
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

    @org.junit.jupiter.api.Test
    void fortify() {
        setUp();
        p1.addTerritory(t1.getTerritoryName(), t1);
        t1.setOccupant(p1);
        t1.addNeighbour(t2);
        t1.addNeighbour(t5);

        p1.addTerritory(t2.getTerritoryName(), t2);
        t2.setOccupant(p1);
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


        t1.setTroops(4);
        t2.setTroops(4);
        t3.setTroops(4);
        t4.setTroops(4);
        t5.setTroops(4);

        GameEvent gameEvent = new GameEvent(p1);

        System.out.println("\nSuccessful Attack - Territory1 and Territory2 are neighbouring and owned by the same player.");
        // Successful Fortify
        gameEvent.fortify(t1, t2, 1);
        assertEquals(3, t1.getTroops());
        assertEquals(5, t2.getTroops());

        System.out.println("\nUnsuccessful Attack - Territory1 and Territory2 are not owned by the same player.");
        // Unsuccessful Fortify
        t1.setTroops(4);
        t2.setTroops(4);
        gameEvent.fortify(t1, t3, 1);
        assertEquals(4, t1.getTroops());
        assertEquals(4, t3.getTroops());

        System.out.println("\nUnsuccessful Attack - Territory1 and Territory2 are neighbouring BUT is NOT owned by the same player.");
        // Unsuccessful Fortify
        gameEvent.fortify(t1, t5, 1);
        assertEquals(4, t1.getTroops());
        assertEquals(4, t5.getTroops());

        System.out.println("\nUnsuccessful Attack - negative amount of troops.");
        // Unsuccessful Fortify
        gameEvent.fortify(t1, t2, -1);
        assertEquals(4, t1.getTroops());
        assertEquals(4, t2.getTroops());

        System.out.println("\nUnsuccessful Attack - move ALL the troops in your first territory.");
        // Unsuccessful Fortify
        gameEvent.fortify(t1, t2, 4);
        assertEquals(4, t1.getTroops());
        assertEquals(4, t2.getTroops());

        System.out.println("\nUnsuccessful Attack - Territory1 and Territory2 are NOT neighbouring BUT are owned by the same player.");
        // Unsuccessful Fortify
        gameEvent.fortify(t1, t4, 1);
        assertEquals(4, t1.getTroops());
        assertEquals(4, t4.getTroops());
    }
}