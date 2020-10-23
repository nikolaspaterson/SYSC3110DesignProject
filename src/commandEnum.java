/**
 * Enum of all commands the player can use
 * @author nikolaspaterson
 * @version 1.0
 */
public enum commandEnum {
    REINFORCE("reinforce"), ATTACK("attack"), FORTIFY("fortify"),
    QUIT("quit"), UNKNOWN("unknown command"), SKIP("skip"), MAP("map"), HELP("help");

    private String commandString;

    /**
     * Initializes each enum command with its corresponding String
     * @param s
     */
    commandEnum(String s){
        this.commandString = s;
    }

    /**
     * @return enum commands corresponding String
     */
    public String toString(){
        return commandString;
    }
}
