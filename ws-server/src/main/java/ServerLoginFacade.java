

import com.example.sharedcode.interfaces.IServerLoginFacade;
import com.example.sharedcode.model.User;
import org.eclipse.jetty.websocket.api.Session;

import java.util.UUID;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ServerLoginFacade implements IServerLoginFacade {


    private static ServerLoginFacade loginFacade;

    public static ServerLoginFacade instance() {
        if (loginFacade == null) {
            loginFacade = new ServerLoginFacade();
        }

        return loginFacade;
    }

    private ServerLoginFacade() {}


    public static void _login(String username, String password, String socketID) {
        instance().login(username, password, socketID);
    }

    public static void _register(String username, String password, String socketID) {
        instance().register(username, password, socketID);
    }

    public static void _logout(String username) {
        instance().logout(username);
    }


    /**
     * Checks to see if user is already logged in. If so, returns error message.
     * <p>
     * If not, checks to make sure user already exists. If not, returns error message.
     * <p>
     * If so, checks to make sure passwords match. If so, logs in user.
     * If not, returns error message.
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public void login(String username, String password, String socketID) {
        String authToken = "";
        String message = "";
        if (ServerModel.instance().loggedInUsers.containsKey(username)) {
            message = "User already logged in.";
        } else {

            if (ServerModel.instance().allUsers.containsKey(username)) {

                User user = ServerModel.instance().allUsers.get(username);

                if (user.getPassword().equals(password)) {

                    //Do we want to reset authtoken each time?
                    UUID uuid = UUID.randomUUID();
                    authToken = uuid.toString();
                    ServerModel.instance().authTokenToUsername.put(authToken, username);
                    ServerModel.instance().allUsers.get(username).setAuthtoken(authToken);
                    ServerModel.instance().loggedInUsers.put(user.getUsername(), user);
                    matchSocketToAuthToken(socketID, authToken);

                } else {
                    message = "Incorrect password.";
                }
            } else {
                message = "User does not exist.";
            }
        }

        ClientProxyLoginFacade.instance().login(authToken, message);
    }

    private void matchSocketToAuthToken(String socketID, String authToken) {
        if(!ServerModel.instance().loggedInSessions.containsKey(authToken)){
            Session sess = ServerModel.instance().allSessions.get(socketID);
            ServerModel.instance().loggedInSessions.put(authToken, sess);
        }
    }


    /**
     * Checks if username is already taken. If it is, returns error message.
     * <p>
     * If not, creates a new user and adds user to map of all the users and
     * list of logged in users.
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public void register(String username, String password, String socketID) {
        String authToken = "";
        String message = "";

        if (ServerModel.instance().allUsers.containsKey(username)) {
            message = "Username already registered.";
        } else {
            authToken = UUID.randomUUID().toString();

            User user = new User();
            user.setUserID(UUID.randomUUID().toString());
            user.setAuthtoken(authToken);
            user.setUsername(username);
            user.setPassword(password);
            ServerModel.instance().allUsers.put(username, user);
            ServerModel.instance().loggedInUsers.put(username, user);
            ServerModel.instance().authTokenToUsername.put(authToken, username);

            matchSocketToAuthToken(socketID, authToken);
        }

        ClientProxyLoginFacade.instance().register(authToken, message);
    }

    @Override
    public void logout(String authToken){
        String message = "";
        if (ServerModel.instance().authTokenToUsername.containsKey(authToken)) {
            String username = ServerModel.instance().authTokenToUsername.get(authToken);
            ServerModel.instance().loggedInUsers.remove(username);
            ServerModel.instance().authTokenToUsername.remove(authToken);
        } else  {
            //TODO we aren't sending this message right now.
            message = "Error logging out -- not logged in";
        }

        ClientProxyLoginFacade.instance().logout(authToken, message);
        ServerModel.instance().loggedInSessions.remove(authToken);
    }

}
