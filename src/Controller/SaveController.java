package Controller;

import Model.GameModel;
import View.GameMenuBar;
import View.GameView;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveController {

    private final GameModel gameModel;
    private GameMenuBar menuBar;
    private final GameView gameView;
    private String output_path;

    public SaveController(GameModel gameModel,GameView gameView,String output_path){
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.output_path = output_path;

    }
    public void addView(GameMenuBar menuBar){
        this.menuBar = menuBar;
    }

    public void saveFile(ActionEvent event){
        try {
            JSONObject save_file = gameView.getModel().saveJSON();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-HH-mm-ss");
            Date new_date = new Date();
            FileWriter writer = new FileWriter(output_path + save_file.get("GameName") + dateFormat.format(new_date)+".json");
            String value = save_file.toString();
            menuBar.addLoad(save_file.get("GameName") + dateFormat.format(new_date)+".json");
            writer.write(value);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void loadFile(ActionEvent event){
        String path = output_path + event.getActionCommand();
        System.out.println(path);
        try {
            File json_file = new File(path);
            FileReader load_file = new FileReader(json_file);
            JSONParser parser = new JSONParser();
            JSONObject loadedJSON = (JSONObject) parser.parse(load_file);
            gameView.getModel().stopAITimer();
            gameView.newGameModel(new GameModel(loadedJSON, gameView.getModel()));
        }catch (Exception e){
            JOptionPane.showMessageDialog(gameView, "ERROR! SAVE FILE CORRUPTED!");
            e.printStackTrace();
        }
    }

}
