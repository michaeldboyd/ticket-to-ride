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

    // View -> Facade
    /**
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
     * @param ip
     * @return
     */
    @Override
    public boolean changeIP(String ip){
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
    @Override
    public void loginResponse(String message){
        loginFragment.onLoginResponse(message);
    }

    @Override
    public void detachView() {
        ClientModel.getInstance().deleteObserver(this);
    }

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
