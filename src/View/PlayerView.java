package View;

import Event.PlayerEvent;
import Listener.PlayerListener;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Used to represent the Player object as a JPanel that will be placed on the main GUI Frame.
 *
 * @author Ahmad El-Sammak
 * @author Erik Iuhas
 */
public class PlayerView extends JPanel implements PlayerListener {

    private final JLabel total_troops_label;
    private final JLabel player_deploy;
    private final JLabel player_icon;

    /**
     * Constructor for the PlayerView Class.
     * @param name the name of the player
     * @param player_color the player color
     * @param player_icon the icon that the player chose
     * @param total_troops the total number of troops
     */
    public PlayerView(String name, Color player_color, ImageIcon player_icon, int total_troops) {
        Border darkline = BorderFactory.createLineBorder(player_color.darker());
        setBackground(player_color);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        this.player_icon = new JLabel();
        JLabel player_name_label = new JLabel();
        total_troops_label = new JLabel();
        player_deploy = new JLabel();

        player_deploy.setFont(new Font("Impact",Font.PLAIN,20));
        player_deploy.setForeground(new Color(0xE73A3A));
        this.player_icon.setIcon(new ImageIcon(player_icon.getImage()));
        player_name_label.setText(name);
        player_name_label.setFont(new Font("Impact",Font.PLAIN,15));
        total_troops_label.setText("Troop#: " + total_troops);

        add(this.player_icon);
        add(player_name_label);
        add(total_troops_label);
        setBorder(darkline);
    }

    /**
     * Gets the player's icon.
     * @return Icon
     */
    public Icon getplayer_icon(){
        return player_icon.getIcon();
    }

    /**
     * Sets the player's deployable troops JLabel
     * @param troops amount of troops to deploy
     */
    public void setDeployLabel(int troops){
        player_deploy.setText(String.valueOf(troops));
    }

    /**
     * Sets the player's Total troops JLabel.
     * @param troops total troops.
     */
    public void setTotalTroopsLabel(int troops){
        total_troops_label.setText("Troop#: " + (troops));
    }

    /**
     * This method is used to add an "X" ontop of players who were eliminated out of the game.
     */
    public void xOutPlayer(boolean x){
        if(!x){
            setBackground(new Color(0x404040));
            removeAll();
            ImageIcon scaledImg = new ImageIcon(getClass().getResource("/resources/redx.png"));
            Image img = scaledImg.getImage().getScaledInstance( 85, 85,  java.awt.Image.SCALE_SMOOTH );
            scaledImg = new ImageIcon(img);
            JLabel x_mark = new JLabel();
            x_mark.setIcon(scaledImg);
            add(x_mark);

        }
    }

    /**
     * This method is used to handle updates from the PlayerModel and update the view components respectively.
     * @param e the event
     */
    @Override
    public void handlePlayerUpdate(PlayerEvent e) {
        setDeployLabel(e.getDeployable_troops());
        setTotalTroopsLabel(e.getTotal_troops());
        xOutPlayer(e.getStatus());
    }
}
