package Communication;

import Model.ServerModel;
import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;


import java.io.IOException;
import java.util.*;

/**
 * Socket manager stores the logic and data members required to communicate all the generic commands
 * back to the client to everyone, to a single person, or to everyone in a game.
 * @invariant Server must be running
 * @invariant ServerModel must implement observable pattern
 * @invariant SocketManager must be subscribed to the observable pattern.
 */
public class SocketManager implements Observer {
    private static SocketManager _instance;

    /**
     * returns the singleton instance of the socket manager
     * @pre none
     * @post _instance != null
     * @return _instance
     */
    public static SocketManager instance() {

        if (_instance == null){
            _instance = new SocketManager();
        }

        return _instance;
    }

    private final String CLIENT_LOBBY_CLASS_NAME = "e.mboyd6.tickettoride.Facades.ClientLobby";

    /**
     * Sends the command to the specified session.
     * @pre command != null
     * @pre session != null
     * @post session != null
     *
     * @param command - the command to be sent
     * @param sess - the remote session to send it to.
     * @return true if the message sends successfully, false if not.
     */
    private boolean sendCommand(Command command, Session sess) {
        Map args = new HashMap();
        args.put(JsonWriter.TYPE, true);
        command.set_authToken(null);
        try {
            if(sess == null)
                throw new Exception("Session was null, it was not found in the " +
                        "logged in sessions by the auth token");
            String resp = JsonWriter.objectToJson(command, args);
            RemoteEndpoint remote = sess.getRemote();
            assert remote != null;

            remote.sendString(resp);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sends a message based on the socket ID that is paired to the session
     * @pre id != null && !id.equals("")
     * @pre command != null
     * @post returns true if the command sent correctly. false if not.
     * @param command - the command to be send
     * @param id - the socket ID that the command should be sent to.
     */
    public boolean sendBySocketId(Command command, String id) {
        Map args = new HashMap();
        args.put(JsonWriter.TYPE, true);
        Session sess = ServerModel.instance().getAllSessions().get(id);

        assert sess != null;

        try {
            String resp = JsonWriter.objectToJson(command, args);
            sess.getRemote().sendString(resp);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sends a command to a list of authtokens. if the authTOken is found in the logged in sessions,
     * the command is sent to them. If they're in the logged in sessions, it means they have an active socket.
     * @pre authTokens != null,
     * @pre command != null,
     * @pre ServerModel.instance().getLoggedInSessions().size() > 0
     *
     * @post - if all the commands are send to all the authtokens in the list of logged in sessions,
     *          the function will return true
     * @param command - the command to be sent.
     * @param authTokens - the authtokens that the command should be sent to.
     * @return true if it all worked, false if not.
     */
    public boolean sendBroadcast(Collection<String> authTokens, Command command) {
        Map args = new HashMap();
        args.put(JsonWriter.TYPE, true);

        Map<String, Session> sessions = ServerModel.instance().getLoggedInSessions();
        for(String auth : authTokens) {
            try {
                if(sessions.containsKey(auth)) {
                    String resp = JsonWriter.objectToJson(command, args);
                    sessions.get(auth).getRemote().sendString(resp);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * Notifies all the players in a game of any changes.
     *
     * @pre gameID != null
     * @pre command != null
     *    ServerModel functions:
     * @pre getGames().size > 0
     *
     * @post command has been sent to everyone
     * @param gameID - the game ID of the game to be notified
     * @param command - the command that should be send
     *
     */
    public void notifyPlayersInGame(String gameID, Command command) {

        Collection<String> userTokensInGame = new ArrayList<>();
        for (Player player : ServerModel.instance().getGames().get(gameID).getPlayers()) {
            String auth = ServerModel.instance().getAllUsers()
                    .get(player.getName()).getAuthtoken();
            userTokensInGame.add(auth);
        }

        _instance.sendBroadcast(userTokensInGame, command);

    }

    /**
     * sends updates to the list of tokens updating the games in the lobby when something changes.
     *
     * @pre tokens != null && tokens.size() > 0
     * @pre ServerModel.instance().getGames.size() > 0
     *
     * @post all the peeople with those authTOkens receive an updated list of games.
     * @param tokens
     *
     */
    public void updateGameList(Collection<String> tokens) {
        //TODO Can we make the commands ouside this function and send in a collection of tokens and commands instead?
        //just send this to the people in the lobby & waiting room
        Object[] objects = ServerModel.instance().getGames().values().toArray();
        Game[] games = new Game[objects.length];
        int i = 0;
        for(Object g : objects) {
            games[i] = (Game) g;
            i++;
        }

        String message = "";
        String[] paramTypes = {games.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {games, message};
        Command updateGamesClientCommand = CommandFactory.createCommand(null, CLIENT_LOBBY_CLASS_NAME,
                "_updateGamesReceived", paramTypes, paramValues);

      sendBroadcast(tokens, updateGamesClientCommand);
    }

    // *** OBSERVER ***

    /**
     * Called when something in the server model changes such that a command must be sent over the server.
     *
     * @pre arg is a command
     * @pre ServerModel.instance().getLoggedInSession().size() > 0
     *
     * @post command is sent to the correct person
     *
     * @param o - class that is observing the model.
     * @param arg Command that should be sent over the server.
     */
    @Override
    public void update(Observable o, Object arg) {
        //if we can send more than one arg in observable, then se should send the session in here as well.
        if (arg.getClass() != Command.class) {
            return;
        }
        Command command = (Command)arg;
        Session sess = ServerModel.instance().getLoggedInSessions().get(command.get_authToken());
        _instance.sendCommand(command, sess);
    }
}
