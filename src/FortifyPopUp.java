import javax.swing.*;
import java.awt.*;

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

    public FortifyPopUp(Territory territory1, Territory territory2){
        super();
        this.t1=territory1;
        this.t2=territory2;

        initializeComponents();

        setLayout(new GridLayout(0,3));

        add(createLeftPanel());
        add(createMiddlePanel());
        add(createRightPanel());

        setVisible(true);
    }

    private void initializeComponents(){

        FortifyPopUpController controller = new FortifyPopUpController(this);

        title = new JLabel("FORTIFY");
        title.setHorizontalAlignment(title.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.ITALIC,35));

        t1Name = new JLabel();
        t1Name.setHorizontalAlignment(t1.CENTER);
        t1Name.setText(t1.getTerritoryName());
        t1Name.setFont(new Font("Comic Sans MS", Font.ITALIC,20));

        t2Name = new JLabel();
        t2Name.setHorizontalAlignment(t2.CENTER);
        t2Name.setText(t2.getTerritoryName());
        t2Name.setFont(new Font("Comic Sans MS", Font.ITALIC,20));

        t1Troops = new JLabel();
        t1Troops.setHorizontalAlignment(t1Troops.CENTER);
        t1Troops.setText(("Troops: ") + String.valueOf(t1.getTroops()));
        t1Troops.setFont(new Font("Comic Sans MS", Font.ITALIC,20));

        t2Troops = new JLabel();
        t2Troops.setHorizontalAlignment(t2Troops.CENTER);
        t2Troops.setText(("Troops: ") + String.valueOf(t2.getTroops()));
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

    private JPanel createLeftPanel(){
        JPanel leftPanel = new JPanel();
        GridLayout leftGrid = new GridLayout(2,1);
        leftPanel.setLayout(leftGrid);
        leftPanel.add(t1Name);
        leftPanel.add(t1Troops);
        return leftPanel;
    }

    private JPanel createRightPanel(){
        JPanel rightPanel = new JPanel();
        GridLayout rightGrid = new GridLayout(2,1);
        rightPanel.setLayout(rightGrid);
        rightPanel.add(t2Name);
        rightPanel.add(t2Troops);
        return rightPanel;
    }

    private JPanel createMiddlePanel(){
        JPanel middlePanel = new JPanel();
        GridLayout middleGrid = new GridLayout(3,1);
        middlePanel.setLayout(middleGrid);
        middlePanel.add(title);

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

    public JButton getPlusButton() {
        return plusButton;
    }

    public JButton getMinusButton() {
        return minusButton;
    }

    public JLabel getTroops(){
        return troops;
    }

    public JButton getFortifyButton(){
        return fortifyButton;
    }

    public Player getPlayer(){
        return t1.getOccupant();
    }

    public Territory getTerritoryToLoseTroops(){
        return t1;
    }

    public Territory getTerritoryToGainTroops(){
        return t2;
    }

    public void setTroops(int x){
        troops.setText(String.valueOf(x));
    }



}
