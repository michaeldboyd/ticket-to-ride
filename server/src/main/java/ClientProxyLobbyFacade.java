

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IClientLobbyFacade;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;


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


        //SEND JOIN GAME COMMAND TO CREATOR OF GAME
        String[] paramTypes = {"".getClass().toString(), newGame.getClass().toString()};
        Object[] paramValues = {"", newGame};
        Command createGameClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLobbyFacade",
                "_createGameReceived", paramTypes, paramValues);
        Sender.sendCommand(createGameClientCommand, authToken);

        updateGamesBroadcast();
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
    public void joinGame(String authToken, String message, String playerID, String gameID) {
        // This is called after the Server has attempted to join game
        // If successful, message == "" [empty string]

        updateGamesBroadcast();
        String[] paramTypes = {"".getClass().toString(), gameID.getClass().toString(), playerID.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {"", gameID, playerID, message};

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
        String[] paramTypes = {"".getClass().toString(), gameID.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {"", gameID, message};

        Command startGameClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLobbyFacade",
                "_startGameReceived", paramTypes, paramValues);

        // TODO - Send startGameClientCommand to Client via socket
        org.eclipse.jetty.websocket.api.Session sess = ServerModel.instance().session;
        Sender.sendCommand(startGameClientCommand, authToken);
        updateGamesBroadcast();
    }

    public void notifyPlayersOfGameStarted(Collection<String> tokens, String message, String gameID)
    {
        String[] paramTypes = {"".getClass().toString(), message.getClass().toString(), gameID.getClass().toString()};
        Object[] paramValues = {"", gameID, message};
        Command command = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLobbyFacade",
                "_startGameReceived", paramTypes, paramValues);
        for(String token : tokens)
        {
            Sender.sendCommand(command, token);
        }
    }
    @Override
    public void leaveGame(String authToken, String gameID, String message) {
        // This is called after the Server has attempted to join game
        // If successful, message == "" [empty string]
        updateGamesBroadcast();
        String[] paramTypes = {"".getClass().toString(), gameID.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {"", gameID, message};

        Command leaveGameClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLobbyFacade", "_leaveGameReceived", paramTypes, paramValues);
        // TODO - Send leaveGameClientCommand to Client via socket
        org.eclipse.jetty.websocket.api.Session sess = ServerModel.instance().session;
        Sender.sendCommand(leaveGameClientCommand, authToken);

        Game game = ServerModel.instance().games.get(gameID);

        if (game != null && game.getPlayers() != null && game.getPlayers().size() == 0) {
            ServerModel.instance().games.remove(gameID);
        }

        updateGamesBroadcast();
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

    @Override
    public void playerColorChanged(String gameID, String playerID, int color) {
        // This will tell the client to
        updateGamesBroadcast();
    }


    private void updateGamesBroadcast() {
        Object[] games = ServerModel.instance().games.values().toArray();
        Game[] gs = new Game[games.length];
        int i=0;
        for(Object o : games) { gs[i++] = (Game)o; }
        for(Game g:gs) {
            System.out.println(g.getGameID());
        }
        String[] paramTypes = {"".getClass().toString(), gs.getClass().toString(), "".getClass().toString()};
        Object[] paramValues = {"", gs, ""};
        Command updateGamesClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLobbyFacade",
                "_updateGamesReceived", paramTypes, paramValues);
        Sender.sendBroadcast(updateGamesClientCommand);
    }
}
