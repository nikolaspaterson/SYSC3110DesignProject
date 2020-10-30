import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The PlayerSelectController class is used to change and update the PlayerSelectView based which buttons are pressed.
 *
 * @author Ahmad El-Sammak
 */
public class PlayerSelectController implements ActionListener {

    private PlayerSelectView playerSelectView;

    /**
     * Class constructor for PlayerSelectController class.
     * @param playerSelectView the view that needs to be changed.
     */
    public PlayerSelectController(PlayerSelectView playerSelectView) {
        this.playerSelectView = playerSelectView;
    }

    /**
     * Checks to see which button is responsible for the ActionEvent performs the respected action
     * @param e the ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == playerSelectView.getLeftArrow()) { // Left arrow button
            int x = (Integer.parseInt(playerSelectView.getNumPlayers().getText()));
            if (x == 2) {
                x = 6;
                playerSelectView.getPlayerArrayList().clear();
                for(int i = 0; i < 6; i++) {
                    playerSelectView.getPlayerList().add(playerSelectView.getPlayers().get(i));
                    playerSelectView.getPlayerArrayList().add(playerSelectView.getPlayers().get(i));
                    playerSelectView.getPlayerList().revalidate();
                    playerSelectView.getPlayerList().repaint();
                }
            } else {
                x--;
                playerSelectView.getPlayerList().remove(playerSelectView.getPlayers().get(x));
                playerSelectView.getPlayerArrayList().remove(playerSelectView.getPlayers().get(x));
                playerSelectView.getPlayerList().revalidate();
                playerSelectView.getPlayerList().repaint();
            }
            playerSelectView.getNumPlayers().setText("" + x);
        } else if(e.getSource() == playerSelectView.getRightArrow()) { // Right arrow button
            int x = (Integer.parseInt(playerSelectView.getNumPlayers().getText()));
            if (x == 6) {
                x = 2;
                for(int i = 5; i > 1; i--) {
                    playerSelectView.getPlayerList().remove(playerSelectView.getPlayers().get(i));
                    playerSelectView.getPlayerArrayList().remove(playerSelectView.getPlayers().get(i));
                    playerSelectView.getPlayerList().revalidate();
                    playerSelectView.getPlayerList().repaint();
                }
            } else {
                x++;
                playerSelectView.getPlayerList().add(playerSelectView.getPlayers().get(x-1));
                playerSelectView.getPlayerArrayList().add(playerSelectView.getPlayers().get(x-1));
                playerSelectView.getPlayerList().revalidate();
                playerSelectView.getPlayerList().repaint();
            }
            playerSelectView.getNumPlayers().setText("" + x);
        } else if (e.getSource() == playerSelectView.getStartButton()){ //Start Button
            try {
                System.out.println("We started");
                playerSelectView.dispose();
                new GameView(playerSelectView.getPlayerArrayList());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
