import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class StartUpController implements ActionListener {
    private StartUpView startUpView;

    public StartUpController(StartUpView startUp){
        this.startUpView = startUp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("How to Play")){

            //Create frame and panel
            JFrame popUpFrame = new JFrame();
            JPanel howtoPanel = new JPanel();
            howtoPanel.setBorder(BorderFactory.createEmptyBorder(100,100,300,300));
            howtoPanel.setLayout(new BoxLayout(howtoPanel, BoxLayout.Y_AXIS));
            popUpFrame.add(howtoPanel, BorderLayout.CENTER);

            //add labels to panel
            JLabel temp = new JLabel("TEST");
            JLabel temp2 = new JLabel("Turn steps");
            howtoPanel.add(temp);
            howtoPanel.add(temp2);

            popUpFrame.setTitle("Tutorial");
            popUpFrame.pack();
            popUpFrame.setVisible(true);

            //hide panel if not in focus
            popUpFrame.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    popUpFrame.setVisible(true);
                }

                @Override
                public void focusLost(FocusEvent e) {
                    popUpFrame.dispose();
                }
            });
        }else if(e.getActionCommand().equals("Play")){
            startUpView.dispose();
            PlayerSelectView playerSelect = new PlayerSelectView();
        }
    }
}
