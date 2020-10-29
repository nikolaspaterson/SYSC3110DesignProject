import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttackPopUp extends JPopupMenu {

    private Territory attackingTerritory;
    private Territory defendingTerritory;
    private JButton minus;
    private JLabel numTroops;
    private JButton plus;
    private JButton attackBOTTOM;
    private JLabel attackerLabel;
    private JLabel defenderLabel;
    private JLabel outcome;

    public AttackPopUp(Territory attackingTerritory, Territory defendingTerritory) {
        super();
        setLayout(new GridLayout(0, 1));
        Border darkLine = BorderFactory.createLineBorder(Color.black,3); // add this when the actual Game becomes the JFrame
        setBorder(darkLine);
        ////////////////////////
        JPanel jPanel = new JPanel(new FlowLayout());

        ////////////////////////
        this.attackingTerritory = attackingTerritory;
        this.defendingTerritory = defendingTerritory;
        ////////////////////////

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

        numTroops = new JLabel("1");
        numTroops.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        numTroops.setHorizontalAlignment(numTroops.CENTER);
        numTroops.setVerticalAlignment(numTroops.CENTER);
        selectTroops.add(numTroops, BorderLayout.CENTER);

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


        //////////////////////////////


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

        ///////////////////////////////

        JPanel leftPanel = playerPanel("/resources/Chizzy.png", "left");

        ///////////////////////////////

        JPanel rightPanel = playerPanel("/resources/Chizzy.png", "right");


        //////////////////////////////////////


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

    private ImageIcon scaleImage(String filename) {
        ImageIcon scaledImg = new ImageIcon(getClass().getResource(filename));
        Image img = scaledImg.getImage().getScaledInstance( 100, 100,  java.awt.Image.SCALE_SMOOTH );
        scaledImg = new ImageIcon(img);
        return scaledImg;
    }

    private JPanel playerPanel(String filename, String side) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));

        JLabel photo = new JLabel();
        photo.setIcon(scaleImage(filename));

        JLabel T;

        if(side.equals("left")) {
            T = new JLabel("<html>" + attackingTerritory.getTerritoryName() + "<br>Troops: " + attackingTerritory.getTroops() +"</html>");
            panel.add(photo, BorderLayout.WEST);
            panel.add(T, BorderLayout.EAST);
        } else {
            T = new JLabel("<html>" + defendingTerritory.getTerritoryName() + "<br>Troops: " + defendingTerritory.getTroops() +"</html>");
            panel.add(photo, BorderLayout.EAST);
            panel.add(T, BorderLayout.WEST);
        }

        T.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        T.setForeground(new Color(0, 0, 0));

        return panel;
    }

    public JButton getAttackBOTTOM() {
        return attackBOTTOM;
    }

    public JButton getMinus() {
        return minus;
    }

    public JLabel getNumTroops() {
        return numTroops;
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

    public void refreshLabels() {
        attackerLabel.setText(attackingTerritory.getOccupant().getName() + " ROLLED: (ROLL OUTCOME)");
        defenderLabel.setText(defendingTerritory.getOccupant().getName() + " ROLLED: (ROLL OUTCOME)");
        //outcome.setText();
    }


}
