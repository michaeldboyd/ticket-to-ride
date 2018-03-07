package Facades;

import Communication.SocketManager;
import Model.ServerModel;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IChatServerFacade;
import com.example.sharedcode.model.ChatMessage;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public static void _getChatHistory(String authToken, String gameID) {
        instance().getChatHistory(authToken, gameID);
    }


    @Override
    public void sendChatMessage(String authToken, String message, String playerName, String gameID) {
        if (!ServerModel.instance().getChatMessagesForGame().containsKey(gameID)) {
            ServerModel.instance().getChatMessagesForGame().put(gameID, new ArrayList<>());
        }

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date now = new Date();
        String timestamp = df.format(now);

        ChatMessage newMessage = new ChatMessage(message, playerName, timestamp);
        ServerModel.instance().getChatMessagesForGame().get(gameID).add(newMessage);

        String[] paramTypes = {newMessage.getClass().toString(), gameID.getClass().toString()};
        Object[] paramValues = {newMessage, gameID};
        Command addChatCommand = CommandFactory.createCommand(authToken, "e.mboyd6.tickettoride.Facades.ClientChat", "_chatMessageReceived", paramTypes, paramValues);

        SocketManager.instance().notifyPlayersInGame(gameID, addChatCommand);
    }

    @Override
    public void sendIsTyping(String authToken, String gameID, String playerName, Boolean isTyping) {
        //TODO:
        
        if(isTyping) {
            ServerModel.instance().getGames().get(gameID).setPersonTyping(playerName);
        } else {
            ServerModel.instance().getGames().get(gameID).setPersonTyping(null);
        }

        String[] paramTypes = {playerName.getClass().toString(), isTyping.getClass().toString()};
        Object[] paramValues = {playerName, isTyping};
        Command command = CommandFactory.createCommand(authToken, "e.mboyd6.tickettoride.Facades.ClientChat", "_isTypingReceived", paramTypes, paramValues);

        SocketManager.instance().notifyPlayersInGame(gameID, command);
    }

    @Override
    public void getChatHistory(String authToken, String gameID) {
        ArrayList<ChatMessage> cm = ServerModel.instance().getChatMessagesForGame().get(gameID);
        if(cm != null) {
            String[] paramTypes = {cm.getClass().toString(), gameID.getClass().toString()};
            Object[] paramValues = {cm, gameID};
            Command addChatCommand = CommandFactory.createCommand(authToken, "e.mboyd6.tickettoride.Facades.ClientChat", "_chatHistoryReceived", paramTypes, paramValues);

            ServerModel.instance().notifyObserversForUpdate(addChatCommand);
        }

    }
}
