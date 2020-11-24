
package View;

import javax.swing.*;

public class GameMenuBar extends JMenuBar {
    private JMenuItem save_load;
    private JMenuItem save;
    private JMenu load;

    public GameMenuBar(){
        save_load = new JMenu();
        save = new JMenuItem();
        load = new JMenu();

        save_load.setText("File");
        save.setText("Save");
        load.setText("Load");

        save_load.add(save);
        save_load.add(load);
        this.add(save_load);
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