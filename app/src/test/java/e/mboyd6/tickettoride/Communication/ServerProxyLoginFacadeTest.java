package e.mboyd6.tickettoride.Communication;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ali on 2/13/2018.
 */
public class ServerProxyLoginFacadeTest {
    @Test
    public void login() throws Exception {
        String username = "alibub";
        String password = "password";

        ServerLoginFacade.instance()._register(username, password);


    }

    @Test
    public void register() throws Exception {
    }

    @Test
    public void logout() throws Exception {
    }

}