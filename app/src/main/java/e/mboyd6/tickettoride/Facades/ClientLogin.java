package e.mboyd6.tickettoride.Facades;


import com.example.sharedcode.communication.UpdateArgs;
import com.example.sharedcode.interfaces.IClientLoginFacade;
import com.example.sharedcode.model.UpdateType;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;

import e.mboyd6.tickettoride.Communication.SocketManager;
import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Utility.Assert;

/**
 * Created by mboyd6 on 2/1/2018.
 */

public class ClientLogin implements IClientLoginFacade {
    private static ClientLogin loginFacade;

    public static ClientLogin instance() {
        if (loginFacade == null) {
            loginFacade = new ClientLogin();
        }

        return loginFacade;
    }

    public static void _initSocket(String id)
    {
        instance().initSocket(id);
    }
    public static void _loginReceived(String authToken, String message) {
        Assert.notNull(authToken, "_loginReceived was sent a null authToken");
        Assert.notNull(message, "_loginReceived was sent a null error");

        instance().login(authToken, message);
    }

    public static void _registerReceived(String authToken, String message) {
        Assert.notNull(authToken, "_registerReceived was sent a null authToken");
        Assert.notNull(message, "_registerReceived was sent a null error");

        instance().register(authToken, message);
    }

    public static void _logoutReceived(String message)
    {
        instance().logout(message);
    }


    //***** THESE METHODS ARE NOT CALLED FROM THE CLIENT PROXY--ONLY FROM THE CORRESPONDING STATIC METHODS *****

    @Override
    public void login(String authToken, String message) {
        UpdateType type = UpdateType.LOGIN_RESPONSE;
        boolean success = isSuccess(message);

        if(success)
            ClientModel.getInstance().setAuthToken(authToken);

        sendUpdate(type, success, message);

    }

    @Override
    public void register(String authToken, String message) {
        UpdateType type = UpdateType.REGISTER_RESPONSE;
        boolean success = isSuccess(message);

        if(success) {
            ClientModel.getInstance().setAuthToken(authToken);
        }

        sendUpdate(type, success, message);
    }

    @Override
    public void logout(String message) {
        UpdateType type = UpdateType.LOGOUT_RESPONSE;
        boolean success = isSuccess(message);
        if(success) {
            ClientModel model = ClientModel.getInstance();
            WebSocketClient tempSocket = SocketManager.socket;
            String sockID = SocketManager.socketID;
            //model.resetInstance();
            model.clearInstance();
            model = ClientModel.getInstance();
            SocketManager.socket = tempSocket;
            SocketManager.socketID = sockID;
        }

        sendUpdate(type, success, message);
    }

    @Override
    public void initSocket(String id) {
        SocketManager.socketID = id;
        // send the username back to the server, and whether they are logged in (if they have an atuh token

    }

    private boolean isSuccess(String message){
        if(message == null || message.equals(""))
            return true;
        else return false;
    }

    private void sendUpdate(UpdateType type, boolean success, String error)
    {
        UpdateArgs args = new UpdateArgs(type, success, error);
        ClientModel.getInstance().sendUpdate(args);
    }
}
