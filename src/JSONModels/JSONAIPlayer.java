package JSONModels;

import org.json.simple.JSONObject;

/**
 * This class is used to form the AIPlayers from the JSON file
 */
public class JSONAIPlayer extends JSONPlayer {

    private final JSONObject aiPlayer;
    private boolean attacking;

    /**
     * Class constructor for the JSONAIPlayer class.
     */
    public JSONAIPlayer(){
        aiPlayer = new JSONObject();
    }

    /**
     * Class constructor for the JSONAIPlayer class.
     *
     * @param load the JSON object
     */
    public JSONAIPlayer(JSONObject load){
        aiPlayer = new JSONObject();
        setPlayer_json();
        loadJson(load);
    }

    /**
     * This method is used to load the AIPlayer's from JSON object.
     * @param player the player in a JSON Object
     */
    @Override
    public void loadJson(JSONObject player){
        super.loadJson(player);
        setAttacking((boolean) player.get("Attacking"));
    }

    @SuppressWarnings("unchecked")
    public void setAttacking(boolean attacking){
        this.attacking = attacking;
        aiPlayer.put("Attacking", attacking);
    }

    @SuppressWarnings("unchecked")
    public JSONObject getAiPlayer() {
        aiPlayer.putAll(getPlayer_json());
        return aiPlayer;
    }

    /**
     * This method returns a boolean which expresses if the AI is attacking or not.
     */
    public boolean isAttacking() {
        return attacking;
    }
}
