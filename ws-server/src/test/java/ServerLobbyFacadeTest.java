import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServerLobbyFacadeTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void _createGame() {
        ServerLobbyFacade.instance()._createGame();

        assertTrue(ServerModel.instance().games.size() > 0);
    }

    @Test
    public void _getGames() {

        ServerLobbyFacade.instance()._createGame();
        ServerLobbyFacade.instance()._createGame();
        ServerLobbyFacade.instance()._createGame();


        assertTrue(ServerModel.instance().games.size() >= 3);

    }

    @Test
    public void _joinGame() {

    }

    @Test
    public void _leaveGame() {

    }

    @Test
    public void _startGame() {
    }

    @Test
    public void _getPlayersForGame() {
    }
}