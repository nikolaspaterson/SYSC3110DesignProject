/**
 * Defines command formats
 * Commands are of the form, <command territory1 territory2 number>, where command is the action the player
 * wants to do, territory1 is the players starting territory, territory2 is the target territory and
 * number is the number of troops the player wants to use.
 *
 * Example commands,
 * reinforce australia 7 -> player will draft 7 troops to australia
 * attack germany poland 12 -> player will attack poland with 12 german troops
 * fortify ontario yukon 5 -> player will fortify ontario with 5 troops from the yukon
 *
 * @author nikolaspaterson
 * @version 1.0
 */
public class command {

    private String commandAction;
    private String commandOrigin;
    private String commandTarget;
    private String commandNumber;

    /**
     * null constructor
     * @param str
     */
    public command(String str){
        this.commandAction = null;
        this. commandOrigin = null;
        this.commandTarget = null;
        this. commandNumber = null;
    }
    /**
     * command with only 1 territory
     * @param command
     * @param origin
     * @param number
     */
    public command(String command, String origin, String number){
        this.commandAction = command;
        this.commandOrigin = origin;
        this.commandTarget = origin; //Maybe change to null? could avoid attacking self? need to null check somewhere
        this.commandNumber = number;
    }

    /**
     * command with 2 territories
     * @param command
     * @param origin
     * @param target
     * @param number
     */
    public command(String command, String origin, String target, String number){
        this.commandAction = command;
        this.commandOrigin = origin;
        this.commandTarget = target;
        this.commandNumber = number;

    }

    /**
     * @return command action
     */
    public commandEnum getCommandAction() {
        return commandEnum.valueOf(commandAction);
    }

    /**
     * @return commands origin
     */
    public String getCommandOrigin() {
        return commandOrigin;
    }

    /**
     * @return commands target
     */
    public String getCommandTarget() {
        return commandTarget;
    }

    /**
     * @return number of troops to be used
     */
    public String getCommandNumber() {
        return commandNumber;
    }

    /**
     * returns true if command is unknown
     * @return boolean based on if command is a known command
     */
    public boolean isUnknown(){
        return commandAction == null;
    }
}
