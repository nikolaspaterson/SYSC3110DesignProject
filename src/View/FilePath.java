package View;

/**
 * This class is used to represent the different FilePaths that are used throughout the Game.
 */
public enum FilePath {

    JAR_FILE_SIGNATURE(".jar"),
    JSON_FILE_SIGNATURE(".json"),
    DEFAULT_MAP_JSON("/resources/DefaultMap.json"),
    DEFAULT_MAP_PNG("/resources/Map.png"),
    BEAT_FILEPATH("/resources/beat.wav"),
    CHIZZY("/resources/Chizzy.png"),
    TA("/resources/TA.png"),
    CAPTAIN("/resources/Captain.png"),
    NIK("/resources/nik.png"),
    PASSKI("/resources/passki.png"),
    BRUCE("/resources/Bruce.png"),
    LEFT_ARROW("/resources/leftarrow.png"),
    RIGHT_ARROW("/resources/rightarrow.png"),
    RED_X("/resources/redx.png"),
    CONFETTI("/resources/confetti.png"),
    CONFETTI_FLIPPED("/resources/confettiflip.png"),
    CROWN_GIF("/resources/crown1.gif"),
    WINNING_MUSIC("/resources/allido.wav");

    private final String path;

    FilePath(String path) {
        this.path = path;
    }

    public String getPath() { return path; }
}
