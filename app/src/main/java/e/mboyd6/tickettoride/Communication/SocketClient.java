package e.mboyd6.tickettoride.Communication;

/**
 * Created by mboyd6 on 2/10/2018.
 */
import com.cedarsoftware.util.io.JsonReader;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.UpdateArgs;
import com.example.sharedcode.model.UpdateType;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.nio.ByteBuffer;

import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Utility.Reconnector;

public class SocketClient extends WebSocketClient {

    public SocketClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public SocketClient(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("new connection opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
        UpdateType type = UpdateType.SERVER_DISCONNECT;
        boolean success = true;
        String message = "Server was disconnected";
        UpdateArgs args = new UpdateArgs(type, success, message);
        ClientModel.getInstance().sendUpdate(args);

        Thread thread = new Thread(new Reconnector(SocketManager.socket));
        thread.start();

    }

    @Override
    public void onMessage(String message) {
        System.out.println("received: " + message);
        Command res = (Command) JsonReader.jsonToJava(message);
        try {
            res.execute();
        } catch (InvocationTargetException e) {
            System.out.println(e.getCause().getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(ByteBuffer message) {
        System.out.println("received ByteBuffer");
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }
}
