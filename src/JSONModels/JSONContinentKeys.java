package JSONModels;

/**
 * This class is used to represent the different Continent JSON keys that are used in the saving and loading of the JSON file.
 */
public enum JSONContinentKeys {

    NAME("Continent"),
    TROOP_BONUS("TroopBonus"),
    TERRITORIES("Territories");

    private final String key;

    JSONContinentKeys(String key) {
        this.key = key;
    }

    public String getKey() { return key; }
}
