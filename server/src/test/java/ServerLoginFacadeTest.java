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
    ServerModel.instance().loggedInUsers.clear();
    ServerModel.instance().allUsers.clear();

    }
//
//    /
//    @Test
//    public void _login() {
//        String username = "alibub";
//        String password = "password";
//
//        ServerLoginFacade.instance()._register(username, password);
//
//        ServerLoginFacade.instance()._login(username, password);
//
//        assertTrue(ServerModel.instance().loggedInUsers.containsKey(username));
//
//    }
//
//    @Test
//    public void _registerT() {
//
//        String username = "alibub";
//        String password = "password";
//
//        ServerLoginFacade.instance()._register(username, password);
//
//        assertTrue(ServerModel.instance().allUsers.containsKey(username));
//
//        ServerLoginFacade.instance()._register(username, password);
//
//        assertTrue(ServerModel.instance().allUsers.size() == 1);
//    }
}