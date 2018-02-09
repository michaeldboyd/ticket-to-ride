package e.mboyd6.tickettoride.Communication;

import com.example.sharedcode.interfaces.IClientLobbyFacade;
import com.example.sharedcode.interfaces.IServerLobbyFacade;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ClientLobbyFacade implements IClientLobbyFacade {

    private static ClientLobbyFacade _instance = new ClientLobbyFacade();

    private ClientLobbyFacade() {
    }

    public static ClientLobbyFacade instance() {
        return _instance;
    }


    static void _createGame(String gameID, String message) {
        instance().createGame(gameID, message);
    }

    static void _updateGames(Game[] games, String message) {
        instance().updateGames(games, message);
    }

    static void _joinGame(String gameID, String message) {
        instance().joinGame(gameID, message);
    }

    static void _startGame(String gameID, String message) {
        instance().startGame(gameID, message);
    }

    static void _getPlayersForGame(String gameID, Player[] players, String message) {
        instance().getPlayersForGame(gameID, players, message);
    }




    //***** THESE METHODS ARE NOT CALLED FROM THE CLIENT PROXY--ONLY FROM THE CORRESPONDING STATIC METHODS *****

    @Override
    public void createGame(String gameID, String message) {

    }

    @Override
    public void updateGames(Game[] games, String message) {

    }

    @Override
    public void joinGame(String gameID, String message) {

    }

    @Override
    public void startGame(String gameID, String message) {

    }

    @Override
    public void leaveGame(String gameID, String message) {

    }

    @Override
    public void getPlayersForGame(String gameID, Player[] players, String message) {

    }
}
