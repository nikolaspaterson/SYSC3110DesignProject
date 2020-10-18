package src;

import java.util.Scanner;

public class GameEvent {

    private Player player;

    public GameEvent(Player player) {
        this.player = player;
    }

    public void reinforce(Territory territory, int troops) {
        if(territory.getOccupant() == player) {
            player.incrementTroops(territory, troops);
        }
        else {
            new Error();
        }
    }


    public void attack(Territory attacking, Territory defending, int numDice) {

        if(attacking.getOccupant() == player && defending.getOccupant() != player && attacking.isNeighbour(defending)) {

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
        else {
            new Error();
        }
    }

    public void winningMove(Territory attacking, Territory defending){
        // if attacker defeated defending territory then defender has no more troops.
        if(defending.getTroops() == 0) {

            // Get User input for amount of troops to move to winning territory
            System.out.println("How many troops do you want to move to the new territory?");
            System.out.println("You have a choice of moving 1 to " + (attacking.getTroops() - 1));

            Scanner sc = new Scanner(System.in);
            int i = sc.nextInt();

            //CHECK TO SEE IF USER INPUT IS VALID
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

    public void fortify(Territory territory1, Territory territory2, int troops) {
        if(territory1.getOccupant() == territory2.getOccupant() && territory1.getOccupant() == player) {

            if(troops < territory1.getTroops() && troops > 0) {
                player.decrementTroops(territory1, troops);
                player.incrementTroops(territory2, troops);
            } else if (troops == 0){
                System.out.println("No troops will be moved.");
            } else {
                System.out.println("You cannot move more than " + (territory1.getTroops() - 1));
            }

        } else {
            new Error();
        }
    }

}
