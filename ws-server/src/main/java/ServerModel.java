

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.User;
import com.example.sharedcode.communication.Command;

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
    public Map<String, User> loggedInUsers = new HashMap<>();
    public Map<String, User> allUsers = new HashMap<>();
    public Map<String, Game> games = new HashMap<>();

    public static Map<String, org.eclipse.jetty.websocket.api.Session> getLoggedInSessions() {
        return loggedInSessions;
    }

    //this static map keeps track of all open websockets with key: username val: session instance
    public static Map<String, org.eclipse.jetty.websocket.api.Session> loggedInSessions = new HashMap<>();

    public void setSession(org.eclipse.jetty.websocket.api.Session session) {
        this.session = session;
    }

    public org.eclipse.jetty.websocket.api.Session session;





}
