package View;

import Model.Player;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class WinningScreenFrame extends JFrame {

    public WinningScreenFrame(Player currentPlayer, GameView gameView) {
        gameView.dispose();
        gameView.stopMusic();
        JFrame winnerFrame = new JFrame("WINNER!");
        winnerFrame.setSize(650, 450);
        winnerFrame.setLocation(400,400);
        winnerFrame.setLayout(new BorderLayout());

        JPanel playerPanel = new JPanel(new FlowLayout());

        JPanel playerIconName = new JPanel(new BorderLayout());

        JLabel name = new JLabel(currentPlayer.getName());
        name.setFont(new Font("Impact", Font.PLAIN, 30));
        name.setHorizontalAlignment(name.CENTER);
        name.setVerticalAlignment(name.CENTER);

        JLabel playerIcon = new JLabel();

        ImageIcon a = (ImageIcon) currentPlayer.getPlayer_icon();
        Image i = a.getImage().getScaledInstance( 150, 150,  java.awt.Image.SCALE_SMOOTH );
        a = new ImageIcon(i);
        playerIcon.setIcon(a);

        playerIconName.add(playerIcon, BorderLayout.NORTH);
        playerIconName.add(name, BorderLayout.SOUTH);

        JLabel confettiLeft = new JLabel();
        ImageIcon b = new ImageIcon(getClass().getResource("/resources/confetti.png"));
        Image m = b.getImage().getScaledInstance( 200, 200,  java.awt.Image.SCALE_SMOOTH );
        b = new ImageIcon(m);
        confettiLeft.setIcon(b);

        JLabel confettiRight = new JLabel();
        ImageIcon img1 = new ImageIcon(getClass().getResource("/resources/confettiflip.png"));
        Image m1 = img1.getImage().getScaledInstance( 200, 200,  java.awt.Image.SCALE_SMOOTH );
        img1 = new ImageIcon(m1);
        confettiRight.setIcon(img1);

        playerPanel.add(confettiLeft, FlowLayout.LEFT);
        playerPanel.add(playerIconName, FlowLayout.CENTER);
        playerPanel.add(confettiRight, FlowLayout.RIGHT);

        JLabel winnerText = new JLabel("Winner Winner Chicken Dinner!");
        winnerText.setFont(new Font("Comic SANS MS", Font.ITALIC, 35));
        winnerText.setHorizontalAlignment(winnerText.CENTER);
        winnerText.setVerticalAlignment(winnerText.CENTER);

        JLabel crown = new JLabel();
        ImageIcon c = new ImageIcon(getClass().getResource("/resources/crown1.gif"));
        crown.setIcon(c);
        crown.setHorizontalAlignment(crown.CENTER);
        crown.setVerticalAlignment(crown.CENTER);

        winnerFrame.add(winnerText, BorderLayout.NORTH);
        winnerFrame.add(crown, BorderLayout.CENTER);
        winnerFrame.add(playerPanel, BorderLayout.SOUTH);

        gameView.playMusic("/resources/allido.wav");

        winnerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        winnerFrame.setVisible(true);
    }
}
