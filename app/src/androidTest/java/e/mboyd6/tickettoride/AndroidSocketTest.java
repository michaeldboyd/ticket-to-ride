package e.mboyd6.tickettoride;

import org.java_websocket.client.WebSocketClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import e.mboyd6.tickettoride.Communication.ServerProxyLobbyFacade;
import e.mboyd6.tickettoride.Communication.ServerProxyLoginFacade;
import e.mboyd6.tickettoride.Communication.SocketClient;
import e.mboyd6.tickettoride.Model.ClientModel;

public class AndroidSocketTest {

    @Before
    public void init()
    {

        try {
            //Thread.sleep(5000);
            //Thread.sleep(3000);
            WebSocketClient client = new SocketClient(new URI("ws://192.168.255.178:8080/echo/"));


            ClientModel.getInstance().setSocket(client);

        } catch (URISyntaxException  e) { //| InterruptedException

            e.printStackTrace();
        }
        if(ClientModel.getInstance().getSocket() != null)
        {
            ClientModel.getInstance().getSocket().connect();

        } else
        {
            System.out.println("Yo, your socket didn't connect correctly... Sorry broseph");
        }
    }

    @Test
    public void test()
    {
        ServerProxyLoginFacade.instance().register("test2", "test");
        ServerProxyLoginFacade.instance().login("test1", "test");
        ServerProxyLobbyFacade.instance().getGames();
        ServerProxyLobbyFacade.instance().createGame();
        ServerProxyLobbyFacade.instance().startGame("asdf");
        ServerProxyLobbyFacade.instance().getPlayersForGame("asdf");


    }

    @After
    public void close()
    {

    }
}
