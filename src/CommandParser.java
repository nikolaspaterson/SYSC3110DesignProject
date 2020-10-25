import java.util.Scanner;

/**
 * This class parses the players console input into valid commands.
 * @author Nikolas Paterson
 */
public class CommandParser {

    private final CommandWord validCommands;
    private final Scanner inputReader;

    /**
     * Default constructor for the CommandParser class, creates scanner to read in user input.
     */
    public CommandParser(){
        validCommands = new CommandWord();
        inputReader = new Scanner(System.in);
    }

    /**
     * converts input to command and checks command format
     * @return the console input as a command
     */
    public Command getCommand(){
        String inputCommand;
        String[] inputCommands;

        inputCommand = inputReader.nextLine();//read in from console
        inputCommands = inputCommand.trim().split("\\s+"); //trim leading and trailing spaces and split at remaining spaces


        if(inputCommands.length == 1 || inputCommands.length == 3 || inputCommands.length == 4){
            if(inputCommands.length == 1){
                return new Command(inputCommands[0]);
            }else if(inputCommands.length == 3){
                try{
                    Integer.parseInt(inputCommands[2]);
                    return new Command(inputCommands[0], inputCommands[1], inputCommands[2]);
                }catch(NumberFormatException e){
                    System.out.println("Please enter a valid number!");
                    return new Command("");
                }
            }else{
                try{
                    Integer.parseInt(inputCommands[3]);
                    return new Command(inputCommands[0], inputCommands[1], inputCommands[2], inputCommands[3]);
                }catch(NumberFormatException e){
                    System.out.println("Please enter a valid number!");
                    return new Command("");
                }
            }
        }else{
            return new Command("");
        }
    }

    /**
     * Calls commandWords showAllCommands method which,
     * prints all valid commands to the console
     */
    public void printAllCommands(){
        validCommands.showAllCommands();
    }
}
