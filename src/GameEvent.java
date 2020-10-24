import java.util.Scanner;

/**
 * The GameEvent class is responsible for handling the events that are generated once a Player reinforces, attacks or fortifies.
 *
 * @author aelsammak
 * @author dieuleparfait
 * @version 1.0
 */
public class GameEvent {

    private final Player player;

    /**
     * Constructor for GameEvent class where a GameEvent should be initiated by a Player.
     * @param player Player that initiates the GameEvent
     */
    public GameEvent(Player player) {
        this.player = player;
    }

    /**
     * This method is used to add troops to a territory specified by the Player.
     * @param territory territory that troops will be added too.
     * @param troops the number of troops to add.
     */
    public void reinforce(Territory territory, int troops) {
        if(territory.getOccupant() == player && troops <= player.getDeployableTroops()) {
            player.incrementTroops(territory, troops);
            player.setDeployableTroops(player.getDeployableTroops() - troops);
        }
        else {
            System.out.println("Ensure that the territory that you are trying to reinforce belongs to you");
            System.out.println("and you have a valid number of troops to deploy");
            System.out.println("you have " + player.getDeployableTroops() + " to deploy");
        }
    }

    /**
     * This method is used by the Player when he/she wants to attack a neighbouring territory that is owned by another player.
     * This method will remove troops from either side (attacking/defending) based on the attack result.
     * @param attacking the attacking territory.
     * @param defending the defending territory.
     * @param numDice the number of dice the attacker wants to roll with.
     */
    public void attack(Territory attacking, Territory defending, int numDice) {

        if(attacking.getOccupant() == player && defending.getOccupant() != player) {
            try {
                Dice temp = new Dice();
                Dice attackingDice = temp.setUpAttackingDice(attacking.getTroops(), numDice);
                Dice defendingDice = temp.setUpDefendingDice(defending.getTroops());

                int outcome = temp.attackResult(attackingDice.getRoll(), defendingDice.getRoll());

                switch(outcome){
                    case 2:
                        System.out.println("Defender loses two troops!");
                        defending.setTroops(defending.getTroops() - 2);
                        break;
                    case 1:
                        System.out.println("Attacker loses two troops!");
                        attacking.setTroops(attacking.getTroops() - 2);
                        break;
                    case 0:
                        System.out.println("Attacker & Defender lose ONE troop!");
                        attacking.setTroops(attacking.getTroops() - 1);
                        defending.setTroops(defending.getTroops() - 1);
                        break;
                    case -1:
                        System.out.println("Defender loses one troop!");
                        defending.setTroops(defending.getTroops() - 1);
                        break;
                    case -2:
                        System.out.println("Attacker loses one troop!");
                        attacking.setTroops(attacking.getTroops() - 1);
                        break;
                }

                winningMove(attacking, defending);

            } catch (NullPointerException e) {
                System.out.println("Null pointer exception!");
            }
        }
    }

    /**
     * This method is used with combination of the attack() method above to decide if the attacker defeated the defending territory.
     * IF the attacker won, he/she will be asked how many troops (1 to (attacking.getTroops - 1)) they want to move to their winning territory.
     * @param attacking the attacking territory.
     * @param defending the defending territory.
     */
    public void winningMove(Territory attacking, Territory defending){
        // if attacker defeated defending territory then defender has no more troops.
        if(defending.getTroops() == 0) {

            // get user input for their desired amount of troops to move to the winning territory.
            System.out.println("How many troops do you want to move to the new territory?");

            if(attacking.getTroops() == 2) {
                System.out.println("You can only move 1 troop!");
            } else {
                System.out.println("You have a choice of moving 1 to " + (attacking.getTroops() - 1));
            }

            Scanner sc = new Scanner(System.in);
            int i = sc.nextInt();

            // checking user input validity
            if (i > 0 && i < attacking.getTroops()) {
                defending.setOccupant(attacking.getOccupant());
                defending.setTroops(i);
                attacking.setTroops(attacking.getTroops() - i);
            } else if (i == 0){
                System.out.println("You must at least move one troop to the new territory!");
            } else {
                System.out.println("You cannot move more than " + (attacking.getTroops() - 1));
            }

        } else  {
            System.out.println("The defending territory still has " + defending.getTroops());
        }
    }

    /**
     * This method is used by the player to fortify by moving troops from one territory to another.
     * A player can move 1 to (territory1.getTroops() - 1) from one (and only one) of their territories into one (and only one) of their adjacent territories.
     * @param territory1 the territory to move troops FROM.
     * @param territory2 the territory to move troops INTO.
     * @param troops the number of troops to move from territory1 to territory2.
     */
    public void fortify(Territory territory1, Territory territory2, int troops) {
        if(territory1.getOccupant() == territory2.getOccupant() && territory1.getOccupant() == player && territory1.isNeighbour(territory2)) {

            if(troops < territory1.getTroops() && troops > 0) {
                player.decrementTroops(territory1, troops);
                player.incrementTroops(territory2, troops);
            } else if (troops <= 0){
                System.out.println("No troops will be moved.");
            } else {
                System.out.println("You cannot move more than " + (territory1.getTroops() - 1));
            }

        }
    }

    /**
     * This method is used to calculate the number of troops the player will receive based on how many territories and continents they own.
     * @param player the player which will receive the bonus troops.
     */
    private void troopsReceived(Player player) {
        int result = 0;

        if ((player.getTerritoriesOccupied().size()) <= 9) {
            result = 3;
        } else {
            result = ((player.getTerritoriesOccupied().size()) / 3);
        }

        result += troopContinentBonus(player);
        player.setDeployableTroops(result);
    }

    /**
     * This method is used to calculate the amount of extra bonus troops the player gets if they control one or more continent(s).
     * @param player the player which is receiving the bonus troops.
     * @return the number of bonus troops.
     */
    private int troopContinentBonus(Player player) {
        int[] continents = new int[6];

        if (player.getTerritoriesOccupied().size() > 0) {
            for (Territory territory : player.getTerritoriesOccupied().values()) {
                String continentName = territory.getContinentName();

                switch (continentName) {
                    case "Asia":
                        continents[0]++;
                        break;
                    case "Australia":
                        continents[1]++;
                        break;
                    case "Europe":
                        continents[2]++;
                        break;
                    case "Africa":
                        continents[3]++;
                        break;
                    case "South America":
                        continents[4]++;
                        break;
                    case "North America":
                        continents[5]++;
                        break;
                }
            }
        }

        int troops = 0;

        if (continents[0] == 12) troops += 7; // Asia Bonus
        if (continents[1] == 4) troops += 2; // Australia Bonus
        if (continents[2] == 7) troops += 5; // Europe Bonus
        if (continents[3] == 6) troops += 3; // Africa Bonus
        if (continents[4] == 4) troops += 2; // South America Bonus
        if (continents[5] == 9) troops += 5; // North America Bonus

        return troops;
    }

}
