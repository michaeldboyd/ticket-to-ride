package e.mboyd6.tickettoride.Utility;

import com.example.sharedcode.communication.UpdateArgs;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.UpdateType;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.exceptions.WebsocketNotConnectedException;

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

    WebSocketClient socket;
    public Reconnector(WebSocketClient socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            System.out.println("******ATTEMTPING RECONNECT*******");
            socket.reconnectBlocking();
            Thread.sleep(3000);
            String at, id, gameID = "";
            Game game = ClientModel.getInstance().getCurrentGame();
            if(game != null) {
                gameID = game.getGameID();
            }
            at = ClientModel.getInstance().getAuthToken();
            id = ClientModel.getInstance().socketID;
            if (at != null && id != null) {
                UtilityProxy.instance().dontForgetMe(at, id, gameID);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Send toast to server
        UpdateType type = UpdateType.SERVER_DISCONNECT;
        boolean success = true;
        String message = "SERVER HAS RECONNECTED!!! YAY!!!";
        System.out.println(message);
        UpdateArgs args = new UpdateArgs(type, success, message);
        ClientModel.getInstance().sendUpdate(args);


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
