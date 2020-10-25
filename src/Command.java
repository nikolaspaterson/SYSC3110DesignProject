/**
 * Defines command formats
 * Commands are of the form, <command territory1 territory2 number>, where command is the action the player
 * wants to do, territory1 is the players starting territory, territory2 is the target territory and
 * number is the number of troops the player wants to use.
 *
 * Example commands,
 * reinforce australia 7 -> player will draft 7 troops to australia
 * attack germany poland 12 -> player will attack poland with 12 german troops
 * fortify ontario yukon 5 -> player will fortify the yukon with 5 troops from ontario
 *
 * @author Nikolas Paterson
 *
 */
public class Command {

    private final String commandAction;
    private final String commandOrigin;
    private final String commandTarget;
    private final String commandNumber;

    /**
     * Constructor from quit, skip and showMap
     * @param str String
     */
    public Command(String str){
        this.commandAction = str;
        this.commandOrigin = "";
        this.commandTarget = "";
        this.commandNumber = "";
    }
    /**
     * command with only 1 territory
     * @param command String
     * @param origin String
     * @param number String
     */
    public Command(String command, String origin, String number){
        this.commandAction = command;
        this.commandOrigin = origin;
        this.commandTarget = "";
        this.commandNumber = number;
    }

    /**
     * command with 2 territories
     * @param command String
     * @param origin String
     * @param target String
     * @param number String
     */
    public Command(String command, String origin, String target, String number){
        this.commandAction = command;
        this.commandOrigin = origin;
        this.commandTarget = target;
        this.commandNumber = number;

    }

    /**
     * @return command action
     */
    public String getCommandAction() {
        return commandAction;
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
     *
     * @return the length of the command
     */
    public int getLength(){
        int counter = 0;
        if(!(this.getCommandAction()).isEmpty()){
            counter++;
        }
        if(!(this.getCommandOrigin()).isEmpty()){
            counter++;
        }
        if(!(this.getCommandTarget()).isEmpty()){
            counter++;
        }
        if(!(this.getCommandNumber()).isEmpty()){
            counter++;
        }
        return counter;
    }
}
