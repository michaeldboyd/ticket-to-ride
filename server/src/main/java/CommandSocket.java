import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.google.gson.Gson;
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
    Gson gson = new Gson();



    @OnMessage
    public void onWebSocketText(String message)
    {
        System.out.println(message);
        Command res = gson.fromJson(message, Command.class);
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
        //create ID for socket.
        String id = UUID.randomUUID().toString();
        ServerModel.instance().getAllSessions().put(id, session);

        //send command back to client.
        String[] paramTypes = {id.getClass().toString()};
        String[] paramValues = {id};
        Command initCommand = CommandFactory.createCommand(null,
                "e.mboyd6.tickettoride.Communication.ClientLoginFacade",
                "_initSocket", paramTypes, paramValues);
        // Send logoutCommand to Client via socket
        Sender.instance().sendBySocketId(initCommand, id);

    }
}
