package e.mboyd6.tickettoride.Communication.Proxies;

import com.cedarsoftware.util.io.JsonWriter;
import com.example.sharedcode.communication.Command;
import com.example.sharedcode.communication.CommandFactory;
import com.example.sharedcode.interfaces.IServerGameplayFacade;
import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.DestinationDeck;
import com.example.sharedcode.model.FaceUpDeck;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;
import com.example.sharedcode.model.TrainCard;
import com.example.sharedcode.model.TrainCardDeck;

import java.util.HashMap;
import java.util.Map;

import e.mboyd6.tickettoride.Model.ClientModel;

/**
 * Created by jonathanlinford on 3/2/18.
 */

public class GameplayProxy implements IServerGameplayFacade {

    private String CLASS_PATH = "Facades.ServerGameplay";
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
    public void claimRoute(String authToken, String gameID, Player player, Map<Route, Player> routesClaimed) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString(), player.getClass().toString(), routesClaimed.getClass().toString()};
        Object[] paramValues = {authToken, gameID, player, routesClaimed};

        Command sendMessageCommand = CommandFactory.createCommand(null, CLASS_PATH, "_claimRoute", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(sendMessageCommand, args));
    }

    @Override
    public void drawTrainCards(String authToken, String gameID, Player player, FaceUpDeck faceUpDeck, TrainCardDeck trainCardDeck, TrainCardDeck trainDiscardDeck) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString(), player.getClass().toString(),
                faceUpDeck.getClass().toString(), trainCardDeck.getClass().toString(), trainDiscardDeck.getClass().toString()};
        Object[] paramValues = {authToken, gameID, player, faceUpDeck, trainCardDeck, trainDiscardDeck};

        Command sendMessageCommand = CommandFactory.createCommand(null, CLASS_PATH, "_drawTrainCard", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(sendMessageCommand, args));
    }

    @Override
    public void drawDestinationCard(String authToken, String gameID, Player player, DestinationDeck destinationDeck) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString(), player.getClass().toString(), destinationDeck.getClass().toString()};
        Object[] paramValues = {authToken, gameID, player, destinationDeck};

        Command sendMessageCommand = CommandFactory.createCommand(null, CLASS_PATH, "_drawDestinationCard", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(sendMessageCommand, args));
    }

    @Override
    public void getGameHistory(String authToken, String gameID) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString()};
        Object[] paramValues = {authToken, gameID};

        Command sendMessageCommand = CommandFactory.createCommand(null, CLASS_PATH, "_getGameHistory", paramTypes, paramValues);

        ClientModel.getInstance().getSocket().send(JsonWriter.objectToJson(sendMessageCommand, args));
    }
}
