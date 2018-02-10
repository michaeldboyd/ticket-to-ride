package e.mboyd6.tickettoride.Communication;
import com.example.sharedcode.communication.Command;
import com.google.gson.Gson;

import javax.websocket.Session;
import java.io.IOException;

public class Sender {
    private static Gson gson = new Gson();
    public static boolean sendCommand(Command command, Session sess){
        try {
            if(sess != null)
            {
                sess.getBasicRemote().sendText(gson.toJson(command));
            } else throw new Exception("Session is null");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
