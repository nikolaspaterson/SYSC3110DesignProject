/**
 * Enum of all commands the player can use
 */
public enum CommandEnum {

    REINFORCE("reinforce"), ATTACK("attack"), FORTIFY("fortify"),
    QUIT("quit"), UNKNOWN("unknown command"), SKIP("skip"),
    WORLDMAP("worldmap"), HELP("help"), MYMAP("mymap");

    private String commandString;

    /**
     * Initializes each enum command with its corresponding String
     * @param s String
     */
    CommandEnum(String s){
        this.commandString = s;
    }

    /**
     * @return enum commands corresponding String
     */
    public String toString(){
        return commandString;
    }
}
