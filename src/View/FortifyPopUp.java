package View;

import Controller.FortifyPopUpController;
import Model.Player;
import Model.Territory;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Creates popupmenu for player during the fortify stage of
 * their turn.
 */
public class FortifyPopUp extends JPopupMenu {

    private final Territory t1;
    private final Territory t2;
    private JLabel t1Name;
    private JLabel t1Troops;
    private JLabel title;
    private JButton minusButton;
    private JLabel troops;
    private JButton plusButton;
    private JButton fortifyButton;
    private JLabel t2Name;
    private JLabel t2Troops;
    private boolean winningMove;

    /**
     * Popup constructor.
     * @param territory1 - Model.Territory to lose troops
     * @param territory2 - Model.Territory to gain troops
     */
    public FortifyPopUp(Territory territory1, Territory territory2){
        super();
        this.t1=territory1;
        this.t2=territory2;

        winningMove = false;

        //Helper function to initialize all panel components
        initializeComponents();

        //sets panel layout to a gridlayout
        setLayout(new GridLayout(0,3));

        //Sets a black border
        Border darkline = BorderFactory.createLineBorder(Color.black, 3);
        setBorder(darkline);

        //adds panels after calling their respective constructors
        add(createLeftPanel());
        add(createMiddlePanel());
        add(createRightPanel());

        //makes the panel visible
        setVisible(true);
    }

    /**
     * Initializes all components in the panel.
     */
    private void initializeComponents(){
        //Create controller for button actions
        FortifyPopUpController controller = new FortifyPopUpController(this);

        title = new JLabel("FORTIFY");
        title.setHorizontalAlignment(title.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.ITALIC,35));

        t1Name = new JLabel();
        t1Name.setHorizontalAlignment(t1Name.CENTER);
        t1Name.setText(t1.getTerritoryName());
        t1Name.setFont(new Font("Comic Sans MS", Font.ITALIC,20));

        t2Name = new JLabel();
        t2Name.setHorizontalAlignment(t2Name.CENTER);
        t2Name.setText(t2.getTerritoryName());
        t2Name.setFont(new Font("Comic Sans MS", Font.ITALIC,20));

        t1Troops = new JLabel();
        t1Troops.setHorizontalAlignment(t1Troops.CENTER);
        t1Troops.setText(("Troops: ") + t1.getTroops());
        t1Troops.setFont(new Font("Comic Sans MS", Font.ITALIC,20));

        t2Troops = new JLabel();
        t2Troops.setHorizontalAlignment(t2Troops.CENTER);
        t2Troops.setText(("Troops: ") + t2.getTroops());
        t2Troops.setFont(new Font("Comic Sans MS", Font.ITALIC,20));

        minusButton = new JButton("-");
        minusButton.setFont(new Font("Impact", Font.PLAIN,35));
        plusButton = new JButton("+");
        plusButton.setFont(new Font("Impact", Font.PLAIN,35));

        fortifyButton = new JButton("DEPLOY");
        fortifyButton.setFont(new Font("Impact", Font.PLAIN,35));
        fortifyButton.setBackground(new Color(178, 236, 83));

        troops = new JLabel("0");
        troops.setFont(new Font("Impact", Font.PLAIN,20));
        troops.setHorizontalAlignment(troops.CENTER);
        troops.setVerticalAlignment(troops.CENTER);

        minusButton.addActionListener(controller);
        plusButton.addActionListener(controller);
        fortifyButton.addActionListener(controller);
    }

    /**
     * Creates the leftmost label of the fortify popup.
     * @return JLabel
     */
    private JPanel createLeftPanel(){
        JPanel leftPanel = new JPanel();
        GridLayout leftGrid = new GridLayout(2,1);
        leftPanel.setLayout(leftGrid);
        leftPanel.add(t1Name);
        leftPanel.add(t1Troops);
        return leftPanel;
    }

    /**
     * Creates the rightmost panel of the fortify popup.
     * @return JPanel
     */
    private JPanel createRightPanel(){
        JPanel rightPanel = new JPanel();
        GridLayout rightGrid = new GridLayout(2,1);
        rightPanel.setLayout(rightGrid);
        rightPanel.add(t2Name);
        rightPanel.add(t2Troops);
        return rightPanel;
    }

    /**
     * Creates the middle panel of the fortify popup.
     * @return JPanel
     */
    private JPanel createMiddlePanel(){
        JPanel middlePanel = new JPanel();
        GridLayout middleGrid = new GridLayout(3,1);
        middlePanel.setLayout(middleGrid);
        middlePanel.add(title);

        //Separate panel for buttons and troop number
        JPanel middleButtonPanel = new JPanel();
        GridLayout middleButtonGrid = new GridLayout(1,3);
        middleButtonPanel.setLayout(middleButtonGrid);
        middleButtonPanel.add(minusButton);
        middleButtonPanel.add(troops);
        middleButtonPanel.add(plusButton);

        middlePanel.add(middleButtonPanel);
        middlePanel.add(fortifyButton);
        return middlePanel;
    }

    /**
     * Returns the plusButton.
     * @return JButton
     */
    public JButton getPlusButton() {
        return plusButton;
    }

    /**
     * Returns the minusButton.
     * @return JButton
     */
    public JButton getMinusButton() {
        return minusButton;
    }

    /**
     * Returns the number of troops the player want to deploy.
     * @return JLabel
     */
    public JLabel getTroops(){
        return troops;
    }

    /**
     * Returns the fortifyButton.
     * @return JButton
     */
    public JButton getFortifyButton(){
        return fortifyButton;
    }

    /**
     * Returns the player of the territory.
     * @return Model.Player
     */
    public Player getPlayer(){
        return t1.getOccupant();
    }

    /**
     * Returns the territory the player wants to use to reinforce the other territory.
     * @return Model.Territory to lose troops
     */
    public Territory getTerritoryToLoseTroops(){
        return t1;
    }

    /**
     * Returns the territory that will be reinforced.
     * @return Model.Territory to gain troops
     */
    public Territory getTerritoryToGainTroops(){
        return t2;
    }

    /**
     * Updates the number of troops that the player wants to move.
     * @param x int
     */
    public void setTroops(int x){
        troops.setText(String.valueOf(x));
    }

    /**
     * Setter for the winningMove.
     * @param winningMove true if the attacker beat the defending territory, else false.
     */
    public void setWinningMove(boolean winningMove) { this.winningMove = winningMove; }

    /**
     * Getter for the winning move.
     * @return boolean true if the attacker won, false if not.
     */
    public boolean getWinningMove() { return winningMove; }
}
