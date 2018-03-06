package e.mboyd6.tickettoride.Communication;

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

public class UtilityFacade implements IUtility {
    private static UtilityFacade _instance = new UtilityFacade();
    private Map args = new HashMap();
    private UtilityFacade() {args.put(JsonWriter.TYPE, true);}

    public static UtilityFacade instance() {return _instance;}

    @Override
    public void clearServer(String superSecretPassword) {
        String[] paramTypes = {superSecretPassword.getClass().toString()};
        String[] paramValues = {superSecretPassword};
        // the authToken is null because when logging in we don't have an auth token.
        Command loginCommand = CommandFactory.createCommand(null, "Facades.Utility", "_clearServer", paramTypes, paramValues);
        // TODO - send login to Server via socket
        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(loginCommand, args));
    }
}

