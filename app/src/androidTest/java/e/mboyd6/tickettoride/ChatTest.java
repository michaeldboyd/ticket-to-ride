package e.mboyd6.tickettoride;

import com.example.sharedcode.model.Game;

import junit.framework.Assert;

import org.java_websocket.client.WebSocketClient;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import e.mboyd6.tickettoride.Communication.SocketClient;
import e.mboyd6.tickettoride.Communication.Proxies.UtilityProxy;
import e.mboyd6.tickettoride.Communication.SocketManager;
import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by eric on 2/28/18.
 */

public class ChatTest {

    @Before
    public void init() {
        String username = "username";
        String password = "pass";

        try {
            WebSocketClient client = new SocketClient(new URI("ws://localhost:8080/echo/"));
            SocketManager.socket = client;
            SocketManager.socket.connect();
            Thread.sleep(1000);
            UtilityProxy.instance().clearServer("thisisoursupersecrettestpassword");
            Thread.sleep(1000);
            Assert.assertNotNull(SocketManager.socket);
            String id = SocketManager.socketID;
//            ServerProxyLoginFacade.instance().register(username, password, id);
            Thread.sleep(1000);
//            ServerProxyLobbyFacade.instance().createGame(ClientModel.getInstance().getAuthToken());
            Thread.sleep(2000);
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(ClientModel.getInstance().getAuthToken());
    }


    @Test
    public void runTest() {
        String authToken = ClientModel.getInstance().getAuthToken();

        ArrayList<Game> games = ClientModel.getInstance().getGames();
        Game game = games.get(0); // Should only be one game --> @Before

       // ChatServerFacadeProxy.instance().sendChatMessage(authToken, "hello world", "sender", game.getGameID());

        // Make sure that there is one and only one ChatMessage in the one game's ChatMessages
        Assert.assertEquals(1, game.getChatMessages().size());
    }
}
