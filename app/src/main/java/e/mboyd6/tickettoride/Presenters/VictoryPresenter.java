package e.mboyd6.tickettoride.Presenters;

import com.example.sharedcode.communication.UpdateArgs;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

import junit.framework.Assert;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Communication.Proxies.GameplayProxy;
import e.mboyd6.tickettoride.Communication.Proxies.LobbyProxy;
import e.mboyd6.tickettoride.Facades.ClientGameplay;
import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Presenters.Interfaces.IVictoryPresenter;
import e.mboyd6.tickettoride.Views.Interfaces.IVictoryActivity;

/**
 * Created by jonathanlinford on 3/17/18.
 */

public class VictoryPresenter implements IVictoryPresenter, Observer {

    IVictoryActivity victoryActivity;
    Game game = ClientModel.getInstance().getCurrentGame();

    public VictoryPresenter(IVictoryActivity victoryActivity) {
    //TODO: Implement the context setting
        ClientModel.getInstance().addObserver(this);
        this.victoryActivity = victoryActivity;
    }

    @Override
    public List<Player> getPlayersByScore(){
        return game.getPlayerListByScore();
    }

    @Override
    public void returnToLobby() {
        LobbyProxy.instance().leaveGame(ClientModel.getInstance().getAuthToken(), ClientModel.getInstance().getCurrentGame().getGameID(),
                ClientModel.getInstance().getPlayerName());
    }

    @Override
    public void loadScreen() {

    }

    @Override
    public void detachView() {
        ClientModel.getInstance().deleteObserver(this);
    }

    @Override
    public List<Player> getPlayerListByScore() {
        return ClientModel.getInstance().getCurrentGame().getPlayerListByScore();
    }

    @Override
    public void update(Observable observable, Object o) {
        Assert.assertEquals(o.getClass(), UpdateArgs.class);
        UpdateArgs args = (UpdateArgs) o;
        switch (args.type){
            case VICTORY_READY:
                loadScreen();
                break;
            case SERVER_DISCONNECT:
                //TO JONNY, WITH LOVE, FROM MICHAEL <3
                break;
            default:
                break;
        }
    }
}
