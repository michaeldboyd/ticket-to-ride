package e.mboyd6.tickettoride.Views.Interfaces;

import com.example.sharedcode.model.Game;

import java.util.ArrayList;

/**
 * Created by hunte on 2/7/2018.
 */

public interface IMainActivity {
    boolean handleError(String message);
    void transitionToRegisterFromLogin(String usernameData, String passwordData);
    void transitionToLoginFromRegister(String usernameData, String passwordData);
    void transitionToLoginFromLobby();
    void transitionToWaitroomFromLobby();
    void transitionToLobbyFromLoginAndRegister();
    void transitionToLobbyFromWaitroom();
}
