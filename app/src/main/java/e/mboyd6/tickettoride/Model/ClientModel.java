package e.mboyd6.tickettoride.Model;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.websocket.Session;

/**
 * Created by jonathanlinford on 2/5/18.
 */

public class ClientModel extends Observable {

    private static final ClientModel ourInstance = new ClientModel();

    public static ClientModel getInstance() {
        return ourInstance;
    }

    private ClientModel() {
    }

    private ArrayList<Game> games = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer = new Player();
    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public enum UpdateType {
        GAMELIST, PLAYERLIST, GAMESTARTED
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

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }



}

