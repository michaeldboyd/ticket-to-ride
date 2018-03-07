package e.mboyd6.tickettoride.Presenters;

import com.example.sharedcode.communication.UpdateArgs;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Communication.Proxies.LobbyProxy;
import e.mboyd6.tickettoride.Facades.ClientLobby;
import e.mboyd6.tickettoride.Model.ClientModel;

import junit.framework.Assert;

import e.mboyd6.tickettoride.Presenters.Interfaces.IWaitroomPresenter;
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

    // View -> Facade
    @Override
    public void changePlayerColor(int color){
        String gameID = ClientModel.getInstance().getCurrentGame().getGameID();
        String playerID = ClientModel.getInstance().getPlayerID();
        LobbyProxy.instance().playerColorChanged(ClientModel.getInstance().getAuthToken(),
                gameID, playerID, color);
    }

    @Override
    public void leaveGame() {
        LobbyProxy.instance().leaveGame(ClientModel.getInstance().getAuthToken(),
                ClientModel.getInstance().getCurrentGame().getGameID(),
                ClientModel.getInstance().getPlayerID());
    }

    @Override
    public void startGame() {
        LobbyProxy.instance().startGame(ClientModel.getInstance().getAuthToken(),
                ClientModel.getInstance().getCurrentGame().getGameID());
    }

    @Override
    public boolean gameReady(){
        return ClientModel.getInstance().getCurrentGame().getPlayers().size() >= 2;

    }

    // Model -> View
    @Override
    public void updatePlayerList() {
        Game currentGame = ClientModel.getInstance().getCurrentGame();
        if(currentGame != null) // if the current game is null, user is still in lobby.
        {
            for(Game g:ClientModel.getInstance().getGames()) {
                if (g.getGameID().equals(currentGame.getGameID())) {
                    waitroomFragment.updatePlayerList((Player[])g.getPlayers().values().toArray());
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
        Assert.assertEquals(o.getClass(), UpdateArgs.class);
        UpdateArgs args = (UpdateArgs) o;
        switch(args.type) {
            case LOBBY_LIST_UPDATED:
                updatePlayerList();
                break;
            case GAME_STARTED:
                startGameResponse(args.error);
                break;
            case GAME_LEFT:
                leaveGameResponse(args.error);
            case SERVER_DISCONNECT:
                leaveGameResponse(args.error);
            default:
                //System.out.println("ENUM ERROR");
                break;
        }

    }
}
