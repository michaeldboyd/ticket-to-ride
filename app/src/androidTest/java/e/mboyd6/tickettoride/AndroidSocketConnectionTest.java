package e.mboyd6.tickettoride;

import org.eclipse.jetty.util.component.LifeCycle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import e.mboyd6.tickettoride.Communication.CommandSocket;
import e.mboyd6.tickettoride.Communication.ServerProxyLoginFacade;
import e.mboyd6.tickettoride.Model.ClientModel;


public class AndroidSocketConnectionTest {
    WebSocketContainer container;

    @Before
    public void init()
    {
        URI uri = URI.create("ws://10.0.2.2:8080/poll");

        try
        {
            this.container = ContainerProvider.getWebSocketContainer();

            try
            {
                // Attempt Connect
                Session session = container.connectToServer(CommandSocket.class, uri);
                ClientModel.getInstance().setSession(session);
            }
            finally
            {

            }
        }
        catch (Throwable t)
        {
            t.printStackTrace(System.err);
        }
    }


    @Test
    public void test()
    {
        try {
            Session sess = ClientModel.getInstance().getSession();
            ServerProxyLoginFacade.instance().register("test", "test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void close()
    {
        // Force lifecycle stop when done with container.
        // This is to free up threads and resources that the
        // JSR-356 container allocates. But unfortunately
        // the JSR-356 spec does not handle lifecycles (yet)
        if (container instanceof LifeCycle)
        {
            try {
                ClientModel.getInstance().getSession().close();
                ((LifeCycle)container).stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
