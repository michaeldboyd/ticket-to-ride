
import Facades.ServerLobby;
import Model.ServerModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class ServerLobbyFacadeTest {

    @Before
    public void setUp() throws Exception {
//        try {
//            WebSocketClient client = new DummyClientSocket(new URI("ws://localhost:8080/echo/"));
//            DummyClient.instance().setSocket(client);
//            DummyClient.instance().getSocket().connect();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void createGame() {
        ServerLobby._createGame(UUID.randomUUID().toString());
        assertTrue(ServerModel.instance().getGames().size() > 0);
    }

    @Test
    public void getGames() {

        ServerLobby._createGame(UUID.randomUUID().toString());
        ServerLobby._createGame(UUID.randomUUID().toString());
        ServerLobby._createGame(UUID.randomUUID().toString());


        assertTrue(ServerModel.instance().getGames().size() >= 3);

    }

    @Test
    public void joinGame() {

    }

    @Test
    public void leaveGame() {

    }

    @Test
    public void startGame() {
    }

    @Test
    public void getPlayersForGame() {
    }
}