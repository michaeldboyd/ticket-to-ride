package e.mboyd6.tickettoride.Model;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.websocket.Session;

/**
 * Created by jonathanlinford on 2/5/18.
 */

public class ClientModel extends Observable {

    //Game data
    private ArrayList<Game> games = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();

    //Current player data
    private Player currentPlayer = new Player("0", "NO PLAYER YET", PlayerColors.RED);
    private String authToken;
    private String loginResponse;

    private Game currentGame;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    private Session session;
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


    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }
}

