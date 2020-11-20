package Model;

import java.awt.*;
import java.util.HashMap;
import java.util.TimerTask;

public class FlashTimerTask extends TimerTask {

    private final int i;
    private final HashMap<String, Territory> neighbours;
    private final Color main_color;

    public FlashTimerTask(Color main_color, HashMap<String, Territory> neighbours, int i){
        this.i = i;
        this.neighbours = neighbours;
        this.main_color = main_color;
    }

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
