import com.example.sharedcode.communication.Command;
import com.google.gson.Gson;

import javax.websocket.Session;
import java.io.IOException;

public class Sender {
    private static Gson gson = new Gson();
    public static boolean sendCommand(Command command, Session sess){
        try {
            sess.getBasicRemote().sendText(gson.toJson(command));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}