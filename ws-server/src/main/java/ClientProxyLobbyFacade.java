

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IClientLobbyFacade;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;


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


    @Override
    public void createGame(String gameID, String message) {
        Game newGame = new Game();
        newGame.setGameID(gameID);
        ServerModel.instance().games.put(gameID, newGame);

        // This is called after the Server has attempted to get all games
        // If successful, message == null

        String className;
        Object param;
        if (message == null) {
            className = gameID.getClass().toString();
            param = gameID;
        } else {
            className = message.getClass().toString();
            param = message;
        }

        String[] paramTypes = {className};
        Object[] paramValues = {param};
        Command createGameClientCommand = CommandFactory.createCommand("ClientLobbyFacade", "_createGameReceived", paramTypes, paramValues);

        // TODO - Send createGameClientCommand to Client via socket
    }

    @Override
    public void updateGames(Game[] games, String message) {
        // This is called after the Server has attempted to get all games
        // If successful, message == null

        String className;
        Object param;
        if (message == null) {
            className = games.getClass().toString();
            param = games;
        } else {
            className = message.getClass().toString();
            param = message;
        }

        String[] paramTypes = {className};
        Object[] paramValues = {param};

        Command updateGamesClientCommand = CommandFactory.createCommand("ClientLobbyFacade", "_updateGamesReceived", paramTypes, paramValues);

        // TODO - Send updateGamesClientCommand to Client via socket
    }

    @Override
    public void joinGame(String gameID, String message) {
        // This is called after the Server has attempted to join game
        // If successful, message == null

        String className;
        Object param;
        if (message == null) {
            className = gameID.getClass().toString();
            param = gameID;
        } else {
            className = message.getClass().toString();
            param = message;
        }

        String[] paramTypes = {className};
        Object[] paramValues = {param};

        Command joinGameClientCommand = CommandFactory.createCommand("ClientLobbyFacade", "_joinGameReceived", paramTypes, paramValues);

        // TODO - Send joinGameClientCommand to Client via socket
    }

    @Override
    public void startGame(String gameID, String message) {
        // This is called after the Server has attempted to join game
        // If successful, message == null

        String className;
        Object param;
        if (message == null) {
            className = gameID.getClass().toString();
            param = gameID;
        } else {
            className = message.getClass().toString();
            param = message;
        }

        String[] paramTypes = {className};
        Object[] paramValues = {param};

        Command startGameClientCommand = CommandFactory.createCommand("ClientLobbyFacade", "_startGameReceived", paramTypes, paramValues);

        // TODO - Send startGameClientCommand to Client via socket
    }

    @Override
    public void leaveGame(String gameID, String message) {
        // This is called after the Server has attempted to join game
        // If successful, message == null

        String className;
        Object param;
        if (message == null) {
            className = gameID.getClass().toString();
            param = gameID;
        } else {
            className = message.getClass().toString();
            param = message;
        }

        String[] paramTypes = {className};
        Object[] paramValues = {param};

        Command leaveGameClientCommand = CommandFactory.createCommand("ClientLobbyFacade", "_leaveGameReceived", paramTypes, paramValues);

        // TODO - Send leaveGameClientCommand to Client via socket
    }

    @Override
    public void getPlayersForGame(String gameID, Player[] players, String message) {
        // This is called after the Server has attempted to join game
        // If successful, message == null

        String[] paramTypes;
        Object[] paramValues;

        if (message == null) {
            paramTypes = new String[2];
            paramValues = new Object[2];

            paramTypes[0] = gameID.getClass().toString();
            paramTypes[1] = players.getClass().toString();

            paramValues[0] = gameID;
            paramValues[0] = players;
        } else {
            paramTypes = new String[1];
            paramValues = new Object[1];

            paramTypes[0] = message.getClass().toString();

            paramValues[0] = message;
        }


        Command getPlayersForGameClientCommand = CommandFactory.createCommand("ClientLobbyFacade", "_getPlayersForGameReceived", paramTypes, paramValues);

        // TODO - Send getPlayersForGameClientCommand to Client via socket
    }
}
