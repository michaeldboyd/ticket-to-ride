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

    public static void _initSocket(String id)
    {
        instance().initSocket(id);
    }
    public static void _loginReceived(String authToken, String message) {
        System.out.println("_loginReceived");
        instance().login(authToken, message);
    }

    public static void _registerReceived(String authToken, String message) {
        System.out.println("_registerReceived");
        instance().register(authToken, message);
    }

    public static void _logoutReceived(String authToken, String message)
    {
        System.out.println("_logoutReceived");
        instance().logout(authToken, message);
    }


    //***** THESE METHODS ARE NOT CALLED FROM THE CLIENT PROXY--ONLY FROM THE CORRESPONDING STATIC METHODS *****

    @Override
    public void login(String authToken, String message) {
        // Received the command that said a user attempted to log in
        // If successful, message == "" (empty string)
        ClientModel.getInstance().setLoginResponse(authToken, message);
        System.out.println("Cient Logged in called: " + authToken);


        // Essentially, we need to update the Client-side model so that the UI will update properly

    }

    @Override
    public void register(String authToken, String message) {
        // Received the command that said a user attempted to register
        // If successful, message == "" (empty string)
        System.out.println("Register was called. Auth token: " + authToken);
        ClientModel.getInstance().setRegisterResponse(authToken, message);

        //System.out.println("Client has registered Successfully! (And websockets now work)");
        // Essentially, we need to update the Client-side model so that the UI will update properly
    }

    @Override
    public void logout(String token, String message) {
        System.out.println("logout was called");

        ClientModel.getInstance().setLogoutResponse(message);
    }

    @Override
    public void initSocket(String id) {
        ClientModel.getInstance().setSocketID(id);
    }
}
