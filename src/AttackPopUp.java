import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class AttackPopUp extends JPopupMenu {

    private Territory attackingTerritory;
    private Territory defendingTerritory;
    private JButton minus;
    private JLabel numDice;
    private JButton plus;
    private JButton attackBOTTOM;
    private JLabel attackerLabel;
    private JLabel defenderLabel;
    private JLabel outcome;
    private JLabel attackingLabel;
    private JLabel defendingLabel;
    private JFrame gameViewRef;

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

        attackBOTTOM = new JButton("ATTACK");
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

        JPanel leftPanel = playerPanel(attackingTerritory.getOccupant().getplayer_icon(), "left");

        JPanel rightPanel = playerPanel(defendingTerritory.getOccupant().getplayer_icon(), "right");

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

    public JFrame getGameViewRef() { return gameViewRef; }

    public JButton getAttackBOTTOM() {
        return attackBOTTOM;
    }

    public JButton getMinus() {
        return minus;
    }

    public JLabel getNumDice() {
        return numDice;
    }

    public JButton getPlus() {
        return plus;
    }

    public Territory getAttackingTerritory() {
        return attackingTerritory;
    }

    public Territory getDefendingTerritory() {
        return defendingTerritory;
    }

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
