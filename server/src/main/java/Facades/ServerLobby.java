package Facades;

import Communication.SocketManager;
import Model.ServerModel;
import Persistence.PersistenceManager;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerLobbyFacade;
import com.example.sharedcode.interfaces.persistence.IGameDAO;
import com.example.sharedcode.model.*;
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

        IGameDAO gameDAO = PersistenceManager.getInstance().getDatabaseFactory().createGameDAO();
        gameDAO.addGame(newGame);
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
        Player newPlayer = null;
        Game game = null;

        String playerID = "";
        if (ServerModel.instance().getGames().containsKey(gameID)) {
            String username = ServerModel.instance().getAuthTokenToUsername().get(authToken);

            game = ServerModel.instance().getGames().get(gameID);
            newPlayer = new Player(username, username, game.getColorNotChosen());


            // Only set message if we fail to add user to the game
            if (!game.addPlayer(newPlayer)) {
                message = "Could not add player to game because it is already full";
            } else {
                playerID = newPlayer.getPlayerID();
                ServerModel.instance().getUsersInLobby()
                        .remove(ServerModel.instance().getLoggedInUsers().get(username).getUsername());
            }
        } else {
            message = "could not find game in list";
        }

        String[] paramTypes = {message.getClass().toString(),
                newPlayer.getClass().toString(),
                game.getClass().toString()};

        Object[] paramValues = {message, newPlayer, game};

        Command joinGameClientCommand = CommandFactory.createCommand(authToken, CLASS_NAME,
                "_joinGameReceived", paramTypes, paramValues);

        ServerModel.instance().notifyObserversForUpdate(joinGameClientCommand);

        //notify everyone in game and in lobby
        Collection<String> tokens = ServerModel.instance().getLobbyUserAuthTokens();
        tokens.addAll(ServerModel.instance().getPlayerAuthTokens(gameID));
        SocketManager.instance().updateGameList(tokens);

        ServerChat._getChatHistory(authToken, gameID);
    }

    // If the game
    //removes player from list of joined game, then sends that to everyone
    //send everyone the updated games

    @Override
    public void leaveGame(String authToken, String gameID, String playerID) {
        String message = "";

        Map<String, Game> games = ServerModel.instance().getGames();
        // This returns false if playerID is not part of game
        if (games.get(gameID).removePlayer(playerID)) {
            String usnm = ServerModel.instance().getAuthTokenToUsername().get(authToken);
            User user = ServerModel.instance().getLoggedInUsers().get(usnm);
            if(user != null) { // check in case socket connection closes.
                ServerModel.instance().getUsersInLobby().put(user.getUsername(), user);
            }
            Game game = games.get(gameID);
            if (game != null && game.getPlayers() != null && game.getPlayers().size() == 0) {
                games.remove(gameID);
            }
        } else message = "Could not remove player because is not in the game";

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
        if (ServerModel.instance().getGames().containsKey(gameID)) {
            Game game = ServerModel.instance().getGames().get(gameID);
            if (game.getPlayers() != null) {
                if(!game.isStarted())
                {
                    // INITIALIZE GAME
                    GameInitializer gi = new GameInitializer();
                    game = gi.initializeGame(game);
                    game.setStarted(true);

                    for (Player p :
                            game.getPlayers()) {
                        game.getHistory().add(p.getName() + " entered the game.");
                    }

                    ServerModel.instance().getGames().put(gameID, game);

                    //notify everyone in the lobby and in the waitroom of the changes.
                    tokens = ServerModel.instance().getPlayerAuthTokens(gameID);
                    tokens.addAll(ServerModel.instance().getLobbyUserAuthTokens());
                    SocketManager.instance().updateGameList(tokens);

                    String[] paramTypes = {message.getClass().toString(), game.getClass().toString()};
                    Object[] paramValues = {message, game};
                    Command startGameClientCommand = CommandFactory.createCommand(authToken, CLASS_NAME,
                            "_startGameReceived", paramTypes, paramValues);
                    SocketManager.instance().notifyPlayersInGame(gameID, startGameClientCommand);

                    IGameDAO gameDAO = PersistenceManager.getInstance().getDatabaseFactory().createGameDAO();
                    gameDAO.updateGame(gameID, game);
                } else message = "the game you're trying to start has already started!";
            } else message = "The server says no player exists in this game.";
        } else message = "Game doesn't exist.";

        if(!message.equals("")) {
            Game game = new Game();
            String[] paramTypes = {message.getClass().toString(), game.getClass().toString()};
            Object[] paramValues = {message, game};
            Command errorMessage = CommandFactory.createCommand(authToken, CLASS_NAME,
                    "_startGameReceived", paramTypes, paramValues);
            ServerModel.instance().notifyObserversForUpdate(errorMessage);
        }

        // TODO: - serialize/save game in the database
    }

    @Override
    public void playerColorChanged(String authToken, String gameID, String playerName, int color) {
        //Game game = ServerModel.instance().getGames().get(gameID);
        Collection<String> tokens = new ArrayList<String>();
        Boolean success = false;

        for (Player player :
                ServerModel.instance().getGames().get(gameID).getPlayers()) {
            if (player.getName().equals(playerName)) {
                player.setColor(color);
                success = true;
            }
        }

        tokens = ServerModel.instance().getPlayerAuthTokens(gameID);
        tokens.addAll(ServerModel.instance().getLobbyUserAuthTokens());

        if (success) {
            SocketManager.instance().updateGameList(tokens);
        }
    }


    // this should only be sent to the people in the game.


}
