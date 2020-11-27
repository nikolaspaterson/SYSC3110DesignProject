package Model;

import java.awt.*;
import java.util.HashMap;
import java.util.TimerTask;

/**
 * This class is used to add the flashing effect of neighbouring enemy territories in order to make attacking easier.
 */
public class FlashTimerTask extends TimerTask {

    private final int i;
    private final HashMap<String, Territory> neighbours;
    private final Color main_color;
    private final static int FLASHING_PLAYER_COLOR = 0;
    private final static int FLASHING_NEIGHBOUR_COLOR = 1;

    /**
     * Constructor for the FlashTimerTask class.
     *
     * @param main_color the color of your territory
     * @param neighbours the color of neighbouring territories
     * @param i integer value
     */
    public FlashTimerTask(Color main_color, HashMap<String, Territory> neighbours, int i){
        this.i = i;
        this.neighbours = neighbours;
        this.main_color = main_color;
    }

    /**
     * This method is used to configure the flashing of Territories accordingly.
     */
    @Override
    public void run(){
        switch (i) {
            case FLASHING_PLAYER_COLOR:
                for(Territory temp : neighbours.values()){
                    temp.setNeighbourColor(temp.getColor());
                    temp.addColor(main_color);
                }
                break;

            case FLASHING_NEIGHBOUR_COLOR:
                for(Territory temp : neighbours.values()){
                    temp.addColor(temp.getNeighbourColor());
                }
                break;
        }
    }
}
