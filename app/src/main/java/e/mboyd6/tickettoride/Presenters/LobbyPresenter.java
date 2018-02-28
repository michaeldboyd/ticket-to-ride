package e.mboyd6.tickettoride.Presenters;

import android.content.Context;

import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Communication.ServerProxyLobbyFacade;
import e.mboyd6.tickettoride.Communication.ServerProxyLoginFacade;
import e.mboyd6.tickettoride.Model.ClientModel;

import com.example.sharedcode.communication.UpdateArgs;
import com.example.sharedcode.model.UpdateType;

import junit.framework.Assert;

import e.mboyd6.tickettoride.Presenters.Interfaces.ILobbyPresenter;
import e.mboyd6.tickettoride.Views.Activities.MainActivity;

/**
 * Created by jonathanlinford on 2/3/18.
 */

public class LobbyPresenter implements ILobbyPresenter, Observer{

     MainActivity mainActivity;

    public LobbyPresenter(Context context) {
        this.mainActivity = (MainActivity) context;

        ClientModel.getInstance().addObserver(this);
    }

    // View -> Facade
    @Override
    public void logOut() {
        ServerProxyLoginFacade.instance().logout(ClientModel.getInstance().getAuthToken());
    }

    @Override
    public void joinGame(String gameID) {
        ServerProxyLobbyFacade.instance().joinGame(ClientModel.getInstance().getAuthToken(), gameID);
    }

    /**
     * Used to create a game.
     *
     * @return weather the game was correctly created or not
     * @post a new game will be created. Creator will be automatically added
     */
    @Override
    public void createGame() {
        ServerProxyLobbyFacade.instance().createGame(ClientModel.getInstance().getAuthToken());
    }

    // Model -> View
    @Override
    public void gameCreated(String response) {
        mainActivity.onCreateGameResponse(response);
    }

    @Override
    public void updateGameList() {
        mainActivity.updateGameList(ClientModel.getInstance().getGames());
    }

    @Override
    public void logoutResponse(String message){
        mainActivity.onLogOutResponse(message);
    }

    @Override
    public void gameJoined(String message){
        mainActivity.onGameJoinedResponse(message);
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
            case GAME_LIST:
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
                System.out.println("ENUM ERROR");
                break;
        }

    }


}
