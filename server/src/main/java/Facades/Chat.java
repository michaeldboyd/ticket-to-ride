package Facades;

import Model.ServerModel;
import com.example.sharedcode.interfaces.IChatServerFacade;

public class Chat implements IChatServerFacade {

    private static Chat chatServerFacade;

    private static Chat instance() {
        if (chatServerFacade == null) {
            chatServerFacade = new Chat();
        }

        return chatServerFacade;
    }

    private Chat(){};

    private final String CLASS_NAME = "e.mboyd6.tickettoride.Facades.Chat";
    public static void _sendChatMessage(String authToken, String message, String playerName, String gameID) {
        instance().sendChatMessage(authToken, message, playerName, gameID);
    }

    public static void _sendIsTyping(String authToken, String gameID, String playerName, Boolean isTyping) {
        instance().sendIsTyping(authToken, gameID, playerName, isTyping);
    }


    @Override
    public void sendChatMessage(String authToken, String message, String playerName, String gameID) {
        ServerModel.instance().addChatToGame(authToken, message, playerName, gameID);
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
