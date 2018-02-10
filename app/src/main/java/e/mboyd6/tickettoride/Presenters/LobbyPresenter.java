package e.mboyd6.tickettoride.Presenters;

import android.content.Context;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Communication.ClientLobbyFacade;
import e.mboyd6.tickettoride.Communication.ServerProxyLobbyFacade;
import e.mboyd6.tickettoride.Communication.ServerProxyLoginFacade;
import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Model.UpdateType;
import e.mboyd6.tickettoride.Presenters.Interfaces.ILobbyPresenter;
import e.mboyd6.tickettoride.Views.Activities.MainActivity;

/**
 * Created by jonathanlinford on 2/3/18.
 */

public class LobbyPresenter implements ILobbyPresenter, Observer{

     MainActivity mainActivity;

    List<Player> players = new ArrayList<>();

    public LobbyPresenter(Context context) {
        this.mainActivity = (MainActivity) context;

        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void updateGameList() {
        mainActivity.updateGameList(ClientModel.getInstance().getGames());
    }

    @Override
    public void joinGame(String gameID) {
        ClientLobbyFacade.instance().joinGame(gameID, null);
    }

    /**
     * Used to create a game.
     *
     * @param name
     * @param numOfPlayers
     * @return weather the game was correctly created or not
     * @post a new game will be created. Creator will be automatically added
     */
    @Override
    public boolean createGame(String name, int numOfPlayers) {
        if(numOfPlayers >= 2 && numOfPlayers <= 5){
            return false;
        } else if(name == null || name.equals("") || name.equals(" ")){
            return false;
        } else {
            ServerProxyLobbyFacade
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
        UpdateType updateType = (UpdateType) o;

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
