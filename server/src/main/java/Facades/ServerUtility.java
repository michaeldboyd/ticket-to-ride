package Facades;

import Communication.SocketManager;
import Model.ServerModel;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IUtility;
import com.example.sharedcode.model.Game;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.api.Session;

import java.util.Map;
import java.util.UUID;

public class ServerUtility implements IUtility{
    private static ServerUtility _instance = new ServerUtility();
    private ServerUtility() {}
    public static ServerUtility instance() {return _instance;}

    public static void _clearServer(String password)
    {
        instance().clearServer(password);
    }
    public static void _initSocket(Session sess) { instance().initSocket(sess);}
    public static void _dontForgetMe(String authToken, String socketID, String gameID){instance().identify(authToken, socketID, gameID);}
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
                "e.mboyd6.tickettoride.Facades.ClientLogin",
                "_initSocket", paramTypes, paramValues);
        // Send logoutCommand to Client via socket
        SocketManager.instance().sendBySocketId(initCommand, id);
    }

    public void identify(String authToken, String socketID, String gameID) {
        // match the username

        Session sess = ServerModel.instance().getAllSessions().get(socketID);
        Map<String, Session> sm = ServerModel.instance().getAllSessions();
        if(sess != null) {
            ServerModel.instance().getLoggedInSessions().put(authToken, sess);
        }


        // do we need to add these to the games?
        if(!gameID.equals("")) {
            Game currentGame = ServerModel.instance().getGames().get(gameID);
            if (currentGame != null) {
                ServerGameplay.getInstance().sendGameUpdate(authToken, currentGame, "");
            }
        }

    }
}
