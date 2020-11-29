package Model;

import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class MockSave extends GameModel{

    /**
     * Creates a saveAction for testing
     * @param output_path location the json will be save to
     * @param name file name
     */
    public void saveAction(String output_path, String name) {
        try {
            JSONObject save_file = saveJSON();
            String fileName = name + ".json";
            FileWriter writer = new FileWriter(output_path + fileName);
            String value = save_file.toString();
            writer.write(value);
            writer.close();
            updateSaveView(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
