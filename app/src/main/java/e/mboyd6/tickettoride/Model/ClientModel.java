package e.mboyd6.tickettoride.Model;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;

import org.java_websocket.client.WebSocketClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;



/**
 * Created by jonathanlinford on 2/5/18.
 */

public class ClientModel extends Observable {

    //Game data
    private ArrayList<Game> games = new ArrayList<>();
    private Game currentGame = null;

    //Current player data
    private Player currentPlayer = new Player("playerID", "name", PlayerColors.NO_COLOR);
    private String authToken;
    private String response;
    private WebSocketClient socket;

    public WebSocketClient getSocket() {
        return socket;
    }
    //TODO: put all this in the socket manager

    private static ClientModel ourInstance = new ClientModel();

    public static ClientModel getInstance() {
        return ourInstance;
    }

    private ClientModel() {
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getAuthToken() {

        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
        notifyObservers(UpdateType.REGISTERRESPONSE);
    }

    public void setLoginResponse(String authToken, String message) {
        this.authToken = authToken;
        this.response = message;
        this.setChanged();
        notifyObservers(UpdateType.LOGINRESPONSE);
    }

    public void setRegisterResponse(String authToken, String message) {
        this.authToken = authToken;
        this.response = message;
        this.setChanged();
        notifyObservers(UpdateType.REGISTERRESPONSE);
    }

    public void setCreateGameResponse(String gameID, String message){

    }

    public void setUpdateGamesResponse(Game[] games, String message){
        this.games = new ArrayList<>(Arrays.asList(games));
        this.response = message;
        this.setChanged();
        notifyObservers(UpdateType.GAMELIST);
    }

    public void setJoinGameResponse(String gameID, String message){

        if(joinGame(gameID)) {
            this.response = message;
        } else{
            response = "Game not found.";
        }

        this.setChanged();
        notifyObservers(UpdateType.GAMEJOINED);
        System.out.println(response);
    }


    private boolean joinGame(String gameID){
        for(Game g: games){
            if(g.getGameID().equals(gameID)){
                //GameID is set as currentGame
                this.setCurrentGame(g);
                return true;
            }
        }

        return false;
    }

    public void setStartGameResponse(String gameID, String message){
        this.response = message;
        
        if(joinGame(gameID)) {
            this.response = message;
        } else{
            response = "Game not found.";
        }

        this.setChanged();
        notifyObservers(UpdateType.GAMESTARTED);
        System.out.println(response);

    }

    public void setLogoutResponse(String message){
        if(message == null || message.length() == 0){
            //preserve the socket and restart the model
            WebSocketClient tempSocket = this.socket;

            ourInstance = new ClientModel();

            this.socket = tempSocket;
        }

        this.response = message;

        this.setChanged();
        notifyObservers(UpdateType.GAMESTARTED);
    }

    public void setLeaveGameResponse(String gameID, String message) {
        if(message == null || message.length() == 0){
            currentGame = null;
        }

        this.response = message;

        this.setChanged();
        notifyObservers(UpdateType.GAMELEFT);
    }

    public String getResponse() {
        return response;
    }

    @Override
    public synchronized void addObserver(Observer o) {
            super.addObserver(o);
    }


    public void setSocket(WebSocketClient socket) {
        this.socket = socket;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }


}

