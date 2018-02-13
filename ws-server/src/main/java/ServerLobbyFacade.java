import com.example.sharedcode.interfaces.IServerLobbyFacade;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;

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



    int playerLimit = 5;

    // Creates command to create game and send back to the client
    //Everyone needs to know that there is a new game. Send over the game to everyone
    @Override
    public void createGame(String authToken) {
        // Don't need to check for existence of a new game because this should only be called when creating a brand new game
        String id = UUID.randomUUID().toString();
        Game newGame = new Game();
        newGame.setGameID(id);
        ServerModel.instance().games.put(id, newGame);
        // Create a random UUID for gameID to pass to createGame method
        ClientProxyLobbyFacade.instance().createGame(authToken, newGame);
    }

    @Override
    public void getGames(String authToken) {
        Game[] games = (Game[]) ServerModel.instance().games.values().toArray();

        // TODO - message parameter is always null -- we should remove it or figure out potential errors/problems
        //send this to all the clients
        ClientProxyLobbyFacade.instance().updateGames(authToken, games, "");
    }

    //add caller to waiting room, send updated games list to everyone else.
    @Override
    public void joinGame(String authToken, String gameID) {
        String message = null;

        if (ServerModel.instance().games.containsKey(gameID)) {

            Player newPlayer = new Player(UUID.randomUUID().toString(), ServerModel.instance().authTokenToUsername.get(authToken), PlayerColors.NO_COLOR);

            // Only set message if we fail to add user to the game
            if (!ServerModel.instance().games.get(gameID).addPlayer(newPlayer)) {
                message = "Could not add player to game because it is already full";
            }
        }

        ClientProxyLobbyFacade.instance().joinGame(authToken, gameID, message);
    }

    // If the game
    //removes player from list of joined game, then sends that to everyone
    //send everyone the updated games
    @Override
    public void leaveGame(String authToken, String gameID, String playerID) {
        String message = "";

        // This returns false if playerID is not part of game
        if (!ServerModel.instance().games.get(gameID).removePlayer(playerID)) {
            message = "Could not remove player because is not in the game";
        }

        ClientProxyLobbyFacade.instance().leaveGame(authToken, gameID, message);
    }

    //tell everyone to start the game who is in it, andupdate the games list for everyone else
    @Override
    public void startGame(String authToken, String gameID) {
        String message = "";

        if (!ServerModel.instance().games.containsKey(gameID)) {
            message = "Game doesn't exist.";
        }

        ClientProxyLobbyFacade.instance().startGame(authToken, gameID, message);
    }

    @Override
    public void getPlayersForGame(String authToken, String gameID) {
        String message = "";
        Player[] players;

        if (ServerModel.instance().games.containsKey(gameID)) {

            players = (Player[]) ServerModel.instance().games.get(gameID).getPlayers().toArray();
        } else {
            players = new Player[0];
            message = "Game does not exist.";
        }

        ClientProxyLobbyFacade.instance().getPlayersForGame(authToken, players, message, gameID);
    }

    @Override
    public void playerColorChanged(String authToken, String gameID, String playerID, int color) {
        Game game = ServerModel.instance().games.get(gameID);

        Boolean success = false;
        for (Player player :
                game.getPlayers()) {
            if (player.getPlayerID().equals(playerID)) {
                player.setColor(color);
                success = true;
                break;
            }
        }

        if (success) {
            ClientProxyLobbyFacade.instance().playerColorChanged(gameID, playerID, color);
        }
    }


}
