import java.util.*;

/**
 * The Game class's main responsibility is to act as the brain
 *  of the RISK game. This class is used to initialize all other classes.
 * @author Nikolas Paterson
 * @author Erik Iuhas
 */
public class Game {

    private int currentPlayerTurn;
    private ArrayList<Player> playerList;
    private CommandParser commandParser;
    private Map<String,Territory> worldMap;

    /**
     * Create game object and begins the game
     */
    public Game(){
        playerList = new ArrayList<>();
        printWelcome();
        playerSetUp();
        commandParser = new CommandParser();
        playGame();
    }

    /**
     * @return Player - the current players turn
     */
    public Player getPlayerTurn(){
        return playerList.get(currentPlayerTurn-1);
    }

    /**
     * Turns are done in a sequence, when last player
     * takes their turn the sequence restarts from the beginning
     */
    public void nextTurn(){
        if(currentPlayerTurn == playerList.size()){
            currentPlayerTurn = 1;
        }else{
            currentPlayerTurn++;
        }
        checkPlayerStanding();
        (playerList.get(currentPlayerTurn-1)).troopsReceived();
        findWinner();
    }

    /**
     * Checks if a player is still in the game based on if they own any territories
     */
    public void checkPlayerStanding(){
        for (Player player : playerList){
            if(player.getTerritoriesOccupied().isEmpty()){
                playerList.remove(player);
            }
        }
    }

    /**
     * Checks if there is a game winner. There is a winner when there is
     * only 1 active player.
     */
    public void findWinner(){
        if(playerList.size() == 1) {
            System.out.println(playerList.get(0) + " won the game!");
            commandProcessor(new Command("quit"));
        }
    }

    /**
     * Games infinite loop, keeps going until game is won or players quit
     */
    public void playGame(){
        boolean gameInProgress = false;
        while(!gameInProgress){
            Command cmd = commandParser.getCommand();
            gameInProgress = commandProcessor(cmd);
        }
        System.out.println("Game is over");
    }

    /**
     * Allows players to skip their turn
     * @param command - command passed to skip method
     */
    public void skip(Command command){
        if(command.getLength() == 1){
            nextTurn();
        }else {
            System.out.println("skip what?");
        }
    }

    /**
     * Processes user commands and calls the corresponding methods
     * @param cmd - The user input command
     * @return boolean based on if player wants to quit
     */
    private boolean commandProcessor(Command cmd){
        CommandWord commandWord = new CommandWord();
        boolean wantsToQuit = false;
        CommandEnum command = commandWord.getCommandAction(cmd.getCommandAction());

        switch (command) {
            case UNKNOWN -> System.out.println("Unknown command, you can type 'help' to see all commands");
            case HELP -> printHelp(cmd);
            case QUIT -> wantsToQuit = quit(cmd);
            case REINFORCE -> reinforce(cmd);
            case ATTACK -> attack(cmd);
            case FORTIFY -> fortify(cmd);
            case SKIP -> skip(cmd);
            case WORLDMAP -> showWorldMap();
            case MYMAP -> showMyMap();
        }
        System.out.println("=======================================");
        System.out.println(getPlayerTurn().getName() + "'s turn : ");
        System.out.println("you have " + getPlayerTurn().getDeployableTroops() + " to START deploying");
        return wantsToQuit;
    }

    /**
     * displays the entire map
     */
    public void showWorldMap(){
        for(Player pl: playerList){
            System.out.print(pl.toString());
        }
    }

    /**
     * displays the players territories
     */
    public void showMyMap(){
        System.out.print(getPlayerTurn().toString());
    }

    /**
     * Does some command checking and calls GaveEvent's reinforce method
     * @param cmd - The user input command
     */
    public void reinforce(Command cmd){
        if(!(cmd.getCommandOrigin().isEmpty()) && !(cmd.getCommandNumber().isEmpty())) {
            try {
                GameEvent gameevent = new GameEvent(getPlayerTurn());
                Player player = getPlayerTurn();
                Territory territory = player.getTerritoriesOccupied().get(cmd.getCommandOrigin());
                gameevent.reinforce(territory, Integer.parseInt(cmd.getCommandNumber()));
            } catch (NullPointerException e) {
                System.out.println("You can only reinforce a territory you own");
                System.out.println("An example reinforce command looks like the following,");
                System.out.println("reinforce Ontario 5");
            }
        }else{
            System.out.println("Incorrect format, REINFORCE COMMAND: reinforce (Territory_Name) (Troops_To_Deploy)");
        }
    }

    /**
     * Checks command format and calls GameEvent's attack method
     * @param cmd - The user input command
     */
    public void attack(Command cmd){
        if(!(cmd.getCommandTarget().isEmpty()) && !(cmd.getCommandOrigin().isEmpty()) && !(cmd.getCommandNumber().isEmpty())){
            if(worldMap.get(cmd.getCommandTarget()) == null || worldMap.get(cmd.getCommandOrigin()) == null){
                System.out.println("That territory doesn't exist, make sure you type territory names with proper capitalization!");
            } else {
                try {
                    GameEvent gameevent = new GameEvent(getPlayerTurn());
                    Player player = getPlayerTurn();
                    Territory attackingTerritory = player.getTerritoriesOccupied().get(cmd.getCommandOrigin());
                    Territory defendingTerritory = attackingTerritory.getNeighbours().get(cmd.getCommandTarget());
                    gameevent.attack(attackingTerritory, defendingTerritory, Integer.parseInt(cmd.getCommandNumber()));
                } catch (NullPointerException e) {
                    System.out.println("Please enter a neighbouring territory that is owned by a different player");
                }
            }
        }
        else{
            System.out.println("Incorrect format, ATTACK COMMAND: attack (Attacking_Territory) (Defending_Territory) (Num_Attacking_Dice)");
        }
    }

    /**
     * Checks command format and calls GameEvent's fortify method
     * @param cmd - The user input command
     */
    public void fortify(Command cmd){
        if(!(cmd.getCommandTarget().isEmpty()) && !(cmd.getCommandOrigin().isEmpty()) && !(cmd.getCommandNumber().isEmpty())){
            if(worldMap.get(cmd.getCommandTarget()) == null || worldMap.get(cmd.getCommandOrigin()) == null) {
                System.out.println("That territory doesn't exist, make sure you type territory names with proper capitalization!");

            } else {
                try {
                    GameEvent gameevent = new GameEvent(getPlayerTurn());
                    Player player = getPlayerTurn();
                    Territory territory1 = player.getTerritoriesOccupied().get(cmd.getCommandOrigin());
                    Territory territory2 = player.getTerritoriesOccupied().get(cmd.getCommandTarget());
                    gameevent.fortify(territory1, territory2, Integer.parseInt(cmd.getCommandNumber()));
                } catch (NullPointerException e) {
                    System.out.println("Please enter a neighbouring territory that is owned by you");
                }
            }
        } else {
            System.out.println("Incorrect format, FORTIFY COMMAND: fortify (Territory_1) (Territory_2) (Num_Of_Troops_Moved_FROM_T1_TO_T2");
        }
    }

    /**
     * initializes playerList
     * @param nPlayers - The number of players playing the game
     */
    public void setPlayers(int nPlayers){
        for(int i = 1; i <= nPlayers; i++){
            playerList.add(new Player("Player " + i));
        }
        GameSetup gameSetup = new GameSetup(playerList);
        worldMap = gameSetup.returnWorldMap();
        currentPlayerTurn = 1;
        (playerList.get(currentPlayerTurn-1)).troopsReceived();
        System.out.println(getPlayerTurn().getName() + "'s turn : ");
        System.out.println("you have " + getPlayerTurn().getDeployableTroops() + " to START deploying");
    }

    /**
     * asks for number of players, must be less than 8.
     */
    public void playerSetUp(){

        try{
            Scanner inScanner = new Scanner(System.in);
            System.out.println("Please type the number of players you want: ");
            String playerNumber = inScanner.nextLine();
            int pn = Integer.parseInt(playerNumber);
            if(pn > 6 || pn < 2 ){
                throw new NumberFormatException();
            }else{
                setPlayers(pn);
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
    }

    /**
     * prints help message to console
     * @param command - The user input command
     */
    public void printHelp(Command command){
        if(command.getLength() == 1){
            System.out.println();
            System.out.println("Here are your available commands, ");
            commandParser.printAllCommands();
            System.out.println();
        }else{
            System.out.println("help what?");
        }
    }

    /**
     * checks if the player really wants to quit
     * @param command - The user input command
     * @return boolean - True if player types only 'quit', else false
     */
    private boolean quit(Command command)
    {
        if(command.getLength() > 1){
            System.out.println("Quit what?");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        new Game();
    }
}
