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

    public static void _getGames() {
        instance().getGames();
    }

    public static void _joinGame(String gameID, String username) {
        instance().joinGame(gameID, username);
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

    public static void _playerColorChanged(String playerID, String gameID, PlayerColors color) {
        instance().playerColorChanged(playerID, gameID, color);
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
        ClientProxyLobbyFacade.instance().joinGame(id, "");
    }

    @Override
    public void getGames() {
        Game[] games = (Game[]) ServerModel.instance().games.values().toArray();

        // TODO - message parameter is always null -- we should remove it or figure out potential errors/problems
        //send this to all the clients
        ClientProxyLobbyFacade.instance().updateGames(games, "");
    }

    //add caller to waiting room, send updated games list to everyone else.
    @Override
    public void joinGame(String gameID, String username) {
        String message = null;

        if (ServerModel.instance().games.containsKey(gameID)) {

            Player newPlayer = new Player(UUID.randomUUID().toString(), username, PlayerColors.NO_COLOR);

            // Only set message if we fail to add user to the game
            if (!ServerModel.instance().games.get(gameID).addPlayer(newPlayer)) {
                message = "Could not add player to game because it is already full";
            }
        }

        ClientProxyLobbyFacade.instance().joinGame(gameID, message);
    }

    // If the game
    //removes player from list of joined game, then sends that to everyone
    //send everyone the updated games
    @Override
    public void leaveGame(String gameID, String playerID) {
        String message = "";

        // This returns false if playerID is not part of game
        if (!ServerModel.instance().games.get(gameID).removePlayer(playerID)) {
            message = "Could not remove player because is not in the game";
        }

        ClientProxyLobbyFacade.instance().leaveGame(gameID, message);
    }

    //tell everyone to start the game who is in it, andupdate the games list for everyone else
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
        Player[] players;

        if (ServerModel.instance().games.containsKey(gameID)) {

            players = (Player[]) ServerModel.instance().games.get(gameID).getPlayers().toArray();
        } else {
            players = new Player[0];
            message = "Game does not exist.";
        }

        ClientProxyLobbyFacade.instance().getPlayersForGame(gameID, players, message);
    }

    @Override
    public void playerColorChanged(String playerID, String gameID, PlayerColors color) {
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
            ClientProxyLobbyFacade.instance().playerColorChanged(playerID, gameID, color);
        }
    }


}
