package Model;

import java.util.Random;

/**
 * The Model.Dice class's main responsibility is to generate rolls for the attacker and defender and
 * to assist the Model.GameEvent class with handling the outcome of an attack.
 * @author Ahmad El-Sammak
 */
public class Dice {

    private final Random random;
    private int[] rolls;
    private static final int MIN_NUM_DIE = 1;
    private static final int MAX_NUM_DIE = 3;
    private static final int MIN_TROOPS_TO_ATTACK = 2;
    private static final int MIN_TROOPS_TO_DEFEND = 1;

    /**
     * Class constructor for Model.Dice class.
     * Takes the number of dice to roll and stores the results of each roll into an array of integers.
     * @param numDice the number of dice to roll
     */
    public Dice(int numDice) {
        random = new Random();
        rolls = new int[numDice];
        for(int i = 0; i < rolls.length; i++) {
            rolls[i] = rollDie();
        }
    }

    /**
     * Class constructor for the Model.Dice class. It is used to generate a random hashcode
     */
    public Dice() {
        random = new Random();
    }

    /**
     * This method is used to set the up the number of dice the attacker wants to roll with (if optional).
     * The attacker can roll 1,2 or 3 dice but, the attacker MUST HAVE at least one more troop
     * in the their territory than the maximum number of dice they can roll.
     * For ex: If the attacker wants to roll with 3 dice, the attacker MUST HAVE a MINIMUM of 4 troops in their territory.
     * @param troops number of troops in the attacking territory.
     * @param numDice number of dice attacker wants to roll with.
     * @return Model.Dice (attacker's rolls)
     */
    public Dice setUpAttackingDice(int troops, int numDice) {
        if((troops >= MIN_TROOPS_TO_ATTACK && troops != numDice) && (numDice >= MIN_NUM_DIE && numDice <= MAX_NUM_DIE)) {
            if (numDice < (troops + 1)) {
                return new Dice(numDice);
            }
        }
        if(troops < MIN_TROOPS_TO_ATTACK) {
            System.out.println("You need at least two troops to attack.");
        }
        if(numDice >= troops){
            System.out.println("You cannot roll with " + numDice + " because you have " + troops +" troops!");
        }
        if(numDice < MIN_NUM_DIE || numDice > MAX_NUM_DIE) {
            System.out.println("You cannot roll with " + (numDice) + " dice!");
        }
        return null;
    }

    /**
     * This method is used to set up the number of dice the defender will roll with (not optional).
     * The defender will roll with two dice if they have AT LEAST two troops, otherwise the defender will roll one die.
     * @param troops number of troops in the defending territory.
     * @return Model.Dice (defender's rolls)
     */
    public Dice setUpDefendingDice(int troops) {
        if(troops >= MIN_TROOPS_TO_DEFEND) {
            if(troops == MIN_TROOPS_TO_DEFEND) {
                return new Dice(1);
            } else {
                return new Dice(2);
            }
        }
        System.out.println("You cannot defend with zero troops");
        return null;
    }

    /**
     * This method is used to simulate the roll of a die.
     * @return int (generates a random integer between 1-6 inclusive)
     */
    private int rollDie() {
        return random.nextInt(6) + 1;
    }

    /**
     * This method is used to evaluate the outcome of an attacking event.
     * This method compares the two highest dice (if attacker and defender rolled MORE THAN one die) of both the attacker and defender
     * OR the highest die (if the attacker and defender rolled with ONLY one die) of both the attacker and defender.
     * @param attackerRoll an array of integers storing all the attacker's rolls.
     * @param defenderRoll an array of integers storing all the defender's rolls.
     * @return Model.AttackResult      D2 - defender loses 2 troops
     *                           A2 - attacker loses 2 troops
     *                         A1D1 - attacker loses 1 troop and defender loses 1 troop
     *                           D1 - defender loses 1 troop
     *                           A1 - attacker loses 1 troop
     */
    public AttackResult attackResult(int[] attackerRoll, int[] defenderRoll) {
        System.out.print("Attacker Rolls: ");

        for(int x : attackerRoll){
            System.out.print(" || " + x + " || ");
        }

        System.out.print("Defender Rolls: ");
        for(int x : defenderRoll){
            System.out.print(" || " + x + " || ");
        }

        System.out.println();
        int[] attacking = findMax(attackerRoll);
        int[] defending = findMax(defenderRoll);

        if(attacking.length == 2 && defending.length == 2) {
            System.out.println("Attacker Max Roll: " + " || " + attacking[0] +", " +attacking[1] + " || "  + "Defender Max Roll: "+" || " + defending[0] +", " +defending[1] +" || ");
            if((attacking[0] > defending[0]) && (attacking[1] > defending[1])) {
                //defender loses 2 troops
                return AttackResult.D2;
            } else if((defending[0] >= attacking[0]) && (defending[1] >= attacking[1])) {
                //attacker loses 2 troops
                return AttackResult.A2;
            } else {
                //attacker loses 1 troop and the defender loses 1 troop
                return AttackResult.A1D1;
            }
        } else {
            System.out.println("Attacker Max Roll: " + " || " + attacking[0] + " || "  + "Defender Max Roll: "+" || " + defending[0] +" || ");
            if(attacking[0] > defending[0]) {
                //defender loses 1 troop
                return AttackResult.D1;
            } else {
                //attacked loses 1 troop
                return AttackResult.A1;
            }
        }
    }

    /**
     * This method is used to find the maximum and second maximum.
     * Only a maximum will be returned if the array being passed in has a length of 1.
     * @param arr array to search for maximum and second maximum in.
     * @return int[]    The maximum and second maximum (IF SIZEOFARRAY > 1) stored in an array of integers.
     *                  Maximum value is stored at index 0 of returned array.
     *                  Second Maximum is stored at index 1 of returned array (if applicable).
     */
    private int[] findMax(int[] arr) {
        int[] maxArray;
        int length = arr.length;

        if(length == 1) {
            maxArray = new int[1];
            maxArray[0] = arr[0];
            return maxArray;
        }

        maxArray = new int[2];

        for(int roll : arr) {
            if(roll > maxArray[0]) {
                maxArray[1] = maxArray[0];
                maxArray[0] = roll;
            } else if(roll > maxArray[1]) {
                maxArray[1] = roll;
            }
        }
        return maxArray;
    }

    /**
     * This method is used to return the rolls generated calling the constructor of the Model.Dice class.
     * @return int[] rolls created when constructing a Model.Dice object.
     */
    public int[] getRoll() {
        return rolls;
    }
}
