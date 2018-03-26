package e.mboyd6.tickettoride.Presenters.Interfaces;

import com.example.sharedcode.model.Player;

import java.util.List;

/**
 * Created by jonathanlinford on 3/17/18.
 */

public interface IVictoryPresenter {
    List<Player> getPlayersByScore();

    void returnToLobby();
    void loadScreen();
    void detachView();
    List<Player> getPlayerListByScore();
}
