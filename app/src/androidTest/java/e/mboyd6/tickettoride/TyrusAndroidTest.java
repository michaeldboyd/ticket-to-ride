/*package e.mboyd6.tickettoride;


import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.junit.Test;

import java.net.URI;
import java.util.concurrent.Future;

import e.mboyd6.tickettoride.Communication.CommandSocket;
import e.mboyd6.tickettoride.Model.ClientModel;

public class TyrusAndroidTest {
    @Test
    public void ownClientimpl()
    {
        WebSocketClient client = new WebSocketClient();
        CommandSocket socket = new CommandSocket();

        URI uri = URI.create("ws://10.0.2.2:8080/echo/");
        try {
            client.start();
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            Future<Session> sesh =  client.connect(socket, uri, request);


        }
        catch (Throwable t) {
            t.printStackTrace();

        }
    }
}*/

