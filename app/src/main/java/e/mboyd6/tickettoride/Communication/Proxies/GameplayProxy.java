package e.mboyd6.tickettoride.Communication.Proxies;

import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerGamplayFacade;
import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Route;
import com.example.sharedcode.model.TrainCard;

import java.util.HashMap;
import java.util.Map;

import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by jonathanlinford on 3/2/18.
 */

public class GameplayProxy implements IServerGamplayFacade {

    private String CLASS_PATH = "Facades.Gameplay";
    private Map args = new HashMap();

    private static final GameplayProxy ourInstance = new GameplayProxy();

    public static GameplayProxy getInstance() {
        return ourInstance;
    }

    private GameplayProxy() {args.put(JsonWriter.TYPE, true);
    }

    @Override
    public void startGame(String authToken, String gameID) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString()};
        Object[] paramValues = {authToken, gameID};

        Command sendMessageCommand = CommandFactory.createCommand(null, CLASS_PATH, "_startGame", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(sendMessageCommand, args));
    }

    @Override
    public void claimRoute(String authToken, String gameID, String playerID, String city1, String city2) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString(), playerID.getClass().toString(), city1.getClass().toString(), city2.getClass().toString()};
        Object[] paramValues = {authToken, gameID, playerID, city1, city2};

        Command sendMessageCommand = CommandFactory.createCommand(null, CLASS_PATH, "_claimRoute", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(sendMessageCommand, args));
    }

    @Override
    public void drawTrainCard(String authToken, String gameID, String playerID) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString(), playerID.getClass().toString()};
        Object[] paramValues = {authToken, gameID, playerID};

        Command sendMessageCommand = CommandFactory.createCommand(null, CLASS_PATH, "_drawTrainCard", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(sendMessageCommand, args));
    }

    //TODO: This Should take place during the claimRoute function. Therefore, not needed
    @Override
    public void playTrainCard(String authToken, String gameID, String playerID, TrainCard card) {
    //TODO: This Should take place during the claimRoute function. Therefore, not needed

    }

    @Override
    public void drawDestinationCard(String authToken, String gameID, String playerID) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString(), playerID.getClass().toString()};
        Object[] paramValues = {authToken, gameID, playerID};

        Command sendMessageCommand = CommandFactory.createCommand(null, CLASS_PATH, "_drawDestinationCard", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(sendMessageCommand, args));
    }

    @Override
    public void discardDestinationCard(String authToken, String gameID, String playerID, DestinationCard card) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString(), playerID.getClass().toString(), card.getClass().toString()};
        Object[] paramValues = {authToken, gameID, playerID, card};

        Command sendMessageCommand = CommandFactory.createCommand(null, CLASS_PATH, "_discardDestinationCard", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(sendMessageCommand, args));
    }

    //TODO: This Should take place during the claimRoute function. Therefore, not needed
    @Override
    public void placeTrainCars(String authToken, String gameID, String playerID, int numCars, Route route) {
        //TODO: This Should take place during the claimRoute function. Therefore, not needed
    }

    @Override
    public void getGameHistory(String authToken, String gameID) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString()};
        Object[] paramValues = {authToken, gameID};

        Command sendMessageCommand = CommandFactory.createCommand(null, CLASS_PATH, "_getGameHistory", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(sendMessageCommand, args));
    }
}
