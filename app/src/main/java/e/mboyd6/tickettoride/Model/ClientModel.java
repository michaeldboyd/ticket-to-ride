package e.mboyd6.tickettoride.Model;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by jonathanlinford on 2/5/18.
 */

public class ClientModel extends Observable {

    private ArrayList<Game> games = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer = new Player();

    public enum UpdateType {
        GAMELIST, PLAYERLIST, GAMESTARTED
    }

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

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }



}

