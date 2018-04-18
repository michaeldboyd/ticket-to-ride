package e.mboyd6.tickettoride.Utility;

import org.java_websocket.client.WebSocketClient;

import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

import e.mboyd6.tickettoride.Communication.Proxies.UtilityProxy;
import e.mboyd6.tickettoride.Communication.SocketClient;
import e.mboyd6.tickettoride.Communication.SocketManager;
import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by mboyd6 on 4/17/2018.
 */

public class Reconnector implements Runnable{

    @Override
    public void run() {
        int nope = 0;
        WebSocketClient client = null;
        boolean success = false;
        while (!SocketManager.socket.isOpen() && nope < 600) { // try to reconnect for 10 minute
            try {
                //wait 1.5 seconds
                Thread.sleep(1000);
                SocketManager.socket = new SocketClient(new URI("ws://" + SocketManager.ip + ":8080/echo/"));
                if(SocketManager.socket != null) {
                    SocketManager.socket.connect();
                    if(SocketManager.socket.isOpen())
                        break;
                } else nope++;

                System.out.println("Trying to reconect...");

            } catch (Exception e) {
                nope++;
            }
        }
// server dies. on close happens. when server reconnects, command sent to client updating socket number. authtoken sent back to pair for session.
        if(SocketManager.socket.isOpen()) {
            // send command saying who I am.
            System.out.println("RECONNECTED");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String at = ClientModel.getInstance().getAuthToken();
            String id = SocketManager.socketID;
            if(at != null && id != null) {
                UtilityProxy.instance().dontForgetMe(at, id);
            }

        }
    }
}
