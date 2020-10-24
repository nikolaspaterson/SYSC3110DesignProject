import java.util.Scanner;

/**
 * This class parses the players console input into valid commands
 * @author nikolaspaterson
 * @version 1.0
 */
public class CommandParser {

    private CommandWord validCommands;
    private Scanner inputReader;

    /**
     * Default constructor, creates scanner to read in user input
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

        System.out.println("player1 its is your turn:");

        inputCommand = inputReader.nextLine();//read in from console
        inputCommands = inputCommand.trim().split("\\s+"); //trim leading and trailing spaces and split at remaining spaces

        if((inputCommands.length != 0 || inputCommands.length != 2 || inputCommands.length <= 4) && validCommands.isValidCommand(inputCommands[0])){
            if(inputCommands.length == 1){
                return new Command(inputCommands[0]);
            }else if(inputCommands.length == 3){
                return new Command(inputCommands[0], inputCommands[1], inputCommands[2]);
            }else{
                return new Command(inputCommands[0], inputCommands[1], inputCommands[2], inputCommands[3]);
            }
        }
        return new Command("");
    }

    /**
     * Calls commandWords showAllCommands method which,
     * prints all valid commands to the console
     */
    public void printAllCommands(){
        validCommands.showAllCommands();
    }
}
