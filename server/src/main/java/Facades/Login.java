package Facades;

import Model.ServerModel;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerLoginFacade;
import com.example.sharedcode.model.User;
import org.eclipse.jetty.websocket.api.Session;

import java.util.Map;
import java.util.UUID;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class Login implements IServerLoginFacade {


    private static Login loginFacade;
    public static Login instance() {
        if (loginFacade == null) {
            loginFacade = new Login();
        }

        return loginFacade;
    }

    private Login() {}

    private final String CLASS_NAME = "e.mboyd6.tickettoride.Facades.Login";
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
        Map<String, User> loggedInUsers = ServerModel.instance().getLoggedInUsers();
        Map<String, User> allUsers = ServerModel.instance().getAllUsers();
        Map<String, String> authTokenToUsername = ServerModel.instance().getAuthTokenToUsername();
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
        String[] paramTypes = {authToken.getClass().toString(), message.getClass().toString()};
        String[] paramValues = {authToken, message};
        Command loginClientCommand = CommandFactory.createCommand(authToken,
                CLASS_NAME,
                "_loginReceived", paramTypes, paramValues);

        ServerModel.instance().notifyObserversForUpdate(loginClientCommand);
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
        Map<String, User> loggedInUsers = ServerModel.instance().getLoggedInUsers();
        Map<String, User> allUsers = ServerModel.instance().getAllUsers();
        Map<String, String> authTokenToUsername = ServerModel.instance().getAuthTokenToUsername();

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
        Command registerClientCommand = CommandFactory.createCommand(authToken,
                CLASS_NAME,"_registerReceived", paramTypes, paramValues);

        ServerModel.instance().notifyObserversForUpdate(registerClientCommand);

    }


    @Override
    public void logout(String authToken){
        String message = "";
        Map<String, User> loggedInUsers = ServerModel.instance().getLoggedInUsers();
        Map<String, User> allUsers = ServerModel.instance().getAllUsers();
        Map<String, String> authTokenToUsername = ServerModel.instance().getAuthTokenToUsername();
        Map<String, Session> loggedInSessions = ServerModel.instance().getLoggedInSessions();
        if (authTokenToUsername.containsKey(authToken)) {
            String username = authTokenToUsername.get(authToken);
            loggedInUsers.remove(username);
            authTokenToUsername.remove(authToken);
        } else  {
            message = "Error logging out -- not logged in";
        }

        String[] paramTypes = {message.getClass().toString()};
        String[] paramValues = {message};
        Command logoutClientCommand = CommandFactory.createCommand(authToken,
                CLASS_NAME,
                "_logoutReceived", paramTypes, paramValues);

        ServerModel.instance().notifyObserversForUpdate(logoutClientCommand);
        loggedInSessions.remove(authToken);
    }




    private void matchSocketToAuthToken(String socketID, String authToken) {
        Map<String, User> loggedInUsers = ServerModel.instance().getLoggedInUsers();
        Map<String, Session> allSessions = ServerModel.instance().getAllSessions();
        Map<String, Session> loggedInSessions = ServerModel.instance().getLoggedInSessions();

        if(!loggedInUsers.containsKey(authToken)){
            Session session = allSessions.get(socketID);
            loggedInSessions.put(authToken, session);
        }
    }
}
