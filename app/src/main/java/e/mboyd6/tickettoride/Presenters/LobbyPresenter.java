package e.mboyd6.tickettoride.Presenters;

import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Communication.Proxies.LobbyProxy;
import e.mboyd6.tickettoride.Communication.Proxies.LoginProxy;
import e.mboyd6.tickettoride.Model.ClientModel;

import com.example.sharedcode.communication.UpdateArgs;

import junit.framework.Assert;

import e.mboyd6.tickettoride.Presenters.Interfaces.ILobbyPresenter;
import e.mboyd6.tickettoride.Views.Interfaces.ILobbyFragment;

/**
 * Created by jonathanlinford on 2/3/18.
 */

public class LobbyPresenter implements ILobbyPresenter, Observer{

     ILobbyFragment lobbyFragment;

    public LobbyPresenter(ILobbyFragment lobbyFragment) {
        this.lobbyFragment = lobbyFragment;

        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void updateGameList() {
        lobbyFragment.updateGameList(ClientModel.getInstance().getGames());
    }

    //TODO: Implement logOut method
    @Override
    public void logOut() {
        LoginProxy.instance().logout(ClientModel.getInstance().getAuthToken());
    }

    @Override
    public void joinGame(String gameID) {
        LobbyProxy.instance().joinGame(ClientModel.getInstance().getAuthToken(), gameID);
    }

    /**
     * Used to create a game.
     *
     * @return weather the game was correctly created or not
     * @post a new game will be created. Creator will be automatically added
     */
    @Override
    public void createGame() {
        LobbyProxy.instance().createGame(ClientModel.getInstance().getAuthToken());
    }

    @Override
    public void gameCreated(String response) {
        lobbyFragment.onCreateGameResponse(response);
    }

    @Override
    public void logoutResponse(String message){
        lobbyFragment.onLogOutResponse(message);
    }

    @Override
    public void gameJoined(String message){
        lobbyFragment.onGameJoinedResponse(message);
    }

    @Override
    public void detachView() {
        ClientModel.getInstance().deleteObserver(this);
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
        Assert.assertEquals(o.getClass(), UpdateArgs.class);
        UpdateArgs args = (UpdateArgs) o;
        switch(args.type){
            case LOBBY_LIST_UPDATED:
                updateGameList();
                break;
            case LOGOUT_RESPONSE:
                logoutResponse(args.error);
                break;
            case GAME_CREATED:
                gameCreated(args.error);
                break;
            case GAME_JOINED:
                gameJoined(args.error);
                break;
            default:
                //System.out.println("ENUM ERROR");
                break;
        }

    }


}
