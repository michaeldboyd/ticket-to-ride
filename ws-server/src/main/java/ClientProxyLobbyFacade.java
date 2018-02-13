

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


    /**
     * Creates the new game and adds to server model.
     * Creates command and sends it back to client.
     */
    @Override
    public void createGame(String authToken, Game newGame) {

        // SEND UPDATED GAMES LIST TO ERRYBODY


        updateGamesBroadcast();

        //SEND JOIN GAME COMMAND TO CREATOR OF GAME
        String[] paramTypes = {newGame.getClass().toString()};
        Object[] paramValues = {newGame};
        Command createGameClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLobbyFacade",
                "_createGameReceived", paramTypes, paramValues);
        org.eclipse.jetty.websocket.api.Session sess = ServerModel.instance().session;
        Sender.sendCommand(createGameClientCommand, authToken);
    }

    @Override
    public void updateGames(String authToken, Game[] games, String message) {
        // This is called after the Server has attempted to get all games
        // If successful, message == "" [empty string]

        String[] paramTypes = {games.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {games, message};

        Command updateGamesClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLobbyFacade",
                "_updateGamesReceived", paramTypes, paramValues);

        org.eclipse.jetty.websocket.api.Session sess = ServerModel.instance().session;
        Sender.sendCommand(updateGamesClientCommand, authToken);
    }

    @Override
    public void joinGame(String authToken, String message, String gameID) {
        // This is called after the Server has attempted to join game
        // If successful, message == "" [empty string]

        updateGamesBroadcast();
        String[] paramTypes = {gameID.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, message};

        Command joinGameClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLobbyFacade",
                "_joinGameReceived", paramTypes, paramValues);

        // TODO - Send joinGameClientCommand to Client via socket
        org.eclipse.jetty.websocket.api.Session sess = ServerModel.instance().session;
        Sender.sendCommand(joinGameClientCommand, authToken);
    }

    @Override
    public void startGame(String authToken, String message, String gameID) {
        // This is called after the Server has attempted to join game
        // If successful, message == "" [empty string]
        updateGamesBroadcast();
        String[] paramTypes = {gameID.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, message};

        Command startGameClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLobbyFacade", "_startGameReceived", paramTypes, paramValues);

        // TODO - Send startGameClientCommand to Client via socket
        org.eclipse.jetty.websocket.api.Session sess = ServerModel.instance().session;
        Sender.sendCommand(startGameClientCommand, authToken);
    }

    @Override
    public void leaveGame(String authToken, String message, String gameID) {
        // This is called after the Server has attempted to join game
        // If successful, message == "" [empty string]
        updateGamesBroadcast();
        String[] paramTypes = {gameID.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, message};

        Command leaveGameClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLobbyFacade", "_leaveGameReceived", paramTypes, paramValues);
        // TODO - Send leaveGameClientCommand to Client via socket
        org.eclipse.jetty.websocket.api.Session sess = ServerModel.instance().session;
        Sender.sendCommand(leaveGameClientCommand, authToken);

    }

    @Override
    public void getPlayersForGame(String authToken, Player[] players, String message, String gameID) {
        // This is called after the Server has attempted to join game
        // If successful, message == "" [empty string]

        String[] paramTypes = {gameID.getClass().toString(), players.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, players, message};


        Command getPlayersForGameClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLobbyFacade", "_getPlayersForGameReceived", paramTypes, paramValues);

        // TODO - Send getPlayersForGameClientCommand to Client via socket
        Sender.sendCommand(getPlayersForGameClientCommand, authToken);
    }

    private void updateGamesBroadcast()
    {
        Game[] games = (Game[]) ServerModel.instance().games.values().toArray();
        String[] paramTypes1 = {games.getClass().toString(), "".getClass().toString()};
        Object[] paramValues1 = {games, ""};
        Command updateGamesClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLobbyFacade",
                "_updateGamesReceived", paramTypes1, paramValues1);
        Sender.sendBroadcast(updateGamesClientCommand);
    }

}
