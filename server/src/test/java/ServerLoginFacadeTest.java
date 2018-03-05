import Model.ServerModel;
import org.junit.After;
import org.junit.Before;

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
//
//    /
//    @Test
//    public void _login() {
//        String username = "alibub";
//        String password = "password";
//
//        Facades.ServerLogin.instance()._register(username, password);
//
//        Facades.ServerLogin.instance()._login(username, password);
//
//        assertTrue(Model.ServerModel.instance().loggedInUsers.containsKey(username));
//
//    }
//
//    @Test
//    public void _registerT() {
//
//        String username = "alibub";
//        String password = "password";
//
//        Facades.ServerLogin.instance()._register(username, password);
//
//        assertTrue(Model.ServerModel.instance().allUsers.containsKey(username));
//
//        Facades.ServerLogin.instance()._register(username, password);
//
//        assertTrue(Model.ServerModel.instance().allUsers.size() == 1);
//    }
}