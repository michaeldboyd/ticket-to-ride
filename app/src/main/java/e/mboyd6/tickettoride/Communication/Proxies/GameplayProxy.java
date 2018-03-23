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
import com.example.sharedcode.model.TrainCardDeck;

import java.util.HashMap;
import java.util.Map;

import e.mboyd6.tickettoride.Communication.SocketManager;

/**
 *
 * @invariant CLASS_PATH is immutable and corresponds to the ServerGameplay class on the client.
 * All methods create a command that will execute a method from that class.
 *
 * @invariant args is a map that has serialization information
 *
 * @invariant ourInstance is a singleton object of GameplayProxy
 */

public class GameplayProxy implements IServerGameplayFacade {

    private final String CLASS_PATH = "Facades.ServerGameplay";
    private Map args = new HashMap();

    private static final GameplayProxy ourInstance = new GameplayProxy();

    /**
     * @return ourInstance -- singleton
     */
    public static GameplayProxy getInstance() {
        return ourInstance;
    }

    /**
     * private so that standard constructor is unavailable outside
     */
    private GameplayProxy() {args.put(JsonWriter.TYPE, true);
    }


    @Override
    public void discardDestinationCard(String authToken, String gameID, Player player, DestinationCard destinationCard) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString(), player.getClass().toString(), destinationCard.getClass().toString()};
        Object[] paramValues = {authToken, gameID, player, destinationCard};

        Command sendMessageCommand = CommandFactory.createCommand(null, CLASS_PATH, "_discardDestinationCard", paramTypes, paramValues);

        SocketManager.socket.send(JsonWriter.objectToJson(sendMessageCommand, args));
    }

    /**
     *
     * @pre - all parameters are not null and correspond to the proper objects that need to be updated on the Server
     * @post - function is void, so the only assumption is that it passed the correct parameters to create a command to send to the Server
     *
     * @param authToken - authToken of the user sending the request so Server can identify the user
     * @param gameID - id of the game to be updated
     * @param player - updated Player object resulting from claiming a route
     * @param routesClaimed - all the claimed routes of the associated game
     */
    @Override
    public void claimRoute(String authToken, String gameID, Player player, Map<Route, Player> routesClaimed) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString(), player.getClass().toString(), routesClaimed.getClass().toString()};
        Object[] paramValues = {authToken, gameID, player, routesClaimed};

        Command sendMessageCommand = CommandFactory.createCommand(null, CLASS_PATH, "_claimRoute", paramTypes, paramValues);

        SocketManager.socket.send(JsonWriter.objectToJson(sendMessageCommand, args));
    }


    /**
     * @pre - all parameters are not null and correspond to the proper objects that need to be updated on the Server
     * @post - function is void, so the only assumption is that it passed the correct parameters to create a command to send to the Server
     *
     * @param authToken - authToken of the user sending the request so Server can identify the user
     * @param gameID - id of the game to be updated
     * @param player - updated Player object resulting from drawing or playing train cards
     * @param faceUpDeck - the updated face up deck in the associated game
     * @param trainCardDeck - the updated train card deck in the associated game
     * @param trainDiscardDeck - the updated train discard deck in the associated game
     */
    @Override
    public void updateTrainCards(String authToken, String gameID, Player player, FaceUpDeck faceUpDeck, TrainCardDeck trainCardDeck, TrainCardDeck trainDiscardDeck) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString(), player.getClass().toString(),
                faceUpDeck.getClass().toString(), trainCardDeck.getClass().toString(), trainDiscardDeck.getClass().toString()};
        Object[] paramValues = {authToken, gameID, player, faceUpDeck, trainCardDeck, trainDiscardDeck};

        Command sendMessageCommand = CommandFactory.createCommand(null, CLASS_PATH, "_updateTrainCards", paramTypes, paramValues);

        SocketManager.socket.send(JsonWriter.objectToJson(sendMessageCommand, args));
    }


    /**
     * @pre - all parameters are not null and correspond to the proper objects that need to be updated on the Server
     * @post - function is void, so the only assumption is that it passed the correct parameters to create a command to send to the Server
     *
     * @param authToken - authToken of the user sending the request so Server can identify the user
     * @param gameID - id of the game to be updated
     * @param player - updated Player object resulting from drawing new destination cards
     * @param destinationDeck - the updated deck of destination cards to be updated in the associated game
     */
    @Override
    public void updateDestinationCards(String authToken, String gameID, Player player, DestinationDeck destinationDeck) {
        String[] paramTypes = {authToken.getClass().toString(), gameID.getClass().toString(), player.getClass().toString(), destinationDeck.getClass().toString()};
        Object[] paramValues = {authToken, gameID, player, destinationDeck};

        Command sendMessageCommand = CommandFactory.createCommand(null, CLASS_PATH, "_updateDestinationCards", paramTypes, paramValues);

        SocketManager.socket.send(JsonWriter.objectToJson(sendMessageCommand, args));
    }

}
