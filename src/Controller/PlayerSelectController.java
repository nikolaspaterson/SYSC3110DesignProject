package Controller;

import View.GameView;
import View.PlayerSelectView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The Controller.PlayerSelectController class is used to change and update the View.PlayerSelectView based which buttons are pressed.
 *
 * @author Ahmad El-Sammak
 */
public class PlayerSelectController implements ActionListener {

    private final PlayerSelectView playerSelectView;
    private static final int MIN_PLAYER_SIZE = 2;
    private static final int MAX_PLAYER_SIZE = 6;

    /**
     * Class constructor for Controller.PlayerSelectController class.
     * @param playerSelectView the view that needs to be changed.
     */
    public PlayerSelectController(PlayerSelectView playerSelectView) {
        this.playerSelectView = playerSelectView;
    }

    /**
     * Checks to see which button is responsible for the ActionEvent and performs the respected action based on which button was pressed.
     * @param e the ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == playerSelectView.getLeftArrow()) { // Left arrow button
            int x = (Integer.parseInt(playerSelectView.getNumPlayers().getText()));
            if (x == MIN_PLAYER_SIZE) {
                x = MAX_PLAYER_SIZE;
                playerSelectView.getPlayerArrayList().clear();
                for(int i = 0; i < 6; i++) {
                    addPlayers(i);
                }
            } else {
                x--;
                removePlayers(x);
            }
            playerSelectView.getNumPlayers().setText("" + x);
        } else if(e.getSource() == playerSelectView.getRightArrow()) { // Right arrow button
            int x = (Integer.parseInt(playerSelectView.getNumPlayers().getText()));
            if (x == MAX_PLAYER_SIZE) {
                x = MIN_PLAYER_SIZE;
                for(int i = 5; i > 1; i--) {
                    removePlayers(i);
                }
            } else {
                x++;
                addPlayers(x-1);
            }
            playerSelectView.getNumPlayers().setText("" + x);
        } else if (e.getSource() == playerSelectView.getStartButton()){ //Start Button
            System.out.println("We started");
            playerSelectView.dispose();
            new GameView(playerSelectView.getPlayerArrayList(), playerSelectView.getMapFilePath());
        }
    }

    /**
     * This method is used to add a player to the view.
     *
     * @param index the player to add
     */
    private void addPlayers(int index) {
        playerSelectView.getPlayerList().add(playerSelectView.getPlayers().get(index));
        playerSelectView.getPlayerArrayList().add(playerSelectView.getPlayers().get(index));
        refresh();
    }

    /**
     * This method is used to remove a player from the view.
     *
     * @param index the player to remove
     */
    private void removePlayers(int index) {
        playerSelectView.getPlayerList().remove(playerSelectView.getPlayers().get(index));
        playerSelectView.getPlayerArrayList().remove(playerSelectView.getPlayers().get(index));
        refresh();
    }

    /**
     * This method is used to refresh the view.
     */
    private void refresh() {
        playerSelectView.getPlayerList().revalidate();
        playerSelectView.getPlayerList().repaint();
    }
}
