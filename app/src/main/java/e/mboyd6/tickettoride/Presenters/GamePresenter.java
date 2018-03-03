package e.mboyd6.tickettoride.Presenters;

import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Presenters.Interfaces.IGamePresenter;
import e.mboyd6.tickettoride.Views.Interfaces.IBoardFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IGameActivityFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IHandFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IHistoryFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IScoreFragment;

/**
 * Created by jonathanlinford on 3/2/18.
 */

public class GamePresenter implements IGamePresenter {

    private IGameActivityFragment gameActivityFragment;

    public GamePresenter(IBoardFragment boardFragment) {
        gameActivityFragment = boardFragment;
    }

    public GamePresenter(IHandFragment handFragment) {
        gameActivityFragment = handFragment;
    }

    public GamePresenter(IScoreFragment scoreFragment) {
        gameActivityFragment = scoreFragment;
    }

    public GamePresenter(IHistoryFragment historyFragment) {
        gameActivityFragment = historyFragment;
    }

    @Override
    public void drawDestinationCards() {
        
    }

    @Override
    public void chooseDestinationCards() {

    }

    @Override
    public void drawTrainCards() {

    }

    @Override
    public void sendDestinationCards() {

    }

    @Override
    public void onNewTurn(String PlayerID) {

    }

    @Override
    public void enterGame(String PlayerID) {

    }

    @Override
    public void leaveGame(String PlayerID) {

    }

    @Override
    public void updateBoard() {
        if (gameActivityFragment instanceof IBoardFragment)
            ((IBoardFragment) gameActivityFragment).updateBoard(ClientModel.getInstance().getCurrentGame());
        else if (gameActivityFragment instanceof IHandFragment)
            ((IHandFragment) gameActivityFragment).updateHand(ClientModel.getInstance().getCurrentGame());
    }
}
