package View;

import Controller.AttackPopUpController;
import Model.Player;
import Model.Territory;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * The View.AttackPopUp class is used for when a player has selected which territory they want to attack.
 * It asks the user how many dice they want to roll with based on the number of troops in their territory.
 *
 * @author Ahmad El-Sammak
 */
public class AttackPopUp extends JPopupMenu {

    private final Territory attackingTerritory;
    private final Territory defendingTerritory;
    private final JButton minus;
    private final JLabel numDice;
    private final JButton plus;
    private final JLabel attackerLabel;
    private final JLabel defenderLabel;
    private final JLabel outcome;
    private JLabel attackingLabel;
    private JLabel defendingLabel;
    private final JFrame gameViewRef;

    /**
     * Class constructor for View.AttackPopUp class.
     * @param attackingTerritory the attacker's territory object.
     * @param defendingTerritory the defender's territory object.
     * @param gameViewRef a reference to the main game's frame.
     */
    public AttackPopUp(Territory attackingTerritory, Territory defendingTerritory, JFrame gameViewRef) {
        super();
        setLayout(new GridLayout(0, 1));
        Border darkLine = BorderFactory.createLineBorder(Color.black,3);
        setBorder(darkLine);

        this.gameViewRef = gameViewRef;
        this.attackingTerritory = attackingTerritory;
        this.defendingTerritory = defendingTerritory;

        JPanel jPanel = new JPanel(new FlowLayout());
        JPanel jp = new JPanel();

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout(0, 10));

        JLabel attackTOP = new JLabel("DICE ROLL");
        attackTOP.setFont(new Font("Comic Sans MS", Font.ITALIC, 45));
        attackTOP.setHorizontalAlignment(attackTOP.CENTER);
        attackTOP.setVerticalAlignment(attackTOP.CENTER);

        JPanel selectTroops = new JPanel(new BorderLayout(1, 0));

        minus = new JButton("-");
        minus.setFont(new Font("Impact", Font.BOLD, 25));
        selectTroops.add(minus, BorderLayout.WEST);

        numDice = new JLabel("1");
        numDice.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        numDice.setHorizontalAlignment(numDice.CENTER);
        numDice.setVerticalAlignment(numDice.CENTER);
        selectTroops.add(numDice, BorderLayout.CENTER);

        plus = new JButton("+");
        plus.setFont(new Font("Impact", Font.BOLD, 25));
        selectTroops.add(plus, BorderLayout.EAST);

        JButton attackBOTTOM = new JButton("ATTACK");
        attackBOTTOM.setFont(new Font("Impact", Font.PLAIN, 40));
        attackBOTTOM.setBackground(new Color(217, 61, 62));

        middlePanel.add(attackTOP, BorderLayout.NORTH);
        middlePanel.add(selectTroops, BorderLayout.CENTER);
        middlePanel.add(attackBOTTOM, BorderLayout.SOUTH);

        jp.add(middlePanel);

        JPanel result = new JPanel();
        result.setLayout(new GridLayout(4, 1));

        JLabel resultLabel = new JLabel("RESULT");
        resultLabel.setFont(new Font("Impact", Font.BOLD, 35));
        resultLabel.setHorizontalAlignment(resultLabel.CENTER);
        resultLabel.setVerticalAlignment(resultLabel.CENTER);

        attackerLabel = new JLabel(attackingTerritory.getOccupant().getName() + " ROLLED: (ROLLS)");
        attackerLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
        attackerLabel.setHorizontalAlignment(attackerLabel.CENTER);
        attackerLabel.setVerticalAlignment(attackerLabel.CENTER);

        defenderLabel = new JLabel(defendingTerritory.getOccupant().getName() + " ROLLED: (ROLLS)");
        defenderLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
        defenderLabel.setHorizontalAlignment(defenderLabel.CENTER);
        defenderLabel.setVerticalAlignment(defenderLabel.CENTER);

        outcome = new JLabel("RESULT OF ROLLS");
        outcome.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
        outcome.setHorizontalAlignment(outcome.CENTER);
        outcome.setVerticalAlignment(outcome.CENTER);

        result.add(resultLabel);
        result.add(attackerLabel);
        result.add(defenderLabel);
        result.add(outcome);

        Player attacker = attackingTerritory.getOccupant();
        Player defender = defendingTerritory.getOccupant();

        PlayerView attackingView = attacker.getPlayerView();
        PlayerView defendingView = defender.getPlayerView();

        JPanel leftPanel = playerPanel(attackingView.getplayer_icon(), "left");
        JPanel rightPanel = playerPanel(defendingView.getplayer_icon(), "right");

        AttackPopUpController attackPopUpController = new AttackPopUpController(this);

        attackBOTTOM.addActionListener(attackPopUpController);
        minus.addActionListener(attackPopUpController);
        plus.addActionListener(attackPopUpController);

        jPanel.add(leftPanel, BorderLayout.WEST);
        jPanel.add(jp, BorderLayout.CENTER);
        jPanel.add(rightPanel, BorderLayout.EAST);

        add(jPanel);
        add(result);
        setVisible(true);
    }

    /**
     * This method is used to create the player panels for either the attacking player or defending player.
     * @param image the player's image
     * @param side the side, i.e. "left" is for the attacker, "right" is for defender.
     * @return JPanel the new playerPanel.
     */
    private JPanel playerPanel(Icon image, String side) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        JLabel photo = new JLabel();
        photo.setIcon(image);

        if(side.equals("left")) {
            attackingLabel = new JLabel("<html>" + attackingTerritory.getTerritoryName() + "<br>Troops: " + attackingTerritory.getTroops() +"</html>");
            attackingLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
            attackingLabel.setForeground(new Color(0, 0, 0));
            panel.add(photo, BorderLayout.WEST);
            panel.add(attackingLabel, BorderLayout.EAST);
        } else {
            defendingLabel = new JLabel("<html>" + defendingTerritory.getTerritoryName() + "<br>Troops: " + defendingTerritory.getTroops() +"</html>");
            defendingLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
            defendingLabel.setForeground(new Color(0, 0, 0));
            panel.add(photo, BorderLayout.EAST);
            panel.add(defendingLabel, BorderLayout.WEST);
        }
        return panel;
    }

    /**
     * Gets the reference to the main game's JFrame.
     * @return JFrame
     */
    public JFrame getGameViewRef() { return gameViewRef; }

    /**
     * Gets the minus button.
     * @return JButton
     */
    public JButton getMinus() {
        return minus;
    }

    /**
     * Gets the JLabel which specified the number of dice to roll with.
     * @return JLabel
     */
    public JLabel getNumDice() {
        return numDice;
    }

    /**
     * Gets the plus button.
     * @return JButton
     */
    public JButton getPlus() {
        return plus;
    }

    /**
     * Gets the attacking territory.
     * @return Model.Territory attacking territory
     */
    public Territory getAttackingTerritory() {
        return attackingTerritory;
    }

    /**
     * Gets the defending territory.
     * @return Model.Territory defending territory
     */
    public Territory getDefendingTerritory() {
        return defendingTerritory;
    }

    /**
     * This method is used to refresh the JLabels after the outcome of an attack.
     * @param attackerRolls the attacker's dice rolls
     * @param defenderRolls the defender's dice rolls
     * @param outcome the outcome of the attack.
     */
    public void refreshLabels(String attackerRolls, String defenderRolls, String outcome) {
        attackerLabel.setText(attackingTerritory.getOccupant().getName() + " ROLLED: " + attackerRolls);
        defenderLabel.setText(defendingTerritory.getOccupant().getName() + " ROLLED: " + defenderRolls);
        this.outcome.setText(outcome);
        attackingLabel.setText("<html>" + attackingTerritory.getTerritoryName() + "<br>Troops: " + attackingTerritory.getTroops() +"</html>");
        defendingLabel.setText("<html>" + defendingTerritory.getTerritoryName() + "<br>Troops: " + defendingTerritory.getTroops() +"</html>");
        numDice.setText("1");
        if (attackingTerritory.getTroops() == 1) {
            numDice.setText("0");
        }
    }
}
