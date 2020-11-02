import Model.GameEvent;
import Model.Player;
import Model.Territory;

import static org.junit.jupiter.api.Assertions.*;

class GameEventTest {

    @org.junit.jupiter.api.Test
    void reinforce() {
        Player p1 = new Player("Fred");
        Player p2 = new Player("John");

        Territory t1 = new Territory("Ontario");
        Territory t2 = new Territory("Quebec");
        Territory t3 = new Territory("Egypt");

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
        assertEquals(t1.getTroops(), 5);

        System.out.println("\nFailed Reinforce - Trying to reinforce more troops than the player's deployable troop number");
        // Failed Reinforce - Trying to reinforce more troops than the player's deployable troop number
        p1.setDeployableTroops(3);
        gameEvent.reinforce(t1, 4);
        assertEquals(t1.getTroops(), 5);

        System.out.println( "\nFailed Reinforce  - Negative troops as input");
        // Failed Reinforce  - Negative troops as input
        gameEvent.reinforce(t1, -2);
        assertEquals(t1.getTroops(), 5);

        System.out.println("\nFailed Reinforce - Model.Player does NOT occupy the Model.Territory");
        // Failed Reinforce - Model.Player does NOT occupy the Model.Territory
        gameEvent.reinforce(t2, 2);
        assertEquals(t2.getTroops(), 1);
    }

    @org.junit.jupiter.api.Test
    void attack() {
        Player p1 = new Player("Fred");
        Player p2 = new Player("John");

        Territory t1 = new Territory("Ontario");
        Territory t2 = new Territory("Quebec");
        Territory t3 = new Territory("Egypt");

        p1.addTerritory(t1.getTerritoryName(), t1);
        t1.setOccupant(p1);
        t1.addNeighbour(t2);

        p2.addTerritory(t2.getTerritoryName(), t2);
        t2.setOccupant(p2);
        t2.addNeighbour(t1);

        p2.addTerritory(t3.getTerritoryName(), t3);
        t3.setOccupant(p2);

        ////////////////////////////////////////////
        GameEvent gameEvent = new GameEvent(p1);

        System.out.println("Successful Attack");
        // Successful Attack
        t1.setTroops(2);
        t2.setTroops(1);
        gameEvent.attack(t1, t2, 1);





    }

    @org.junit.jupiter.api.Test
    void getResult() {
    }

    @org.junit.jupiter.api.Test
    void getAttackerRolls() {
    }

    @org.junit.jupiter.api.Test
    void getDefendingRolls() {
    }

    @org.junit.jupiter.api.Test
    void getAttackerWon() {
    }

    @org.junit.jupiter.api.Test
    void fortify() {
    }
}