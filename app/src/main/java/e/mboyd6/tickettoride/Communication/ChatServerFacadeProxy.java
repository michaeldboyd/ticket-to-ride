package e.mboyd6.tickettoride.Communication;

import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IChatServerFacade;
import com.google.gson.Gson;

import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by eric on 2/26/18.
 */

public class ChatServerFacadeProxy implements IChatServerFacade {

    private String CLASS_NAME = "ChatServerFacade";

    @Override
    public void sendChatMessage(String authToken, String message, String playerName, String gameID) {
        String[] paramTypes = {authToken.getClass().toString(), message.getClass().toString(), playerName.getClass().toString(), gameID.getClass().toString()};
        Object[] paramValues = {authToken, message, playerName, gameID};

        Command sendMessageCommand = CommandFactory.createCommand(null, CLASS_NAME, "_sendChatMessage", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(new Gson().toJson(sendMessageCommand));
    }

    @Override
    public void sendIsTyping(String authToken, String playerName, Boolean isTyping) {
        String[] paramTypes = {authToken.getClass().toString(), playerName.getClass().toString(), isTyping.getClass().toString()};
        Object[] paramValues = {authToken, playerName, isTyping};

        Command isTypingCommand = CommandFactory.createCommand(null, CLASS_NAME, "_sendIsTyping", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(new Gson().toJson(isTypingCommand));
    }
}
