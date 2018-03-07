package e.mboyd6.tickettoride.Communication.Proxies;

import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.communication.UpdateArgs;
import com.example.sharedcode.interfaces.IChatServerFacade;
import com.example.sharedcode.model.UpdateType;


import java.util.HashMap;
import java.util.Map;

import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by eric on 2/26/18.
 */

public class ChatProxy implements IChatServerFacade {

    private String CLASS_PATH = "Facades.ServerChat";
    private Map args = new HashMap();

    private static ChatProxy _instance = new ChatProxy();

    public static ChatProxy instance() {

        if (_instance == null){
            _instance = new ChatProxy();
        }

        return _instance;
    }

    private ChatProxy() {args.put(JsonWriter.TYPE, true);}



    @Override
    public void sendChatMessage(String authToken, String message, String playerName, String gameID) {
        String[] paramTypes = {authToken.getClass().toString(), message.getClass().toString(), playerName.getClass().toString(), gameID.getClass().toString()};
        Object[] paramValues = {authToken, message, playerName, gameID};

        Command sendMessageCommand = CommandFactory.createCommand(null, CLASS_PATH, "_sendChatMessage", paramTypes, paramValues);

        Sender.sendToServer(sendMessageCommand);

    }

    @Override
    public void sendIsTyping(String authToken, String gameID, String playerName, Boolean isTyping) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString(), playerName.getClass().toString(), isTyping.getClass().toString()};
        Object[] paramValues = {authToken, gameID, playerName, isTyping};

        Command isTypingCommand = CommandFactory.createCommand(null, CLASS_PATH, "_sendIsTyping", paramTypes, paramValues);

        Sender.sendToServer(isTypingCommand);
    }
}
