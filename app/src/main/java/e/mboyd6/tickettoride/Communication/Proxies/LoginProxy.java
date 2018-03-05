package e.mboyd6.tickettoride.Communication.Proxies;

import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerLoginFacade;


import java.util.HashMap;
import java.util.Map;

import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by eric on 2/7/18.
 */

public class LoginProxy implements IServerLoginFacade {
    private Map args = new HashMap();
    private static LoginProxy _instance = new LoginProxy();

    private LoginProxy() {args.put(JsonWriter.TYPE, true);}

    public static LoginProxy instance() {return _instance;}

    public final String CLASS_NAME = "Facades.ServerLogin";

    @Override
    public void login(String username, String password, String socketID) {

        String[] paramTypes = {username.getClass().toString(), password.getClass().toString(), socketID.getClass().toString()};
        String[] paramValues = {username, password, socketID};
        // the authToken is null because when logging in we don't have an auth token.
        Command loginCommand = CommandFactory.createCommand(null, CLASS_NAME, "_login", paramTypes, paramValues);
        // TODO - send login to Server via socket
        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(loginCommand, args));
    }

    @Override
    public void register(String username, String password, String socketID) {
        String[] paramTypes = {"".getClass().toString(), "".getClass().toString(), "".getClass().toString()};
        String[] paramValues = {username, password, socketID};
        Command registerCommand = CommandFactory.createCommand(null, CLASS_NAME, "_register", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(registerCommand, args));
    }

    @Override
    public void logout(String authToken) {
        String[] paramTypes = {authToken.getClass().toString()};
        String[] paramValues = {authToken};
        Command logoutCommand = CommandFactory.createCommand(null, CLASS_NAME, "_logout", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(logoutCommand, args));
    }

}
