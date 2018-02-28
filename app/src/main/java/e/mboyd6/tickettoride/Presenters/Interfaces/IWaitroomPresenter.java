package e.mboyd6.tickettoride.Presenters.Interfaces;

import com.example.sharedcode.model.PlayerColors;

/**
 * Created by jonathanlinford on 2/2/18.
 */

public interface IWaitroomPresenter {

    // View -> Facade
    void changePlayerColor(int color);

    void leaveGame();

    void startGame();

    // Model -> View
    void updatePlayerList();

    void startGameResponse(String message);

    void leaveGameResponse(String message);
}
