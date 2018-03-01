package Model;

import Communication.SocketManager;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.model.*;
import com.example.sharedcode.communication.Command;
import org.eclipse.jetty.websocket.api.Session;



import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;


public class ServerModel extends Observable {

    private static ServerModel _instance;
    private String testPassword = "thisisoursupersecrettestpassword";



    public static ServerModel instance() {

        if (_instance == null){
            _instance = new ServerModel();
            _instance.addObserver(SocketManager.instance());
        }

        return _instance;
    }

    private ServerModel() {}

    private Map<String, User> loggedInUsers = new HashMap<>(); // <username, User>
    private Map<String, User> allUsers = new HashMap<>(); // <username, User>
    private Map<String, String> authTokenToUsername = new HashMap<>(); // <authToken, username>
    private Map<String, Game> games = new HashMap<>(); // <gameID, Game>
    private Map<String, ArrayList<ChatMessage>> chatMessagesForGame = new HashMap<>(); // <gameID, ChatMessage[]>
    //this static map keeps track of all open websockets
    private Map<String, Session> loggedInSessions = Collections.synchronizedMap(new HashMap<>());
    private Map<String, Session> allSessions = Collections.synchronizedMap(new HashMap<>());


    // *** Observer pattern methods *** //
    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    public void notifyObserversForUpdate(Command command) {
        this.setChanged();
        this.notifyObservers(command);
    }



    // *** CHAT ***
    //TODO add contents of this class to the chatServerFacade
    public void addChatToGame(String authToken, String message, String playerName, String gameID) {
        if (!chatMessagesForGame.containsKey(gameID)) {
            chatMessagesForGame.put(gameID, new ArrayList<>());
        }
        Timestamp timestamp = new Timestamp(ZonedDateTime.now(ZoneId.of("UTC")).toInstant().toEpochMilli());
        String stamp = timestamp.toString();
        ChatMessage newMessage = new ChatMessage(message, playerName, stamp);
        chatMessagesForGame.get(gameID).add(newMessage);

        String[] paramTypes = {newMessage.getClass().toString(), gameID.getClass().toString()};
        Object[] paramValues = {newMessage, gameID};
        for(String auth : loggedInSessions.keySet())
        {
            Command addChatCommand = CommandFactory.createCommand(auth, "e.mboyd6.tickettoride.Facades.Chat", "_chatMessageReceived", paramTypes, paramValues);
            notifyObserversForUpdate(addChatCommand);
        }

    }

    //*** Getters ***

    public Map<String, User> getLoggedInUsers() {
        return loggedInUsers;
    }

    public Map<String, User> getAllUsers() {
        return allUsers;
    }

    public Map<String, String> getAuthTokenToUsername() {
        return authTokenToUsername;
    }

    public Map<String, Game> getGames() {
        return games;
    }

    public Map<String, Session> getLoggedInSessions() {
        return loggedInSessions;
    }

    public Map<String, Session> getAllSessions() {
        return allSessions;
    }
    public String getTestPassword() {
        return testPassword;
    }
    public Map<String, ArrayList<ChatMessage>> getChatMessagesForGame() {
        return chatMessagesForGame;
    }
}
