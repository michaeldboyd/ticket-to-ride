package e.mboyd6.tickettoride.Presenters.Interfaces;

/**
 * Created by jonathanlinford on 2/2/18.
 */

/**
 * This interface is to provide a structure to the login presenters.
 */
public interface IRegisterPresenter {

    boolean validUsername(String s);

    boolean validPassword(String s);

    boolean passwordsMatch(String password1, String password2);

    void register(String username, String password);
}
