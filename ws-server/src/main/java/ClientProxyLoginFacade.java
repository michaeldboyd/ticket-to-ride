

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
        // If login was successful, message == "" [empty string]

        String[] paramTypes = {authToken.getClass().toString(), message.getClass().toString()};
        String[] paramValues = {authToken, message};
        Command loginClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLoginFacade", "_loginReceived", paramTypes, paramValues);

        // TODO - Send loginCommand to Client via socket

        org.eclipse.jetty.websocket.api.Session sess = ServerModel.instance().session;
        Sender.sendCommand(loginClientCommand, sess);
    }

    @Override
    public void register(String authToken, String message) {
        // This is called after the Server has attempted to register a new user
        // If register was successful, message == "" [empty string]

        String[] paramTypes = {authToken.getClass().toString(), message.getClass().toString()};
        String[] paramValues = {authToken, message};
        Command registerClientCommand = CommandFactory.createCommand("e.mboyd6.tickettoride.Communication.ClientLoginFacade", "_registerReceived", paramTypes, paramValues);

        // TODO - Send registerCommand to Client via socket
        org.eclipse.jetty.websocket.api.Session sess = ServerModel.instance().session;
        Sender.sendCommand(registerClientCommand, sess);
    }
}
