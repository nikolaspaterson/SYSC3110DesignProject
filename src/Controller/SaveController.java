package Controller;

import Model.GameModel;
import View.GameMenuBar;
import View.GameView;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The SaveController class is used to save the game or load it to the GameView.
 */
public class SaveController implements ActionListener {

    private GameMenuBar menuBar;
    private final GameView gameView;
    private final String output_path;

    /**
     * Class constructor for SaveController class.
     *
     * @param gameView the game view
     * @param output_path the output path
     */
    public SaveController(GameView gameView,String output_path){
        this.gameView = gameView;
        this.output_path = output_path;
    }

    /**
     * This method is used to add a GameMenuBar
     *
     * @param menuBar the menu bar
     */
    public void addView(GameMenuBar menuBar){
        this.menuBar = menuBar;
    }

    /**
     * Checks to see which button is responsible for the ActionEvent and performs the respected action based on which button was pressed.
     *
     * @param event the action event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("Save")) {
            saveAction();
        } else {
            loadAction(event.getActionCommand());
        }
    }

    /**
     * This method is used to save the game to a JSON file.
     */
    private void saveAction() {
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

    /**
     * This method is used to load the game based on which previous saved game you select.
     *
     * @param actionCommand the filepath
     */
    private void loadAction(String actionCommand) {
        String path = output_path + actionCommand;
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
