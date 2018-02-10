package e.mboyd6.tickettoride;

import org.java_websocket.client.WebSocketClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import e.mboyd6.tickettoride.Communication.ServerProxyLoginFacade;
import e.mboyd6.tickettoride.Communication.SocketClient;
import e.mboyd6.tickettoride.Model.ClientModel;

public class AndroidSocketTest {
    WebSocketClient client;
    @Before
    public void init()
    {
        this.client = null;
        try {
            client = new SocketClient(new URI("ws://192.168.255.178:8080/echo/"));

        } catch (URISyntaxException e) {

            e.printStackTrace();
        }
        if(client != null)
        {
            client.connect();
            ClientModel.getInstance().setSocket(client);
        } else
        {
            System.out.println("Yo, your socket didn't connect correctly... Sorry broseph");
        }
    }


    @Test
    public void test()
    {
        ServerProxyLoginFacade.instance().register("test1", "test");

    }

    @After
    public void close()
    {

    }
}
