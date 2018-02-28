package e.mboyd6.tickettoride;

import junit.framework.Assert;

import org.java_websocket.client.WebSocketClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.TreeMap;

import e.mboyd6.tickettoride.Communication.ServerProxyLoginFacade;
import e.mboyd6.tickettoride.Communication.SocketClient;
import e.mboyd6.tickettoride.Communication.UtilityFacade;
import e.mboyd6.tickettoride.Model.ClientModel;

public class LoginCommandsTest {
    private final String[] names = {"michael", "johnny", "ali", "eric", "bekah", "rodham"};
    @Before
    public void init()
    {

        try {
            WebSocketClient client = new SocketClient(new URI("ws://localhost:8080/echo/"));
            ClientModel.getInstance().setSocket(client);
            ClientModel.getInstance().getSocket().connect();
            Thread.sleep(1000);
            UtilityFacade.instance().clearServer("thisisoursupersecrettestpassword");
            Thread.sleep(1500);

        } catch (URISyntaxException | InterruptedException  e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoginCommands_AllCorrect()
    {
        try {
            String id = ClientModel.getInstance().getSocketID();

            assert(id != null);
            assert(ClientModel.getInstance().getSocket() != null);
            Map<String, String> tokens = new TreeMap<String, String>();

            //REGISTER
            for(String n : names)
            {
                ServerProxyLoginFacade.instance().register(n, "pass", id);
                Thread.sleep(1000);
                String authToken = ClientModel.getInstance().getAuthToken();
                Assert.assertNotNull(authToken);
                tokens.put(n, authToken);
            }

            //LOGOUT
            for(String auth : tokens.values())
            {
                ServerProxyLoginFacade.instance().logout(auth);
                Thread.sleep(1000);
                String authToken = ClientModel.getInstance().getAuthToken();
                Assert.assertNull(authToken);
            }

            //LOGIN
            for(String n : tokens.keySet())
            {
                ServerProxyLoginFacade.instance().login(n, "pass", id);
                Thread.sleep(1000);
                String authToken = ClientModel.getInstance().getAuthToken();
                Assert.assertNotNull(authToken);
                tokens.put(n, authToken);
            }

            //LOGOUT AGAIN
            for(String auth : tokens.values())
            {
                ServerProxyLoginFacade.instance().logout(auth);
                Thread.sleep(1000);
                String authToken = ClientModel.getInstance().getAuthToken();
                Assert.assertNull(authToken);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void LoginFacade_IncorrectInput() {

    }

    @Test
    public void LoginFacade_SocketDisconnect() {

    }

    @After
    public void close()
    {
        UtilityFacade.instance().clearServer("thisisoursupersecrettestpassword");
        ClientModel.getInstance().getSocket().close();
        ClientModel.getInstance().clearInstance();
    }
}
