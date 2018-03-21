package e.mboyd6.tickettoride.Presenters;

import com.example.sharedcode.communication.UpdateArgs;

import junit.framework.Assert;

import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Presenters.Interfaces.IVictoryPresenter;

/**
 * Created by jonathanlinford on 3/17/18.
 */

public class VictoryPresenter implements IVictoryPresenter, Observer {

    public VictoryPresenter() {
    //TODO: Implement the context setting
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void returnToLobby() {

    }

    @Override
    public void loadScreen() {

    }

    @Override
    public void detachView() {
        ClientModel.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        Assert.assertEquals(o.getClass(), UpdateArgs.class);
        UpdateArgs args = (UpdateArgs) o;
        switch (args.type){
            case VICTORY_READY:
                loadScreen();
                break;
            default:
                break;
        }
    }
}
