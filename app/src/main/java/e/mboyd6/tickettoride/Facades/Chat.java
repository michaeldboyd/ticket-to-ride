package e.mboyd6.tickettoride.Facades;

import com.example.sharedcode.interfaces.IChatClientFacade;
import com.example.sharedcode.model.ChatMessage;
import com.example.sharedcode.model.Game;

import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by eric on 2/26/18.
 */

public class Chat implements IChatClientFacade {


    private static Chat _instance = new Chat();

    public static Chat instance() {

        if (_instance == null){
            _instance = new Chat();
        }

        return _instance;
    }

    private Chat() {}



    public static void _chatMessageReceived(ChatMessage chatMessage, String gameID) {
        _instance.chatMessageReceived(chatMessage, gameID);

        if(ClientModel.getInstance().getCurrentGame() == null) {      //Game hasn't been started
            for(Game g : ClientModel.getInstance().getGames()){
                if(gameID.equals(g.getGameID())){
                    g.getChatMessages().add(chatMessage);
                    break;
                }
            }

            //TODO: set changed and notify observers
        } else {    // if the game has started
            ClientModel.getInstance().getCurrentGame().getChatMessages().add(chatMessage);
            //TODO: set changed and notify observers
        }
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
