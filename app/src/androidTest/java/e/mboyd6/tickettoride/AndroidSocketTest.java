package e.mboyd6.tickettoride;

import org.java_websocket.client.WebSocketClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import e.mboyd6.tickettoride.Communication.Proxies.LobbyProxy;
import e.mboyd6.tickettoride.Communication.Proxies.LoginProxy;
import e.mboyd6.tickettoride.Communication.SocketClient;
import e.mboyd6.tickettoride.Communication.SocketManager;
import e.mboyd6.tickettoride.Model.ClientModel;

public class AndroidSocketTest {

    @Before
    public void init()
    {

        try {
            //Thread.sleep(5000);
            //Thread.sleep(3000);
            WebSocketClient client = new SocketClient(new URI("ws://localhost:8080/echo/"));


            SocketManager.socket = client;

        } catch (URISyntaxException  e) { //| InterruptedException

            e.printStackTrace();
        }
        if(SocketManager.socket != null)
        {
            SocketManager.socket.connect();

        } else
        {
            System.out.println("Yo, your socket didn't connect correctly... Sorry broseph");
        }
    }

    @Test
    public void testCreateGame()
    {
        LobbyProxy.instance().createGame("");
    }
    @Test
    public void testLogin()
    {
        LoginProxy.instance().login("test1", "test", "");
        LobbyProxy.instance().createGame("");
        LobbyProxy.instance().getGames("");
        LobbyProxy.instance().joinGame("", "asdf");
        LobbyProxy.instance().startGame("", "asdf");
        LobbyProxy.instance().leaveGame("", "asdf", "asdf");
    }
    /*public void testCreateGame()
    {

    }
    public void testJoinGame()
    {

    }
    public void testStartGame()
    {

    }
    public void testCreateGame()
    {

    }*/

    @After
    public void close()
    {

    }
}
