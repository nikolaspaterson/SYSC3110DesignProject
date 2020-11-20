package View;

import javax.swing.*;

public class GameMenuBar extends JMenuBar {
    private JMenuItem save_load;
    private JMenuItem save;
    private JMenu load;

    public GameMenuBar(){
        save_load.setText("File");
        save.setText("Save");
        load.setText("Load");
    }

    public void addLoad(){
        load.removeAll();
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
