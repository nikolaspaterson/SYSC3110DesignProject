package Controller;

public enum JButtonActionCommands {
    RESOURCES("resources"),
    SAVE("Save"),
    HOW_TO_PLAY("How to Play");

    private final String command;

    JButtonActionCommands(String command) {
        this.command = command;
    }

    public String getCommand() { return command; }
}
