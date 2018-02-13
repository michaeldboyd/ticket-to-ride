package e.mboyd6.tickettoride.Presenters;

import android.content.Context;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.PlayerColors;

import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Communication.ServerProxyLobbyFacade;
import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Model.UpdateType;
import e.mboyd6.tickettoride.Presenters.Interfaces.IWaitroomPresenter;
import e.mboyd6.tickettoride.Views.Activities.MainActivity;

/**
 * Created by jonathanlinford on 2/3/18.
 */

public class WaitroomPresenter implements IWaitroomPresenter, Observer {

    MainActivity mainActivity;

    public WaitroomPresenter(Context context) {
        this.mainActivity = (MainActivity) context;

        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void changePlayerColor(PlayerColors color){

    }



    //Called by the observer
    @Override
    public void updatePlayerList() {
        for(Game g:ClientModel.getInstance().getGames()) {
            if (g.getGameID().equals(ClientModel.getInstance().getCurrentGame().getGameID())) {
                mainActivity.updatePlayerList(g.getPlayers());
            }
        }
    }

    @Override
    public void startGame(String message) {
        mainActivity.onStartGameResponse(message);
    }

    @Override
    public void leaveGame() {
        ServerProxyLobbyFacade.instance().leaveGame(
                ClientModel.getInstance().getCurrentGame().getGameID(),
                ClientModel.getInstance().getCurrentPlayer().getPlayerID());
    }


    /**
     * This method handles the update of information in the model
     *
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {

        UpdateType updateType = (UpdateType) o;

        switch(updateType) {
            case PLAYERLIST:
                updatePlayerList();
                break;
            case GAMESTARTED:
                startGame(ClientModel.getInstance().getResponse());
                break;
            default:
                System.out.println("ENUM ERROR");
                break;
        }

    }
}
