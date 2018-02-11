package e.mboyd6.tickettoride.Presenters;

import android.content.Context;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.PlayerColors;

import java.io.Console;
import java.util.Observable;
import java.util.Observer;

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
    public void updateReadyPlayers() {
        ClientModel.getInstance().getPlayers();

    }

    @Override
    public void startGame() {

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
                updateReadyPlayers();
                break;
            case GAMESTARTED:
                startGame();
                break;
            default:
                System.out.println("ENUM ERROR");
                break;
        }

    }
}
