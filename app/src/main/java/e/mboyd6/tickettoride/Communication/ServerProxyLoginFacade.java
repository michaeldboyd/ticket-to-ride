package e.mboyd6.tickettoride.Communication;

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerLoginFacade;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by eric on 2/7/18.
 */

public class ServerProxyLoginFacade implements IServerLoginFacade {

    private static ServerProxyLoginFacade _instance = new ServerProxyLoginFacade();

    private ServerProxyLoginFacade() {}

    public static ServerProxyLoginFacade instance() {return _instance;}

    private WebSocketContainer container;
    private void socketConnect()
    {
        URI uri = URI.create("ws://localhost:8080/echo/");
        try {
            this.container = ContainerProvider.getWebSocketContainer();
            Session session = container.connectToServer(CommandSocket.class, uri);
            ClientModel.getInstance().setSession(session);
        }
        catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    @Override
    public void login(String username, String password) {
        Object[] paramTypes = {(Object)username.getClass().toString(), (Object)password.getClass().toString()};
        String[] paramValues = {username, password};
        Command loginCommand = CommandFactory.createCommand("ServerLoginFacade", "_login", paramTypes, paramValues);
        // TODO - send login to Server via socket
        Sender.sendCommand(loginCommand, ClientModel.getInstance().getSession());
    }

    @Override
    public void register(String username, String password) {
        String[] paramTypes = {username.getClass().toString(), password.getClass().toString()};
        String[] paramValues = {username, password};
        Command registerCommand = CommandFactory.createCommand("ServerLoginFacade", "_register", paramTypes, paramValues);

        // TODO - Put sender functions into socket manager
        Sender.sendCommand(registerCommand, ClientModel.getInstance().getSession());
    }

}
