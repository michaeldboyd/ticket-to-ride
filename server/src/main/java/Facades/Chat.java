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


    public static void _sendChatMessage(String authToken, String message, String playerName, String gameID) {
        instance().sendChatMessage(authToken, message, playerName, gameID);
    }

    public static void _sendIsTyping(String authToken, String playerName, Boolean isTyping) {
        instance().sendIsTyping(authToken, playerName, isTyping);
    }


    @Override
    public void sendChatMessage(String authToken, String message, String playerName, String gameID) {
        ServerModel.instance().addChatToGame(authToken, message, playerName, gameID);
    }

    @Override
    public void sendIsTyping(String authToken, String playerName, Boolean isTyping) {
        // TODO: - Just send a command for the ClientChatFacade directly to Communication.Sender because there is nothing to update on the model
    }
}
