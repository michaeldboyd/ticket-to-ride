package e.mboyd6.tickettoride.Presenters;

import android.content.Context;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.PlayerColors;

import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Communication.ServerProxyLobbyFacade;
import e.mboyd6.tickettoride.Communication.ServerProxyLoginFacade;
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
    public void changePlayerColor(int color){
        String gameID = ClientModel.getInstance().getCurrentGame().getGameID();
        String playerID = ClientModel.getInstance().getCurrentPlayer().getPlayerID();
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

    //Called by the observer
    @Override
    public void updatePlayerList() {
        Game currentGame = ClientModel.getInstance().getCurrentGame();
        if(currentGame != null) // if the current game is null, user is still in lobby.
        {
            for(Game g:ClientModel.getInstance().getGames()) {
                if (g.getGameID().equals(currentGame.getGameID())) {
                    mainActivity.updatePlayerList(g.getPlayers());
                }
            }
        }

    }

    @Override
    public void startGameResponse(String message) {
        mainActivity.onStartGameResponse(message);
    }

    @Override
    public void leaveGameResponse(String message){
        mainActivity.onBackoutResponse(message);
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
            case GAMELIST:
                updatePlayerList();
                break;
            case GAMESTARTED:
                startGameResponse(ClientModel.getInstance().getResponse());
                break;
            case GAMELEFT:
                leaveGameResponse(ClientModel.getInstance().getResponse());
            default:
                System.out.println("ENUM ERROR");
                break;
        }

    }
}
