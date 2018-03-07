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

//TODO this class shouldn't call the server model.
public class SocketManager implements Observer {
    private static SocketManager _instance;

    public static SocketManager instance() {

        if (_instance == null){
            _instance = new SocketManager();
        }

        return _instance;
    }

    private final String CLIENT_LOBBY_CLASS_NAME = "e.mboyd6.tickettoride.Facades.ClientLobby";

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
    public void sendBySocketId(Command command, String id) {
        Map args = new HashMap();
        args.put(JsonWriter.TYPE, true);
        Session sess = ServerModel.instance().getAllSessions().get(id);

        assert sess != null;

        try {
            String resp = JsonWriter.objectToJson(command, args);
            sess.getRemote().sendString(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends to a list of people. Right now that list is all the logged in users.
     * @param command
     */
    public void sendBroadcast(Collection<String> authTokens, Command command) {
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
            }
        }
    }


    public void notifyPlayersInGame(String gameID, Command command) {

        Collection<String> userTokensInGame = new ArrayList<>();
        for (Player player : ServerModel.instance().getGames().get(gameID).getPlayers()) {
            String auth = ServerModel.instance().getAllUsers()
                    .get(player.getName()).getAuthtoken();
            userTokensInGame.add(auth);
        }

        SocketManager.instance().sendBroadcast(userTokensInGame, command);

    }

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

        SocketManager.instance().sendBroadcast(tokens, updateGamesClientCommand);
    }

    // *** OBSERVER ***

    @Override
    public void update(Observable o, Object arg) {
        //if we can send more than one arg in observable, then se should send the session in here as well.
        if (arg.getClass() != Command.class) {
            return;
        }
        Command command = (Command)arg;
        Session sess = ServerModel.instance().getLoggedInSessions().get(command.get_authToken());
        instance().sendCommand(command, sess);
    }
}
