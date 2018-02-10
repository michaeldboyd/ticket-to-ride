package e.mboyd6.tickettoride.Communication;



import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by mboyd6 on 2/10/2018.
 */

public class SocketManager {

    public static void socketConnect(String IPaddress, String port, String path)
    {
        Session session= ClientModel.getInstance().getSession();
        WebSocketContainer container = ClientModel.getInstance().getContainer();
        URI uri = URI.create("ws://192.168.255.178:8080/echo/");
        try {
            //If open, close the connections
            if(session != null)
                session.close();

            container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(CommandSocket.class, uri);

            ClientModel.getInstance().setSession(session);
            ClientModel.getInstance().setContainer(container);
        }
        catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
