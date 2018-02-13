import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class ServerLobbyFacadeTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createGame() {
        ServerLobbyFacade.instance()._createGame(UUID.randomUUID().toString());

        assertTrue(ServerModel.instance().games.size() > 0);
    }

    @Test
    public void getGames() {

        ServerLobbyFacade.instance()._createGame(UUID.randomUUID().toString());
        ServerLobbyFacade.instance()._createGame(UUID.randomUUID().toString());
        ServerLobbyFacade.instance()._createGame(UUID.randomUUID().toString());


        assertTrue(ServerModel.instance().games.size() >= 3);

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