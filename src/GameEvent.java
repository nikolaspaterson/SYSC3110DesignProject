import java.util.Scanner;

/**
 * The GameEvent class is responsible for handling the events that are generated once a Player reinforces, attacks or fortifies.
 * @author Ahmad El-Sammak
 */
public class GameEvent {

    private final Player player;
    private String result;
    private String attackingRolls;
    private String defendingRolls;
    private boolean attackerWon;

    /**
     * Constructor for GameEvent class where a GameEvent should be initiated by a Player.
     * @param player Player that initiates the GameEvent
     */
    public GameEvent(Player player) {
        this.player = player;
        attackingRolls = "";
        defendingRolls = "";
        attackerWon = false;
    }

    /**
     * This method is used to add troops to a territory specified by the Player.
     * @param territory territory that troops will be added too.
     * @param troops the number of troops to add.
     */
    public void reinforce(Territory territory, int troops) {
        if(territory.getOccupant().equals(player) && troops <= player.getDeployableTroops() && troops > 0) {
            player.incrementTroops(territory, troops);
            player.setDeployableTroops(player.getDeployableTroops() - troops);
            System.out.println("you have " + player.getDeployableTroops() + " LEFT to deploy");
        }else if(troops < 0){
            System.out.println("Nice try! No negative troops!");
        }else {
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

        if(attacking.getOccupant().equals(player) &&  !defending.getOccupant().equals(player)) {
            try {
                Dice temp = new Dice();
                Dice attackingDice = temp.setUpAttackingDice(attacking.getTroops(), numDice);
                Dice defendingDice = temp.setUpDefendingDice(defending.getTroops());

                int outcome = temp.attackResult(attackingDice.getRoll(), defendingDice.getRoll());

                switch(outcome){
                    case 2:
                        result = "Defender loses two troops!";
                        defending.addTroops(-2);
                        break;
                    case 1:
                        result = "Attacker loses two troops!";
                        attacking.addTroops(-2);
                        break;
                    case 0:
                        result = "Attacker & Defender lose ONE troop!";
                        attacking.addTroops(-1);
                        defending.addTroops(-1);
                        break;
                    case -1:
                        result = "Defender loses one troop!";
                        defending.addTroops(-1);
                        break;
                    case -2:
                        result = "Attacker loses one troop!";
                        attacking.addTroops(-1);
                        break;
                }

                attackingRolls = "";
                defendingRolls = "";

                for(int x : attackingDice.getRoll()){
                    attackingRolls += " || " + x + " || ";
                }

                for(int y : defendingDice.getRoll()){
                    defendingRolls += " || " + y + " || ";
                }

                if (defending.getTroops() == 0) {
                    attackerWon = true;
                    (attacking.getOccupant()).addTerritory(defending.getTerritoryName(), defending);
                    (defending.getOccupant()).removeTerritory(defending.getTerritoryName());
                    defending.setOccupant(attacking.getOccupant());
                    defending.setTroops(1);
                    attacking.setTroops(attacking.getTroops() - 1);
                }

            } catch (NullPointerException e) {
                System.out.println("Null pointer exception!");
            }
        }else{
            System.out.println("You cannot attack your own territory!");
        }
    }

    public String getResult() { return result; }

    public String getAttackerRolls() { return attackingRolls; }

    public String getDefendingRolls() { return defendingRolls; }

    public boolean getAttackerWon() { return attackerWon; }

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

            if (i <= 0){
                i = 1;
                System.out.println("You must at least move one troop to the new territory!");
                System.out.println( i +" troop has been moved because of your lack of knowledge...");
            } else if(i > attacking.getTroops() - 1){
                i = attacking.getTroops() - 1;
                System.out.println("You cannot move more than " + (attacking.getTroops() - 1));
                System.out.println( i +" troops have been moved because of your lack of knowledge...");
            }
            if (i > 0 && i < attacking.getTroops()) {
                (attacking.getOccupant()).addTerritory(defending.getTerritoryName(), defending);
                (defending.getOccupant()).removeTerritory(defending.getTerritoryName());
                defending.setOccupant(attacking.getOccupant());
                defending.setTroops(i);
                attacking.setTroops(attacking.getTroops() - i);
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
        if(territory1.getOccupant() == territory2.getOccupant() && territory1.getOccupant().equals(player) && territory1.isNeighbour(territory2)) {

            if(troops < territory1.getTroops() && troops > 0) {
                player.decrementTroops(territory1, troops);
                player.incrementTroops(territory2, troops);
                System.out.println("You have moved " + troops + " from " + territory1.getTerritoryName() + " to " + territory2.getTerritoryName());
            } else if (troops <= 0){
                System.out.println("No troops will be moved.");
            } else {
                System.out.println("You cannot move more than " + (territory1.getTroops() - 1));
            }

        }
    }
}
