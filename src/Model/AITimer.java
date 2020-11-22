package Model;

import java.util.TimerTask;

/**
 * This class is used to add some delay between the AIPlayer's moves in order to make it appear as if someone is actually playing.
 */
public class AITimer extends TimerTask {

    private final AIPlayer aiPlayer;

    /**
     * Constructor for the AITimer class.
     *
     * @param aiPlayer the AIPlayer
     */
    public AITimer(AIPlayer aiPlayer){
        this.aiPlayer = aiPlayer;
    }

    /**
     * Controls which move the AIPlayer is supposed to be controlling.
     */
    @Override
    public void run() {
        switch (aiPlayer.getState()) {
            case REINFORCE -> {
                if(aiPlayer.getDeployableTroops() != 0) {
                    aiPlayer.reinforce();
                } else {
                    aiPlayer.nextState();
                }
            }
            case ATTACK -> {
                if(aiPlayer.stillAttacking()){
                    aiPlayer.attack();
                }else {
                    aiPlayer.nextState();
                }
            }
            case FORTIFY -> {
                aiPlayer.fortify();
                aiPlayer.nextState();
            }
        }
    }
}
