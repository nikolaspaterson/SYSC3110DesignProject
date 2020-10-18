package src;

import java.util.HashMap;

public class Player {

    private int deployableTroops;
    private String name;
    private HashMap<String, Territory> territoriesOccupied;

    public Player(String name) {
        this.name = name;
        territoriesOccupied = new HashMap<String, Territory>();
    }

    public HashMap<String, Territory> getTerritoriesOccupied() { return territoriesOccupied; }

    public void setDeployableTroops(int deployableTroops) {
        this.deployableTroops = deployableTroops;
    }

    public int getDeployableTroops() {
        return deployableTroops;
    }

    public void incrementTroops(Territory territory, int numTroops) {
        territory.setTroops(territory.getTroops() + numTroops);
    }

    public void decrementTroops(Territory territory, int numTroops) {
        territory.setTroops(territory.getTroops() - numTroops);
    }

    public void addTerritory(String territoryName, Territory territory) {
        territoriesOccupied.put(territoryName, territory);
    }

    public void removeTerritory(String territory) {
        territoriesOccupied.remove(territory);
    }

    public String toString() {
        return "";
    }
}
