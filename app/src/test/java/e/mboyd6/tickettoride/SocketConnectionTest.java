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

public class SocketConnectionTest {

    @Before
    public void init()
    {

        try {
            WebSocketClient client = new SocketClient(new URI("ws://localhost:8080/echo/"));
            ClientModel.getInstance().setSocket(client);
            ClientModel.getInstance().getSocket().connect();
        } catch (URISyntaxException  e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateGame()
    {
        ServerProxyLobbyFacade.instance().createGame("");
    }
    @Test
    public void testRegister()
    {
        ServerProxyLoginFacade.instance().login("test1", "test", );
        ServerProxyLobbyFacade.instance().createGame("");
        ServerProxyLobbyFacade.instance().getGames("");
        ServerProxyLobbyFacade.instance().joinGame("", "asdf");
        ServerProxyLobbyFacade.instance().startGame("", "asdf");
        ServerProxyLobbyFacade.instance().getPlayersForGame("asdf", "");
        ServerProxyLobbyFacade.instance().leaveGame("", "asdf", "asdf");
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
