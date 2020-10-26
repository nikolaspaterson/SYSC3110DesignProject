import java.awt.*;
import java.util.HashMap;

public class Continent {

    private HashMap<String, Territory> continentTerritory;
    private String continentName;
    private Color countryColor;

    public Continent(String continentName){
        this.continentName = continentName;
        continentTerritory = new HashMap<>();
    }

    public void addContinentTerritory(String territory_name, Territory territory){
        continentTerritory.put(territory_name,territory);
    }

    public HashMap<String, Territory> getContinentTerritory() {
        return continentTerritory;
    }

    public String getContinentName() {
        return continentName;
    }


    public Boolean checkContinentOccupant(Player player){
        Player current_player = player;
        for(Territory temp_territory : continentTerritory.values()){
            if (!current_player.equals(temp_territory.getOccupant())){
                return false;
            }
        }
        return true;
    }

}
