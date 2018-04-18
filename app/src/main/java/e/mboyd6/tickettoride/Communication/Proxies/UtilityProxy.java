package e.mboyd6.tickettoride.Communication.Proxies;

import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;

import com.example.sharedcode.interfaces.IUtility;


import java.util.HashMap;
import java.util.Map;

import e.mboyd6.tickettoride.Model.ClientModel;


/**
 * Created by mboyd6 on 2/23/2018.
 */

public class UtilityProxy implements IUtility {
    private static UtilityProxy _instance = new UtilityProxy();
    private Map args = new HashMap();
    private UtilityProxy() {args.put(JsonWriter.TYPE, true);}

    public static UtilityProxy instance() {return _instance;}

    private final String CLASS_NAME = "Facades.ServerUtility";
    @Override
    public void clearServer(String superSecretPassword) {
        String[] paramTypes = {superSecretPassword.getClass().toString()};
        String[] paramValues = {superSecretPassword};
        // the authToken is null because when logging in we don't have an auth token.
        Command command = CommandFactory.createCommand(null, CLASS_NAME, "_clearServer", paramTypes, paramValues);
        // TODO - send login to Server via socket
        Sender.sendToServer(command);
    }

    public void dontForgetMe(String authToken, String socketID, String gameID) {
        String[] paramTypes = {authToken.getClass().toString(), socketID.getClass().toString(), gameID.getClass().toString()};
        String[] paramValues = {authToken, socketID, gameID};
        // the authToken is null because when logging in we don't have an auth token.
        Command command = CommandFactory.createCommand(null, CLASS_NAME, "_dontForgetMe", paramTypes, paramValues);
        // TODO - send login to Server via socket
        Sender.sendToServer(command);
    }
}

