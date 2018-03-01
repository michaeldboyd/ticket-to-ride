package Communication;

import Facades.Utility;
import Model.ServerModel;
import com.cedarsoftware.util.io.JsonReader;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.api.Session;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;


@ServerEndpoint(value="/echo/")
public class CommandSocket implements WebSocketListener
{



    @OnMessage
    public void onWebSocketText(String message)
    {
        System.out.println(message);
        Command req = (Command) JsonReader.jsonToJava(message);
        try {
            req.execute();
        } catch (InvocationTargetException e) {
            System.out.println(e.getCause().getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
    public void onWebSocketClose(int statusCode, String reason) {
    }

    /**
     *
     * @param session
     */
    @Override
    public void onWebSocketConnect(Session session) {
       Utility.instance().initSocket(session);

    }
}
