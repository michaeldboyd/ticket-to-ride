package e.mboyd6.tickettoride.Presenters;

import java.util.Observable;
import java.util.Observer;

import e.mboyd6.tickettoride.Communication.Proxies.LoginProxy;
import e.mboyd6.tickettoride.Communication.SocketManager;
import e.mboyd6.tickettoride.Model.ClientModel;

import com.example.sharedcode.communication.UpdateArgs;

import e.mboyd6.tickettoride.Presenters.Interfaces.ILoginPresenter;
import e.mboyd6.tickettoride.Views.Interfaces.ILoginFragment;

import junit.framework.Assert;

/**
 * Created by jonathanlinford on 2/2/18.
 *
 * Login Presenter provides the views with the ability to verify a username and password
 * and send those fields to be used to log in.
 *
 * @inv loginFragment != null
 */

public class LoginPresenter implements ILoginPresenter, Observer{

    ILoginFragment loginFragment;

    public LoginPresenter(ILoginFragment loginFragment){
        this.loginFragment = loginFragment;

        ClientModel.getInstance().addObserver(this);
    }

    /**
     * Used to check whether a username is valid.
     *
     * @pre a string is passed. Any string is acceptable
     * @post if null, empty, contains .. or __ or contains chars other than A-Za-z0-9_., return false.
     * @post else return true;
     * @param username
     * @return return false if null, .., __, contains spaces, empty string, contains characters
     * other than . and _. Returns true if the username is valid
     */
    @Override
    public boolean validUsername(String username) {
        if(username == null || username.equals(""))
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
     * @pre any string as a parameter
     * @post if null, empty or contains a space, returns false. Else true
     * @param password
     * @return returns false if password is null, empty or contains contain a space. Returns true otherwise
     */
    @Override
    public boolean validPassword(String password) {
        return password != null && !password.equals("") && !password.contains(" ");
    }

    // View -> Facade
    /**
     * This is used to send information to the facade to be logged in
     *
     * @pre username and password are verified with the methods validUsername and validPassword
     * @post The method is called on the Login facade
     * @param username
     * @param password
     * @return
     */
    @Override
    public void login(String username, String password) {
        String socketID = SocketManager.socketID;
        LoginProxy.instance().login(username, password, socketID);
    }

    /**
     * Used to change ip to be pointed to
     *
     * @pre string != null
     * @post the ip is changed on the SocketManager
     * @param ip
     * @return true if successful, false if not
     */
    @Override
    public boolean changeIP(String ip){
        if(ip == null){
            return false;
        }

        if(ip.matches("[0-9.]+") || ip.equals("localhost")) {
            SocketManager.ip = ip;
            System.out.println("IP changed to: " + ip);
            try {
                SocketManager.socket.closeConnection(500, "ChangeIP");
                SocketManager.ConnectSocket(ip);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("IP " + ip + " NOT set");
            return false;
        }
    }

    //Model -> View
    /**
     * This method is called when the observer sees that a login response has been received.
     *
     * @pre A login request is pending on the view. This confirms the response of the login
     * @post nothing changes on this here presenter. The change is sent up to the view
     *
     */
    @Override
    public void loginResponse(String message){
        loginFragment.onLoginResponse(message);
    }

    /**
     * This is called by the view to avoid dangling observers
     *
     * @pre This observer
     * @post This is removed as an observer of the ClientModel observable
     */
    @Override
    public void detachView() {
        ClientModel.getInstance().deleteObserver(this);
    }

    /**
     * This is called as observable items within the model are called
     *
     * @pre This must be added as an observer of the observable
     * @post Methods are called to update the view
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        Assert.assertEquals(o.getClass(), UpdateArgs.class);
        UpdateArgs args = (UpdateArgs) o;
        switch (args.type){
            case LOGIN_RESPONSE:
                loginResponse(args.error);
                break;
            case SERVER_DISCONNECT:
                loginResponse(args.error);
            default:
                break;
        }

    }
}
