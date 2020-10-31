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
                        defending.removeTroops((-2));
                        break;
                    case 1:
                        result = "Attacker loses two troops!";
                        attacking.removeTroops((-2));
                        break;
                    case 0:
                        result = "Attacker & Defender lose ONE troop!";
                        attacking.removeTroops((-1));
                        defending.removeTroops((-1));
                        break;
                    case -1:
                        result = "Defender loses one troop!";
                        defending.removeTroops((-1));
                        break;
                    case -2:
                        result = "Attacker loses one troop!";
                        attacking.removeTroops((-1));
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

    /**
     * Gets the result of an attack.
     * @return String
     */
    public String getResult() { return result; }

    /**
     * Gets the rolls of the attacker.
     * @return String
     */
    public String getAttackerRolls() { return attackingRolls; }

    /**
     * Gets the rolls of the defender.
     * @return String
     */
    public String getDefendingRolls() { return defendingRolls; }

    /**
     * Gets if the attacker defeated the defending territory or not.
     * @return boolean true if the attacker won, else false.
     */
    public boolean getAttackerWon() { return attackerWon; }

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
