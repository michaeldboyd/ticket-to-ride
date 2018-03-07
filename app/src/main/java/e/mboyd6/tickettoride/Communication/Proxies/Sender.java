package e.mboyd6.tickettoride.Communication.Proxies;

import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.UpdateArgs;
import com.example.sharedcode.model.UpdateType;

import java.util.HashMap;
import java.util.Map;

import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by mboyd6 on 3/6/2018.
 */

public class Sender {
    private static Map args = new HashMap();
    static {
        args.put(JsonWriter.TYPE, true);
    }
    public static void sendToServer(Command command) {
        try {
            ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(command, args));
        } catch(Exception e) {
            e.printStackTrace();
            UpdateArgs args = new UpdateArgs(UpdateType.SERVER_DISCONNECT,
                    false, "Server Disconnected. Make Rodham Proud");
            ClientModel.getInstance().sendUpdate(args);
        }
    }
}
