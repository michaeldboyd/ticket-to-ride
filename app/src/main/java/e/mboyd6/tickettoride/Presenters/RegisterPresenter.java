package e.mboyd6.tickettoride.Presenters;

import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Communication.ServerProxyLobbyFacade;
import e.mboyd6.tickettoride.Communication.ServerProxyLoginFacade;
import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Model.UpdateType;
import e.mboyd6.tickettoride.Presenters.Interfaces.IRegisterPresenter;

/**
 * Created by jonathanlinford on 2/2/18.
 */

public class RegisterPresenter implements IRegisterPresenter, Observer {


    /**
     * Used to check whether a username is valid.
     *
     * @param username
     * @return return false if null, .., __, contains spaces, empty string, contains characters
     * other than . and _. Returns true if the username is valid
     */
    @Override
    public boolean validUsername(String username) {
        if(username == null)
            return false;
        if(username.contains(".."))
            return false;
        if(username.contains("__"))
            return false;
        if(!username.matches("[A-Za-z0-9_.]+"))
            return false;
        return true;
    }


    /**
     * Use to check whether a password is valid
     *
     * @param password
     * @return returns false if password is null, empty or contains contain a space. Returns true otherwise
     */
    @Override
    public boolean validPassword(String password) {
        return password != null && !password.equals("") && !password.contains(" ");
    }

    /**
     * Checks that the two passwords match
     *
     * @param password1
     * @param password2
     * @return false if either of the passwords are null or if they don't match. Otherwise, true is returned
     */
    @Override
    public boolean passwordsMatch(String password1, String password2){

        //Avoid null pointer exception
        if(password1 == null || password2 == null){
            return false;
        } else if(!password1.equals(password2)){
            return false;
        } else {
            return true;
        }
    }


    /**
     * This method calls the register on the proxy. It doesn't return anything.
     *
     * @param username
     * @param password
     */
    @Override
    public void register(String username, String password) {
        ServerProxyLoginFacade.instance().register(username, password);
    }


    /**
     * This should be called as a response is received by the client from the server.
     * @param message - if null, register was successful. If not null, register was unsuccessful
     *                and the message is contained
     */
    private void registerResponse(String message){
        //mainActivity.onRegisterUpdate(message);
    }

    @Override
    public void update(Observable observable, Object o) {
        UpdateType updateType = (UpdateType) o;

        switch (updateType){
            case REGISTERRESPONSE:
                registerResponse(ClientModel.getInstance().getLoginResponse());
                break;
            default:
                break;
        }
    }
}
