package Model;

import View.GameView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AIPlayer extends Player {

    private GameView gameView;
    private GameEvent gameEvent;

    public AIPlayer(String name, GameView gameView) {
        super(name);
        this.gameView = gameView;
        gameEvent = new GameEvent(this);
    }

    public Territory bestReinforceTerritory() {
        Territory bestTerritory = null;
        for(Territory territory : getTerritoriesOccupied().values()) {
            if(bestTerritory == null) {
                bestTerritory = territory;
            } else if(bestTerritory.getTroops() > territory.getTroops()) {
                bestTerritory = territory;
            }
        }
        return bestTerritory;
    }

    public void reinforce() {
        Territory bestTerritory = bestReinforceTerritory();
        gameEvent.reinforce(bestTerritory, this.getDeployableTroops());
    }

    public ArrayList<Territory> bestAttackTerritory() {
        ArrayList<Territory> weakest = new ArrayList<>();
        for(Territory territory : getTerritoriesOccupied().values()) {
            for(Territory enemy : territory.getNeighbours().values()) {
                if(!enemy.getOccupant().equals(this)) {
                    if(weakest.isEmpty() && territory.getTroops() != 1) {
                        weakest.add(territory);
                        weakest.add(enemy);
                    }
                }
            }
        }
        return weakest;
    }

    public void attack() {
        ArrayList<Territory> terrAttack = bestAttackTerritory();
        gameEvent.attack(terrAttack.get(0), terrAttack.get(1), 1);
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
        for(int i = 0; i < mostInDanger.length; i++){ //check if territory can be helped
            for(Territory friendlyNeighbour : mostInDanger[i].getLinkedNeighbours()){
                if(assignThreatLevel(friendlyNeighbour, friendlyNeighbour.getTroops()) < assignThreatLevel(mostInDanger[i], mostInDanger[i].getTroops())){
                    //friend is safer than you, so they can help
                    int newThreatLevelTerritory = assignThreatLevel(mostInDanger[i], mostInDanger[i].getTroops() + availableTroopsToReceive(friendlyNeighbour));
                    int newThreatLevelDonoTerritory = assignThreatLevel(friendlyNeighbour, friendlyNeighbour.getTroops() - availableTroopsToReceive(friendlyNeighbour));
                    if(newThreatLevelTerritory < threatMap.get(mostInDanger[i]) && newThreatLevelDonoTerritory == assignThreatLevel(friendlyNeighbour, friendlyNeighbour.getTroops())){
                        //friend helping you will not hurt them, so they will sent troops
                        territories[0] = friendlyNeighbour;
                        territories[1] = mostInDanger[i];
                        return territories;
                    }//else it will hurt friend so you will find some other friend
                }
            }
        }
        return null; //no good fortify move found
    }

    /**
     * Takes a hashmap of territories and their threat levels, returns a sorted array of territories decreasing based
     * on their threat levels.
     * @param map
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


    public String getState(){
        return gameView.getCurrentState();
    }

    public void nextState(){
        System.out.println(gameView.getCurrentState());
        gameView.nextState();
    }



    private float continentValue(Territory territory){
        Continent temp_continent = gameView.getContinent(territory);
        int continentSize = temp_continent.getContinentTerritory().size();
        int ownedTerritories = 0;
        for(Territory c_terri : temp_continent.getContinentTerritory().values()){
            if(c_terri.getOccupant().equals(this)){
                ownedTerritories++;
            }
        }
        float continentRatio = ownedTerritories/continentSize;
        return continentRatio;
    }
}
