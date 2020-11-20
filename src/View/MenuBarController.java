package View;

import java.awt.event.ActionEvent;

public class MenuBarController {
    private GameView model;
    private GameMenuBar menuBar;

    public MenuBarController(GameView model,GameMenuBar menuBar){
        this.model = model;
        this.menuBar = menuBar;
        menuBar.getSave().addActionListener(this::saveJSON);
    }

    public void saveJSON(ActionEvent event){


    }
    public void loadJSON(ActionEvent event){

    }
}
