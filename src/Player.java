import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.HashMap;

/**
 * The Player class is responsible for containing important attributes that every player should have in the game of Risk.
 * @author Ahmad El-Sammak
 */
public class Player extends JPanel {

    private int deployableTroops;
    private final String name;
    private final HashMap<String, Territory> territoriesOccupied;
    private final Color player_color;

    private int total_troops;

    private JLabel player_icon;
    private JLabel player_name_label;
    private JLabel total_troops_label;
    private JLabel player_deploy;
    private Boolean in_game;

    /**
     * Class constructor for the Player class. Sets the name of the player and initializes the HashMap which will store what territory the player occupies.
     * @param name the name of the player.
     */
    public Player(String name,Color player_color,ImageIcon player_icon) {
        this.name = name;
        this.total_troops = 0;
        this.player_color = player_color;

        Border darkline = BorderFactory.createLineBorder(player_color.darker());
        this.setBackground(player_color);
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.player_icon = new JLabel();
        this.player_name_label = new JLabel();
        this.total_troops_label = new JLabel();
        this.player_deploy = new JLabel();
        player_deploy.setFont(new Font("Impact",Font.PLAIN,20));
        player_deploy.setForeground(new Color(0xE73A3A));
        this.player_icon.setIcon(new ImageIcon(player_icon.getImage()));
        this.player_name_label.setText(name);
        this.player_name_label.setFont(new Font("Impact",Font.PLAIN,15));
        this.total_troops_label.setText("Troop#: " + total_troops);

        in_game = true;

        this.add(this.player_icon);
        this.add(this.player_name_label);
        this.add(this.total_troops_label);
        this.setBorder(darkline);



        territoriesOccupied = new HashMap<>();
    }
    public Icon getplayer_icon(){
        return player_icon.getIcon();
    }

    /**
     * Gets the player's name.
     * @return String - Players name
     */
    public String getName(){
        return name;
    }

    /**
     * Getter for the hashmap containing all the territories that the player occupies.
     * @return HashMap<String,Territory> The territories Occupied.
     */
    public HashMap<String, Territory> getTerritoriesOccupied() { return territoriesOccupied; }

    public Color getPlayer_color(){ return player_color;}
    /**
     * Sets the amount of deployable troops the player can use during their reinforcement.
     * @param deployableTroops number of deployable troops.
     */
    public void setDeployableTroops(int deployableTroops) {
        this.deployableTroops = deployableTroops;
        player_deploy.setText(String.valueOf(this.deployableTroops));
        addTotal(deployableTroops);
    }

    public JLabel getDeployLabel(){
        return player_deploy;
    }
    /**
     * Adds the deployableTroops on top of the existing deployableTroops.
     * This accounts for bonus troops awarded to the player at the beginning of a round.
     * @param deployableTroops number of deployable troops.
     */
    public void addDeployableTroops(int deployableTroops) {
        this.deployableTroops += deployableTroops;
        player_deploy.setText(String.valueOf(this.deployableTroops));
        addTotal(deployableTroops);
    }

    public void addTotal(int troops) {
        total_troops += troops;
        total_troops_label.setText("Troop#: " + total_troops);
    }

    /**
     * Gets the amount of deployable troops the player can use during their reinforcement.
     * @return int number of deployable troops.
     */
    public int getDeployableTroops() {
        return deployableTroops;
    }

    /**
     * Decrements the troops that are available to deploy and returns the value that it was decremented by.
     * @param troop_count number of troops
     * @return int - The amount of deployable troops.
     */
    public int placeDeployableTroops(int troop_count) {
        if (deployableTroops - troop_count >= 0) {
            deployableTroops -= troop_count;
            return troop_count;
        } else {
            int temp = deployableTroops;
            deployableTroops = 0;
            return temp;
        }
    }

    /**
     * Increments the number of troops in a specified territory.
     * @param territory the territory where troops will be added.
     * @param numTroops the number of troops to add.
     */
    public void incrementTroops(Territory territory, int numTroops) {
        territory.setTroops(territory.getTroops() + numTroops);
    }

    /**
     * Decrements the number of troops in a specified territory.
     * @param territory the territory where troops will be removed.
     * @param numTroops the number of troops to remove.
     */
    public void decrementTroops(Territory territory, int numTroops) {
        territory.setTroops(territory.getTroops() - numTroops);
    }

    /**
     * Adds a territory to the HashMap of territories occupied by the player.
     * @param territoryName the name of the territory.
     * @param territory the territory object itself.
     */
    public void addTerritory(String territoryName, Territory territory) {
        territoriesOccupied.put(territoryName, territory);
    }

    /**
     * Removes a territory from the HashMap of territories occupied by the player.
     * @param territory the territory to remove.
     */
    public void removeTerritory(String territory) {
        territoriesOccupied.remove(territory);
    }

    public boolean getIn_game(){ return in_game;}

    public void xOutPlayer(){
        setBackground(new Color(0x404040));
        removeAll();
        ImageIcon scaledImg = new ImageIcon(getClass().getResource("/resources/redx.png"));
        Image img = scaledImg.getImage().getScaledInstance( 85, 85,  java.awt.Image.SCALE_SMOOTH );
        scaledImg = new ImageIcon(img);
        JLabel x_mark = new JLabel();
        x_mark.setIcon(scaledImg);
        add(x_mark);
        revalidate();
        repaint();



    }

    public int getTotal_troops() {
        return total_troops;
    }
    /**
     * Combines the Player's Name, all their Territory's owned and how many troops are in each Territory.
     * @return String combination of information above.
     */
    public String toString() {
        String output = "------>Player: " + this.name + "<------\n";
        String enemy;
        String ally;
        for(Territory temp_territory : territoriesOccupied.values()) {
            output += "\n======TerritoryOwned:Troops======\n";
            output += "         " + temp_territory.getTerritoryName() + " : " + temp_territory.getTroops() + "\n";
            enemy = "Neighbouring Enemy Territories: ";
            ally = "Neighbouring Ally Territories: ";
            for(Territory owners : temp_territory.getNeighbours().values()){
                if(owners.getOccupant().equals(this)) {
                    ally += owners.getTerritoryName() + ": " + owners.getTroops() + ", ";
                }else{
                    enemy += owners.getTerritoryName() + " : " + owners.getTroops() + ", ";
                }

            }
            ally = ally.substring(0,ally.length() -2) + "\n";
            enemy = enemy.substring(0,enemy.length() -2) + "\n";
            output += ally + enemy;
        }
        return output;
    }
}
