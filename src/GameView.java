import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameView extends JFrame {
    private final ArrayList<Player> playerList;

    public GameView() throws IOException {
        super("Risk!");
        playerList = new ArrayList<>();
        playerList.add(new Player("Steve", new Color(153, 221, 255)));
        playerList.add(new Player("Bob", new Color(255, 153, 128)));


        this.setSize(1280,814);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        this.setMinimumSize(new Dimension(1280,814));
        JPanel background;
        BufferedImage image = ImageIO.read(getClass().getResourceAsStream("Map.png"));
        background = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(image,0,0,this);
                System.out.println("I tried");
            }
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(image.getWidth(), image.getHeight());
            }
        };
        add(background);
        setContentPane(background);
        background.setLayout(null);
        setLocationRelativeTo(null);
        GameSetup test_button_load = new GameSetup(playerList,background);
        for(Territory x : test_button_load.returnWorldMap().values()){
            background.add(x);
        }
        this.setVisible(true);
    }


    public static void main(String[] args) {
        try{
            new GameView();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
}
