import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.model.Game;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;


import java.io.IOException;
import java.util.*;

public class Sender implements Observer {
    private static Sender sender = new Sender();

    public static Sender instance() { return sender; }

    private Sender() {
        ServerModel.instance().addObserver(this);
    }
    private static Gson gson = new Gson();
    public static boolean sendCommand(Command command){
        Map args = new HashMap();
        args.put(JsonWriter.TYPE, true);
        //TODO this line below could throw a null exception. handle that ish, michael
        Session sess = ServerModel.instance().getLoggedInSessions().get(command.get_authToken());
        try {
            String resp = JsonWriter.objectToJson(command, args);
            sess.getRemote().sendString(resp);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void initialSocketConnect(Command command, String id){
        Map args = new HashMap();
        args.put(JsonWriter.TYPE, true);
        Session sess = ServerModel.instance().getAllSessions().get(id);
        try {
            String resp = JsonWriter.objectToJson(command, args);
            sess.getRemote().sendString(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void sendBroadcast(Command command) {
        Map args = new HashMap();
        args.put(JsonWriter.TYPE, true);

        Map<String, Session> sessions = ServerModel.instance().getLoggedInSessions();
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


    // *** OBSERVER ***

    @Override
    public void update(Observable o, Object arg) {
        if (arg.getClass() != Command.class) {
            return;
        }

        Command command = (Command)arg;
        Sender.sendCommand(command);
    }
}
