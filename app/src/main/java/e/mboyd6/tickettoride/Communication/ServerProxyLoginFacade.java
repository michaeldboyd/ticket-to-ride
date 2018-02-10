package e.mboyd6.tickettoride.Communication;

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerLoginFacade;
import com.google.gson.Gson;

import java.io.IOException;

import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by eric on 2/7/18.
 */

public class ServerProxyLoginFacade implements IServerLoginFacade {

    private static ServerProxyLoginFacade _instance = new ServerProxyLoginFacade();

    private ServerProxyLoginFacade() {}

    public static ServerProxyLoginFacade instance() {return _instance;}

    @Override
    public void login(String username, String password) {
        String[] paramTypes = {username.getClass().toString(), password.getClass().toString()};
        String[] paramValues = {username, password};
        Command loginCommand = CommandFactory.createCommand("ServerLoginFacade", "_login", paramTypes, paramValues);
        // TODO - send login to Server via socket
        try {
            ClientModel.getInstance().getSession()
                    .getBasicRemote()
                    .sendText(new Gson().toJson(loginCommand));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(String username, String password) {
        String[] paramTypes = {username.getClass().toString(), password.getClass().toString()};
        String[] paramValues = {username, password};
        Command registerCommand = CommandFactory.createCommand("ServerLoginFacade", "_register", paramTypes, paramValues);

        // TODO - send registerCommand to Server via socket
        try {
            ClientModel.getInstance().getSession()
                    .getBasicRemote()
                    .sendText(new Gson().toJson(registerCommand));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
