package e.mboyd6.tickettoride.Presenters;

import e.mboyd6.tickettoride.Presenters.Interfaces.ILoginPresenter;

/**
 * Created by jonathanlinford on 2/2/18.
 */

public class LoginPresenter implements ILoginPresenter {


    /**
     * @param username
     * @return returns boolean whether
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


        //return ((username != null) && username.matches("[A-Za-z0-9_.]+"));
    }

    @Override
    public boolean validPassword(String password) {
        return password != null && !password.equals("") && !password.contains(" ");
    }
}
