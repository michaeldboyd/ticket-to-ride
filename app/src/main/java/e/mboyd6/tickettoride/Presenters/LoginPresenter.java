package e.mboyd6.tickettoride.Presenters;

import android.content.Context;

import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Communication.ServerProxyLoginFacade;
import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Model.UpdateType;
import e.mboyd6.tickettoride.Presenters.Interfaces.ILoginPresenter;
import e.mboyd6.tickettoride.Views.Activities.MainActivity;

/**
 * Created by jonathanlinford on 2/2/18.
 */

public class LoginPresenter implements ILoginPresenter, Observer{

    MainActivity mainActivity;

    public LoginPresenter(Context context){
        mainActivity = (MainActivity) context;

        ClientModel.getInstance().addObserver(this);
    }

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
     * @param username
     * @param password
     * @return
     */
    @Override
    public void login(String username, String password) {
        ServerProxyLoginFacade.instance().login(username, password);
    }

    @Override
    public void loginResponse(String message){
        mainActivity.onLoginResponse(message);
    }

    @Override
    public void update(Observable observable, Object o) {

        UpdateType updateType = (UpdateType) o;

        switch (updateType){
            case LOGINRESPONSE:
                loginResponse(ClientModel.getInstance().getLoginResponse());
                break;
            default:
                break;
        }

    }
}
