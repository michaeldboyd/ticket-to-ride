package com.example.server;

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IClientLoginFacade;

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

        String[] paramTypes = {authToken.getClass().toString(), message.getClass().toString()};
        String[] paramValues = {authToken, message};
        Command loginClientCommand = CommandFactory.createCommand("ClientLoginFacade", "login", paramTypes, paramValues);

        // TODO - Send loginCommand to Client via socket
    }

    @Override
    public void register(String authToken, String message) {
        // This is called after the Server has attempted to register a new user
        // If register was successful, message == null

        String[] paramTypes = {authToken.getClass().toString(), message.getClass().toString()};
        String[] paramValues = {authToken, message};
        Command registerClientCommand = CommandFactory.createCommand("ClientLoginFacade", "register", paramTypes, paramValues);

        // TODO - Send registerCommand to Client via socket
    }
}
