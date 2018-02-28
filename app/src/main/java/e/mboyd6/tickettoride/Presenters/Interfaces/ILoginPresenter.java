package e.mboyd6.tickettoride.Presenters.Interfaces;

/**
 * Created by jonathanlinford on 2/2/18.
 */

/**
 * This interface is to provide a structure to the login presenters.
 */
public interface ILoginPresenter {

    boolean validUsername(String username);

    boolean validPassword(String password);

    void login(String username, String password);

    void loginResponse(String message);

    void detachView();

}
