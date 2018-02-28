package e.mboyd6.tickettoride.Presenters;

import android.content.Context;

import com.example.sharedcode.model.Game;

import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Communication.ServerProxyLobbyFacade;
import e.mboyd6.tickettoride.Model.ClientModel;
import com.example.sharedcode.model.UpdateType;
import e.mboyd6.tickettoride.Presenters.Interfaces.IWaitroomPresenter;
import e.mboyd6.tickettoride.Views.Activities.MainActivity;
import e.mboyd6.tickettoride.Views.Interfaces.IWaitroomFragment;

/**
 * Created by jonathanlinford on 2/3/18.
 */

public class WaitroomPresenter implements IWaitroomPresenter, Observer {

    IWaitroomFragment waitroomFragment;

    public WaitroomPresenter(IWaitroomFragment waitroomFragment) {
        this.waitroomFragment = waitroomFragment;

        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void changePlayerColor(int color){
        String gameID = ClientModel.getInstance().getCurrentGame().getGameID();
        String playerID = ClientModel.getInstance().getPlayerID();
        ServerProxyLobbyFacade.instance().playerColorChanged(ClientModel.getInstance().getAuthToken(),
                gameID, playerID, color);
    }

    @Override
    public void leaveGame() {
        ServerProxyLobbyFacade.instance().leaveGame(ClientModel.getInstance().getAuthToken(),
                ClientModel.getInstance().getCurrentGame().getGameID(),
                ClientModel.getInstance().getPlayerID());
    }

    @Override
    public void startGame() {
        ServerProxyLobbyFacade.instance().startGame(ClientModel.getInstance().getAuthToken(),
                ClientModel.getInstance().getCurrentGame().getGameID());
    }

    @Override
    public boolean gameReady(){
        return ClientModel.getInstance().getCurrentGame().getPlayers().size() >= 2;

    }

    //Called by the observer
    @Override
    public void updatePlayerList() {
        Game currentGame = ClientModel.getInstance().getCurrentGame();
        if(currentGame != null) // if the current game is null, user is still in lobby.
        {
            for(Game g:ClientModel.getInstance().getGames()) {
                if (g.getGameID().equals(currentGame.getGameID())) {
                    waitroomFragment.updatePlayerList(g.getPlayers());
                }
            }
        }

    }

    @Override
    public void startGameResponse(String message) {
        waitroomFragment.onStartGameResponse(message);
    }

    @Override
    public void leaveGameResponse(String message){
        waitroomFragment.onLeaveGameResponse(message);
    }



    @Override
    public void detachView() {
        ClientModel.getInstance().deleteObserver(this);
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
            case GAME_LIST:
                updatePlayerList();
                break;
            case GAME_STARTED:
                startGameResponse(ClientModel.getInstance().getResponse());
                break;
            case GAME_LEFT:
                leaveGameResponse(ClientModel.getInstance().getResponse());
            default:
                //System.out.println("ENUM ERROR");
                break;
        }

    }
}
