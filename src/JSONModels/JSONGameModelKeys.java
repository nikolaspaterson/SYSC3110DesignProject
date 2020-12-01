package JSONModels;

/**
 * This class is used to represent the different Game Model JSON keys that are used in the saving and loading of the JSON file.
 */
public enum JSONGameModelKeys {

    NAME("GameName"),
    PLAYERS("Players"),
    TERRITORIES("Territories"),
    GAME_STATE("GameState"),
    CURRENT_PLAYER("CurrentPlayer");

    private final String key;

    JSONGameModelKeys(String key) {
        this.key = key;
    }

    public String getKey() { return key; }
}
