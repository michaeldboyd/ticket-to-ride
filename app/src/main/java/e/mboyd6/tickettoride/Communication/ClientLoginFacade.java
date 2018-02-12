package e.mboyd6.tickettoride.Communication;


import com.example.sharedcode.interfaces.IClientLoginFacade;
import com.example.sharedcode.interfaces.IServerLoginFacade;

import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ClientLoginFacade implements IClientLoginFacade {
    private static ClientLoginFacade loginFacade;

    public static ClientLoginFacade instance() {
        if (loginFacade == null) {
            loginFacade = new ClientLoginFacade();
        }

        return loginFacade;
    }


    public static void _loginReceived(String authToken, String message) {
        System.out.println("_loginReceived");
        instance().login(authToken, message);
    }

    public static void _registerReceived(String authToken, String message) {
        System.out.println("_registerReceived");
        instance().register(authToken, message);
    }



    //***** THESE METHODS ARE NOT CALLED FROM THE CLIENT PROXY--ONLY FROM THE CORRESPONDING STATIC METHODS *****

    @Override
    public void login(String authToken, String message) {
        // Received the command that said a user attempted to log in
        // If successful, message == "" (empty string)
        if(message.length() == 0) {
            System.out.println("Client has logged in successfully. Auth token: " + authToken);
            //update client model
        } else {
            System.out.println(message);
        }
        // Essentially, we need to update the Client-side model so that the UI will update properly
    }

    @Override
    public void register(String authToken, String message) {
        // Received the command that said a user attempted to register
        // If successful, message == "" (empty string)
        if(message.length() == 0) {
            ClientModel.getInstance().setAuthToken(authToken);
            System.out.println("Client has registered successfully. Auth token: " + authToken);
        } else {
            System.out.println(message);
        }
        //System.out.println("Client has registered Successfully! (And websockets now work)");
        // Essentially, we need to update the Client-side model so that the UI will update properly
    }
}
