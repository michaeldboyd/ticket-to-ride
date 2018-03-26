package Model;

import Communication.SocketManager;
import Facades.ServerLobby;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.model.*;
import com.example.sharedcode.communication.Command;
import org.eclipse.jetty.websocket.api.Session;



import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;


public class ServerModel extends Observable {

    private static volatile ServerModel _instance;


    public static ServerModel instance() {

        if (_instance == null){
            _instance = new ServerModel();
            _instance.addObserver(SocketManager.instance());
        }

        return _instance;
    }

    private ServerModel() {}
    //User Info
    private Map<String, User> loggedInUsers = Collections.synchronizedMap(new HashMap<>()); // <username, User>
    private Map<String, User> allUsers = Collections.synchronizedMap(new HashMap<>()); // <username, User>
    private Map<String, String> authTokenToUsername = Collections.synchronizedMap(new HashMap<>()); // <authToken, username>
    private Map<String, User> usersInLobby = Collections.synchronizedMap(new HashMap<>());
    private Map<String, Game> startedGames = Collections.synchronizedMap(new HashMap<>());

    //Game list
    private Map<String, Game> games = Collections.synchronizedMap(new HashMap<>()); // <gameID, Game>
    private Map<String, ArrayList<ChatMessage>> chatMessagesForGame = Collections.synchronizedMap(new HashMap<>()); // <gameID, ChatMessage[]>


    //this static map keeps track of all open websockets
    private Map<String, Session> loggedInSessions = Collections.synchronizedMap(new HashMap<>());
    private Map<String, Session> allSessions = Collections.synchronizedMap(new HashMap<>());

    public void setGames(Map<String, Game> games) {
        this.games = games;
    }

    public void setChatMessagesForGame(Map<String, ArrayList<ChatMessage>> chatMessagesForGame) {
        this.chatMessagesForGame = chatMessagesForGame;
    }

    // *** Observer pattern methods *** //
    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    public void notifyObserversForUpdate(Command command) {
        this.setChanged();
        this.notifyObservers(command);
    }

    //*** Getters ***
    public Map<String, Game> getStartedGames() { return startedGames; }
    public Map<String, User> getUsersInLobby() { return usersInLobby;}
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
    public Map<String, Session> getLoggedInSessions() { return loggedInSessions; }
    public Map<String, Session> getAllSessions() {
        return allSessions;
    }
    public String getTestPassword() {
        return "thisisoursupersecrettestpassword";

    }
    public Map<String, ArrayList<ChatMessage>> getChatMessagesForGame() {
        return chatMessagesForGame;
    }
    public ArrayList<String> getPlayerAuthTokens(String gameID) {
        ArrayList<String> tokens = new ArrayList<String>();
        if(gameID != null && games.get(gameID) != null) {
            for (Player p : ServerModel.instance().getGames().get(gameID).getPlayers()) {
                tokens.add(ServerModel.instance().getAllUsers().get(p.getName()).getAuthtoken());
            }
        }

        return tokens;
    }
    public ArrayList<String> getLobbyUserAuthTokens() {
        ArrayList<String> tokens = new ArrayList<>();
        for(User u : ServerModel.instance().getUsersInLobby().values()) {
            tokens.add(u.getAuthtoken());
        }

        return tokens;
    }

    /**
     * This function exists to handle websocket disconnect errors. Whenever a websocket closes, this
     * function will go through the relevant server model objects to remove the client that has been disconnected
     * from the server. Normally this functionality will be covered by the server logic during standard use, but in
     * case of an error, this fucntion will clean the the client from the data and from any game it was in.
     */
    public void cleanSessions() {
        try {
            //clear all sessions
            for(Map.Entry<String, Session> e : allSessions.entrySet()) {
                if(e.getValue() == null || !(e.getValue().isOpen())) {
                    allSessions.remove(e.getKey());
                    break;
                }
            }
            ArrayList<String> removedSessions = new ArrayList<>();
            //clear logged in sessions and server model data
            for(Map.Entry<String, Session> e : loggedInSessions.entrySet()) {
                String token = e.getKey();
                String username = authTokenToUsername.get(token);
                if(e.getValue() == null || !(e.getValue().isOpen())) {
                    //check the games for an instance of the client.
                    removedSessions.add(token);
                    loggedInUsers.remove(username);
                    authTokenToUsername.remove(token);
                    usersInLobby.remove(username);
                    for(Game g : games.values()) {
                        int i = 0;
                        for(Player p : g.getPlayers()) {
                            if (p.getName().equals(username)) {
                                // if the player was still in a game when his client got killed, remove him and notify
                                // the others in the game.
                                ServerLobby.instance().leaveGame(token, g.getGameID(), p.getPlayerID());
                                break;
                            } i++;
                        }
                    }
                }
            }
            for(String tok : removedSessions ){
                loggedInSessions.remove(tok);
            }
            System.out.println("Successfully cleaned all sessions of closed session.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
