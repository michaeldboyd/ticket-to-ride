package e.mboyd6.tickettoride.Facades;

import com.example.sharedcode.communication.UpdateArgs;
import com.example.sharedcode.interfaces.IChatClientFacade;
import com.example.sharedcode.model.ChatMessage;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.UpdateType;

import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by eric on 2/26/18.
 */

public class ClientChat implements IChatClientFacade {


    private static ClientChat _instance = new ClientChat();

    public static ClientChat instance() {

        if (_instance == null){
            _instance = new ClientChat();
        }

        return _instance;
    }

    private ClientChat() {}



    public static void _chatMessageReceived(ChatMessage chatMessage, String gameID) {
        _instance.chatMessageReceived(chatMessage, gameID);
    }

    public static void _isTypingReceived(String playerName, Boolean isTyping) {
        _instance.isTypingReceived(playerName, isTyping);
    }


    @Override
    public void chatMessageReceived(ChatMessage chatMessage, String gameID) {
        UpdateType type = UpdateType.CHAT_RECEIVED;
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

        }
        sendUpdate(type, true, "");
    }

    @Override
    public void isTypingReceived(String playerName, Boolean isTyping) {
        UpdateType type = UpdateType.TYPING_UPDATED;
        // TODO: What next?  the server is calling this method
        ClientModel.getInstance().getCurrentGame().setTyping(isTyping);
        ClientModel.getInstance().getCurrentGame().setPersonTyping(playerName);

        sendUpdate(type, true, "");

    }

    private void sendUpdate(UpdateType type, boolean success, String error)
    {
        UpdateArgs args = new UpdateArgs(type, success, error);
        ClientModel.getInstance().sendUpdate(args);
    }
}
