package Controller;

import Model.AIPlayer;
import Model.GameState;
import Model.Territory;
import View.*;
import java.awt.event.ActionEvent;

public class GameController {

    private final GameView gameViewRef;

    public GameController(GameView game_ref){
        gameViewRef = game_ref;
    }

    /**
     *  Using this ActionEvent we attach it to all Territories in the set up phase of the View.GameView.
     *  Its purpose is to check the current player clicking, the state of the game, and the territory button
     * @param e This action event is a button Model.Territory
     */
    public void territoryAction(ActionEvent e){
        GameState state = gameViewRef.getCurrentState();
        Object obj = e.getSource();
        TerritoryButton territoryButton = (TerritoryButton) obj;
        Territory temp_territory = gameViewRef.getWorldMap().get(territoryButton.getTerritoryName());

        if(gameViewRef.getCurrentPlayer() instanceof AIPlayer) {
            return;
        }

        switch (state) {
            case REINFORCE:
                if (temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer()) && gameViewRef.getCurrentPlayer().getDeployableTroops() != 0) {
                    gameViewRef.addCommandTerritory(temp_territory);
                    ReinforcePopUp temp = new ReinforcePopUp(temp_territory);
                    temp.show(gameViewRef, 300, 350);
                    System.out.println("Pop up Reinforce + \n" + temp_territory.toString());
                    gameViewRef.clearCommandTerritory();
                }
                break;

            case ATTACK:
                if (gameViewRef.getCommandTerritorySize() == 0 && temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer()) && temp_territory.getTroops() > 1) {
                    temp_territory.activateTimer();
                    gameViewRef.addCommandTerritory(temp_territory);
                } else if (gameViewRef.getCommandTerritorySize() == 1 && temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer())) {
                    gameViewRef.getCommandTerritory().get(0).cancel_timer();
                    gameViewRef.clearCommandTerritory();
                    temp_territory.activateTimer();
                    gameViewRef.addCommandTerritory(temp_territory);
                } else if (gameViewRef.getCommandTerritorySize() == 1 && !temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer()) && gameViewRef.getCommandTerritory().get(0).isNeighbour(temp_territory)) {
                    AttackPopUp attackPopUp = new AttackPopUp(gameViewRef.getCommandTerritory().get(0), temp_territory, gameViewRef);
                    attackPopUp.show(gameViewRef, 300, 350);
                    System.out.println("Attacker: " + gameViewRef.getCommandTerritory().get(0).toString() + "Defender: " + temp_territory.toString());
                    gameViewRef.getCommandTerritory().get(0).cancel_timer();
                    gameViewRef.clearCommandTerritory();
                } else {
                    for(Territory time_stop : gameViewRef.getCommandTerritory()){
                        time_stop.cancel_timer();
                    }
                    gameViewRef.clearCommandTerritory();

                }
                break;

            case FORTIFY:
                if (gameViewRef.getCommandTerritorySize() == 0 && temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer()) && gameViewRef.getCurrentPlayer().getFortifyStatus()) {
                    gameViewRef.clearCommandTerritory();
                    gameViewRef.addCommandTerritory(temp_territory);
                } else if (gameViewRef.getCommandTerritorySize() == 1 && temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer()) && gameViewRef.getFirstCommandTerritory().getLinkedNeighbours().contains(temp_territory)) {
                    gameViewRef.addCommandTerritory(temp_territory);
                    System.out.println("Ally1: " + gameViewRef.getCommandTerritory().get(0).toString() + "Ally2: " + gameViewRef.getCommandTerritory().get(1).toString());
                    FortifyPopUp fortifyPopUp = new FortifyPopUp(gameViewRef.getCommandTerritory().get(0), gameViewRef.getCommandTerritory().get(1));
                    fortifyPopUp.show(gameViewRef, 300, 350);
                    gameViewRef.clearCommandTerritory();
                } else {
                    gameViewRef.clearCommandTerritory();
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
        if(gameViewRef.getCurrentPlayer() instanceof AIPlayer) {
            return;
        }
        for(Territory time_stop : gameViewRef.getCommandTerritory()){
            time_stop.cancel_timer();
        }
        gameViewRef.nextState();
        gameViewRef.clearCommandTerritory();
    }
}
