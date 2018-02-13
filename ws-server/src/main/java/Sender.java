import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.model.Game;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;


import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Sender {
    private static Gson gson = new Gson();
    public static boolean sendCommand(Command command, String authToken){
        Map args = new HashMap();
        args.put(JsonWriter.TYPE, true);
        Session sess = ServerModel.instance().loggedInSessions.get(authToken);
        try {
            String resp = JsonWriter.objectToJson(command, args);
            sess.getRemote().sendString(resp);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void sendBroadcast(Command command) {
        Map args = new HashMap();
        args.put(JsonWriter.TYPE, true);

        Map<String, Session> sessions = ServerModel.instance().loggedInSessions;
        for(Session s : sessions.values())
        {
            try {

                String resp = JsonWriter.objectToJson(command, args);
                s.getRemote().sendString(resp);
                //s.getRemote().sendString(gson.toJson(command));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
