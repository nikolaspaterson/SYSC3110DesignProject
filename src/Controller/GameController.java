package Controller;

import Model.AIPlayer;
import Model.GameModel;
import Model.GameState;
import Model.Territory;
import View.*;
import java.awt.event.ActionEvent;

/**
 * The GameController class is used to change and update the View.GameView based which buttons are pressed.
 */
public class GameController {

    private GameModel gameModel;
    private final GameView gameView;
    private final static int POPUP_X = 300;
    private final static int POPUP_Y = 350;
    private final static int ZERO_TROOPS_DEPLOYABLE = 0;

    /**
     * Class constructor for the GameController class.
     *
     * @param gameModel the game model
     * @param gameView the game view
     */
    public GameController(GameModel gameModel, GameView gameView){
        this.gameModel = gameModel;
        this.gameView = gameView;
    }

    /**
     * This method is used to update Game Model.
     *
     * @param gameModel the game model
     */
    public void updateModel(GameModel gameModel){
        this.gameModel = gameModel;
    }

    /**
     *  Using this ActionEvent we attach it to all Territories in the set up phase of the View.GameView.
     *  Its purpose is to check the current player clicking, the state of the game, and the territory button
     * @param e This action event is a button Model.Territory
     */
    public void territoryAction(ActionEvent e){
        GameState state = gameModel.getCurrentState();
        Object obj = e.getSource();
        TerritoryButton territoryButton = (TerritoryButton) obj;
        Territory temp_territory = gameModel.getWorldMap().get(territoryButton.getTerritoryName());

        if(gameModel.getCurrentPlayer() instanceof AIPlayer) {
            return;
        }

        switch (state) {
            case REINFORCE -> reinforceAction(temp_territory);
            case ATTACK -> attackAction(temp_territory);
            case FORTIFY -> fortifyAction(temp_territory);
        }
    }

    /**
     * This method is used handle the reinforce action when the player selects which a territory during the Reinforce stage.
     *
     * @param temp_territory the territory to reinforce
     */
    private void reinforceAction(Territory temp_territory) {
        if (temp_territory.getOccupant().equals(gameModel.getCurrentPlayer()) && gameModel.getCurrentPlayer().getDeployableTroops() != ZERO_TROOPS_DEPLOYABLE) {
            gameModel.addCommandTerritory(temp_territory);
            ReinforcePopUp temp = new ReinforcePopUp(temp_territory);
            temp.show(gameView, POPUP_X, POPUP_Y);
            System.out.println("Pop up Reinforce + \n" + temp_territory.toString());
            gameModel.clearCommandTerritory();
        }
    }

    /**
     * This method is used handle the attack action when the player selects which
     * territory they are attacking with and which one they want to attack.
     *
     * @param temp_territory the territory to attack
     */
    private void attackAction(Territory temp_territory) {
        if (gameModel.getCommandTerritorySize() == 0 && temp_territory.getOccupant().equals(gameModel.getCurrentPlayer()) && temp_territory.getTroops() > 1) {
            temp_territory.activateTimer();
            gameModel.addCommandTerritory(temp_territory);
        } else if (gameModel.getCommandTerritorySize() == 1 && temp_territory.getOccupant().equals(gameModel.getCurrentPlayer())) {
            gameModel.getCommandTerritory().get(0).cancel_timer();
            gameModel.clearCommandTerritory();
            temp_territory.activateTimer();
            gameModel.addCommandTerritory(temp_territory);
        } else if (gameModel.getCommandTerritorySize() == 1 && !temp_territory.getOccupant().equals(gameModel.getCurrentPlayer()) && gameModel.getCommandTerritory().get(0).isNeighbour(temp_territory)) {
            AttackPopUp attackPopUp = new AttackPopUp(gameModel.getCommandTerritory().get(0), temp_territory, gameView);
            attackPopUp.show(gameView, POPUP_X, POPUP_Y);
            System.out.println("Attacker: " + gameModel.getCommandTerritory().get(0).toString() + "Defender: " + temp_territory.toString());
            gameModel.getCommandTerritory().get(0).cancel_timer();
            gameModel.clearCommandTerritory();
        } else {
            for(Territory time_stop : gameModel.getCommandTerritory()){
                time_stop.cancel_timer();
            }
            gameModel.clearCommandTerritory();
        }
    }

    /**
     * This method is used handle the fortify action when the player selects which two territories he wants to move troops from and into.
     *
     * @param temp_territory the territory to fortify
     */
    private void fortifyAction(Territory temp_territory) {
        if (gameModel.getCommandTerritorySize() == 0 && temp_territory.getOccupant().equals(gameModel.getCurrentPlayer()) && gameModel.getCurrentPlayer().getFortifyStatus()) {
            gameModel.clearCommandTerritory();
            gameModel.addCommandTerritory(temp_territory);
        } else if (gameModel.getCommandTerritorySize() == 1 && temp_territory.getOccupant().equals(gameModel.getCurrentPlayer()) && gameModel.getFirstCommandTerritory().getLinkedNeighbours().contains(temp_territory)) {
            gameModel.addCommandTerritory(temp_territory);
            System.out.println("Ally1: " + gameModel.getCommandTerritory().get(0).toString() + "Ally2: " + gameModel.getCommandTerritory().get(1).toString());
            FortifyPopUp fortifyPopUp = new FortifyPopUp(gameModel.getCommandTerritory().get(0), gameModel.getCommandTerritory().get(1));
            fortifyPopUp.show(gameView, POPUP_X, POPUP_Y);
            gameModel.clearCommandTerritory();
        } else {
            gameModel.clearCommandTerritory();
        }
    }

    /**
     * This ActionEvent is added to the button in View.StatusBar which is in charge of changing the state of the game
     * and if at the end of gameStates to load the next player.
     * @param e The button from View.StatusBar
     */
    public void nextState(ActionEvent e){
        if(gameModel.getCurrentPlayer() instanceof AIPlayer) {
            return;
        }
        for(Territory time_stop : gameModel.getCommandTerritory()){
            time_stop.cancel_timer();
        }
        gameModel.nextState();
        gameModel.clearCommandTerritory();
    }
}
