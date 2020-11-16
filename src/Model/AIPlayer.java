package Model;

import View.GameView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AIPlayer extends Player {

    private GameView gameView;
    private GameEvent gameEvent;
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
        int highestValue = 0;
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
        int value = 0;
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
}
