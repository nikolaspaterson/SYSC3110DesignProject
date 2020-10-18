/**
 * Enum of all commands the player can use
 * @author nikolaspaterson
 * @version 1.0
 */
public enum commandEnum {
    REINFORCE("reinforce"), ATTACK("attack"), FORTIFY("fortify"),
    RESTART("restart"), UNKNOWN("unknown command");

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