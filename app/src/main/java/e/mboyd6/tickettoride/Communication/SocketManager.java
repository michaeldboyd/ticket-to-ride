package e.mboyd6.tickettoride.Communication;

import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;

import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by mboyd6 on 2/12/2018.
 */

public class SocketManager {

    public static String ConnectSocket(String url)
    {
        WebSocketImpl.DEBUG = true;
        String error = null;
        WebSocketClient client = null;
        try {
            client = new SocketClient(new URI("ws://192.168.1.233:8080/echo/"));

        } catch (URISyntaxException e) {
            error = "Yo, your socket didn't connect correctly... Sorry broseph. Error: " + e.getMessage();
            e.printStackTrace();
        }
        if(client != null)
        {
            client.connect();
            ClientModel.getInstance().setSocket(client);
        } else
        {
            error = "Yo, your socket didn't connect correctly... Sorry broseph";
        }
        return error;
    }


}
