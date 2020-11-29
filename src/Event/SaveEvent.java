package Event;

import Model.GameModel;
import java.util.EventObject;

public class SaveEvent extends EventObject {

    private String saveName;

    /**
     * Constructs a prototypical Event.
     * @param model Gamemodel
     * @param saveName name of file
     */
    public SaveEvent(GameModel model, String saveName) {
        super(model);
        this.saveName = saveName;
    }

    public String getSaveName(){
        return saveName;
    }
}
