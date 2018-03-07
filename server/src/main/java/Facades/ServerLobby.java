package Facades;

import Communication.SocketManager;
import Model.ServerModel;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerLobbyFacade;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;
import com.example.sharedcode.model.User;
import org.eclipse.jetty.server.Server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;


/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ServerLobby implements IServerLobbyFacade {

    private static ServerLobby lobbyFacade;

    public static ServerLobby instance() {
        if (lobbyFacade == null) {
            lobbyFacade = new ServerLobby();
        }
        return lobbyFacade;
    }

    private final String CLASS_NAME = "e.mboyd6.tickettoride.Facades.ClientLobby";
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


    public static void _playerColorChanged(String authToken, String playerID, String gameID, int color) {
        instance().playerColorChanged(authToken, playerID, gameID, color);
    }


    // Creates command to create game and send back to the client
    //Everyone needs to know that there is a new game. Send over the game to everyone
    @Override
    public void createGame(String authToken) {
        // Don't need to check for existence of a new game because this should only be called when creating a brand new game
        String id = UUID.randomUUID().toString();
        Game newGame = new Game();
        newGame.setGameID(id);

        ServerModel.instance().getGames().put(id, newGame);

        String[] paramTypes = {newGame.getClass().toString()};
        Object[] paramValues = {newGame};
        Command createGameClientCommand = CommandFactory.createCommand(authToken, CLASS_NAME,
                "_createGameReceived", paramTypes, paramValues);

        ServerModel.instance().notifyObserversForUpdate(createGameClientCommand);

        SocketManager.instance().updateGameList(ServerModel.instance().getLobbyUserAuthTokens());
    }


    //Is this being used?
    @Override
    public void getGames(String authToken) {
        Object[] games = ServerModel.instance().getGames().values().toArray();
        Game[] gs = new Game[games.length];
        int i = 0;
        for (Object o : games) {
            gs[i++] = (Game) o;
        }
        // Message is empty
        String message = "";
        String[] paramTypes = {gs.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gs, message};

        Command updateGamesClientCommand = CommandFactory.createCommand(authToken, CLASS_NAME,
                "_updateGamesReceived", paramTypes, paramValues);

        //send this to all the clients
        ServerModel.instance().notifyObserversForUpdate(updateGamesClientCommand);
    }

    //add caller to waiting room, send updated games list to everyone else.
    @Override
    public void joinGame(String authToken, String gameID) {
        String message = "";

        String playerID = "";
        if (ServerModel.instance().getGames().containsKey(gameID)) {
            String usnm = ServerModel.instance().getAuthTokenToUsername().get(authToken);
            Player newPlayer = new Player(usnm,usnm, PlayerColors.NO_COLOR);

            // Only set message if we fail to add user to the game
            if (!ServerModel.instance().getGames().get(gameID).addPlayer(newPlayer)) {
                message = "Could not add player to game because it is already full";
            } else {
                playerID = newPlayer.getPlayerID();
                ServerModel.instance().getUsersInLobby()
                        .remove(ServerModel.instance().getLoggedInUsers().get(usnm).getUsername());
            }
        } else message = "could not find game in list";

        String[] paramTypes = {gameID.getClass().toString(),
                playerID.getClass().toString(),
                message.getClass().toString()};
        Object[] paramValues = {gameID, playerID, message};

        Command joinGameClientCommand = CommandFactory.createCommand(authToken, CLASS_NAME,
                "_joinGameReceived", paramTypes, paramValues);

        ServerModel.instance().notifyObserversForUpdate(joinGameClientCommand);

        //notify everyone in game and in lobby
        Collection<String> tokens = ServerModel.instance().getLobbyUserAuthTokens();
        tokens.addAll(ServerModel.instance().getPlayerAuthTokens(gameID));

        SocketManager.instance().updateGameList(tokens);
    }

    // If the game
    //removes player from list of joined game, then sends that to everyone
    //send everyone the updated games

    @Override
    public void leaveGame(String authToken, String gameID, String playerID) {
        String message = "";

        Map<String, Game> games = ServerModel.instance().getGames();
        // This returns false if playerID is not part of game
        if (!games.get(gameID).removePlayer(playerID)) {
            message = "Could not remove player because is not in the game";
        }
        String usnm = ServerModel.instance().getAuthTokenToUsername().get(authToken);
        User user = ServerModel.instance().getLoggedInUsers().get(usnm);
        ServerModel.instance().getUsersInLobby().put(user.getUsername(), user);
        Game game = games.get(gameID);
        //remove the game if there are no more players
        if (game != null && game.getPlayers() != null && game.getPlayers().size() == 0) {
            games.remove(gameID);
        }
        ServerModel.instance().setGames(games);

        String[] paramTypes = {gameID.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, message};

        Command leaveGameClientCommand = CommandFactory.createCommand(authToken, CLASS_NAME,
                "_leaveGameReceived", paramTypes, paramValues);

        ServerModel.instance().notifyObserversForUpdate(leaveGameClientCommand);

        Collection<String> tokens = ServerModel.instance().getPlayerAuthTokens(gameID);
        tokens.addAll(ServerModel.instance().getLobbyUserAuthTokens());
        SocketManager.instance().updateGameList(tokens);
    }

    //tell everyone to start the game who is in it, andupdate the games list for everyone else
    @Override
    public void startGame(String authToken, String gameID) {
        String message = "";
        Collection<String> tokens;
        if (!ServerModel.instance().getGames().containsKey(gameID)) {
            message = "Game doesn't exist.";
        } else {
            Game game = ServerModel.instance().getGames().get(gameID);
            if (game.getPlayers() == null) {
                message = "The server says no player exists in this game.";
            } else {
                // send a startGame command to everyone in the waitroom
                String[] paramTypes = {gameID.getClass().toString(), message.getClass().toString()};
                Object[] paramValues = {gameID, message};
                Command startGameClientCommand = CommandFactory.createCommand(authToken, CLASS_NAME,
                        "_startGameReceived", paramTypes, paramValues);
                SocketManager.instance().notifyPlayersInGame(gameID, startGameClientCommand);
            }
        }
        // send a notification to everyone that the game has started
        tokens = ServerModel.instance().getPlayerAuthTokens(gameID);
        tokens.addAll(ServerModel.instance().getLobbyUserAuthTokens());

        SocketManager.instance().updateGameList(tokens);


    }

    @Override
    public void playerColorChanged(String authToken, String gameID, String playerID, int color) {
        //Game game = ServerModel.instance().getGames().get(gameID);
        Collection<String> tokens = new ArrayList<String>();
        Boolean success = false;
        for (Player player : ServerModel.instance().getGames().get(gameID).getPlayers()) {
            if (player.getPlayerID().equals(playerID)) {
                player.setColor(color);
                success = true;
                break;
            }
        }
        tokens = ServerModel.instance().getPlayerAuthTokens(gameID);
        if (success) {
            SocketManager.instance().updateGameList(tokens);
        }
    }


    // this should only be sent to the people in the game.


}
