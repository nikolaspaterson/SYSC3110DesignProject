
package View;

import Controller.SaveController;
import Event.SaveEvent;
import Listener.SaveListener;
import javax.swing.*;
import java.io.File;

/**
 * The GameMenuBar class is used to display the Save and Loading buttons on top of the main GameView.
 */
public class GameMenuBar extends JMenuBar implements SaveListener {

    private final JMenuItem save;
    private final JMenu load;
    private final SaveController controller;
    private final String outputPath;

    /**
     * Constructor for the GameMenuBar class.
     * @param controller the controller
     * @param path the path to save
     */
    public GameMenuBar(SaveController controller, String path){
        JMenuItem save_load = new JMenu();
        save = new JMenuItem();
        this.controller = controller;
        load = new JMenu();

        outputPath = path;
        save_load.setText("File");
        save.setText("Save");
        load.setText("Load");
        loadAll();
        save.addActionListener(this.controller);

        save_load.add(save);
        save_load.add(load);
        this.add(save_load);
    }

    /**
     * This method is used to display all saved files in the load menu.
     */
    public void loadAll(){
        try{
            System.out.println(outputPath);
            File file = new File(outputPath);
            File[] files = file.listFiles();
            if (files != null) {
                for(File temp_file : files){
                    if(temp_file.getName().contains(".json")){
                        addLoad(temp_file.getName());
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    /**
     * This method is used to add a singular saved file in the load menu.
     * @param new_save newSave string
     */
    public void addLoad(String new_save){
        JMenuItem temp_button = new JMenuItem(new_save);
        temp_button.addActionListener(this.controller);
        load.add(temp_button);
    }

    public JMenuItem getSave() {
        return save;
    }

    @Override
    public void updateSave(SaveEvent e) {
        addLoad(e.getSaveName());
    }
}