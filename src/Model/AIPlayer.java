package Model;

import View.GameView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AIPlayer extends Player {

    private GameView gameView;
    private GameEvent gameEvent;

    public AIPlayer(String name, GameView gameView) {
        super(name);
        this.gameView = gameView;
        gameEvent = new GameEvent(this);
    }

    public float enemyNeighbourRatio(Territory territory) {
        int enemyNeighbour = 0;
        for (Territory neighbour : territory.getNeighbours().values()) {
            if (!(neighbour.getOccupant().equals(this))) {
                enemyNeighbour++;
            }
        }
        return (enemyNeighbour/territory.getNeighbours().size());
    }

    public int leastEnemySurrounded(float enemyNeighbourRatio) {
        if(enemyNeighbourRatio <= 0.25 && enemyNeighbourRatio > 0) {
            return 6;
        } else if(enemyNeighbourRatio > 0.25 && enemyNeighbourRatio <= 0.50) {
            return 4;
        } else if(enemyNeighbourRatio > 0.50 && enemyNeighbourRatio <= 0.75) {
            return 2;
        } else {
            return -3;
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
            } else {
                int challengerValue = 0;
                challengerValue += leastEnemySurrounded(enemyNeighbourRatio(curr));
                challengerValue += lowestTroopTerritory(curr);
                if (value < challengerValue) {
                    bestTerritory = curr;
                }
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



    public void play() {
        try {
            reinforce();
            TimeUnit.SECONDS.sleep(1);
            gameView.nextState();
            attack();
            TimeUnit.SECONDS.sleep(1);
            gameView.nextState();
            //fortify();
            gameView.nextState();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
