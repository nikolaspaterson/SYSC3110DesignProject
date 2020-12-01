package JSONModels;

/**
 * This class is used to represent the different Map JSON keys that are used in the saving and loading of the JSON file.
 */
public enum JSONMapKeys {

    NAME("Name"),
    NEIGHBOURS("Neighbours"),
    TERRITORY("Territory"),
    TROOPS("Troops"),
    COORDINATE_X1("X1"),
    COORDINATE_X2("X2"),
    COORDINATE_Y1("Y1"),
    COORDINATE_Y2("Y2"),
    CONTINENTS("Continents"),
    BACKGROUND("Background");

    private final String key;

    JSONMapKeys(String key) {
        this.key = key;
    }

    public String getKey() { return key; }
}
