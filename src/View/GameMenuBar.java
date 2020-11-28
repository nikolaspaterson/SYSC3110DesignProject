
package View;

import Controller.SaveController;
import javax.swing.*;
import java.io.File;

public class GameMenuBar extends JMenuBar {

    private final JMenuItem save_load;
    private final JMenuItem save;
    private final JMenu load;
    private final SaveController controller;
    private final String outputPath;

    public GameMenuBar(SaveController controller,String path){
        save_load = new JMenu();
        save = new JMenuItem();
        this.controller = controller;
        load = new JMenu();

        outputPath = path;
        save_load.setText("File");
        save.setText("Save");
        load.setText("Load");
        loadAll();
        save.addActionListener(controller::saveFile);

        save_load.add(save);
        save_load.add(load);
        this.add(save_load);
    }

    public void loadAll(){
        try{
            System.out.println(outputPath);
            File file = new File(outputPath);
            File[] files = file.listFiles();
            for(File temp_file : files){
                if(temp_file.getName().contains(".json")){
                    addLoad(temp_file.getName());
                }

            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    public void addLoad(String new_save){
        JMenuItem temp_button = new JMenuItem(new_save);
        temp_button.addActionListener(controller::loadFile);
        load.add(temp_button);
    }

    public JMenuItem getSave_load() {
        return save_load;
    }

    public JMenuItem getSave() {
        return save;
    }

    public JMenu getLoad() {
        return load;
    }
}