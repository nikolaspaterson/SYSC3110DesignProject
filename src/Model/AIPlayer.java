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

    public String getState(){
        return gameView.getCurrentState();
    }

    public void nextState(){
        System.out.println(gameView.getCurrentState());
        gameView.nextState();

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
