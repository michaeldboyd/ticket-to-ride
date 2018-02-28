package e.mboyd6.tickettoride.Presenters.Interfaces;

/**
 * Created by jonathanlinford on 2/20/18.
 */

public interface IChatPresenter {

    // View > Presenter
    void sendMessage(String message);

    // Presenter > View
    void chatReceived();
    void isTypingUpdated();
}
