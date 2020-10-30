import javax.swing.*;
import java.awt.*;

public class ReinforcePopUp extends JPopupMenu {

    private Territory selectedTerritory;
    private JButton minusButton;
    private JButton plusButton;
    private JButton deployButton;
    private JLabel title;
    private JLabel territoryName;
    private JLabel photo;
    private JLabel troops;

    public ReinforcePopUp(Territory t){
        super();
        this.selectedTerritory = t;
        initializeComponents();

        setLayout(new GridLayout(1,3));

        createLeftPanel();

        add(createMiddlePanel());
        add(createRightPanel());
        setVisible(true);
    }

    private void initializeComponents(){
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
        photo.setIcon(getPlayer().getplayer_icon());
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

    private void createLeftPanel(){
        add(photo);
    }

    private JPanel createMiddlePanel(){

        JPanel middlePanel = new JPanel();

        GridLayout middleLayout = new GridLayout(3,1);
        middlePanel.setLayout(middleLayout);
        middlePanel.add(title);
        middlePanel.add(territoryName);

        JPanel troopPanel = new JPanel();
        GridLayout buttonGrid = new GridLayout(0,3);
        troopPanel.setLayout(buttonGrid);
        troopPanel.add(minusButton);
        troopPanel.add(troops);
        troopPanel.add(plusButton);

        middlePanel.add(troopPanel);
        return middlePanel;
    }

    private JPanel createRightPanel(){
        JPanel rightPanel = new JPanel();
        GridLayout rightGrid = new GridLayout(3,1);
        rightPanel.setLayout(rightGrid);
        rightPanel.add(new JLabel());
        rightPanel.add(deployButton);
        rightPanel.add(new JLabel());
        return rightPanel;
    }

    public JLabel getTroops(){
        return troops;
    }

    public void setTroops(int x){
        troops.setText(String.valueOf(x));
    }

    public JButton getPlus(){
        return plusButton;
    }

    public JButton getMinus(){
        return minusButton;
    }

    public JButton getDeployButton(){
        return deployButton;
    }

    public Territory getTerritory(){
        return selectedTerritory;
    }

    public Player getPlayer(){
        return selectedTerritory.getOccupant();
    }

}
