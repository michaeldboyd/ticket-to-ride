package Facades;

import Communication.SocketManager;
import Model.ServerModel;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerLoginFacade;
import com.example.sharedcode.model.User;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ServerLogin implements IServerLoginFacade {


    private static ServerLogin loginFacade;
    public static ServerLogin instance() {
        if (loginFacade == null) {
            loginFacade = new ServerLogin();
        }

        return loginFacade;
    }

    private ServerLogin() {}

    private final String CLASS_NAME = "e.mboyd6.tickettoride.Facades.ClientLogin";
    public static void _login(String username, String password, String socketID) {
        instance().login(username, password, socketID);
    }

    public static void _register(String username, String password, String socketID) {
        instance().register(username, password, socketID);
    }

    public static void _logout(String authToken) {
        instance().logout(authToken);
    }


    /**
     * Checks to see if user is already logged in. If so, returns error message.
     * <p>
     * If not, checks to make sure user already exists. If not, returns error message.
     * <p>
     * If so, checks to make sure passwords match. If so, logs in user.
     * If not, returns error message.
     *
     * @param username -
     * @param password -
     * @return
     */
    @Override
    public void login(String username, String password, String socketID) {
        String authToken = "";
        String message = "";
        if (ServerModel.instance().getLoggedInUsers().containsKey(username)) {
            message = "User already logged in.";
        } else {

            if (ServerModel.instance().getAllUsers().containsKey(username)) {

                User user = ServerModel.instance().getAllUsers().get(username);

                if (user.getPassword().equals(password)) {

                    //Do we want to reset authtoken each time?
                    UUID uuid = UUID.randomUUID();
                    authToken = uuid.toString();

                    ServerModel.instance().getAuthTokenToUsername().put(authToken, username);
                    ServerModel.instance().getAllUsers().get(username).setAuthtoken(authToken);
                    ServerModel.instance().getLoggedInUsers().put(username, user);
                    ServerModel.instance().getUsersInLobby().put(username, user);
                    matchSocketToAuthToken(socketID, authToken);
                } else {
                    message = "Incorrect password.";
                }
            } else {
                message = "User does not exist.";
            }
        }
        String[] paramTypes = {authToken.getClass().toString(), message.getClass().toString()};
        String[] paramValues = {authToken, message};
        Command loginClientCommand = CommandFactory.createCommand(authToken,
                CLASS_NAME,
                "_loginReceived", paramTypes, paramValues);
        if(!authToken.equals(""))
            ServerModel.instance().notifyObserversForUpdate(loginClientCommand);
        else SocketManager.instance().sendBySocketId(loginClientCommand, socketID);

        Collection<String> tok = new ArrayList<String>();
        tok.add(authToken);
        SocketManager.instance().updateGameList(tok);
    }



    /**
     * Checks if username is already taken. If it is, returns error message.
     * <p>
     * If not, creates a new user and adds user to map of all the users and
     * list of logged in users.
     *
     * @param username -
     * @param password -
     * @param socketID -
     */
    @Override
    public void register(String username, String password, String socketID) {
        String authToken = "";
        String message = "";

        if (ServerModel.instance().getAllUsers().containsKey(username)) {
            message = "Username already registered.";
        } else {
            authToken = UUID.randomUUID().toString();

            User user = new User();
            user.setUserID(UUID.randomUUID().toString());
            user.setAuthtoken(authToken);
            user.setUsername(username);
            user.setPassword(password);

            // After creating a new User, add them to allUsers and loggedInUsers
            ServerModel.instance().getAllUsers().put(username, user);
            ServerModel.instance().getLoggedInUsers().put(username, user);
            ServerModel.instance().getAuthTokenToUsername().put(authToken, username);
            ServerModel.instance().getUsersInLobby().put(username, user);
            matchSocketToAuthToken(socketID, authToken);
        }

        String[] paramTypes = {authToken.getClass().toString(), message.getClass().toString()};
        String[] paramValues = {authToken, message};
        Command registerClientCommand = CommandFactory.createCommand(authToken,
                CLASS_NAME,"_registerReceived", paramTypes, paramValues);
        if(!authToken.equals(""))
            ServerModel.instance().notifyObserversForUpdate(registerClientCommand);
        else SocketManager.instance().sendBySocketId(registerClientCommand, socketID);
        Collection<String> tok = new ArrayList<String>();
        tok.add(authToken);
        SocketManager.instance().updateGameList(tok);

    }


    @Override
    public void logout(String authToken){
        String message = "";
        if (ServerModel.instance().getAuthTokenToUsername().containsKey(authToken)) {
            String username = ServerModel.instance().getAuthTokenToUsername().get(authToken);
            ServerModel.instance().getLoggedInUsers().remove(username);
            ServerModel.instance().getAuthTokenToUsername().remove(authToken);
            ServerModel.instance().getUsersInLobby().remove(username);
        } else  {
            message = "Error logging out -- not logged in";
        }

        String[] paramTypes = {message.getClass().toString()};
        String[] paramValues = {message};
        Command logoutClientCommand = CommandFactory.createCommand(authToken,
                CLASS_NAME,
                "_logoutReceived", paramTypes, paramValues);

        ServerModel.instance().notifyObserversForUpdate(logoutClientCommand);
        ServerModel.instance().getLoggedInSessions().remove(authToken);
    }




    private void matchSocketToAuthToken(String socketID, String authToken) {

        if(!ServerModel.instance().getLoggedInUsers().containsKey(authToken)){
            Session session = ServerModel.instance().getAllSessions().get(socketID);
            ServerModel.instance().getLoggedInSessions().put(authToken, session);
        }
    }
}
