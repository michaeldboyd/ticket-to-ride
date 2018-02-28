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

    // View -> Facade
    void login(String username, String password);

    // Model -> View
    void loginResponse(String message);

}
