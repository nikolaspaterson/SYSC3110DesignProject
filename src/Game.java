import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Game {
    private int currentPlayerTurn;
    private boolean gameInProgress;
    private ArrayList<Player> playerList;
    private CommandParser commandParser;
    private Map<String, Territory> worldMap;
    private GameSetup gameSetup;

    public Game(){
        printWelcome();
        gameInProgress = true;
        gameSetup = new GameSetup(playerList);
        worldMap = new HashMap<>();
        gameSetup.returnWorldMap();


    }

    public Player getPlayerTurn(){
        return playerList.get(currentPlayerTurn-1);
    }

    /**
     * Turns are done in a sequence, when last player
     * takes their turn the sequence restarts from the beginning
     */
    public void takeTurn(){
        if(currentPlayerTurn == playerList.size()){
            currentPlayerTurn = 1;
        }else{
            currentPlayerTurn++;
        }
    }

    public Player findWinner(){
        if(playerList.size() == 1){
            gameInProgress = false;
            return playerList.get(0);
        }
        gameInProgress = true;
        return null;
    }

    public void playGame(){
        boolean wantsToQuit = false;
        while(gameInProgress || wantsToQuit){
            command cmd = commandParser.getCommand();
            wantsToQuit = commandProcessor(cmd);
        }
    }

    private boolean commandProcessor(command cmd){
        boolean wantsToQuit = false;
        commandEnum command = cmd.getCommandAction();

        switch(command){
            case UNKNOWN:
                System.out.println("Unknow command, you can type 'help' to see all commands");
                break;

            case HELP:
                printHelp();
                break;

            case QUIT:
                wantsToQuit = true;
                break;
        }
        return wantsToQuit;
    }

    /**
     * initializes playerList
     * @param nPlayers
     */
    public void setPlayers(int nPlayers){
        playerList = new ArrayList<>();
        for(int i = 1; i <= nPlayers; i++){
            playerList.add(new Player("Player " + i));
        }
        currentPlayerTurn = 1;
    }

    /**
     * asks for number of players, must be less than 8.
     */
    public void playerSetUp(){
        Scanner inScanner = new Scanner(System.in);
        System.out.println("Please type the number of players you want: ");
        String playerNumber = inScanner.nextLine();
        try{
            if(Integer.parseInt(playerNumber) > 6 || Integer.parseInt(playerNumber) < 2 ){
                throw new NumberFormatException();
            }else{
                setPlayers(Integer.parseInt(playerNumber));
            }
        }catch(NumberFormatException e){
            System.out.println("Please enter a number between 2 and 6");
            playerSetUp();
        }

    }

    /**
     * Print welcome message and calls playerSetUp
     */
    public void printWelcome(){
        System.out.println("Welcome to RISK: Global Domination!");
        System.out.println("Your goal is to battle your friends and conquer the world.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        playerSetUp();
    }

    public void printHelp(){
        System.out.println();
        System.out.println("It is currently " + getPlayerTurn() +"'s turn");
        System.out.println("Here are your available commands, ");
        for(commandEnum command: commandEnum.values()){
            System.out.println(command);
        }
    }


    public static void main(String[] args) {
        /*ArrayList<Player> player_list = new ArrayList<>();
        player_list.add(new Player("Franky"));
        player_list.add(new Player("Freddy"));
        player_list.add(new Player("Fawney"));
        player_list.add(new Player("Obama"));
        GameSetup setup = new GameSetup(player_list);*/
        Game g = new Game();
    }

}
