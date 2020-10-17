import java.util.ArrayList;
import java.util.List;

public class Territory {
    private Player player;
    private int troops;
    private List<Territory> neighbours;

    public Territory() {
        this.player = new Player();
        this.troops = 0;
        neighbours = new ArrayList<>();
    }

    public void setTroops(int n) {
        this.troops = n;
    }

    public int getTroops() {
        return this.troops;
    }

    public void setNeighbour(Territory neighbour){
        neighbours.add(neighbour);
    }
    public List<Territory> getNeighbours() {
        return this.neighbours;
    }

    public void setOccupant(Player player) {
        this.player = player;
    }
    public Player getOccupant() {
        return this.player;
    }
    public String toString() {
        return "";
    }
}
