package e.mboyd6.tickettoride.Presenters;

import android.content.Context;

import com.example.sharedcode.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Communication.ClientLobbyFacade;
import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Presenters.Interfaces.ILobbyPresenter;
import e.mboyd6.tickettoride.Views.MainActivity;

/**
 * Created by jonathanlinford on 2/3/18.
 */

public class LobbyPresenter implements ILobbyPresenter, Observer{

     //mainActivity =


    Player currentPlayer = new Player();
    List<Player> players = new ArrayList<>();

    public LobbyPresenter(Context mainActivity) {
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void updateGameList() {
        //TODO: Hunter needs
    }

    @Override
    public void joinGame(String gameID) {

    }

    @Override
    public boolean createGame(String name, int numOfPlayers) {
        if(numOfPlayers >= 2 && numOfPlayers <= 5){
            return false;
        } else if(name == null || name.equals("") || name.equals(" ")){
            return false;
        } else {
            ClientLobbyFacade.instance().createGame();
            return true;
        }
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
