import javax.swing.*;
import java.awt.*;

public class FortifyPopUp extends JPopupMenu {

    private Territory t1;
    private Territory t2;
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

        FortifyPopUpController controller = new FortifyPopUpController(this);

        JPanel fortifyPanel = new JPanel();
        setLayout(new GridLayout(0,1));

        title = new JLabel("FORTIFY");
        title.setHorizontalAlignment(title.CENTER);
        title.setFont(new Font("Impact", Font.PLAIN,35));

        t1Name = new JLabel();
        t1Name.setHorizontalAlignment(t1.CENTER);
        t1Name.setText(t1.getTerritoryName());
        t1Name.setFont(new Font("Impact", Font.PLAIN,20));

        t2Name = new JLabel();
        t2Name.setHorizontalAlignment(t2.CENTER);
        t2Name.setText(t2.getTerritoryName());
        t2Name.setFont(new Font("Impact", Font.PLAIN,20));

        t1Troops = new JLabel();
        t1Troops.setHorizontalAlignment(t1Troops.CENTER);
        t1Troops.setText(String.valueOf(t1.getTroops()));
        t1Troops.setFont(new Font("Impact", Font.PLAIN,20));

        t2Troops = new JLabel();
        t2Troops.setHorizontalAlignment(t2Troops.CENTER);
        t2Troops.setText(String.valueOf(t2.getTroops()));
        t2Troops.setFont(new Font("Impact", Font.PLAIN,20));

        minusButton = new JButton("-");
        minusButton.setFont(new Font("Impact", Font.PLAIN,12));
        plusButton = new JButton("+");
        plusButton.setFont(new Font("Impact", Font.PLAIN,12));

        fortifyButton = new JButton("DEPLOY");
        fortifyButton.setFont(new Font("Impact", Font.PLAIN,35));

        troops = new JLabel("1");
        troops.setFont(new Font("Impact", Font.PLAIN,20));

        minusButton.addActionListener(controller);
        plusButton.addActionListener(controller);
        fortifyButton.addActionListener(controller);

        JPanel leftPanel = new JPanel();
        BorderLayout leftBorderLayout = new BorderLayout();
        leftPanel.setLayout(leftBorderLayout);
        leftPanel.add(t1Name, BorderLayout.NORTH);
        leftPanel.add(t1Troops, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel();
        BorderLayout RightBorderLayout = new BorderLayout();
        rightPanel.setLayout(RightBorderLayout);
        rightPanel.add(t2Name, BorderLayout.NORTH);
        rightPanel.add(t2Troops, BorderLayout.SOUTH);

        JPanel middlePanel = new JPanel();
        BorderLayout middleBorderLayout = new BorderLayout();
        middlePanel.setLayout(middleBorderLayout);
        middlePanel.add(title, BorderLayout.NORTH);

        JPanel middleButtonPanel = new JPanel();
        BorderLayout middleButtonLayout = new BorderLayout();
        middleButtonPanel.setLayout(middleButtonLayout);
        middleButtonPanel.add(minusButton, BorderLayout.WEST);
        middleButtonPanel.add(troops, BorderLayout.CENTER);
        middleButtonPanel.add(plusButton, BorderLayout.EAST);

        middlePanel.add(middleButtonPanel, BorderLayout.CENTER);
        middlePanel.add(fortifyButton, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.WEST);
        add(middlePanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        setVisible(true);

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

    public void setTroops(int x){
        troops.setText(String.valueOf(x));
    }



}
