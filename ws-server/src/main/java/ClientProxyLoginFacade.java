

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.communication.CommandMessage;
import com.example.sharedcode.interfaces.IClientLoginFacade;
import com.google.gson.Gson;

import javax.websocket.Session;
import java.io.IOException;

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
    public void login(String authToken, String message) {
        // This is called after the Server has attempted to log someone in
        // If login was successful, message == null

        String className;
        String param;
        if (authToken != null) {
            className = authToken.getClass().toString();
            param = authToken;
        } else {
            className = message.getClass().toString();
            param = message;
        }

        String[] paramTypes = {className};
        String[] paramValues = {param};
        Command loginClientCommand = CommandFactory.createCommand("ClientLoginFacade", "_loginReceived", paramTypes, paramValues);

        // TODO - Send loginCommand to Client via socket

        org.eclipse.jetty.websocket.api.Session sess = ServerModel.instance().session;
        try {
            sess.getRemote().sendString(new Gson().toJson(loginClientCommand));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(String authToken, String message) {
        // This is called after the Server has attempted to register a new user
        // If register was successful, message == null

        String className;
        String param;
        if (authToken != null) {
            className = authToken.getClass().toString();
            param = authToken;
        } else {
            className = message.getClass().toString();
            param = message;
        }

        String[] paramTypes = {className};
        String[] paramValues = {param};
        Command registerClientCommand = CommandFactory.createCommand("ClientLoginFacade", "_registerReceived", paramTypes, paramValues);

        // TODO - Send registerCommand to Client via socket
        org.eclipse.jetty.websocket.api.Session sess = ServerModel.instance().session;
        try {
            sess.getRemote().sendString(new Gson().toJson(registerClientCommand));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
