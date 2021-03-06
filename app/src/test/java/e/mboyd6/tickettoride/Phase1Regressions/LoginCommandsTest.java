package e.mboyd6.tickettoride.Phase1Regressions;

import junit.framework.Assert;

import org.java_websocket.client.WebSocketClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.TreeMap;

import e.mboyd6.tickettoride.Communication.Proxies.LoginProxy;
import e.mboyd6.tickettoride.Communication.SocketClient;
import e.mboyd6.tickettoride.Communication.Proxies.UtilityProxy;
import e.mboyd6.tickettoride.Communication.SocketManager;
import e.mboyd6.tickettoride.Model.ClientModel;

public class LoginCommandsTest {
    private final String[] names = {"michael", "santa"};
    @Before
    public void init()
    {

        try {
            WebSocketClient client = new SocketClient(new URI("ws://localhost:8080/echo/"));
            SocketManager.socket = client;
            SocketManager.socket.connect();
            Thread.sleep(1000);
            UtilityProxy.instance().clearServer("thisisoursupersecrettestpassword");
            Thread.sleep(1500);

        } catch (URISyntaxException | InterruptedException  e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoginCommands_AllCorrect()
    {
        try {
            String id = SocketManager.socketID;

            assert(id != null);
            assert(SocketManager.socket != null);
            Map<String, String> tokens = new TreeMap<String, String>();

            //REGISTER
            for(String n : names)
            {
                LoginProxy.instance().register(n, "pass", id);
                Thread.sleep(1000);
                String authToken = ClientModel.getInstance().getAuthToken();
                Assert.assertNotNull(authToken);
                tokens.put(n, authToken);
            }

            //LOGOUT
            for(String auth : tokens.values())
            {
                LoginProxy.instance().logout(auth);
                Thread.sleep(1000);
                String authToken = ClientModel.getInstance().getAuthToken();
                Assert.assertNull(authToken);
            }

            //LOGIN
            for(String n : tokens.keySet()) {
                LoginProxy.instance().login(n, "pass", id);
                Thread.sleep(1000);
                String authToken = ClientModel.getInstance().getAuthToken();
                Assert.assertNotNull(authToken);
                tokens.put(n, authToken);
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
        UtilityProxy.instance().clearServer("thisisoursupersecrettestpassword");
        SocketManager.socket.close();
        ClientModel.getInstance().clearInstance();
    }
}
