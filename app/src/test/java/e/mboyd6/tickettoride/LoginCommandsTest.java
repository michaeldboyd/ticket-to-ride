package e.mboyd6.tickettoride;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import e.mboyd6.tickettoride.Communication.ServerProxyLobbyFacade;
import e.mboyd6.tickettoride.Communication.ServerProxyLoginFacade;
import e.mboyd6.tickettoride.Communication.SocketClient;
import e.mboyd6.tickettoride.Model.ClientModel;

public class LoginCommandsTest {

    @Before
    public void init()
    {

        try {
            WebSocketClient client = new SocketClient(new URI("ws://localhost:8080/echo/"));
            ClientModel.getInstance().setSocket(client);
            ClientModel.getInstance().getSocket().connect();
            Thread.sleep(1000);
            //UtilityFacade.instance().clearServer("thisisoursupersecrettestpassword");
            Thread.sleep(1500);

        } catch (URISyntaxException | InterruptedException  e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRegister()
    {
        try {
            String id = ClientModel.getInstance().getSocketID();

            assert(id != null);
            assert(ClientModel.getInstance().getSocket() != null);
            Map<String, String> tokens = new TreeMap<String, String>();
            String[] names = {"michael", "johnny", "ali", "eric", "bekah", "rodham"};

            for(String n : names)
            {
                ServerProxyLoginFacade.instance().register(n, "pass", id);
                Thread.sleep(1000);
                String authToken = ClientModel.getInstance().getAuthToken();
                assert(authToken != null);
                tokens.put(n, authToken);
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLogout()
    {
        try {
            Thread.sleep(1000);
            String id = ClientModel.getInstance().getSocketID();
            WebSocketClient socket = ClientModel.getInstance().getSocket();

            assert(id != null);
            assert( socket != null);

            ServerProxyLoginFacade.instance().register("m", "pass", id);
            Thread.sleep(1000);

            String authToken = ClientModel.getInstance().getAuthToken();
            assert(authToken != null);

            ServerProxyLoginFacade.instance().logout(authToken);
            Thread.sleep(1000);
            authToken = ClientModel.getInstance().getAuthToken();
            socket = ClientModel.getInstance().getSocket();

            assert(authToken == null);
            assert(socket != null);
            assert(id.equals(ClientModel.getInstance().getSocketID()));

            System.out.println("Test Logout Succeeded");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testLogin()
    {
        try {

            String id = ClientModel.getInstance().getSocketID();

            assert(id != null);
            assert(ClientModel.getInstance().getSocket() != null);
            ServerProxyLoginFacade.instance().register("michael", "pass", id);
            Thread.sleep(1000);

            String authToken = ClientModel.getInstance().getAuthToken();
            assert(authToken != null);

            ServerProxyLoginFacade.instance().logout(authToken);
            Thread.sleep(1000);

            ServerProxyLoginFacade.instance().login("michael", "pass", id);
            Thread.sleep(1000);

            authToken = ClientModel.getInstance().getAuthToken();

            assert(authToken != null);
            System.out.println(String.format("Test Register Succeeded: %s ", authToken));

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

   /* ServerProxyLoginFacade.instance().login("test1", "test", "test");
        ServerProxyLobbyFacade.instance().createGame("");
        ServerProxyLobbyFacade.instance().getGames("");
        ServerProxyLobbyFacade.instance().joinGame("", "asdf");
        ServerProxyLobbyFacade.instance().startGame("", "asdf");
        ServerProxyLobbyFacade.instance().getPlayersForGame("asdf", "");
        ServerProxyLobbyFacade.instance().leaveGame("", "asdf", "asdf");*/
    @After
    public void close()
    {
        ClientModel.getInstance().getSocket().close();
        ClientModel.getInstance().clearInstance();
    }
}
