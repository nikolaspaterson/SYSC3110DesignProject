package Model;

import java.util.TimerTask;

public class PlayerTimer extends TimerTask {
    private AIPlayer aiPlayer;

    public PlayerTimer(AIPlayer aiPlayer){
        this.aiPlayer = aiPlayer;
    }
    @Override
    public void run() {
        switch (aiPlayer.getState()) {
            case "Reinforce" -> {
                aiPlayer.reinforce();
                aiPlayer.nextState();
            }
            case "Attack" -> {
                aiPlayer.attack();
                aiPlayer.nextState();
            }
            case "Fortify" -> aiPlayer.nextState();
        }

    }
}
