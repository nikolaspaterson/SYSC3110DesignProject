import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {
    private GameView gameViewRef;

    public GameController(GameView game_ref){
        gameViewRef = game_ref;
    }

    public void territoryAction(ActionEvent e){
        String state = gameViewRef.getCurrentState();
        Object obj = e.getSource();
        Territory temp_territory = (Territory) obj;

        if(state == "Reinforce"){
            if(temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer())){
                gameViewRef.clearCommandTerritory();
                gameViewRef.addCommandTerritory(temp_territory);
                System.out.println("Pop up Reinforce + \n" + temp_territory.toString());
            }

        }else if(state == "Attack"){

            if(gameViewRef.getCommandTerritorySize() == 0 && temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer()) && temp_territory.getTroops() > 1){
                gameViewRef.clearCommandTerritory();
                gameViewRef.addCommandTerritory(temp_territory);
            }else if(gameViewRef.getCommandTerritorySize()  == 1 && !temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer()) && gameViewRef.getCommandTerritory().get(0).isNeighbour(temp_territory)){
                gameViewRef.addCommandTerritory(temp_territory);
                System.out.println("Attacker: " + gameViewRef.getCommandTerritory().get(0).toString() + "Defender: "  + gameViewRef.getCommandTerritory().get(1).toString());
            }else if(gameViewRef.getCommandTerritorySize() == 2 && temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer()) && temp_territory.getTroops() > 1){
                gameViewRef.clearCommandTerritory();
                gameViewRef.addCommandTerritory(temp_territory);
            }

        }else if(state == "Fortify"){
            if(gameViewRef.getCommandTerritorySize() == 0 && temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer())){
                gameViewRef.clearCommandTerritory();
                gameViewRef.addCommandTerritory(temp_territory);
            }else if(gameViewRef.getCommandTerritorySize()  == 1 && temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer()) && gameViewRef.getCommandTerritory().get(0).isNeighbour(temp_territory)){
                gameViewRef.addCommandTerritory(temp_territory);
                System.out.println("Ally1: " + gameViewRef.getCommandTerritory().get(0).toString() + "Ally2: "  + gameViewRef.getCommandTerritory().get(1).toString());
            }else if(gameViewRef.getCommandTerritorySize() == 2 && temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer())){
                gameViewRef.clearCommandTerritory();
                gameViewRef.addCommandTerritory(temp_territory);
            }
        }

    }

    public void nextState(ActionEvent e){
        gameViewRef.nextState();
        gameViewRef.clearCommandTerritory();
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
