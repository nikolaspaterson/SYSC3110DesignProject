import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PlayerSelectController implements ActionListener {

    private PlayerSelectView playerSelectView;

    public PlayerSelectController(PlayerSelectView playerSelectView) {
        this.playerSelectView = playerSelectView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == playerSelectView.getLeftArrow()) {
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
        } else if(e.getSource() == playerSelectView.getRightArrow()) {
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
        } else if (e.getSource() == playerSelectView.getStartButton()){
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
