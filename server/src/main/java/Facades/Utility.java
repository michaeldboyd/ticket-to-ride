package Facades;

import Communication.Sender;
import Model.ServerModel;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IUtility;
import org.eclipse.jetty.websocket.api.Session;

import java.util.UUID;

public class Utility implements IUtility{
    private static Utility _instance = new Utility();
    private Utility() {}
    public static Utility instance() {return _instance;}

    public static void _clearServer(String password)
    {
        instance().clearServer(password);
    }
    public static void _initSocket(Session sess) { instance().initSocket(sess);}
    @Override
    public void clearServer(String password) {
        if(password.equals(ServerModel.instance().getTestPassword()))
        {
            //Clears the entirety of the root server model
            ServerModel.instance().getAllUsers().clear();
            ServerModel.instance().getLoggedInUsers().clear();
            ServerModel.instance().getLoggedInSessions().clear();
            ServerModel.instance().getAuthTokenToUsername().clear();
            ServerModel.instance().getGames().clear();
            ServerModel.instance().getChatMessagesForGame().clear();
        }
    }

    public void initSocket(Session sess){
        //create ID for socket.
        String id = UUID.randomUUID().toString();
        ServerModel.instance().getAllSessions().put(id, sess);

        //send command back to client.
        String[] paramTypes = {id.getClass().toString()};
        String[] paramValues = {id};
        Command initCommand = CommandFactory.createCommand(null,
                "e.mboyd6.tickettoride.Facades.Login",
                "_initSocket", paramTypes, paramValues);
        // Send logoutCommand to Client via socket
        Sender.instance().sendBySocketId(initCommand, id);
    }
}
