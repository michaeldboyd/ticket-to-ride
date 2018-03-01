package e.mboyd6.tickettoride.Phase1Regressions;

import com.example.sharedcode.model.Game;

import junit.framework.Assert;

import org.java_websocket.client.WebSocketClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import e.mboyd6.tickettoride.Communication.Proxies.LobbyProxy;
import e.mboyd6.tickettoride.Communication.Proxies.LoginProxy;
import e.mboyd6.tickettoride.Communication.SocketClient;
import e.mboyd6.tickettoride.Communication.UtilityFacade;
import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by mboyd6 on 2/28/2018.
 */

public class SingleClientLobbyCommands {
    String name = "michael";
    String password = "pass";


    @Before
    public void init() {
        try {
            WebSocketClient client = new SocketClient(new URI("ws://localhost:8080/echo/"));
            ClientModel.getInstance().setSocket(client);
            ClientModel.getInstance().getSocket().connect();
            Thread.sleep(1000);
            UtilityFacade.instance().clearServer("thisisoursupersecrettestpassword");
            Thread.sleep(1000);
            Assert.assertNotNull(ClientModel.getInstance().getSocketID());
            String id = ClientModel.getInstance().getSocketID();
            LoginProxy.instance().register(name, password, id);
            Thread.sleep(1000);
        } catch (URISyntaxException | InterruptedException  e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(ClientModel.getInstance().getAuthToken());
    }


    @Test
    public void FullLobbyFlow() {
        //Create a game
        try{
            LobbyProxy.instance().createGame(ClientModel.getInstance().getAuthToken());
            Thread.sleep(2000);
            //get the game id
            ArrayList<Game> games = ClientModel.getInstance().getGames();
            Assert.assertNotNull(games);

            //make sure the game was properly stored
            Game game = games.get(0);
            Assert.assertNotNull(game);

            String gameID = game.getGameID();
            Assert.assertNotNull(gameID);

            String playerID = ClientModel.getInstance().getPlayerID();
            Assert.assertNotNull(playerID);

            //did we correctly join the game?
            Assert.assertNotNull(ClientModel.getInstance().getCurrentGame());

            //Check getGames
            LobbyProxy.instance().getGames(ClientModel.getInstance().getAuthToken());
            Thread.sleep(1000);
            Assert.assertEquals(1, ClientModel.getInstance().getGames().size());
            //Start the game
            String auth = ClientModel.getInstance().getAuthToken();
            LobbyProxy.instance().startGame(auth, gameID);
            Thread.sleep(1000);

            LobbyProxy.instance().leaveGame(auth, gameID, playerID);
            Thread.sleep(1000);
            Assert.assertNull(ClientModel.getInstance().getCurrentGame());
            Assert.assertEquals(0, ClientModel.getInstance().getGames().size());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @After
    public void clean() {
        UtilityFacade.instance().clearServer("thisisoursupersecrettestpassword");
        ClientModel.getInstance().getSocket().close();
        ClientModel.getInstance().clearInstance();
    }
}
