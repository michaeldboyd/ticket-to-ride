package e.mboyd6.tickettoride.Communication;

import com.example.sharedcode.interfaces.IChatClientFacade;
import com.example.sharedcode.model.ChatMessage;

/**
 * Created by eric on 2/26/18.
 */

public class ChatClientFacade implements IChatClientFacade {


    private static ChatClientFacade _instance = new ChatClientFacade();

    public static ChatClientFacade instance() {

        if (_instance == null){
            _instance = new ChatClientFacade();
        }

        return _instance;
    }

    private ChatClientFacade() {}



    public static void _chatMessageReceived(ChatMessage chatMessage, String gameID) {
        _instance.chatMessageReceived(chatMessage, gameID);
    }

    public static void _isTypingReceived(String playerName, Boolean isTyping) {
        _instance.isTypingReceived(playerName, isTyping);
    }


    @Override
    public void chatMessageReceived(ChatMessage chatMessage, String gameID) {
        // TODO: - Update ClientModel with new message for the game
    }

    @Override
    public void isTypingReceived(String playerName, Boolean isTyping) {
        // TODO: - Handle this event to show the "[player name] is typing" UI
    }
}
