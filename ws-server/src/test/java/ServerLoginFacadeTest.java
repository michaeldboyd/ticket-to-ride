import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import com.example.sharedcode.interfaces.IServerLoginFacade;


public class ServerLoginFacadeTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    ServerModel.instance().getLoggedInUsers().clear();
    ServerModel.instance().getAllUsers().clear();

    }

    @Test
    public void _login() {
        String username = "alibub";
        String password = "password";

        ServerLoginFacade.instance()._register(username, password, null);

        ServerLoginFacade.instance()._login(username, password, null);

        assertTrue(ServerModel.instance().getLoggedInUsers().containsKey(username));

    }

    @Test
    public void _registerT() {

        String username = "alibub";
        String password = "password";

        ServerLoginFacade.instance()._register(username, password, null);

        assertTrue(ServerModel.instance().getAllUsers().containsKey(username));

        ServerLoginFacade.instance()._register(username, password, null);

        assertTrue(ServerModel.instance().getAllUsers().size() == 1);
    }
}