package View;

import Controller.ReinforcePopUpController;
import Model.Player;
import Model.Territory;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Creates popupmenu for player during the reinforce stage of
 * their turn.
 * @author nikolaspaterson
 */
public class ReinforcePopUp extends JPopupMenu {

    private final Territory selectedTerritory;
    private JButton minusButton;
    private JButton plusButton;
    private JButton deployButton;
    private JLabel title;
    private JLabel territoryName;
    private JLabel photo;
    private JLabel troops;

    /**
     * popup constructor.
     * @param t - Model.Territory
     */
    public ReinforcePopUp(Territory t){
        super();
        this.selectedTerritory = t;
        //Helper function to initialize all panel components
        initializeComponents();

        //sets layout of the panel to a gridlayout
        setLayout(new GridLayout(1,3));

        //Sets a black border
        Border darkline = BorderFactory.createLineBorder(Color.black, 3);
        setBorder(darkline);

        //adds panels after calling their respective constructors
        add(createLeftPanel());
        add(createMiddlePanel());
        add(createRightPanel());

        //makes panel visible
        setVisible(true);
    }

    /**
     * Initializes all components in the panel.
     */
    private void initializeComponents(){
        //Create controller for button actions
        ReinforcePopUpController controller = new ReinforcePopUpController(this);

        title = new JLabel("REINFORCE");
        title.setHorizontalAlignment(title.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.ITALIC,35));

        territoryName = new JLabel();
        territoryName.setHorizontalAlignment(territoryName.CENTER);
        territoryName.setVerticalAlignment(territoryName.CENTER);
        territoryName.setText(selectedTerritory.getTerritoryName());
        territoryName.setFont(new Font("Comic Sans MS", Font.ITALIC,20));

        photo = new JLabel();
        Player player = getPlayer();
        photo.setIcon(player.getPlayer_icon());
        photo.setHorizontalAlignment(photo.CENTER);
        photo.setVerticalAlignment(photo.CENTER);

        minusButton = new JButton("-");
        minusButton.setFont(new Font("Impact", Font.PLAIN,35));
        plusButton = new JButton("+");
        plusButton.setFont(new Font("Impact", Font.PLAIN,35));
        minusButton.addActionListener(controller);
        plusButton.addActionListener(controller);

        deployButton = new JButton("DEPLOY");
        deployButton.setFont(new Font("Impact", Font.PLAIN,35));
        deployButton.setBackground(new Color(178, 236, 83));
        deployButton.addActionListener(controller);

        troops = new JLabel("0");
        troops.setFont(new Font("Impact", Font.PLAIN,20));
        troops.setHorizontalAlignment(troops.CENTER);
        troops.setVerticalAlignment(troops.CENTER);
    }

    /**
     * Creates the leftmost label of the reinforce popup.
     * @return JLabel
     */
    private JLabel createLeftPanel(){
        return photo;
    }

    /**
     * Creates the middle panel of the reinforce popup.
     * @return JPanel
     */
    private JPanel createMiddlePanel(){
        JPanel middlePanel = new JPanel();

        GridLayout middleLayout = new GridLayout(3,1);
        middlePanel.setLayout(middleLayout);
        middlePanel.add(title);
        middlePanel.add(territoryName);

        //Separate panel for buttons and troop number
        JPanel troopPanel = new JPanel();
        GridLayout buttonGrid = new GridLayout(0,3);
        troopPanel.setLayout(buttonGrid);
        troopPanel.add(minusButton);
        troopPanel.add(troops);
        troopPanel.add(plusButton);

        middlePanel.add(troopPanel);
        return middlePanel;
    }

    /**
     * Creates the rightmost panel of the reinforce popup.
     * @return JPanel
     */
    private JPanel createRightPanel(){
        JPanel rightPanel = new JPanel();
        GridLayout rightGrid = new GridLayout(3,1);
        rightPanel.setLayout(rightGrid);
        rightPanel.add(new JLabel());
        rightPanel.add(deployButton);
        rightPanel.add(new JLabel());
        return rightPanel;
    }

    /**
     * Returns the number of troops the player want to deploy.
     * @return JLabel
     */
    public JLabel getTroops(){
        return troops;
    }

    /**
     * Updates the number of troops that the player wants to move.
     * @param x int - number of troops we want to set it to.
     */
    public void setTroops(int x){
        troops.setText(String.valueOf(x));
    }

    /**
     * Returns the plusButton.
     * @return JButton
     */
    public JButton getPlus(){
        return plusButton;
    }

    /**
     * Return the minusButton.
     * @return JButton
     */
    public JButton getMinus(){
        return minusButton;
    }

    /**
     * Returns the territory the player wants to reinforce.
     * @return Model.Territory
     */
    public Territory getTerritory(){
        return selectedTerritory;
    }

    /**
     * Returns the player of the territory.
     * @return Model.Player
     */
    public Player getPlayer(){
        return selectedTerritory.getOccupant();
    }
}
