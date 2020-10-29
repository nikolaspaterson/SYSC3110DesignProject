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
                temp_territory.activateTimer();
                gameViewRef.addCommandTerritory(temp_territory);
            }else if(temp_territory.equals(gameViewRef.getCommandTerritory().get(0))) {

            } else if(gameViewRef.getCommandTerritorySize()  == 1 && temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer())){
                Territory main_territory = gameViewRef.getCommandTerritory().get(0);
                main_territory.cancel_timer();
                gameViewRef.clearCommandTerritory();
                temp_territory.activateTimer();
                gameViewRef.addCommandTerritory(temp_territory);
            }else if(gameViewRef.getCommandTerritorySize()  == 1 && !temp_territory.getOccupant().equals(gameViewRef.getCurrentPlayer()) && gameViewRef.getCommandTerritory().get(0).isNeighbour(temp_territory)){
                Territory main_territory = gameViewRef.getCommandTerritory().get(0);
                gameViewRef.addCommandTerritory(temp_territory);
                main_territory.cancel_timer();
                AttackPopUp attackPopUp = new AttackPopUp(gameViewRef.getCommandTerritory().get(0), gameViewRef.getCommandTerritory().get(1));
                attackPopUp.show(gameViewRef, 300, 350);
                System.out.println("Attacker: " + gameViewRef.getCommandTerritory().get(0).toString() + "Defender: "  + gameViewRef.getCommandTerritory().get(1).toString());
                gameViewRef.clearCommandTerritory();
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
