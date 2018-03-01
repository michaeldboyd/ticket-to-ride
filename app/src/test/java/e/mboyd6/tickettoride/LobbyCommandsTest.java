package e.mboyd6.tickettoride;

import com.example.sharedcode.model.Game;

import junit.framework.Assert;

import org.java_websocket.client.WebSocketClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import e.mboyd6.tickettoride.Communication.Proxies.LobbyProxy;
import e.mboyd6.tickettoride.Communication.Proxies.LoginProxy;
import e.mboyd6.tickettoride.Communication.SocketClient;
import e.mboyd6.tickettoride.Communication.UtilityFacade;
import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by mboyd6 on 2/28/2018.
 */

public class LobbyCommandsTest {

    Map<String, ClientModel> testModels = new HashMap<>();
    private final String[] names = {"michael", "ali", "eric", "hunter", "jonny"};

    @Before
    public void init(){
        testModels.clear();
        try {
            for(String n : names) {
                ClientModel model = ClientModel.getInstance().getTestInstance();
                WebSocketClient client = new SocketClient(new URI("ws://localhost:8080/echo/"));
                client.connect();
                Thread.sleep(1000);
                if(n.equals("michael")) {
                    UtilityFacade.instance().clearServer("thisisoursupersecrettestpassword");
                    Thread.sleep(1000);
                }
                model.setSocket(client);
                testModels.put(n, model);
            }

        } catch (URISyntaxException | InterruptedException  e) {
            e.printStackTrace();
        }
        //make a test clientmodel and connection for each person

    }
    @Test
    public void Create_Join_Game() {
        String id = ClientModel.getInstance().getSocketID();

        assert (id != null);
        assert (ClientModel.getInstance().getSocket() != null);
        Map<String, String> tokens = new TreeMap<String, String>();
        try {
            for (String n : names) {
                LoginProxy.instance().register(n, "pass", id);
                Thread.sleep(1000);
                String authToken = ClientModel.getInstance().getAuthToken();
                Assert.assertNotNull(authToken);
                tokens.put(n, authToken);
            }

            //Create a game
            LobbyProxy.instance().createGame(tokens.get("michael"));

            //get the game id
            ArrayList<Game> games = ClientModel.getInstance().getGames();
            Assert.assertNotNull(games);
            Game game = games.get(0);
            Assert.assertNotNull(game);
            String gameID = game.getGameID();
            Assert.assertNotNull(gameID);
            //join the game
            for (String n : names) {
                if (n.equals("michael")) // the create game already joins the game
                    continue;
                LobbyProxy.instance().joinGame(tokens.get(n), gameID);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        @After
        public void close()
        {
            UtilityFacade.instance().clearServer("thisisoursupersecrettestpassword");
            ClientModel.getInstance().getSocket().close();
            ClientModel.getInstance().clearInstance();
        }

}
