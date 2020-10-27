import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The Game class's main responsibility is to act as the brain
 *  of the RISK game. This class is used to initialize all other classes.
 * @author Nikolas Paterson
 * @author Erik Iuhas
 */
public class Game {

    private int currentPlayerTurn;
    private final ArrayList<Player> playerList;
    private final CommandParser commandParser;
    private Map<String,Territory> worldMap;
    private HashMap<String,Continent> continentMap;
    boolean wantsToQuit;

    /**
     * Create game object and begins the game
     */
    public Game(){
        wantsToQuit = false;
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
        if(playerList.size() != 1) {
            playerBonus();
        }

    }

    /**
     * This method is used to calculate the number of troops the player will receive based on how many territories and continents they own.
     */
    public void playerBonus(){

        int troops = 0;
        if (continentMap.get("Asia").checkContinentOccupant(getPlayerTurn())) troops += 7; // Asia Bonus
        if (continentMap.get("Australia").checkContinentOccupant(getPlayerTurn())) troops += 2; // Australia Bonus
        if (continentMap.get("Europe").checkContinentOccupant(getPlayerTurn())) troops += 5; // Europe Bonus
        if (continentMap.get("Africa").checkContinentOccupant(getPlayerTurn())) troops += 3; // Africa Bonus
        if (continentMap.get("SouthAmerica").checkContinentOccupant(getPlayerTurn())) troops += 2; // South America Bonus
        if (continentMap.get("NorthAmerica").checkContinentOccupant(getPlayerTurn())) troops += 5; // North America Bonus

        if ((getPlayerTurn().getTerritoriesOccupied().size()) <= 9) {
            troops += 3;
        } else {
            troops += ((getPlayerTurn().getTerritoriesOccupied().size()) / 3);
        }
        getPlayerTurn().addDeployableTroops(troops);
    }
    /**
     * Checks if a player is still in the game based on if they own any territories
     */
    public void checkPlayerStanding(){
        Player p_to_delete = null;
        for (Player player : playerList){
            if(player.getTerritoriesOccupied().isEmpty()){
                p_to_delete = player;
            }
        }
        if(p_to_delete != null){
            playerList.remove(p_to_delete);
        }
    }

    /**
     * Checks if there is a game winner. There is a winner when there is
     * only 1 active player.
     */
    public void findWinner(){
        checkPlayerStanding();
        if(playerList.size() == 1) {
            System.out.println(playerList.get(0).getName() + " won the game!");
            wantsToQuit = true;
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
        if(!wantsToQuit){
            System.out.println("=======================================");
            System.out.println(getPlayerTurn().getName() + "'s turn : ");
            System.out.println("you have " + getPlayerTurn().getDeployableTroops() + " to START deploying");
        }
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
                    findWinner();
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
            playerList.add(new Player("Player " + i, Color.red));
        }
        GameSetup gameSetup = new GameSetup(playerList, new JPanel());
        worldMap = gameSetup.returnWorldMap();
        continentMap = gameSetup.returnContinentMap();
        currentPlayerTurn = 1;
        playerBonus();
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
        }
        System.out.println("help what?");
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

    /**public static void main(String[] args) {
        new Game();
    }*/
}
