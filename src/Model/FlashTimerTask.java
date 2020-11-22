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
        if(i == 0){
            for(Territory temp : neighbours.values()){
                temp.setNeighbourColor(temp.getColor());
                temp.addColor(main_color);
            }
        } else if(i == 1){
            for(Territory temp : neighbours.values()){
                temp.addColor(temp.getNeighbourColor());
            }
        }
    }
}
