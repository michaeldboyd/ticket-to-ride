package e.mboyd6.tickettoride.Model;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

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
    private ArrayList<Player> players = new ArrayList<>();

    //Current player data
    private Player currentPlayer = new Player();
    private String authToken;
    private String loginResponse;
    private WebSocketClient socket;

    public WebSocketClient getSocket() {
        return socket;
    }
    //TODO: put all this in the socket manager

    private static final ClientModel ourInstance = new ClientModel();

    public static ClientModel getInstance() {
        return ourInstance;
    }

    private ClientModel() {
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
        notifyObservers(UpdateType.GAMELIST);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
        notifyObservers(UpdateType.PLAYERLIST);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getAuthToken() {
        //TODO: Notify presenters of changes
        notifyObservers(UpdateType.REGISTERRESPONSE);
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(String loginResponse) {
        this.loginResponse = loginResponse;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }


    public void setSocket(WebSocketClient socket) {
        this.socket = socket;
    }
}

