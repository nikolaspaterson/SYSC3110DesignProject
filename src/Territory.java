package src;

import java.util.HashMap;

public class Territory {

    private Player occupant;
    private int troops;
    private HashMap<String, Territory> neighbours;
    private String territoryName;
    private String continentName;


    public Territory(Player occupant, String territoryName, String continentName) {
        this.occupant = occupant;
        this.territoryName = territoryName;
        this.continentName = continentName;
        troops = 0;
        neighbours = new HashMap<>();
    }

    public void setContinentName(String continentName) { this.continentName = continentName;}

    public String getContinentName() { return continentName; }

    public void setTerritoryName(String territoryName) { this.territoryName = territoryName; }

    public String getTerritoryName() { return territoryName; }

    public void setTroops(int troops) {
        this.troops = troops;
    }

    public int getTroops() {
        return troops;
    }

    public void setNeighbour(Territory neighbour){ neighbours.put(neighbour.getTerritoryName() ,neighbour); }

    public HashMap<String,Territory> getNeighbours() {
        return this.neighbours;
    }

    public void setOccupant(Player occupant) {
        this.occupant = occupant;
    }

    public Player getOccupant() {
        return occupant;
    }

    public boolean isNeighbour(Territory territoryToCheck) {
        String terrToCheck = territoryToCheck.getTerritoryName();
        if(this.neighbours.containsKey(terrToCheck)) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "";
    }
}
