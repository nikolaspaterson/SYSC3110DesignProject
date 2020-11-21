package Controller;

import Model.AIPlayer;
import Model.GameModel;
import Model.GameState;
import Model.Territory;
import View.*;
import java.awt.event.ActionEvent;

public class GameController {

    private final GameModel gameModelRef;
    private final GameView gameView;

    public GameController(GameModel game_ref,GameView gameView){
        gameModelRef = game_ref;
        this.gameView = gameView;
    }

    /**
     *  Using this ActionEvent we attach it to all Territories in the set up phase of the View.GameView.
     *  Its purpose is to check the current player clicking, the state of the game, and the territory button
     * @param e This action event is a button Model.Territory
     */
    public void territoryAction(ActionEvent e){
        GameState state = gameModelRef.getCurrentState();
        Object obj = e.getSource();
        TerritoryButton territoryButton = (TerritoryButton) obj;
        Territory temp_territory = gameModelRef.getWorldMap().get(territoryButton.getTerritoryName());

        if(gameModelRef.getCurrentPlayer() instanceof AIPlayer) {
            return;
        }

        switch (state) {
            case REINFORCE:
                if (temp_territory.getOccupant().equals(gameModelRef.getCurrentPlayer()) && gameModelRef.getCurrentPlayer().getDeployableTroops() != 0) {
                    gameModelRef.addCommandTerritory(temp_territory);
                    ReinforcePopUp temp = new ReinforcePopUp(temp_territory);
                    temp.show(gameView, 300, 350);
                    System.out.println("Pop up Reinforce + \n" + temp_territory.toString());
                    gameModelRef.clearCommandTerritory();
                }
                break;

            case ATTACK:
                if (gameModelRef.getCommandTerritorySize() == 0 && temp_territory.getOccupant().equals(gameModelRef.getCurrentPlayer()) && temp_territory.getTroops() > 1) {
                    temp_territory.activateTimer();
                    gameModelRef.addCommandTerritory(temp_territory);
                } else if (gameModelRef.getCommandTerritorySize() == 1 && temp_territory.getOccupant().equals(gameModelRef.getCurrentPlayer())) {
                    gameModelRef.getCommandTerritory().get(0).cancel_timer();
                    gameModelRef.clearCommandTerritory();
                    temp_territory.activateTimer();
                    gameModelRef.addCommandTerritory(temp_territory);
                } else if (gameModelRef.getCommandTerritorySize() == 1 && !temp_territory.getOccupant().equals(gameModelRef.getCurrentPlayer()) && gameModelRef.getCommandTerritory().get(0).isNeighbour(temp_territory)) {
                    AttackPopUp attackPopUp = new AttackPopUp(gameModelRef.getCommandTerritory().get(0), temp_territory, gameView);
                    attackPopUp.show(gameView, 300, 350);
                    System.out.println("Attacker: " + gameModelRef.getCommandTerritory().get(0).toString() + "Defender: " + temp_territory.toString());
                    gameModelRef.getCommandTerritory().get(0).cancel_timer();
                    gameModelRef.clearCommandTerritory();
                } else {
                    for(Territory time_stop : gameModelRef.getCommandTerritory()){
                        time_stop.cancel_timer();
                    }
                    gameModelRef.clearCommandTerritory();

                }
                break;

            case FORTIFY:
                if (gameModelRef.getCommandTerritorySize() == 0 && temp_territory.getOccupant().equals(gameModelRef.getCurrentPlayer()) && gameModelRef.getCurrentPlayer().getFortifyStatus()) {
                    gameModelRef.clearCommandTerritory();
                    gameModelRef.addCommandTerritory(temp_territory);
                } else if (gameModelRef.getCommandTerritorySize() == 1 && temp_territory.getOccupant().equals(gameModelRef.getCurrentPlayer()) && gameModelRef.getFirstCommandTerritory().getLinkedNeighbours().contains(temp_territory)) {
                    gameModelRef.addCommandTerritory(temp_territory);
                    System.out.println("Ally1: " + gameModelRef.getCommandTerritory().get(0).toString() + "Ally2: " + gameModelRef.getCommandTerritory().get(1).toString());
                    FortifyPopUp fortifyPopUp = new FortifyPopUp(gameModelRef.getCommandTerritory().get(0), gameModelRef.getCommandTerritory().get(1));
                    fortifyPopUp.show(gameView, 300, 350);
                    gameModelRef.clearCommandTerritory();
                } else {
                    gameModelRef.clearCommandTerritory();
                }
                break;
        }
    }

    /**
     * This ActionEvent is added to the button in View.StatusBar which is in charge of changing the state of the game
     * and if at the end of gameStates to load the next player.
     * @param e The button from View.StatusBar
     */
    public void nextState(ActionEvent e){
        if(gameModelRef.getCurrentPlayer() instanceof AIPlayer) {
            return;
        }
        for(Territory time_stop : gameModelRef.getCommandTerritory()){
            time_stop.cancel_timer();
        }
        gameModelRef.nextState();
        gameModelRef.clearCommandTerritory();
    }
}
