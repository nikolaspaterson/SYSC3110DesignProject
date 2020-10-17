    public class GameEvent {
        private Player player;
    public GameEvent(Player player) {
        this.player = player;
    }

    public void reinforce(Territory territory, int troops) {
        if(territory.getOccupant() == this.player) {
            this.player.incrementTroops(territory, troops);
        }
        else {
            new Error();
        }
    }

    public void attack(Territory attacking, Territory defending, int numDice) {
        int troopsLoss;
        if(attacking.getOccupant() == this.player && defending.getOccupant() != this.player && attacking.getTroops() >= numDice + 1 && numDice <= 3 && numDice >= 1) {
            Dice attackingDice = new Dice(numDice);
            if(defending.getTroops() == 1) {
                Dice defendingDice = new Dice(1);
                troopsLoss = 1;
            }
            else {
                Dice defendingDice = new Dice(2);
                troopsLoss = 2;
            }
            int result = attackingDice.attack(attacking.getRoll(), defending.getRoll());

            /*both lose 1 troop*/
            if(result == 0) {
                attacking.setTroops(attacking.getTroops() - 1);
                defending.setTroops(attacking.getTroops() - 1);
            }

            /*attacker loses troops*/
            else if(result == 1) {
                attacking.setTroops(attacking.getTroops() = troopsLoss);
            }

            /*defender loses troops*/
            else if(defending.getTroops() <= 2 && numDice >= 2) {
                //attacker win Territory
                defending.setOccupant(this.player);
                defending.setTroops(1);
                attacking.setTroops(attacking.getTroops() - 1);
            }

            else {
                //attacker doesn't win territory
                defending.setTroops(defending.getTroops() - troopsLoss);
            }
        }
        else {
            new Error();
        }
    }

    public void fortify(Territory territory1, Territory territory2, int troops) {
        if(territory1.getOccupant() == territory2.getOccupant() && territory1.getOccupant() == this.player) {
            this.player.decrementTroops(territory1, troops);
            this.player.incrementTroops(territory2, troops);
        }
        else {
            new Error();
        }
    }

    public void playerTurn() {

    }
}
