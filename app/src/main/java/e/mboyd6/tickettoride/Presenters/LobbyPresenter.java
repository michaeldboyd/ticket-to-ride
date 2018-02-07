package e.mboyd6.tickettoride.Presenters;

import com.example.sharedcode.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Presenters.Interfaces.ILobbyPresenter;

/**
 * Created by jonathanlinford on 2/3/18.
 */

public class LobbyPresenter implements ILobbyPresenter, Observer{

    Player currentPlayer = new Player();
    List<Player> players = new ArrayList<>();

    public LobbyPresenter(Player player) {
        this.currentPlayer = player;

        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void updateGameList() {

    }

    @Override
    public void joinGame(String gameID) {

    }

    @Override
    public void createGame() {

    }

    /**
     * Handles the when the game lists changes
     *
     * @param observable
     * @param o
     *
     * @pre Object o should be an enum value from ClintModel.UpdateType
     */
    @Override
    public void update(Observable observable, Object o) {
        ClientModel.UpdateType updateType = (ClientModel.UpdateType) o;

        switch(updateType){
            case GAMELIST:
                updateGameList();
                break;
            default:
                System.out.println("ENUM ERROR");
                break;
        }

    }


}
