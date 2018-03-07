package e.mboyd6.tickettoride.Model;

import com.example.sharedcode.communication.UpdateArgs;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;

import org.java_websocket.client.WebSocketClient;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;



/**
 * Created by jonathanlinford on 2/5/18.
 */

public class ClientModel extends Observable {

    //Game data
    private ArrayList<Game> games = new ArrayList<>();
    private Game currentGame = null;
    private String playerName = "";

    private String authToken;

    private String playerTurn;

    //TODO:Di

    //TODO: These shoudl be managed in the SocketManage
    private WebSocketClient socket;
    private String socketID = "";


    // ******* USEFUL FUNCTIONS ******//
    /**
     * this function sends an update to the presenters subscribed
     * as observers. It is primarily used by the facades to signal that they are done
     * modifying the model and ready to update the presenters of their changes.
     * @param args - includes the UpdateType, boolean success, and an error
     *             message if applicable (a message of "" means success)
     */
    public void sendUpdate(UpdateArgs args) {
        this.setChanged();
        this.notifyObservers(args);
    }

    /**
     * This function must be kept up to date with any additions of data to the model.
     * It sets all values of the ClientModel back to default settings. Primarily used by test
     * cases and the logout feature.
     * Note: it does not clear the WebSocketClient instance nor the SocketID
     */
    public void clearInstance() {
        games.clear();
        playerName = "";
        authToken = null;
        currentGame = null;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    //****** GETTERS & SETTERS *****//
    public String getSocketID() {
        return socketID;
    }

    public WebSocketClient getSocket() {
        return socket;
    }

    private static ClientModel ourInstance = new ClientModel();

    public static ClientModel getInstance() {
        return ourInstance;
    }

    private ClientModel(){}

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public Player getCurrentPlayer() {
        Player player = null;

        for (Player p :
                currentGame.getPlayers()) {
            if (p.getName().equals(playerName)) {
                player = p;
                break;
            }
        }

        return player;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setSocket(WebSocketClient socket) {
        this.socket = socket;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setSocketID(String socketID) {
        this.socketID = socketID;
    }

    //for testing multiple clients
    public ClientModel getTestInstance() {return new ClientModel(); }

    public String getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(String playerTurn) {
        this.playerTurn = playerTurn;
    }
}

