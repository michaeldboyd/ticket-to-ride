package e.mboyd6.tickettoride.Utility;

import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;

import e.mboyd6.tickettoride.Communication.SocketClient;
import e.mboyd6.tickettoride.Communication.SocketManager;

/**
 * Created by mboyd6 on 4/17/2018.
 */

public class Reconnector implements Runnable{

    @Override
    public void run() {
        int nope = 0;
        WebSocketClient client = null;
        while (client != null && nope < 600) { // try to reconnect for 10 minute
            try {
                //wait 1.5 seconds
                Thread.sleep(1000);
                client = new SocketClient(new URI("ws://" + SocketManager.ip + ":8080/echo/"));
                client.connect();
                SocketManager.socket = client;
                System.out.println("Trying to reconect...");
            } catch (Exception e) {
                nope++;
            }
        }

        if(client != null) {
            // send command saying who I am.

        }
    }
}
