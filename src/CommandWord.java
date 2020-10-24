import java.util.HashMap;
import java.util.Map;

/**
 * Stores all the valid commands the players can use
 */
public class CommandWord {
    private Map<String, CommandEnum> validCommands;

    /**
     * Populates HashMap with all valid commands a String and commandEnum
     */
    public CommandWord(){
        validCommands = new HashMap<>();
        for(CommandEnum cmd : CommandEnum.values()){
            if(cmd != CommandEnum.UNKNOWN){
                validCommands.put(cmd.toString(), cmd);
            }
        }
    }

    /**
     * Find correct commandEnum from command
     * @param cmd
     * @return commandEnum linked to command
     */
    public CommandEnum getCommandAction(String cmd){
        CommandEnum isValidActionCommand = validCommands.get(cmd);
        return (isValidActionCommand == null) ? CommandEnum.UNKNOWN : isValidActionCommand;
    }

    /**
     * Checks if command is valid
     * @param cmd A command to be validated
     * @return boolean based on if command is valid
     */
    public boolean isValidCommand(String cmd){
        return validCommands.containsKey(cmd); //if key exists return true, else false
    }

    /**
     * Prints all commands to console
     */
    public void showAllCommands(){
        for(String cmd : validCommands.keySet()){
            System.out.println(cmd);
        }
    }
}
