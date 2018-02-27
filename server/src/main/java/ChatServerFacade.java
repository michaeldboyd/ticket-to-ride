import com.example.sharedcode.interfaces.IChatServerFacade;

public class ChatServerFacade implements IChatServerFacade {

    private static ChatServerFacade chatServerFacade;

    private static ChatServerFacade instance() {
        if (chatServerFacade == null) {
            chatServerFacade = new ChatServerFacade();
        }

        return chatServerFacade;
    }

    private ChatServerFacade(){};


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
        // TODO: - Just send a command for the ClientChatFacade directly to Sender because there is nothing to update on the model
    }
}
