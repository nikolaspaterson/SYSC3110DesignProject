package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class is used to set up the background panel for the GameView.
 */
public class BackgroundPanel extends JPanel {

    private final BufferedImage image;

    /**
     * Constructor for the BackgroundPanel class.
     * @param image the image to add
     */
    public BackgroundPanel(BufferedImage image) {
        this.image = image;
    }

    /**
     * Used to paint the image.
     * @param g Graphics
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image,0,0,this);
    }

    /**
     * Used to get the preferred size.
     * @return Dimension the size
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }
}
