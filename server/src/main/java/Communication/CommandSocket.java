package Communication;

import Facades.ServerGameplay;
import Facades.ServerUtility;
import Model.ServerModel;
import Persistence.PersistenceManager;
import com.cedarsoftware.util.io.JsonReader;
import com.example.sharedcode.communication.Command;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.api.Session;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;
import java.lang.reflect.InvocationTargetException;


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

            if(shouldSaveCommand(req)){
                saveCommand(req);
            }
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
         ServerModel.instance().cleanSessions();
    }

    /**
     *
     * @param session
     */
    @Override
    public void onWebSocketConnect(Session session) {
       ServerUtility.instance().initSocket(session);

    }

    private boolean shouldSaveCommand(Command command){
        if(command.get_className().equals(ServerGameplay.class.toString())){
            return true;
        } else {
            return false;
        }
    }

    private void saveCommand(Command command){
        PersistenceManager.getInstance().getDatabaseFactory().createCommandDAO().storeGameCommand(command, getGameIDFromCommand(command));
    }

    private String getGameIDFromCommand(Command command){
        String[] paramTypes = command.get_paramTypesStringNames();
        Object[] params = command.get_paramValues();


        for (int i = 0; i < paramTypes.length; i++){
            if(paramTypes[i].equals("gameID")){
                return (String) command.get_paramValues()[i];
            }
        }

        return null;

    }
}
