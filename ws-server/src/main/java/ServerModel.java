

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
    public static Map<String, Session> sessionMap = new HashMap<>();

    public org.eclipse.jetty.websocket.api.Session session;





}
