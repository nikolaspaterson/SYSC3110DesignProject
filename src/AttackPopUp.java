import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttackPopUp extends JFrame {

    private Territory attackingTerritory, defendingTerritory;

    public AttackPopUp() {
        super();
        setSize(800, 250);
        setMinimumSize(new Dimension(600,150));
        setMaximumSize(new Dimension(600,150));
        Border darkLine = BorderFactory.createLineBorder(Color.black,3); // add this when the actual Game becomes the JFrame

        ////////////////////////
        Player attacker = new Player("Chizzy", Color.BLACK, new ImageIcon("/resources/Chizzy.png"));
        Player defender = new Player("Nips", Color.BLACK, new ImageIcon("/resources/Chizzy.png"));

        Territory attackingTerritory = new Territory("Jamaica", 0, 0, 0,0, null);
        attackingTerritory.setOccupant(attacker);
        attackingTerritory.setTroops(5);

        Territory defendingTerritory = new Territory("Somalia", 0, 0, 0,0, null);
        defendingTerritory.setOccupant(defender);
        defendingTerritory.setTroops(4);

        setAttackingTerritory(attackingTerritory);
        setDefendingTerritory(defendingTerritory);
        ////////////////////////

        JPanel jp = new JPanel();

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout(0, 10));

        JLabel attackTOP = new JLabel("DICE ROLL");
        attackTOP.setFont(new Font("Comic Sans MS", Font.ITALIC, 45));
        attackTOP.setHorizontalAlignment(attackTOP.CENTER);
        attackTOP.setVerticalAlignment(attackTOP.CENTER);

        JPanel selectTroops = new JPanel(new BorderLayout(1, 0));

        JButton minus = new JButton("-");
        minus.setFont(new Font("Impact", Font.BOLD, 25));
        selectTroops.add(minus, BorderLayout.WEST);

        JLabel numTroops = new JLabel("1");
        numTroops.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        numTroops.setHorizontalAlignment(numTroops.CENTER);
        numTroops.setVerticalAlignment(numTroops.CENTER);
        selectTroops.add(numTroops, BorderLayout.CENTER);

        JButton plus = new JButton("+");
        plus.setFont(new Font("Impact", Font.BOLD, 25));
        selectTroops.add(plus, BorderLayout.EAST);

        JButton attackBOTTOM = new JButton("ATTACK");
        attackBOTTOM.setFont(new Font("Impact", Font.PLAIN, 40));
        attackBOTTOM.setBackground(new Color(217, 61, 62));

        middlePanel.add(attackTOP, BorderLayout.NORTH);
        middlePanel.add(selectTroops, BorderLayout.CENTER);
        middlePanel.add(attackBOTTOM, BorderLayout.SOUTH);

        jp.add(middlePanel);


        ///////////////////////////////

        JPanel leftPanel = playerPanel("/resources/Chizzy.png", "left");

        ///////////////////////////////

        JPanel rightPanel = playerPanel("/resources/Chizzy.png", "right");


        //////////////////////////////////////


        attackBOTTOM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame result = new JFrame();
                result.setSize(400, 200);
                result.setLayout(new GridLayout(4, 1));

                JLabel resultLabel = new JLabel("RESULT");
                resultLabel.setFont(new Font("Impact", Font.BOLD, 35));
                resultLabel.setHorizontalAlignment(resultLabel.CENTER);
                resultLabel.setVerticalAlignment(resultLabel.CENTER);

                JLabel attackerLabel = new JLabel(attackingTerritory.getOccupant().getName() + " ROLLED: (ROLLS)");
                attackerLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
                attackerLabel.setHorizontalAlignment(attackerLabel.CENTER);
                attackerLabel.setVerticalAlignment(attackerLabel.CENTER);

                JLabel defenderLabel = new JLabel(defendingTerritory.getOccupant().getName() + " ROLLED: (ROLLS)");
                defenderLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
                defenderLabel.setHorizontalAlignment(defenderLabel.CENTER);
                defenderLabel.setVerticalAlignment(defenderLabel.CENTER);

                JLabel outcome = new JLabel("RESULT OF ROLLS");
                outcome.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
                outcome.setHorizontalAlignment(outcome.CENTER);
                outcome.setVerticalAlignment(outcome.CENTER);

                result.add(resultLabel);
                result.add(attackerLabel);
                result.add(defenderLabel);
                result.add(outcome);

                result.setDefaultCloseOperation(EXIT_ON_CLOSE);
                result.setVisible(true);
            }
        });

        minus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = Integer.parseInt(numTroops.getText());
                if(x == 1) {
                    x = 3;
                } else {
                    x--;
                }
                numTroops.setText(x + "");
            }
        });

        plus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = Integer.parseInt(numTroops.getText());
                if(x == 3) {
                    x = 1;
                } else {
                    x++;
                }
                numTroops.setText(x + "");
            }
        });

        add(leftPanel, BorderLayout.WEST);
        add(jp, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private ImageIcon scaleImage(String filename) {
        ImageIcon scaledImg = new ImageIcon(getClass().getResource(filename));
        Image img = scaledImg.getImage().getScaledInstance( 100, 100,  java.awt.Image.SCALE_SMOOTH );
        scaledImg = new ImageIcon(img);
        return scaledImg;
    }

    private void setAttackingTerritory(Territory attackingTerritory) { this.attackingTerritory = attackingTerritory; }

    private void setDefendingTerritory(Territory defendingTerritory) { this.defendingTerritory = defendingTerritory; }


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

    public static void main(String[] args) {
        AttackPopUp attackPopUp = new AttackPopUp();
    }
}
