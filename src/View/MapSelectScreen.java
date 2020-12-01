package View;

import Controller.MapSelectController;
import JSONModels.JSONContinent;
import JSONModels.JSONMap;
import JSONModels.JSONMapKeys;
import JSONModels.JSONMapTerritory;
import Model.Territory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * The MapSelectScreen class is used to make the GUI for the MapSelectScreen.
 */
public class MapSelectScreen extends JFrame {

    private final JPanel mapPanel;
    private JButton defaultMap;
    private final HashMap<String, Territory> world_map;

    /**
     * Constructor for the MapSelectScreen class.
     */
    public MapSelectScreen() {
        super("Map Select Screen!");

        world_map = new HashMap<>();

        setSize(new Dimension(1280,814));
        setMinimumSize(new Dimension(1280,814));
        setMaximumSize(new Dimension(1280,814));
        setLayout(new BorderLayout());

        JLabel text = new JLabel("Choose your map!");
        text.setFont(new Font("Impact", Font.PLAIN, 55));
        text.setForeground(new Color(112, 114, 234));
        text.setHorizontalAlignment(text.CENTER);
        text.setVerticalAlignment(text.TOP);
        add(text, BorderLayout.NORTH);

        MapSelectController mapSelectController = new MapSelectController(this);

        mapPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        loadDefaultMap();
        loadCustomMap(mapSelectController);
        defaultMap.addActionListener(mapSelectController);

        add(mapPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * This method is used to load the custom map on the screen.
     * @param mapSelectController the controller
     */
    private void loadCustomMap(MapSelectController mapSelectController) {
        try {
            String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
            File chop_jar = new File(decodedPath);
            if (chop_jar.getName().contains(FilePath.JAR_FILE_SIGNATURE.getPath())) {
                decodedPath = decodedPath.replace(chop_jar.getName(), "");
            }
            String outputFolder = decodedPath + "Maps/";
            File file = new File(outputFolder);
            File[] files = file.listFiles();
            assert files != null;
            for(File temp_file : files) {
                if(temp_file.getName().contains(FilePath.JSON_FILE_SIGNATURE.getPath())) {
                    JButton customMap = new JButton();
                    FileReader jsonFile = new FileReader(temp_file.getPath());
                    JSONParser parser = new JSONParser();
                    JSONObject map = (JSONObject) parser.parse(jsonFile);
                    ImageIcon icon2 = scaleImage( outputFolder + map.get(JSONMapKeys.BACKGROUND.getKey()), false);
                    customMap.setActionCommand(temp_file.getPath());
                    customMap.setIcon(icon2);
                    mapPanel.add(customMap);
                    customMap.addActionListener(mapSelectController);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method is used to load the default map on the screen.
     */
    private void loadDefaultMap() {
        try {
            defaultMap = new JButton();
            InputStreamReader jsonFile = new InputStreamReader(getClass().getResourceAsStream(FilePath.DEFAULT_MAP_JSON.getPath()));
            JSONParser parser = new JSONParser();
            JSONObject map = (JSONObject) parser.parse(jsonFile);
            ImageIcon icon2 = scaleImage(map.get(JSONMapKeys.BACKGROUND.getKey()).toString(), true);
            defaultMap.setActionCommand(FilePath.DEFAULT_MAP_JSON.getPath());
            defaultMap.setIcon(icon2);
            mapPanel.add(defaultMap);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method is used to check if the custom map provided is valid.
     * @param path the custom map path
     * @return boolean true if custom map is valid, else if not
     */
    public boolean checkCustomMapValidity(String path){
        try {
            int total_territories = 0;
            FileReader jsonFile = new FileReader(path);
            JSONParser parser = new JSONParser();
            JSONMap map = new JSONMap((JSONObject) parser.parse(jsonFile));
            for(JSONContinent continent : map.getContinents()){
                for(JSONMapTerritory territory : continent.getTerritories()){
                    addToWorld(territory.getName());
                    for(String neighbour : territory.getNeighbours()){
                        addToWorld(neighbour);
                        world_map.get(territory.getName()).addNeighbour(world_map.get(neighbour));
                    }
                    total_territories++;
                }
            }
            for(Territory check_territory : world_map.values()){
                if(check_territory.debugLink().size() != total_territories){
                    return false;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * This method is used to add territories to the world map.
     * @param territory_name the territory name
     */
    private void addToWorld(String territory_name){
        if(world_map.get(territory_name) == null){
            Territory new_territory = new Territory(territory_name);
            world_map.put(territory_name, new_territory);
        }
    }

    /**
     * This method is used to scale the image to properly fit.
     * @param filename the filepath of the image
     * @return ImageIcon the scaled image
     */
    private ImageIcon scaleImage(String filename, boolean isResource) {
        ImageIcon scaledImg;
        if(isResource) {
            scaledImg = new ImageIcon(getClass().getResource(filename));
        } else {
            scaledImg = new ImageIcon(filename);
        }
        Image img = scaledImg.getImage().getScaledInstance( 300, 250,  java.awt.Image.SCALE_SMOOTH );
        scaledImg = new ImageIcon(img);
        return scaledImg;
    }
}
