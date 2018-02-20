

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IClientLoginFacade;
import com.example.sharedcode.model.UpdateType;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by eric on 2/7/18.
 */

public class ClientProxyLoginFacade implements IClientLoginFacade {
    private static ClientProxyLoginFacade loginFacade;

    public static ClientProxyLoginFacade instance() {
        if (loginFacade == null) {
            loginFacade = new ClientProxyLoginFacade();
        }

        return loginFacade;
    }

    private ClientProxyLoginFacade() {}


    @Override
    public void register(String authToken, String message) {
        // This is called after the Server has attempted to register a new user
        // If register was successful, message == "" [empty string]

    }

    @Override
    public void login(String authToken, String message) {
        // This is called after the Server has attempted to log someone in
        // If login was successful, message == "" [empty string]

    }

    @Override
    public void logout(String message){
        // Send logoutCommand to Client via socket
    }

    @Override
    public void initSocket(String id) {
       //
    }


}
