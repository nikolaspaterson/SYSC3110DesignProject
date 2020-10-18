import java.util.HashMap;
import java.util.Map;

public class commandWord {
    private Map<String, commandEnum> validCommands;

    /**
     * Populates HashMap with all valid commands a String and commandEnum
     */
    public commandWord(){
        validCommands = new HashMap<>();
        for(commandEnum cmd : commandEnum.values()){
            if(cmd != commandEnum.UNKNOWN){
                validCommands.put(cmd.toString(), cmd);
            }
        }
    }

    /**
     * Find correct commandEnum from command
     * @param cmd
     * @return commandEnum linked to command
     */
    public commandEnum getCommandAction(String cmd){
        commandEnum isValidActionCommand = validCommands.get(cmd);
        return (isValidActionCommand == null) ? commandEnum.UNKNOWN : isValidActionCommand;
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
