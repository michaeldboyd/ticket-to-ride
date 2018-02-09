

import com.example.sharedcode.interfaces.IServerLoginFacade;
import com.example.sharedcode.model.User;

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
    public void login(String username, String password) {
        //New command result
        String authToken = null;
        String message = null;
        if (ServerModel.instance().loggedInUsers.containsKey(username)) {
            message = "User already logged in.";
        } else {

            if (ServerModel.instance().allUsers.containsKey(username)) {

                User user = ServerModel.instance().allUsers.get(username);

                if (user.getPassword().equals(password)) {

                    //Do we want to reset authtoken each time?
                    UUID uuid = UUID.randomUUID();
                    authToken = uuid.toString();

                    ServerModel.instance().allUsers.get(username).setAuthtoken(authToken);
                    ServerModel.instance().loggedInUsers.put(user.getUsername(), user);
                } else {
                    message = "Incorrect password.";
                }
            } else {
                message = "User does not exist.";
            }
        }

        ClientProxyLoginFacade.instance().login(authToken, message);
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
    public void register(String username, String password) {
        //New command result
        String authToken = null;
        String message = null;

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
        }

        ClientProxyLoginFacade.instance().register(authToken, message);
    }

}
