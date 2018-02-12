import com.example.sharedcode.interfaces.IServerLobbyFacade;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

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


    public static void _createGame() {
        instance().createGame();
    }

    public static void _getGames() {
        instance().getGames();
    }

    public static void _joinGame(String gameID, String playerID) {
        instance().joinGame(gameID, playerID);
    }

    public static void _leaveGame(String gameID, String playerID) {
        instance().leaveGame(gameID, playerID);
    }

    public static void _startGame(String gameID) {
        instance().startGame(gameID);
    }

    public static void _getPlayersForGame(String gameID) {
        instance().getPlayersForGame(gameID);
    }



    int playerLimit = 5;

    // Creates command to create game and send back to the client
    @Override
    public void createGame() {
        // Don't need to check for existence of a new game because this should only be called when creating a brand new game
        String id = UUID.randomUUID().toString()
        Game newGame = new Game();
        newGame.setGameID(id);
        ServerModel.instance().games.put(id, newGame);

        // TODO - message parameter is always empty string -- we should remove it or figure out potential errors/problems
        // Create a random UUID for gameID to pass to createGame method
        ClientProxyLobbyFacade.instance().createGame(id, "");
    }

    @Override
    public void getGames() {
        Game[] games = (Game[]) ServerModel.instance().games.values().toArray();

        // TODO - message parameter is always empty string -- we should remove it or figure out potential errors/problems
        ClientProxyLobbyFacade.instance().updateGames(games, "");
    }

    @Override
    public void joinGame(String gameID, String playerID) {
        String message = "";

        if (ServerModel.instance().games.containsKey(gameID)) {

            // this line is broken. removing it for now
            // Only set message if we fail to add user to the game
            if (false) { // PREVIOUS CONDITIONAL: !ServerModel.instance().games.get(gameID).addPlayer(playerID)
                message = "Could not add player to game [id = " + gameID + "]";
            }

        }

        ClientProxyLobbyFacade.instance().joinGame(gameID, message);
    }

    @Override
    public void leaveGame(String gameID, String playerID) {
        String message = "";

        // This returns false if playerID is not part of game
        if (!ServerModel.instance().games.get(gameID).removePlayer(playerID)) {
            message = "couldn't remove player because wasn't in game yet";
        }

        ClientProxyLobbyFacade.instance().leaveGame(gameID, message);
    }


    @Override
    public void startGame(String gameID) {
        String message = "";

        if (!ServerModel.instance().games.containsKey(gameID)) {
            message = "Game doesn't exist.";
        }

        ClientProxyLobbyFacade.instance().startGame(gameID, message);
    }

    @Override
    public void getPlayersForGame(String gameID) {
        String message = "";
        Player[] players = null;

        //create commandresult
        if (ServerModel.instance().games.containsKey(gameID)) {

            players = (Player[]) ServerModel.instance().games.get(gameID).getPlayers().toArray();
        } else {
            players = new Player[0];
            message = "Game does not exist.";
        }

        ClientProxyLobbyFacade.instance().getPlayersForGame(gameID, players, message);
    }

}
