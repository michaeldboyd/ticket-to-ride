package e.mboyd6.tickettoride.Presenters;

import com.example.sharedcode.model.ChatMessage;
import com.example.sharedcode.model.UpdateType;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Communication.ChatServerFacadeProxy;
import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Presenters.Interfaces.IChatPresenter;

/**
 * Created by jonathanlinford on 2/26/18.
 */

public class ChatPresenter implements IChatPresenter, Observer {
    @Override
    public void sendMessage(String message) {
        ChatServerFacadeProxy.instance().sendChatMessage(ClientModel.getInstance().getAuthToken(), message,
                ClientModel.getInstance().getCurrentPlayer().getName(), ClientModel.getInstance().getCurrentGame().getGameID());
    }

    @Override
    public void chatReceived() {
        ArrayList<ChatMessage> chatMessages = ClientModel.getInstance().getCurrentGame().getChatMessages();
        //TODO: Call items on the fragment
    }

    @Override
    public void isTypingUpdated() {
        boolean isTyping = ClientModel.getInstance().getCurrentGame().isTyping();
        //TODO: Call items on the fragment
    }

    @Override
    public void detachView() {

    }

    @Override
    public void update(Observable observable, Object o) {
        UpdateType updateType = (UpdateType) o;

        switch(updateType){
            case CHAT_RECEIVED:
                chatReceived();
                break;
            case TYPING_UPDATED:
                isTypingUpdated();
                break;
            default:
                //System.out.println("ENUM ERROR");
                break;
        }
    }
}
