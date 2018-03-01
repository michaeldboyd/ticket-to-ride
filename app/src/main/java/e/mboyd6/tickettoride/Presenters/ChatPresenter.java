package e.mboyd6.tickettoride.Presenters;

import com.example.sharedcode.communication.UpdateArgs;
import com.example.sharedcode.model.ChatMessage;
import com.example.sharedcode.model.UpdateType;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Communication.ChatServerFacadeProxy;
import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Presenters.Interfaces.IChatPresenter;
import e.mboyd6.tickettoride.Views.Interfaces.IChatFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IGameActivity;

/**
 * Created by jonathanlinford on 2/26/18.
 */

public class ChatPresenter implements IChatPresenter, Observer {

    IChatFragment chatFragment;
    IGameActivity gameActivity;

    public ChatPresenter(Object o) {
        if(o instanceof IGameActivity)
            this.gameActivity = (IGameActivity) o;
        else
            this.chatFragment = (IChatFragment) o;


        ClientModel.getInstance().addObserver(this);

    }

    @Override
    public String getPlayerID() {
        return ClientModel.getInstance().getPlayerID();
    }

    // View -> Facade
    @Override
    public void sendMessage(String message) {
        ChatServerFacadeProxy.instance().sendChatMessage(ClientModel.getInstance().getAuthToken(), message,
                ClientModel.getInstance().getCurrentPlayer().getName(), ClientModel.getInstance().getCurrentGame().getGameID());
    }

    @Override
    public void isTypingChanged(boolean isTyping){
        ChatServerFacadeProxy.instance().sendIsTyping(ClientModel.getInstance().getAuthToken(),
                ClientModel.getInstance().getCurrentPlayer().getName(), isTyping);
    }

    // View -> Model
    @Override
    public void messagesRead(){
        ClientModel.getInstance().getCurrentGame().setUnreadMessages(0);
    }


    // Model -> View
    @Override
    public void chatReceived() {
        ArrayList<ChatMessage> chatMessages = ClientModel.getInstance().getCurrentGame().getChatMessages();
        int unreadMessages = ClientModel.getInstance().getCurrentGame().getUnreadMessages();

        chatFragment.updateChat(chatMessages);
        if (gameActivity != null)
        gameActivity.onChatReceived(chatMessages, unreadMessages);
    }

    @Override
    public void isTypingUpdated() {
        boolean isTyping = ClientModel.getInstance().getCurrentGame().isTyping();

        chatFragment.updateTyping(isTyping, ClientModel.getInstance().getCurrentGame().getPersonTyping());
        if (gameActivity != null)
        gameActivity.onIsTypingChanged(isTyping);
    }

    @Override
    public void detachView() {

    }

    @Override
    public void update(Observable observable, Object o) {

        Assert.assertEquals(o.getClass(), UpdateArgs.class);
        UpdateArgs args = (UpdateArgs) o;

        switch(args.type){
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
