package JSONModels;

/**
 * This class is used to represent the different Player JSON keys that are used in the saving and loading of the JSON file.
 */
public enum JSONPlayerKeys {

    NAME("Name"),
    TYPE("Type"),
    TOTAL_TROOPS("TotalTroops"),
    DEPLOYABLE_TROOPS("DeployableTroops"),
    FORTIFY_STATUS("Fortify"),
    OCCUPIED_TERRITORIES("OccupiedTerritories"),
    PLAYER_COLOR("Color"),
    PLAYER_NUMBER("PlayerIndex"),
    PLAYER_STATUS("InGame"),
    PLAYER_ICON("FilePath"),
    AIPLAYER_ATTACKING("Attacking");

    private final String key;

    JSONPlayerKeys(String key) {
        this.key = key;
    }

    public String getKey() { return key; }

}
