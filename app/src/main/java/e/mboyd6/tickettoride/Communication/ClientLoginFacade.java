package e.mboyd6.tickettoride.Communication;


import com.example.sharedcode.interfaces.IClientLoginFacade;
import com.example.sharedcode.model.UpdateType;

import e.mboyd6.tickettoride.BuildConfig;
import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Utility.Assert;

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

        Assert.notNull(authToken, "_loginReceived was sent a null authToken");
        Assert.notNull(message, "_loginReceived was sent a null message");

        instance().login(authToken, message);
    }

    public static void _registerReceived(String authToken, String message) {
        Assert.notNull(authToken, "_registerReceived was sent a null authToken");
        Assert.notNull(message, "_registerReceived was sent a null message");
        System.out.println("_registerReceived");
        instance().register(authToken, message);
    }

    public static void _logoutReceived(String message)
    {
        System.out.println("_logoutReceived");
        Assert.notNull(message, "_loginReceived was sent a null message");
        instance().logout(message);
    }


    //***** THESE METHODS ARE NOT CALLED FROM THE CLIENT PROXY--ONLY FROM THE CORRESPONDING STATIC METHODS *****

    @Override
    public void login(String authToken, String message) {
        UpdateType type = UpdateType.LOGIN_RESPONSE;
        if(!handleError(message, type)){
            ClientModel.getInstance().setAuthToken(authToken, type);
        }
    }

    @Override
    public void register(String authToken, String message) {
        UpdateType type = UpdateType.REGISTER_RESPONSE;
        if(!handleError(message, type)) {
            ClientModel.getInstance().setAuthToken(authToken, UpdateType.REGISTER_RESPONSE);
        }
    }

    @Override
    public void logout(String message) {
        UpdateType type = UpdateType.LOGOUT_RESPONSE;
        if(!handleError(message, type))
        ClientModel.getInstance().sendUpdate(type);
    }

    @Override
    public void initSocket(String id) {
        ClientModel.getInstance().setSocketID(id);
    }

    private boolean handleError(String message, UpdateType type){
        if(message == null || message.equals(""))
            return false;
        else {
            ClientModel.getInstance().sendErrorUpdate(message, type);
            return true;
        }

    }
}
