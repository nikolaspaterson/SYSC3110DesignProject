import java.util.HashMap;
import java.util.Map;

/**
 * Stores all the valid commands that the players can use.
 * @author Nikolas Paterson
 */
public class CommandWord {

    private final Map<String, CommandEnum> validCommands;

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
     * @param cmd - Command
     * @return commandEnum linked to command
     */
    public CommandEnum getCommandAction(String cmd){
        CommandEnum isValidActionCommand = validCommands.get(cmd);
        return (isValidActionCommand == null) ? CommandEnum.UNKNOWN : isValidActionCommand;
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
