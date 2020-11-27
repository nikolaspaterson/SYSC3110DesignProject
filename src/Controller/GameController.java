package Controller;

import Model.AIPlayer;
import Model.GameModel;
import Model.GameState;
import Model.Territory;
import View.*;
import java.awt.event.ActionEvent;

public class GameController {

    private final GameModel gameModel;
    private final GameView gameView;

    public GameController(GameModel gameModel, GameView gameView){
        this.gameModel = gameModel;
        this.gameView = gameView;
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

    public void reinforceAction(Territory temp_territory) {
        if (temp_territory.getOccupant().equals(gameModel.getCurrentPlayer()) && gameModel.getCurrentPlayer().getDeployableTroops() != 0) {
            gameModel.addCommandTerritory(temp_territory);
            ReinforcePopUp temp = new ReinforcePopUp(temp_territory);
            temp.show(gameView, 300, 350);
            System.out.println("Pop up Reinforce + \n" + temp_territory.toString());
            gameModel.clearCommandTerritory();
        }
    }

    public void attackAction(Territory temp_territory) {
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
            attackPopUp.show(gameView, 300, 350);
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

    public void fortifyAction(Territory temp_territory) {
        if (gameModel.getCommandTerritorySize() == 0 && temp_territory.getOccupant().equals(gameModel.getCurrentPlayer()) && gameModel.getCurrentPlayer().getFortifyStatus()) {
            gameModel.clearCommandTerritory();
            gameModel.addCommandTerritory(temp_territory);
        } else if (gameModel.getCommandTerritorySize() == 1 && temp_territory.getOccupant().equals(gameModel.getCurrentPlayer()) && gameModel.getFirstCommandTerritory().getLinkedNeighbours().contains(temp_territory)) {
            gameModel.addCommandTerritory(temp_territory);
            System.out.println("Ally1: " + gameModel.getCommandTerritory().get(0).toString() + "Ally2: " + gameModel.getCommandTerritory().get(1).toString());
            FortifyPopUp fortifyPopUp = new FortifyPopUp(gameModel.getCommandTerritory().get(0), gameModel.getCommandTerritory().get(1));
            fortifyPopUp.show(gameView, 300, 350);
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
