package e.mboyd6.tickettoride.Facades;

import com.example.sharedcode.communication.UpdateArgs;
import com.example.sharedcode.interfaces.IClientGamplayFacade;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.UpdateType;

import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by jonathanlinford on 3/2/18.
 */

public class ClientGameplay implements IClientGamplayFacade {
    private static final ClientGameplay ourInstance = new ClientGameplay();
    private ClientModel model;

    public static ClientGameplay getInstance() {
        return ourInstance;
    }

    private ClientGameplay() {
        model = ClientModel.getInstance();
    }

    public static void _claimedRoute(String gameID, String playerID) {
        ourInstance.claimedRoute(gameID, playerID);
    }

    public static void _drewTrainCard(String gameID, String playerID) {
        ourInstance.drewTrainCard(gameID, playerID);
    }

    public static void _discardedTrainCard(String gameID, String playerID) {
        ourInstance.discardedTrainCard(gameID, playerID);
    }

    public static void _drewDestinationCard(String gameID, String playerID) {
        ourInstance.drewDestinationCard(gameID, playerID);
    }

    public static void _discardedDestinationCard(String gameID, String playerID) {
        ourInstance.discardedDestinationCard(gameID, playerID);
    }

    public static void _placedTrainCars(String gameID, String playerID) {
        ourInstance.placedTrainCars(gameID, playerID);
    }

    public static void _historyUpdated(String gameID, String historyItem) {
        ourInstance.historyUpdated(gameID, historyItem);

    }

    public static void _newPlayerTurn(String gameID, String playerID){
        ourInstance.newPlayerTurn(gameID, playerID);
    }

    public static void _updateGame(Game game, String message) {
        ourInstance.updateGame(game, message);
    }

    public static void _endGame(Game game, String message) {
        ourInstance.endGame(game, message);
    }

    @Override
    public void claimedRoute(String gameID, String playerID) {
        UpdateType type = UpdateType.CLAIMED_ROUTE;

    }

    @Override
    public void drewTrainCard(String gameID, String playerID) {
        UpdateType type = UpdateType.DREW_TRAIN_CARD;
    }

    @Override
    public void discardedTrainCard(String gameID, String playerID) {
        UpdateType type = UpdateType.DISCARDED_TRAIN_CARD;
    }

    @Override
    public void drewDestinationCard(String gameID, String playerID) {
        UpdateType type = UpdateType.DREW_DESTINATION_CARD;
    }

    @Override
    public void discardedDestinationCard(String gameID, String playerID) {
        UpdateType type = UpdateType.DISCARDED_DESTINATION_CARD;
    }

    @Override
    public void placedTrainCars(String gameID, String playerID) {
        UpdateType type = UpdateType.PLACED_TRAIN_CARS;
    }

    @Override
    public void historyUpdated(String gameID, String historyItem) {
        UpdateType type = UpdateType.HISTORY_UPDATED;

        ClientModel.getInstance().getCurrentGame().getHistory().add(historyItem);

        sendUpdate(type, true, "");
    }

    @Override
    public void newPlayerTurn(String gameID, String playerID) {
        UpdateType type = UpdateType.NEW_PLAYER_TURN;

        ClientModel.getInstance().getCurrentGame().setCurrentTurnPlayerName(playerID);

        sendUpdate(type, true, "");
    }

    @Override
    public void updateGame(Game game, String message) {
        UpdateType type = UpdateType.GAME_UPDATED;

        boolean success = message.equals("");
        if(success) {
            if (ClientModel.getInstance().getCurrentGame().getGameID().equals(game.getGameID())) {
                ClientModel.getInstance().setCurrentGame(game);
            } else {
                message = "Game sent does not match current game";
            }
        }

        sendUpdate(type, success, message);

        //If this is the final round, then let the players know
        if(model.getCurrentGame().isLastRound()){
            sendUpdate(UpdateType.FINAL_ROUND, success, null);
        }
    }

    @Override
    public void endGame(Game game, String message) {
        UpdateType type = UpdateType.GAME_DONE;

        boolean success = message.equals("");
        if(success) {
            if (ClientModel.getInstance().getCurrentGame().getGameID().equals(game.getGameID())) {
                ClientModel.getInstance().setCurrentGame(game);
            } else {
                message = "Game sent does not match current game";
            }
        }

        sendUpdate(type, success, message);
    }

    private void sendUpdate(UpdateType type, boolean success, String error)
    {
        UpdateArgs args = new UpdateArgs(type, success, error);
        ClientModel.getInstance().sendUpdate(args);
    }
}
