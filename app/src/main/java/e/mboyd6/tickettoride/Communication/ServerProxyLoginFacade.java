package e.mboyd6.tickettoride.Communication;

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerLoginFacade;

/**
 * Created by eric on 2/7/18.
 */

public class ServerProxyLoginFacade implements IServerLoginFacade {

    private static ServerProxyLoginFacade instance = new ServerProxyLoginFacade();

    private ServerProxyLoginFacade() {}


    @Override
    public void login(String username, String password) {
        String[] paramTypes = {username.getClass().toString(), password.getClass().toString()};
        String[] paramValues = {username, password};
        Command loginCommand = CommandFactory.createCommand("ServerLoginFacade", "register", paramTypes, paramValues);

        // TODO - Send loginCommand to Server via socket
    }

    @Override
    public void register(String username, String password) {
        String[] paramTypes = {username.getClass().toString(), password.getClass().toString()};
        String[] paramValues = {username, password};
        Command registerCommand = CommandFactory.createCommand("ServerLoginFacade", "login", paramTypes, paramValues);

        // TODO - send registerCommand to Server via socket
    }

}
