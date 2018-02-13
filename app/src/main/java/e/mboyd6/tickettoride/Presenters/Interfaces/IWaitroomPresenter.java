package e.mboyd6.tickettoride.Presenters.Interfaces;

import com.example.sharedcode.model.PlayerColors;

/**
 * Created by jonathanlinford on 2/2/18.
 */

public interface IWaitroomPresenter {


    void changePlayerColor(int color);

    void startGameResponse(String message);

    void leaveGame();

    void startGame();

    //Observer methods

    void updatePlayerList();


    void leaveGameResponse(String message);
}
