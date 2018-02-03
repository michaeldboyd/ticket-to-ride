package e.mboyd6.tickettoride.Presenters.Interfaces;

/**
 * Created by jonathanlinford on 2/2/18.
 */

/**
 * This interface is to provide a structure to the login presenters.
 */
public interface ILoginPresenter {

    boolean validUsername(String s);

    boolean validPassword(String s);
}
