

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerLoginFacade;
import com.example.sharedcode.model.UpdateType;
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
        ServerModel.instance().loginUser(username, password, socketID);
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
        ServerModel.instance().registerUser(username, password, socketID);
    }


    @Override
    public void logout(String authToken){
        ServerModel.instance().logout(authToken);
    }

}
