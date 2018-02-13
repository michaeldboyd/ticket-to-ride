package e.mboyd6.tickettoride.Presenters.Interfaces;

import com.example.sharedcode.model.PlayerColors;

/**
 * Created by jonathanlinford on 2/2/18.
 */

public interface IWaitroomPresenter {


    void changePlayerColor(PlayerColors color);

    void startGame(String message);

    void leaveGame();

    //Observer methods

    void updateReadyPlayers();



}
