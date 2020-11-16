package Model;

import View.GameView;
import java.util.ArrayList;
import java.util.HashMap;

public class AIPlayer extends Player {

    private final GameView gameView;
    private final GameEvent gameEvent;
    private boolean attacking;

    public AIPlayer(String name, GameView gameView) {
        super(name);
        this.gameView = gameView;
        gameEvent = new GameEvent(this);
        attacking = true;

    }

    public float enemyNeighbourRatio(Territory territory) {
        int enemyNeighbour = 0;
        for (Territory neighbour : territory.getNeighbours().values()) {
            if (!(neighbour.getOccupant().equals(this))) {
                enemyNeighbour++;
            }
        }
        return ((float) enemyNeighbour/territory.getNeighbours().size());
    }

    public int leastEnemySurrounded(float enemyNeighbourRatio) {
        if(enemyNeighbourRatio <= 0.25 && enemyNeighbourRatio > 0) {
            return 6;
        } else if(enemyNeighbourRatio > 0.25 && enemyNeighbourRatio <= 0.50) {
            return 4;
        } else if(enemyNeighbourRatio > 0.50 && enemyNeighbourRatio <= 0.75) {
            return 2;
        } else {
            return -100;
        }
    }

    public int lowestTroopTerritory(Territory territory) {
        if(territory.getTroops() == 1) {
            return 3;
        } else if(territory.getTroops() == 2) {
            return 2;
        } else {
            return 1;
        }
    }

    public Territory bestReinforceTerritory() {
        Territory bestTerritory = null;
        int value = 0;
        for (Territory curr : getTerritoriesOccupied().values()) {
            if (bestTerritory == null) {
                bestTerritory = curr;
                value += leastEnemySurrounded(enemyNeighbourRatio(bestTerritory));
                value += lowestTroopTerritory(bestTerritory);
                value += continentValue(curr);
            } else {
                int challengerValue = 0;
                challengerValue += leastEnemySurrounded(enemyNeighbourRatio(curr));
                challengerValue += lowestTroopTerritory(curr);
                challengerValue += continentValue(curr);
                if (value < challengerValue) {
                    value = challengerValue;
                    bestTerritory = curr;
                }
            }
        }
        return bestTerritory;
    }

    public int splitDeployTroops() {
        int troops = this.getDeployableTroops();
        if(troops <= 3) {
            return troops;
        }
        return (troops/3);
    }

    public void reinforce() {
        Territory bestTerritory = bestReinforceTerritory();
        gameEvent.reinforce(bestTerritory, splitDeployTroops());
    }

    public ArrayList<Territory> bestAttackTerritory() {
        ArrayList<Territory> weakest = new ArrayList<>();
        int highestValue = 2;
        int newValue;
        for(Territory allTerritories : getTerritoriesOccupied().values()){
            for(Territory currentEnemy : allTerritories.getNeighbours().values()){
                if(!currentEnemy.getOccupant().equals(this)){
                    newValue = successfulAttackProbability(allTerritories,currentEnemy);
                    //newValue += continentValue(allTerritories);
                    if(weakest.size() == 0 && highestValue < newValue){
                        highestValue = newValue;
                        weakest.add(allTerritories);
                        weakest.add(currentEnemy);
                    }else if(highestValue < newValue){
                        highestValue = newValue;
                        weakest.set(0,allTerritories);
                        weakest.set(1,currentEnemy);
                    }
                }
            }
        }
        return weakest;
    }

    public void attack() {
        ArrayList<Territory> terrAttack = bestAttackTerritory();
        if (terrAttack.size() != 0){
            Territory attacker = terrAttack.get(0);
            if(attacker.getTroops() >= 4){
                gameEvent.attack(attacker, terrAttack.get(1), 3);
            }else if(attacker.getTroops() == 3){
                gameEvent.attack(attacker, terrAttack.get(1), 2);
            }else {
                gameEvent.attack(attacker, terrAttack.get(1), 1);
            }
            if(gameEvent.getAttackerWon()) {
                gameEvent.fortify(terrAttack.get(0), terrAttack.get(1), (terrAttack.get(0).getTroops()/2));
            }
        }else {
            attacking = false;
        }
    }

    public String getState(){
        return gameView.getCurrentState();
    }

    public void nextState(){
        attacking = true;
        gameView.nextState();
    }
    private int successfulAttackProbability(Territory allyTerritory,Territory enemyTerritory){
        float troopDifference;
        int total = allyTerritory.getTroops() + enemyTerritory.getTroops();
        troopDifference = (float) (allyTerritory.getTroops() - enemyTerritory.getTroops())/total;
        if(troopDifference >= 0.75){
            return 8;
        } else if(troopDifference < 0.75 && troopDifference >= 0.5){
            return 6;
        } else if (troopDifference < 0.5 && troopDifference >= 0.25){
            return 4;
        } else if (troopDifference < 0.25 && troopDifference > 0){
            return 2;
        }else {
            return -4;
        }
    }

    public boolean stillAttacking(){
        return attacking;
    }

    private int continentValue(Territory territory){
        Continent temp_continent = gameView.getContinent(territory);
        int continentSize = temp_continent.getContinentTerritory().size();
        int ownedTerritories = 0;
        for(Territory c_terri : temp_continent.getContinentTerritory().values()){
            if(c_terri.getOccupant().equals(this)){
                ownedTerritories++;
            }
        }
        float continentRatio = (float) ownedTerritories/continentSize;
        if (continentRatio == 1){
            return -5;
        }if(continentRatio >= 0.75){
            return 5;
        }else {
           return  0;
        }
    }

    /**
     * Creates a fortify event if a territory can benefit from fortifying
     */
    public void fortify(){
        Territory[] territories = bestFortifyTerritory();
        if(territories != null){
            gameEvent.fortify(territories[0], territories[1], availableTroopsToReceive(territories[0]));
        }
    }

    /**
     * Checks for the best territory to reinforce.
     * Territory will only be reinforced if there is a territory that can donate troops without increasing their own
     * threat level.
     * @return territory array, index 0 is territory to lose troops, index 1 is territory to gain troops.
     */
    private Territory[] bestFortifyTerritory(){
        Territory[] territories = new Territory[2];
        HashMap<Territory, Integer> threatMap = new HashMap<>();

        for(Territory t : this.getTerritoriesOccupied().values()){ //assign threat to each territory
            threatMap.put(t, assignThreatLevel(t, t.getTroops()));
        }

        Territory[] mostInDanger = getGreatestDangerLevel(threatMap); //array of territories in decreasing danger
        if (mostInDanger != null) {
            for (Territory territory : mostInDanger) { //check if territory can be helped
                for (Territory friendlyNeighbour : territory.getLinkedNeighbours()) {
                    if (assignThreatLevel(friendlyNeighbour, friendlyNeighbour.getTroops()) < assignThreatLevel(territory, territory.getTroops())) {
                        //friend is safer than you, so they can help
                        int newThreatLevelTerritory = assignThreatLevel(territory, territory.getTroops() + availableTroopsToReceive(friendlyNeighbour));
                        int newThreatLevelDonoTerritory = assignThreatLevel(friendlyNeighbour, friendlyNeighbour.getTroops() - availableTroopsToReceive(friendlyNeighbour));
                        if (newThreatLevelTerritory < threatMap.get(territory) && newThreatLevelDonoTerritory == assignThreatLevel(friendlyNeighbour, friendlyNeighbour.getTroops())) {
                            //friend helping you will not hurt them, so they will sent troops
                            territories[0] = friendlyNeighbour;
                            territories[1] = territory;
                            return territories;
                        }//else it will hurt friend so you will find some other friend
                    }
                }
            }
        }
        return null; //no good fortify move found
    }

    /**
     * Takes a hashmap of territories and their threat levels, returns a sorted array of territories decreasing based
     * on their threat levels.
     * @param map hashmap of territories and their threat levels
     * @return sorted Territory array
     */
    private Territory[] getGreatestDangerLevel(HashMap<Territory, Integer> map){
        if(map.isEmpty()){
            return null;
        }

        Territory[] sortedDanger = new Territory[map.size()];
        int i = 0;
        for(Territory t : map.keySet()){ //fill array with territories
            sortedDanger[i] = t;
            i++;
        }

        //sorts array based on territories threat level in hashmap
        for(int j = 0; j < sortedDanger.length; j++){
            for(int k = 0; k < sortedDanger.length; k++){
                if(map.get(sortedDanger[j])  < map.get(sortedDanger[k])){
                    Territory temp = sortedDanger[j];
                    sortedDanger[j] = sortedDanger[k];
                    sortedDanger[k] = temp;
                }
            }
        }
        return sortedDanger;
    }

    /**
     * Assigns threat level to a territory. level 0 if territory is safe, level 1 if territory borders and enemy
     * that has less troops, level 2 territory borders and enemy with more troops
     * @param t territory
     * @param troops number of troops
     * @return threat level
     */
    private int assignThreatLevel(Territory t, int troops){
        int threat = 0;
        if (!isSafe(t)) {
            for (Territory neighbourTerritory : t.getNeighbours().values()) {
                if (!neighbourTerritory.getOccupant().equals(t.getOccupant())) {
                    if (troops > neighbourTerritory.getTroops() && threat < 1) {
                        threat = 1;
                    } else {
                        threat = 2;
                    }
                }
            }
        }
        return threat;
    }

    /**
     * Returns the number of troops that a territory can donate and still be safe. If a territory is surrounded by
     * its own country it will donate all but 1 troop. If the territory has an enemy neighbour it will keep 2 more
     * troops than the enemy and donate the rest.
     * !! Could return 0 or negative troops !!
     * @param t Territory
     * @return int - The number of troops that territory t can donate
     */
    private int availableTroopsToReceive(Territory t){
        if(isSafe(t)){
            return t.getTroops()-1;
        }else{
            //Check for neighbouring enemy with highest number of troops (AKA Biggest threat)
            int enemyTroops = 0;
            for(Territory enemyTerritory : t.getNeighbours().values()){
                if(! enemyTerritory.getOccupant().equals(t.getOccupant())){
                    if(enemyTerritory.getTroops() > enemyTroops){
                        enemyTroops = enemyTerritory.getTroops();
                    }
                }
            }
            return t.getTroops() - (enemyTroops + 2);
        }
    }

    /**
     * Checks if a territory is surrounding by territories of the same owner
     * @param t Territory to check
     * @return true if all neighbours are owned by the owner of t
     */
    private boolean isSafe(Territory t){
        for(Territory neighbouringTerritory: t.getNeighbours().values()){
            if(! neighbouringTerritory.getOccupant().equals(this)){
                return false;
            }
        }
        return true;
    }
}
