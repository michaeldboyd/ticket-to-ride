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

        updateGamesBroadcast();
    }

    @Override
    public void getGames(String authToken) {
        Object[] games = ServerModel.instance().getGames().values().toArray();
        Game[] gs = new Game[games.length];
        int i = 0;
        for (Object o : games) {
            gs[i++] = (Game) o;
        }
        // TODO - message parameter is always null -- we should remove it or figure out potential errors/problems

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

            Player newPlayer = new Player(UUID.randomUUID().toString(),
                    ServerModel.instance().getAuthTokenToUsername().get(authToken),
                    PlayerColors.NO_COLOR);

            // Only set message if we fail to add user to the game
            if (!ServerModel.instance().getGames().get(gameID).addPlayer(newPlayer)) {
                message = "Could not add player to game because it is already full";
            } else {
                playerID = newPlayer.getPlayerID();
            }
        } else message = "could not find game in list";

        String[] paramTypes = {gameID.getClass().toString(),
                playerID.getClass().toString(),
                message.getClass().toString()};
        Object[] paramValues = {gameID, playerID, message};

        Command joinGameClientCommand = CommandFactory.createCommand(authToken, CLASS_NAME,
                "_joinGameReceived", paramTypes, paramValues);

        ServerModel.instance().notifyObserversForUpdate(joinGameClientCommand);
        updateGamesBroadcast();
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

        String[] paramTypes = {gameID.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, message};

        Command leaveGameClientCommand = CommandFactory.createCommand(authToken, CLASS_NAME, "_leaveGameReceived", paramTypes, paramValues);

        Game game = games.get(gameID);

        //remove the game if there are no more players
        if (game != null && game.getPlayers() != null && game.getPlayers().size() == 0) {
            games.remove(gameID);
        }
        ServerModel.instance().setGames(games);
        ServerModel.instance().notifyObserversForUpdate(leaveGameClientCommand);
        updateGamesBroadcast();
    }

    //tell everyone to start the game who is in it, andupdate the games list for everyone else
    @Override
    public void startGame(String authToken, String gameID) {
        String message = "";

        if (!ServerModel.instance().getGames().containsKey(gameID)) {
            message = "Game doesn't exist.";
        } else {
            Game game = ServerModel.instance().getGames().get(gameID);

            Collection<String> authTokens = new ArrayList<>();
            if (game.getPlayers() != null) {
                for (Player p : game.getPlayers()) {
                    User user = ServerModel.instance().getAllUsers().get(p.getName());
                    if (user != null) {
                        authTokens.add(user.getAuthtoken());
                    }
                }
                //update the model

                ServerModel.instance().getGames().put(gameID, game);
                notifyPlayersOfGameStarted(authTokens, message, gameID);
            }
        }

        String[] paramTypes = {gameID.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, message};

        Command startGameClientCommand = CommandFactory.createCommand(authToken, CLASS_NAME,
                "_startGameReceived", paramTypes, paramValues);

        ServerModel.instance().notifyObserversForUpdate(startGameClientCommand);
        updateGamesBroadcast();
    }

    @Override
    public void playerColorChanged(String authToken, String gameID, String playerID, int color) {
        Game game = ServerModel.instance().getGames().get(gameID);

        Boolean success = false;
        for (Player player : game.getPlayers()) {
            if (player.getPlayerID().equals(playerID)) {
                player.setColor(color);
                success = true;
                break;
            }
        }
        ServerModel.instance().getGames().put(gameID, game);

        if (success) {
            updateGamesBroadcast();
        }
    }

    private void updateGamesBroadcast() {
        //TODO is there a better way to send the games over the server?
        //just send this to the people in the lobby & waiting room
        Object[] games = ServerModel.instance().getGames().values().toArray();
        Game[] gs = new Game[games.length];
        int i = 0;
        for (Object o : games) {
            gs[i++] = (Game) o;
        }
        // Message is blank
        String message = "";
        String[] paramTypes = {gs.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gs, message};
        Command updateGamesClientCommand = CommandFactory.createCommand(null, CLASS_NAME,
                "_updateGamesReceived", paramTypes, paramValues);
        SocketManager.instance().sendBroadcast(ServerModel.instance().getLoggedInSessions().keySet(), updateGamesClientCommand);
    }

    private void notifyPlayersOfGameStarted(Collection<String> tokens, String message, String gameID) {
        String[] paramTypes = {message.getClass().toString(), gameID.getClass().toString()};
        Object[] paramValues = {gameID, message};
        Command command = CommandFactory.createCommand("", CLASS_NAME,
                "_startGameReceived", paramTypes, paramValues);
        //TODO this eventually should be changed so it only sends the command to the people in the right game.
        for (String token : tokens) {
            command.set_authToken(token);
            ServerModel.instance().notifyObserversForUpdate(command);
        }
    }

}
