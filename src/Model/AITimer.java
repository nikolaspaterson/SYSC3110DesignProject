package Model;

import java.util.TimerTask;

public class AITimer extends TimerTask {
    private final AIPlayer aiPlayer;

    public AITimer(AIPlayer aiPlayer){
        this.aiPlayer = aiPlayer;
    }
    @Override
    public void run() {
        switch (aiPlayer.getState()) {
            case "Reinforce" -> {
                if(aiPlayer.getDeployableTroops() != 0) {
                    aiPlayer.reinforce();
                } else {
                    aiPlayer.nextState();
                }
            }
            case "Attack" -> {
                if(aiPlayer.stillAttacking()){
                    aiPlayer.attack();
                }else {
                    aiPlayer.nextState();
                }
            }
            case "Fortify" -> {
                aiPlayer.fortify();
                aiPlayer.nextState();
            }
        }
    }
}
