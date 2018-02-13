

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.User;
import com.example.sharedcode.communication.Command;
import com.sun.xml.internal.ws.api.ha.StickyFeature;

import javax.websocket.Session;
import java.util.*;


public class ServerModel {

    public static ServerModel _instance;

    public static ServerModel instance() {

        if (_instance == null){
            _instance = new ServerModel();
        }

        return _instance;
    }

    private ServerModel() {}

    public ArrayList<Command> queuedCommands = new ArrayList<>();
    public Map<String, User> loggedInUsers = new HashMap<>(); // <username, User>
    public Map<String, User> allUsers = new HashMap<>(); // <username, User>
    public Map<String, String> authTokenToUsername = new HashMap<>(); // <authToken, username>
    public Map<String, Game> games = new HashMap<>(); // <gameID, Game>

    //this static map keeps track of all open websockets with key: username val: session instance
    public Map<String, org.eclipse.jetty.websocket.api.Session> loggedInSessions = new HashMap<>();
    public org.eclipse.jetty.websocket.api.Session session;





}
