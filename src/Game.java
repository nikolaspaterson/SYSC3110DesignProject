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
    private boolean wantsToQuit;

    public Game(){
        printWelcome();
        gameInProgress = true;
        wantsToQuit = false;
        gameSetup = new GameSetup(playerList);
        worldMap = new HashMap<>();
        gameSetup.returnWorldMap();
        playGame();
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

    public void removePlayer(){
        for (Player player : playerList){
            if(player.getTerritoriesOccupied())
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
        while(gameInProgress || !wantsToQuit){
            command cmd = commandParser.getCommand();
            wantsToQuit = commandProcessor(cmd);
        }
    }

    private boolean commandProcessor(command cmd){
        boolean wantsToQuit = false;
        commandEnum command = cmd.getCommandAction();

        switch(command){
            case UNKNOWN:
                System.out.println("Unknown command, you can type 'help' to see all commands");
                break;

            case HELP:
                printHelp();
                break;

            case QUIT:
                quit(cmd);
                break;

            case REINFORCE:
                reinforce(cmd);
                break;

            case ATTACK:
                attack(cmd);
                break;

            case FORTIFY:
                fortify(cmd);
                break;

            /*case SKIP:
                break;

            case MAP:
                break;*/
        }
        return wantsToQuit;
    }

    public void skipTurn(){

    }

    public void showMap(){

    }

    public void reinforce(command cmd){
        if(cmd.getCommandTarget() != null && cmd.getCommandNumber() != null){
            GameEvent gameevent = new GameEvent(getPlayerTurn());
            Player player = getPlayerTurn();
            Territory territory = player.getTerritoriesOccupied().get(cmd.getCommandOrigin());
            gameevent.reinforce(territory, Integer.parseInt(cmd.getCommandNumber()));
        }
    }

    public void attack(command cmd){
        if(cmd.getCommandTarget() != null && cmd.getCommandOrigin() != null){
            GameEvent gameevent = new GameEvent(getPlayerTurn());
            Player player = getPlayerTurn();
            Territory attackingTerritory = player.getTerritoriesOccupied().get(cmd.getCommandOrigin());
            Territory defendingTerritory = attackingTerritory.getNeighbours().get(cmd.getCommandTarget());
            gameevent.attack(attackingTerritory, defendingTerritory, Integer.parseInt(cmd.getCommandNumber()));
        }
    }

    public void fortify(command cmd){
        if(cmd.getCommandTarget() != null && cmd.getCommandNumber() != null){
            GameEvent gameevent = new GameEvent(getPlayerTurn());
            Player player = getPlayerTurn();
            Territory territory = player.getTerritoriesOccupied().get(cmd.getCommandTarget());
            Territory territory2 = player.getTerritoriesOccupied().get(cmd.getCommandOrigin());
            gameevent.fortify(territory,territory2, Integer.parseInt(cmd.getCommandNumber()));
        }
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

    private boolean quit(command command)
    {
        if(command.getCommandOrigin() == null){
            System.out.println("Quit what?");
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        Game g = new Game();
    }

}
