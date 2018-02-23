import com.example.sharedcode.interfaces.IServerLobbyFacade;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;
import com.example.sharedcode.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ServerLobbyFacade implements IServerLobbyFacade {

    private static ServerLobbyFacade lobbyFacade;

    public static ServerLobbyFacade instance() {
        if (lobbyFacade == null) {
            lobbyFacade = new ServerLobbyFacade();
        }

        return lobbyFacade;
    }


    public static void _createGame(String authToken) {
        instance().createGame(authToken);
    }

    public static void _getGames(String authToken) {
        instance().getGames(authToken);
    }

    public static void _joinGame(String authToken, String gameID) {
        instance().joinGame(authToken, gameID);
    }

    public static void _leaveGame(String authToken, String gameID, String playerID) {
        instance().leaveGame(authToken, gameID, playerID);
    }

    public static void _startGame(String authToken, String gameID) {
        instance().startGame(authToken, gameID);
    }

    public static void _getPlayersForGame(String authToken, String gameID) {
        instance().getPlayersForGame(authToken, gameID);
    }

    public static void _playerColorChanged(String authToken, String playerID, String gameID, int color) {
        instance().playerColorChanged(authToken, playerID, gameID, color);
    }


    // Creates command to create game and send back to the client
    //Everyone needs to know that there is a new game. Send over the game to everyone
    @Override
    public void createGame(String authToken) {
       ServerModel.instance().createGame(authToken);
    }

    @Override
    public void getGames(String authToken) {
        ServerModel.instance().getAllGames(authToken);
    }

    //add caller to waiting room, send updated games list to everyone else.
    @Override
    public void joinGame(String authToken, String gameID) {
        ServerModel.instance().joinGame(authToken, gameID);
    }

    // If the game
    //removes player from list of joined game, then sends that to everyone
    //send everyone the updated games
    @Override
    public void leaveGame(String authToken, String gameID, String playerID) {
        ServerModel.instance().leaveGame(authToken, gameID, playerID);
    }

    //tell everyone to start the game who is in it, andupdate the games list for everyone else
    @Override
    public void startGame(String authToken, String gameID) {
        ServerModel.instance().startGame(authToken, gameID);
    }

    @Override
    public void getPlayersForGame(String authToken, String gameID) {
        ServerModel.instance().getPlayersForGame(authToken, gameID);
    }

    @Override
    public void playerColorChanged(String authToken, String gameID, String playerID, int color) {
        ServerModel.instance().playerColorChanged(authToken, gameID, playerID, color);
    }


}
