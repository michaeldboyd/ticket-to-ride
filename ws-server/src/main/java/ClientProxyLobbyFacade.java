

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IClientLobbyFacade;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.google.gson.Gson;

import java.io.IOException;


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
    //Michael put this code in the ServerLobbyFacade because it interacts with the model.
     /*    Game newGame = new Game();
        newGame.setGameID(gameID);
        ServerModel.instance().games.put(gameID, newGame);*/

        // This is called after the Server has attempted to get all games
        // If successful, message == "" [empty string]

        String[] paramTypes = {gameID.getClass().toString(), message.getClass().toString()};
        String[] paramValues = {gameID, message};
        Command createGameClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLobbyFacade", "_createGameReceived", paramTypes, paramValues);

        // TODO - Send createGameClientCommand to Client via socket
        org.eclipse.jetty.websocket.api.Session sess = ServerModel.instance().session;
        Sender.sendCommand(createGameClientCommand, sess);

    }

    @Override
    public void updateGames(Game[] games, String message) {
        // This is called after the Server has attempted to get all games
        // If successful, message == "" [empty string]

        String[] paramTypes = {games.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {games, message};

        Command updateGamesClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLobbyFacade", "_updateGamesReceived", paramTypes, paramValues);

        org.eclipse.jetty.websocket.api.Session sess = ServerModel.instance().session;
        Sender.sendCommand(updateGamesClientCommand, sess);
    }

    @Override
    public void joinGame(String gameID, String message) {
        // This is called after the Server has attempted to join game
        // If successful, message == "" [empty string]

        String[] paramTypes = {gameID.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, message};

        Command joinGameClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLobbyFacade", "_joinGameReceived", paramTypes, paramValues);

        // TODO - Send joinGameClientCommand to Client via socket
        org.eclipse.jetty.websocket.api.Session sess = ServerModel.instance().session;
        Sender.sendCommand(joinGameClientCommand, sess);
    }

    @Override
    public void startGame(String gameID, String message) {
        // This is called after the Server has attempted to join game
        // If successful, message == "" [empty string]

        String[] paramTypes = {gameID.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, message};

        Command startGameClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLobbyFacade", "_startGameReceived", paramTypes, paramValues);

        // TODO - Send startGameClientCommand to Client via socket
        org.eclipse.jetty.websocket.api.Session sess = ServerModel.instance().session;
        Sender.sendCommand(startGameClientCommand, sess);
    }

    @Override
    public void leaveGame(String gameID, String message) {
        // This is called after the Server has attempted to join game
        // If successful, message == "" [empty string]

        String[] paramTypes = {gameID.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, message};

        Command leaveGameClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLobbyFacade", "_leaveGameReceived", paramTypes, paramValues);
        // TODO - Send leaveGameClientCommand to Client via socket
        org.eclipse.jetty.websocket.api.Session sess = ServerModel.instance().session;
        Sender.sendCommand(leaveGameClientCommand, sess);

    }

    @Override
    public void getPlayersForGame(String gameID, Player[] players, String message) {
        // This is called after the Server has attempted to join game
        // If successful, message == "" [empty string]

        String[] paramTypes = {gameID.getClass().toString(), players.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, players, message};


        Command getPlayersForGameClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLobbyFacade", "_getPlayersForGameReceived", paramTypes, paramValues);

        // TODO - Send getPlayersForGameClientCommand to Client via socket
        org.eclipse.jetty.websocket.api.Session sess = ServerModel.instance().session;
        Sender.sendCommand(getPlayersForGameClientCommand, sess);
    }
}
