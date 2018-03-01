package e.mboyd6.tickettoride.Communication;

import android.support.v7.app.AppCompatActivity;

import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;

import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.R;

/**
 * Created by mboyd6 on 2/12/2018.
 */

public class SocketManager {

    //Default set to own machine
    public static String ip = "10.0.2.2";
    //public static String ip = "192.168.1.134";

    public static String ConnectSocket(String url)
    {
        WebSocketImpl.DEBUG = true;
        String error = null;
        WebSocketClient client = null;
        try {
            client = new SocketClient(new URI("ws://" + ip + ":8080/echo/"));

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
