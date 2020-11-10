package Controller;

import Model.Territory;
import View.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class GameController implements ActionListener {

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
        String state = gameViewRef.getCurrentState();
        Object obj = e.getSource();
        TerritoryButton territoryButton = (TerritoryButton) obj;
        Territory temp_territory = gameViewRef.getWorldMap().get(territoryButton.getTerritoryName());

        switch (state) {
            case "Reinforce":
                if (temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer()) && gameViewRef.getCurrentPlayer().getDeployableTroops() != 0) {
                    gameViewRef.clearCommandTerritory();
                    gameViewRef.addCommandTerritory(temp_territory);
                    ReinforcePopUp temp = new ReinforcePopUp(temp_territory);
                    temp.show(gameViewRef, 300, 350);
                    System.out.println("Pop up Reinforce + \n" + temp_territory.toString());
                }
                break;

            case "Attack":
                if (gameViewRef.getCommandTerritorySize() == 0 && temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer()) && temp_territory.getTroops() > 1) {
                    gameViewRef.clearCommandTerritory();
                    //temp_territory.activateTimer();
                    gameViewRef.addCommandTerritory(temp_territory);
                } else if (gameViewRef.getCommandTerritorySize() == 1 && temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer())) {
                    gameViewRef.clearCommandTerritory();
                    //temp_territory.activateTimer();
                    gameViewRef.addCommandTerritory(temp_territory);
                } else if (gameViewRef.getCommandTerritorySize() == 1 && !temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer()) && gameViewRef.getCommandTerritory().get(0).isNeighbour(temp_territory)) {
                    gameViewRef.addCommandTerritory(temp_territory);
                    AttackPopUp attackPopUp = new AttackPopUp(gameViewRef.getCommandTerritory().get(0), gameViewRef.getCommandTerritory().get(1), gameViewRef);
                    attackPopUp.show(gameViewRef, 300, 350);
                    System.out.println("Attacker: " + gameViewRef.getCommandTerritory().get(0).toString() + "Defender: " + gameViewRef.getCommandTerritory().get(1).toString());
                    gameViewRef.clearCommandTerritory();
                } else {
                    gameViewRef.clearCommandTerritory();

                }
                break;

            case "Fortify":
                if (gameViewRef.getCommandTerritorySize() == 0 && temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer())) {
                    gameViewRef.clearCommandTerritory();
                    gameViewRef.addCommandTerritory(temp_territory);
                } else if (gameViewRef.getCommandTerritorySize() == 1 && temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer()) && gameViewRef.getCommandTerritory().get(0).isNeighbour(temp_territory)) {
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
        gameViewRef.nextState();
        gameViewRef.clearCommandTerritory();
    }

    /**
     * This override function is not being used due to the buttons referencing the ActionListeners from Controller.GameController Methods
     * @param e N/A
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
