package e.mboyd6.tickettoride.Facades;

import com.example.sharedcode.interfaces.IClientGamplayFacade;
import com.example.sharedcode.model.UpdateType;

/**
 * Created by jonathanlinford on 3/2/18.
 */

public class ClientGameplay implements IClientGamplayFacade {
    private static final ClientGameplay ourInstance = new ClientGameplay();

    public static ClientGameplay getInstance() {
        return ourInstance;
    }

    private ClientGameplay() {
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

    public static void _historyUpdated(String gameID, String[] s) {
        ourInstance.historyUpdated(gameID, s);

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
    public void historyUpdated(String gameID, String[] s) {
        UpdateType type = UpdateType.HISTORY_UPDATED;
    }
}
