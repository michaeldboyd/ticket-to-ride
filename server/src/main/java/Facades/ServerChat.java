package Facades;

import Model.ServerModel;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IChatServerFacade;
import com.example.sharedcode.model.ChatMessage;

import java.util.ArrayList;

public class ServerChat implements IChatServerFacade {

    private static ServerChat chatServerFacade;

    private static ServerChat instance() {
        if (chatServerFacade == null) {
            chatServerFacade = new ServerChat();
        }

        return chatServerFacade;
    }

    private ServerChat(){};

    private final String CLASS_NAME = "e.mboyd6.tickettoride.Facades.ClientChat";
    public static void _sendChatMessage(String authToken, String message, String playerName, String gameID) {
        instance().sendChatMessage(authToken, message, playerName, gameID);
    }

    public static void _sendIsTyping(String authToken, String gameID, String playerName, Boolean isTyping) {
        instance().sendIsTyping(authToken, gameID, playerName, isTyping);
    }


    @Override
    public void sendChatMessage(String authToken, String message, String playerName, String gameID) {
        if (!ServerModel.instance().getChatMessagesForGame().containsKey(gameID)) {
            ServerModel.instance().getChatMessagesForGame().put(gameID, new ArrayList<>());
        }

        ChatMessage newMessage = new ChatMessage(message, playerName, null);
        ServerModel.instance().getChatMessagesForGame().get(gameID).add(newMessage);

        String[] paramTypes = {newMessage.getClass().toString(), gameID.getClass().toString()};
        Object[] paramValues = {newMessage, gameID};
        Command addChatCommand = CommandFactory.createCommand(authToken, "e.mboyd6.tickettoride.Facades.ClientChat", "_chatMessageReceived", paramTypes, paramValues);

        ServerModel.instance().notifyObserversForUpdate(addChatCommand);
    }

    @Override
    public void sendIsTyping(String authToken, String gameID, String playerName, Boolean isTyping) {
        //TODO:
        
        if(isTyping) {
            ServerModel.instance().getGames().get(gameID).setPersonTyping(playerName);
        } else {
            ServerModel.instance().getGames().get(gameID).setPersonTyping(null);
        }
    }
}
