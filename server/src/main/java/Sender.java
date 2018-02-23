import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.communication.Command;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;


import java.io.IOException;
import java.util.*;

public class Sender implements Observer {
    private static Sender _instance;

    public static Sender instance() {

        if (_instance == null){
            _instance = new Sender();
        }

        return _instance;
    }


    public static boolean sendCommand(Command command, Session session){
        return _instance._sendCommand(command, session);
    }

    private boolean _sendCommand(Command command, Session sess) {
        Map args = new HashMap();
        args.put(JsonWriter.TYPE, true);
        command.set_authToken(null);
        try {
            if(sess == null)
                throw new Exception("Session was null, it was not found in the " +
                        "logged in sessions by the auth token");
            String resp = JsonWriter.objectToJson(command, args);
            RemoteEndpoint remote = sess.getRemote();
            assert remote != null;

            remote.sendString(resp);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void connectBySocketId(Command command, String id){
        _instance._connectBySocketId(command, id);
    }

    private void _connectBySocketId(Command command, String id) {
        Map args = new HashMap();
        args.put(JsonWriter.TYPE, true);
        Session sess = ServerModel.instance().getAllSessions().get(id);

        assert sess != null;

        try {
            String resp = JsonWriter.objectToJson(command, args);
            sess.getRemote().sendString(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void sendBroadcast(Command command) {
        _instance._sendBroadcast(command);
    }

    private void _sendBroadcast(Command command) {
        Map args = new HashMap();
        args.put(JsonWriter.TYPE, true);

        Map<String, Session> sessions = ServerModel.instance().getLoggedInSessions();
        for(Session s : sessions.values())
        {
            assert s != null;
            try {
                String resp = JsonWriter.objectToJson(command, args);
                s.getRemote().sendString(resp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    // *** OBSERVER ***

    @Override
    public void update(Observable o, Object arg) {
        //if we can send more than one arg in observable, then se should send the session in here as well.
        if (arg.getClass() != Command.class) {
            return;
        }
        Command command = (Command)arg;
        Session sess = ServerModel.instance().getLoggedInSessions().get(command.get_authToken());
        Sender.sendCommand(command, sess);
    }
}
