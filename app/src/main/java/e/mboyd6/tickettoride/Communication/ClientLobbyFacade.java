package e.mboyd6.tickettoride.Communication;

import com.example.sharedcode.interfaces.IClientLobbyFacade;
import com.example.sharedcode.interfaces.IServerLobbyFacade;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ClientLobbyFacade implements IClientLobbyFacade {

    private static ClientLobbyFacade lobbyFacade;

    public static ClientLobbyFacade instance() {
        if (lobbyFacade == null) {
            lobbyFacade = new ClientLobbyFacade();
        }

        return lobbyFacade;
    }


    public static void _createGameReceived(String gameID, String message) {
        instance().createGame(gameID, message);
    }

    public static void _updateGamesReceived(Game[] games, String message) {
        instance().updateGames(games, message);
    }

    public static void _joinGameReceived(String gameID, String message) {
        instance().joinGame(gameID, message);
    }

    public static void _startGameReceived(String gameID, String message) {
        instance().startGame(gameID, message);
    }

    public static void _leaveGameReceived(String gameID, String message) {
        instance().leaveGame(gameID, message);
    }
    static void _getPlayersForGameReceived(String gameID, Player[] players, String message) {
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
