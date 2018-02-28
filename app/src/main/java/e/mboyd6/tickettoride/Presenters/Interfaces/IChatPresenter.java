package e.mboyd6.tickettoride.Presenters.Interfaces;

/**
 * Created by jonathanlinford on 2/20/18.
 */

public interface IChatPresenter {

    // View -> Presenter
    void sendMessage(String message);
    void isTypingChanged(boolean isTyping, String name);

    // View -> Model
    void messagesRead();

    // Presenter -> View
    void chatReceived();
    void isTypingUpdated();
    void detachView();
}
