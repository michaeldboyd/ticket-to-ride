

import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.model.*;
import com.example.sharedcode.communication.Command;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import org.eclipse.jetty.websocket.api.Session;


import java.util.*;


public class ServerModel extends Observable {

    private static ServerModel _instance;

    public static ServerModel instance() {

        if (_instance == null){
            _instance = new ServerModel();
            _instance.addObserver(Sender.instance());
        }

        return _instance;
    }

    private ServerModel() {}

    private Map<String, User> loggedInUsers = new HashMap<>(); // <username, User>
    private Map<String, User> allUsers = new HashMap<>(); // <username, User>
    private Map<String, String> authTokenToUsername = new HashMap<>(); // <authToken, username>
    private Map<String, Game> games = new HashMap<>(); // <gameID, Game>
    private Map<String, ArrayList<ChatMessage>> chatMessagesForGame = new HashMap<>(); // <gameID, ChatMessage[]>

    //this static map keeps track of all open websockets with key: username val: session instance
    private Map<String, Session> loggedInSessions = Collections.synchronizedMap(new HashMap<>());
    private Map<String, Session> allSessions = Collections.synchronizedMap(new HashMap<>());


    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    private void notifyObserversForUpdate(Command command) {
        this.setChanged();
        this.notifyObservers(command);
    }



    // *** REGISTER / LOGIN ***

    public void registerUser(String username, String password, String socketID) {
        String authToken = "";
        String message = "";

        if (allUsers.containsKey(username)) {
            message = "Username already registered.";
        } else {
            authToken = UUID.randomUUID().toString();

            User user = new User();
            user.setUserID(UUID.randomUUID().toString());
            user.setAuthtoken(authToken);
            user.setUsername(username);
            user.setPassword(password);

            // After creating a new User, add them to allUsers and loggedInUsers
            allUsers.put(username, user);
            loggedInUsers.put(username, user);
            authTokenToUsername.put(authToken, username);

            matchSocketToAuthToken(socketID, authToken);
        }

        String[] paramTypes = {authToken.getClass().toString(), message.getClass().toString()};
        String[] paramValues = {authToken, message};
        Command registerClientCommand = CommandFactory.createCommand(authToken,"e.mboyd6.tickettoride.Communication.ClientLoginFacade","_registerReceived", paramTypes, paramValues);

        notifyObserversForUpdate(registerClientCommand);
    }

    public void loginUser(String username, String password, String socketID) {
        String authToken = "";
        String message = "";
        if (loggedInUsers.containsKey(username)) {
            message = "User already logged in.";
        } else {

            if (allUsers.containsKey(username)) {

                User user = allUsers.get(username);

                if (user.getPassword().equals(password)) {

                    //Do we want to reset authtoken each time?
                    UUID uuid = UUID.randomUUID();
                    authToken = uuid.toString();

                    authTokenToUsername.put(authToken, username);
                    allUsers.get(username).setAuthtoken(authToken);
                    loggedInUsers.put(username, user);

                    matchSocketToAuthToken(socketID, authToken);
                } else {
                    message = "Incorrect password.";
                }
            } else {
                message = "User does not exist.";
            }
        }

        //make command
        String[] paramTypes = {authToken.getClass().toString(), message.getClass().toString()};
        String[] paramValues = {authToken, message};
        Command loginClientCommand = CommandFactory.createCommand(authToken,
                "e.mboyd6.tickettoride.Communication.ClientLoginFacade",
                "_loginReceived", paramTypes, paramValues);
        notifyObserversForUpdate(loginClientCommand);
    }


    public void logout(String authToken) {
        String message = "";
        if (authTokenToUsername.containsKey(authToken)) {
            String username = authTokenToUsername.get(authToken);
            loggedInUsers.remove(username);
            authTokenToUsername.remove(authToken);
        } else  {
            //TODO we aren't sending this message right now.
            message = "Error logging out -- not logged in";
        }

        String[] paramTypes = {message.getClass().toString()};
        String[] paramValues = {message};
        Command logoutClientCommand = CommandFactory.createCommand(authToken,
                "e.mboyd6.tickettoride.Communication.ClientLoginFacade",
                "_logoutReceived", paramTypes, paramValues);
        notifyObserversForUpdate(logoutClientCommand);
        loggedInSessions.remove(authToken);
    }


    private void matchSocketToAuthToken(String socketID, String authToken) {
        if(!loggedInUsers.containsKey(authToken)){
            Session session = allSessions.get(socketID);
            loggedInSessions.put(authToken, session);
        }
    }



    // *** LOBBY ***

    public void createGame(String authToken) {
        // Don't need to check for existence of a new game because this should only be called when creating a brand new game
        String id = UUID.randomUUID().toString();
        Game newGame = new Game();
        newGame.setGameID(id);

        ServerModel.instance().games.put(id, newGame);

        // SEND UPDATED GAMES LIST TO ERRYBODY
        // SEND JOIN GAME COMMAND TO CREATOR OF GAME
        String[] paramTypes = {newGame.getClass().toString()};
        Object[] paramValues = {newGame};
        Command createGameClientCommand = CommandFactory.createCommand(authToken, "e.mboyd6.tickettoride.Communication.ClientLobbyFacade",
                "_createGameReceived", paramTypes, paramValues);
        notifyObserversForUpdate(createGameClientCommand);

        updateGamesBroadcast();
    }

    public void getAllGames(String authToken) {
        Game[] games = (Game[]) ServerModel.instance().games.values().toArray();

        // TODO - message parameter is always null -- we should remove it or figure out potential errors/problems

        // Message is empty
        String message= "";
        String[] paramTypes = {games.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {games, message};

        Command updateGamesClientCommand = CommandFactory.createCommand(authToken, "e.mboyd6.tickettoride.Communication.ClientLobbyFacade",
                "_updateGamesReceived", paramTypes, paramValues);

        //send this to all the clients
        notifyObserversForUpdate(updateGamesClientCommand);
    }

    public void joinGame(String authToken, String gameID) {
        String message = "";

        String playerID = "";
        if (games.containsKey(gameID)) {

            Player newPlayer = new Player(UUID.randomUUID().toString(), ServerModel.instance().authTokenToUsername.get(authToken), PlayerColors.NO_COLOR);

            // Only set message if we fail to add user to the game
            if (!ServerModel.instance().games.get(gameID).addPlayer(newPlayer)) {
                message = "Could not add player to game because it is already full";
            } else {
                playerID = newPlayer.getPlayerID();
            }
        }

        String[] paramTypes = {gameID.getClass().toString(), playerID.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, playerID, message};

        Command joinGameClientCommand = CommandFactory.createCommand(authToken, "e.mboyd6.tickettoride.Communication.ClientLobbyFacade",
                "_joinGameReceived", paramTypes, paramValues);

        notifyObserversForUpdate(joinGameClientCommand);
        updateGamesBroadcast();
    }

    public void leaveGame(String authToken, String gameID, String playerID) {
        String message = "";

        // This returns false if playerID is not part of game
        if (!games.get(gameID).removePlayer(playerID)) {
            message = "Could not remove player because is not in the game";
        }

        String[] paramTypes = {gameID.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, message};

        Command leaveGameClientCommand = CommandFactory.createCommand(authToken, "e.mboyd6.tickettoride.Communication.ClientLobbyFacade", "_leaveGameReceived", paramTypes, paramValues);

        Game game = games.get(gameID);

        if (game != null && game.getPlayers() != null && game.getPlayers().size() == 0) {
            games.remove(gameID);
        }

        notifyObserversForUpdate(leaveGameClientCommand);
        updateGamesBroadcast();
    }


    public void startGame(String authToken, String gameID) {
        String message = "";

        if (!ServerModel.instance().games.containsKey(gameID)) {
            message = "Game doesn't exist.";
        }else{
            Game game = ServerModel.instance().games.get(gameID);
            ArrayList<Player> players = game.getPlayers();
            Collection<String> authTokens = new ArrayList<>();
            if( players != null) {
                for(Player p : players)
                {
                    User user = ServerModel.instance().allUsers.get(p.getName());
                    if(user != null)
                    {
                        authTokens.add(user.getAuthtoken());
                    }
                }

                notifyPlayersOfGameStarted(authTokens, message, gameID);
            }
        }

        String[] paramTypes = {gameID.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, message};

        Command startGameClientCommand = CommandFactory.createCommand(authToken, "e.mboyd6.tickettoride.Communication.ClientLobbyFacade",
                "_startGameReceived", paramTypes, paramValues);

        notifyObserversForUpdate(startGameClientCommand);
        updateGamesBroadcast();
    }

    private void notifyPlayersOfGameStarted(Collection<String> tokens, String message, String gameID)
    {
        String[] paramTypes = {message.getClass().toString(), gameID.getClass().toString()};
        Object[] paramValues = {gameID, message};
        Command command = CommandFactory.createCommand("", "e.mboyd6.tickettoride.Communication.ClientLobbyFacade",
                "_startGameReceived", paramTypes, paramValues);
        for(String token : tokens)
        {
            command.set_authToken(token);
            notifyObserversForUpdate(command);
        }
    }


    public void getPlayersForGame(String authToken, String gameID) {
        String message = "";
        Player[] players;

        if (games.containsKey(gameID)) {

            players = (Player[]) ServerModel.instance().games.get(gameID).getPlayers().toArray();
        } else {
            players = new Player[0];
            message = "Game does not exist.";
        }

        String[] paramTypes = {gameID.getClass().toString(), players.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gameID, players, message};


        Command getPlayersForGameClientCommand = CommandFactory.createCommand(authToken, "e.mboyd6.tickettoride.Communication.ClientLobbyFacade", "_getPlayersForGameReceived", paramTypes, paramValues);

        notifyObserversForUpdate(getPlayersForGameClientCommand);
    }


    public void playerColorChanged(String authToken, String gameID, String playerID, int color) {
        Game game = ServerModel.instance().games.get(gameID);

        Boolean success = false;
        for (Player player :
                game.getPlayers()) {
            if (player.getPlayerID().equals(playerID)) {
                player.setColor(color);
                success = true;
                break;
            }
        }

        if (success) {
            updateGamesBroadcast();
        }
    }


    private void updateGamesBroadcast() {
        //TODO is there a better way to send the games over the server?
        Object[] games = ServerModel.instance().games.values().toArray();
        Game[] gs = new Game[games.length];
        int i=0;
        for(Object o : games) {
            gs[i++] = (Game)o;
        }

        // Message is blank
        String message = "";
        String[] paramTypes = {gs.getClass().toString(), message.getClass().toString()};
        Object[] paramValues = {gs, message};
        Command updateGamesClientCommand = CommandFactory.createCommand(null, "e.mboyd6.tickettoride.Communication.ClientLobbyFacade",
                "_updateGamesReceived", paramTypes, paramValues);
        Sender.sendBroadcast(updateGamesClientCommand);
    }



    // *** CHAT ***

    public void addChatToGame(String authToken, String message, String playerName, String gameID) {
        if (!chatMessagesForGame.containsKey(gameID)) {
            chatMessagesForGame.put(gameID, new ArrayList<>());
        }

        ChatMessage newMessage = new ChatMessage(message, playerName);
        chatMessagesForGame.get(gameID).add(newMessage);

        String[] paramTypes = {newMessage.getClass().toString(), gameID.getClass().toString()};
        Object[] paramValues = {newMessage, gameID};
        Command addChatCommand = CommandFactory.createCommand(authToken, "e.mboyd6.tickettoride.Communication.ChatClientFacade", "_chatMessageReceived", paramTypes, paramValues);
        notifyObserversForUpdate(addChatCommand);
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

}
