package e.mboyd6.tickettoride.Communication;


import com.example.sharedcode.interfaces.IClientLoginFacade;
import com.example.sharedcode.interfaces.IServerLoginFacade;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ClientLoginFacade implements IClientLoginFacade {
    private static ClientLoginFacade instance = new ClientLoginFacade();

    private ClientLoginFacade() {
    }


    public static void _login(String authToken, String message) {
        instance.login(authToken, message);
    }

    public static void _register(String authToken, String message) {
        instance.register(authToken, message);
    }



    //***** THESE METHODS ARE NOT CALLED FROM THE CLIENT PROXY--ONLY FROM THE CORRESPONDING STATIC METHODS *****

    @Override
    public void login(String authToken, String message) {
        // Received the command that said a user attempted to log in
        // If successful, message == null
        if(message == null)
        {
            System.out.println("Client has logged in successfully");
            //update client model
        }
        // Essentially, we need to update the Client-side model so that the UI will update properly
    }

    @Override
    public void register(String authToken, String message) {
        // Received the command that said a user attempted to register
        // If successful, message == null
        System.out.println("Client has registered Successfully! (And websockets now work)");
        // Essentially, we need to update the Client-side model so that the UI will update properly
    }
}
