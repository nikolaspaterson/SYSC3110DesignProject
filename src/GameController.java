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
        System.out.println("Button Pressed");
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
