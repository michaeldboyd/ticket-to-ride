package e.mboyd6.tickettoride.Presenters;

import e.mboyd6.tickettoride.Presenters.Interfaces.ILoginPresenter;

/**
 * Created by jonathanlinford on 2/2/18.
 */

public class LoginPresenter implements ILoginPresenter {
    @Override
    public boolean validUsername(String s) {
        return false;
    }

    @Override
    public boolean validPassword(String s) {
        return false;
    }
}
