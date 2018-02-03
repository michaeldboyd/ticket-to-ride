package e.mboyd6.tickettoride.Presenters;

import e.mboyd6.tickettoride.Presenters.Interfaces.IRegisterPresenter;

/**
 * Created by jonathanlinford on 2/2/18.
 */

public class RegisterPresenter implements IRegisterPresenter {
    @Override
    public boolean validUsername(String s) {
        return false;
    }

    @Override
    public boolean validPassword(String s) {
        return false;
    }
}
