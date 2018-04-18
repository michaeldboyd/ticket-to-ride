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
    boolean isClosed = SocketManager.socket.isClosed();
    boolean isConnecting = SocketManager.socket.isConnecting();
    @Override
    public void run() {
        while(isClosed || isConnecting) {
            try {
                SocketManager.socket.closeConnection(500, "ChangeIP");
                SocketManager.ConnectSocket();
            } catch (Exception e) {
                e.printStackTrace();
            }
            isClosed = SocketManager.socket.isClosed();
            isConnecting = SocketManager.socket.isConnecting();
            boolean isOpen = SocketManager.socket.isOpen();
        }

    }
}


    /*int nope = 0;
    WebSocketClient client = null;
    boolean success = false;
        while (!SocketManager.socket.isOpen() && nope < 600) {
        SocketManager.socket.getConnection().closeConnection(500, "Reconnect");
        try {
        SocketManager.socket.connectBlocking();
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
        }*/
//                //wait 1.5 seconds
//                while (!SocketManager.socket.isOpen() && nope < 600) { // try to reconnect for 10 minute
//                    try {
//                        Thread.sleep(1000);
//                        SocketManager.socket = new SocketClient(new URI("ws://" + SocketManager.ip + ":8080/echo/"));
//                        if (SocketManager.socket != null) {
//                            SocketManager.socket.connect();
//                            nope++;
//                            if (SocketManager.socket.isOpen())
//                                break;
//                            else
//                                SocketManager.socket.close();
//                        } else nope++;
//
//                        System.out.println("Trying to reconect...");
//                    } catch (Exception e) {
//                        nope++;
//                    }
