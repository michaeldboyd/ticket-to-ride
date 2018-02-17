

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IClientLobbyFacade;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;


/**
 * Created by eric on 2/7/18.
 */

public class ClientProxyLobbyFacade implements IClientLobbyFacade {

    private static ClientProxyLobbyFacade lobbyFacade;

    public static ClientProxyLobbyFacade instance() {
        if (lobbyFacade == null) {
            lobbyFacade = new ClientProxyLobbyFacade();
        }

        return lobbyFacade;
    }

    private ClientProxyLobbyFacade() {}


    /**
     * Creates the new game and adds to server model.
     * Creates command and sends it back to client.
     */
    @Override
    public void createGame(String authToken, Game newGame) {

    }

    @Override
    public void updateGames(String authToken, Game[] games, String message) {
        // This is called after the Server has attempted to get all games
        // If successful, message == "" [empty string]


    }

    @Override
    public void joinGame(String authToken, String message, String playerID, String gameID) {
        // This is called after the Server has attempted to join game
        // If successful, message == "" [empty string]


    }

    @Override
    public void startGame(String authToken, String message, String gameID) {
        // This is called after the Server has attempted to join game
        // If successful, message == "" [empty string]

    }


    @Override
    public void leaveGame(String authToken, String gameID, String message) {
        // This is called after the Server has attempted to join game
        // If successful, message == "" [empty string]

    }

    @Override
    public void getPlayersForGame(String authToken, Player[] players, String message, String gameID) {
        // This is called after the Server has attempted to join game
        // If successful, message == "" [empty string]


    }

    @Override
    public void playerColorChanged(String gameID, String playerID, int color) {
        // This will tell the client to

    }



}
