import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.model.Game;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;


import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class Sender {
    private static Gson gson = new Gson();
    public static boolean sendCommand(Command command, Session sess){
        try {
            sess.getRemote().sendString(gson.toJson(command));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void sendBroadcast(Command command) {
        Map<String, Session> sessions = ServerModel.instance().loggedInSessions;
        for(Session s : sessions.values())
        {
                Sender.sendCommand(command, s);
        }
    }
}
