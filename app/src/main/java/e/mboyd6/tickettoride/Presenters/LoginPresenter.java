package e.mboyd6.tickettoride.Presenters;

import com.example.sharedcode.communication.CommandResult;

import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Communication.ClientLobbyFacade;
import e.mboyd6.tickettoride.Communication.ClientLoginFacade;
import e.mboyd6.tickettoride.Presenters.Interfaces.ILoginPresenter;

/**
 * Created by jonathanlinford on 2/2/18.
 */

public class LoginPresenter implements ILoginPresenter, Observer {


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
    public boolean login(String username, String password) {
        CommandResult result = ClientLoginFacade._login(username, password);

        //if(result.)

        return false;

    }



    @Override
    public void update(Observable observable, Object o) {

    }
}
