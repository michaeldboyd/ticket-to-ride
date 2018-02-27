package e.mboyd6.tickettoride.Communication;

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerLoginFacade;
import com.google.gson.Gson;


import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by eric on 2/7/18.
 */

public class ServerProxyLoginFacade implements IServerLoginFacade {

    private static ServerProxyLoginFacade _instance = new ServerProxyLoginFacade();

    private ServerProxyLoginFacade() {}

    public static ServerProxyLoginFacade instance() {return _instance;}



    @Override
    public void login(String username, String password, String socketID) {

        String[] paramTypes = {username.getClass().toString(), password.getClass().toString(), socketID.getClass().toString()};
        String[] paramValues = {username, password, socketID};
        // the authToken is null because when logging in we don't have an auth token.
        Command loginCommand = CommandFactory.createCommand(null, "ServerLoginFacade", "_login", paramTypes, paramValues);
        // TODO - send login to Server via socket
        ClientModel.getInstance().getSocket().send(new Gson().toJson(loginCommand));
    }

    @Override
    public void register(String username, String password, String socketID) {
        String[] paramTypes = {"".getClass().toString(), "".getClass().toString(), "".getClass().toString()};
        String[] paramValues = {username, password, socketID};
        Command registerCommand = CommandFactory.createCommand(null, "ServerLoginFacade", "_register", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(new Gson().toJson(registerCommand));
    }

    @Override
    public void logout(String authToken) {
        String[] paramTypes = {authToken.getClass().toString()};
        String[] paramValues = {authToken};
        Command logoutCommand = CommandFactory.createCommand(null, "ServerLoginFacade", "_logout", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(new Gson().toJson(logoutCommand));
    }

}
