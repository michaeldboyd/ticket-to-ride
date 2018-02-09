import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandMessage;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.WebSocketListener;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ClientEndpoint
@ServerEndpoint(value="/echo")
public class CommandSocket implements WebSocketListener
{
    Gson gson = new Gson();
    @OnOpen
    public void onWebSocketConnect(Session sess)
    {
       ServerModel.instance().session = sess;
       // TODO: linke each session with the appropriate user. this is where it all starts
    }



    @OnMessage
    public void onWebSocketText(String message)
    {
        System.out.println(message);
        Command res = gson.fromJson(message, Command.class);
        try {
            res.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason)
    {
        System.out.println("Socket Closed: " + reason);
    }

    @OnError
    public void onWebSocketError(Throwable cause)
    {
        cause.printStackTrace(System.err);
    }

    // NOTE: Dont use these function right now, use the other ones. They need to be here though
    @Override
    public void onWebSocketBinary(byte[] payload, int offset, int len) {}
    @Override
    public void onWebSocketClose(int statusCode, String reason) {}

    @Override
    public void onWebSocketConnect(org.eclipse.jetty.websocket.api.Session session) {}
}
