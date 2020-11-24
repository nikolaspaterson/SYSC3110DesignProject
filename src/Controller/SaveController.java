package Controller;

import Model.AIPlayer;
import Model.GameModel;
import Model.GameState;
import Model.Territory;
import View.*;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventObject;

public class SaveController {
    private final GameModel gameModel;
    private final GameMenuBar menuBar;
    private String output_path;

    public SaveController(GameModel gameModel, GameMenuBar menuBar,String output_path){
        this.gameModel = gameModel;
        this.menuBar = menuBar;
        this.output_path = output_path;
        menuBar.getSave().addActionListener(this::saveFile);
    }

    public void saveFile(ActionEvent event){
        try {
            JSONObject save_file = gameModel.saveJSON();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-HH-mm-ss");
            Date new_date = new Date();
            FileWriter writer = new FileWriter(output_path + save_file.get("GameName") + dateFormat.format(new_date)+".json");
            String value = save_file.toString();
            writer.write(value);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
