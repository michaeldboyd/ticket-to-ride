package e.mboyd6.tickettoride.Views.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;

import java.util.ArrayList;

import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.Presenters.Interfaces.ILoginPresenter;
import e.mboyd6.tickettoride.Presenters.LoginPresenter;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Interfaces.ILoginFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IMainActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ILoginFragment} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements ILoginFragment, IMainActivity {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USERNAME_DATA = "Username Data";
    private static final String PASSWORD_DATA = "Password Data";

    private String mUsernameData;
    private String mPasswordData;

    private Button mLoginButton;
    private Button mSignUpButton;
    private EditText mUsernameField;
    private EditText mPasswordField;
    private ImageView mLogo;

    private Activity activity;
    private IMainActivity mListener;
    private ILoginPresenter mLoginPresenter = new LoginPresenter((ILoginFragment) this);

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param usernameData Parameter 1.
     * @param passwordData Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance(String usernameData, String passwordData) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME_DATA, usernameData);
        args.putString(PASSWORD_DATA, passwordData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUsernameData = getArguments().getString(USERNAME_DATA);
            mPasswordData = getArguments().getString(PASSWORD_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_login, container, false);
        mLoginButton = layout.findViewById(R.id.login_fragment_log_in_button);
        mSignUpButton = layout.findViewById(R.id.login_fragment_sign_up_button);
        mUsernameField = layout.findViewById(R.id.login_fragment_username_field);
        mPasswordField = layout.findViewById(R.id.login_fragment_password_field);
        mUsernameField.setText(mUsernameData);
        mPasswordField.setText(mPasswordData);
        mLogo = layout.findViewById(R.id.login_fragment_logo);

        mLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                onLoginFragmentLoginButton(mUsernameField.getText().toString(), mPasswordField.getText().toString());
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                onLoginFragmentSignUpButton(mUsernameField.getText().toString(), mPasswordField.getText().toString());
            }
        });

        mLogo.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                String newIP = mUsernameField.getText().toString();

                if(mLoginPresenter.changeIP(newIP)){
                    handleError("You've made Rodham proud.\nIP successfully changed to " + newIP);

                    mUsernameField.setText("");
                } else {
                    handleError("You've disappointed Rodham.\nIP " + newIP + "is invalid. NOT Set.");
                }

                return false;
            }

        });




        return layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
        if (context instanceof IMainActivity) {
            mListener = (IMainActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mLoginPresenter.detachView();
        mLoginPresenter = null;
    }


    private void onLoginFragmentSignUpButton(String usernameData, String passwordData) {
        transitionToRegisterFromLogin(usernameData, passwordData);
    }

    private void onLoginFragmentLoginButton(String usernameData, String passwordData) {

        String message = "";
        if (usernameData.equals("guest") && passwordData.equals("pass")) {
            GuestLogin();
        }
        else if(mLoginPresenter == null)
            message = "Something went wrong on our end";
        else if(!mLoginPresenter.validUsername(usernameData)) {
            message = "Invalid username";
        }
        else if(!mLoginPresenter.validPassword(passwordData)) {
            message = "Invalid password";
        }
        else {
            mLoginPresenter.login(usernameData, passwordData);
        }
        if (mListener.handleError(message)) {
            return;
        } else {
            onLoginSent();
        }
    }

    //Receives this from MainActivity
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void disableLoginUI() {
        mLoginButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.waiting_animated,0);
        mLoginButton.setEnabled(false);
        mSignUpButton.setEnabled(false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                enableLoginUI();
                //Do something after 100ms
            }
        }, 4000);
    }

    //Calls main activity
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void enableLoginUI() {
        mLoginButton.setCompoundDrawablesWithIntrinsicBounds(0,0, 0,0);
        mLoginButton.setEnabled(true);
        mSignUpButton.setEnabled(true);
    }

    @Override
    public boolean handleError(String message) {
        return mListener.handleError(message);
    }

    @Override
    public void transitionToRegisterFromLogin(String usernameData, String passwordData) {
        mListener.transitionToRegisterFromLogin(usernameData, passwordData);
    }

    @Override
    public void transitionToLoginFromRegister(String usernameData, String passwordData) {
        return;
    }

    @Override
    public void transitionToLoginFromLobby() {
        return;
    }

    @Override
    public void transitionToWaitroomFromLobby() {
        return;
    }

    @Override
    public void transitionToLobbyFromLoginAndRegister() {
        mListener.transitionToLobbyFromLoginAndRegister();
    }

    @Override
    public void transitionToLobbyFromWaitroom() {
        return;
    }

    //Potentially this could be called by a lower level, which is why it has protection to run on the UI thread
    @Override
    public void onLoginSent() {

        activity.runOnUiThread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void run() {
                disableLoginUI();
            }
        });
    }

    @Override
    public void onLoginResponse(String message) {
        final String mess = message;
        activity.runOnUiThread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
                @Override
            public void run() {
                    enableLoginUI();
                    if (!handleError(mess)) {
                        transitionToLobbyFromLoginAndRegister();
                    }
                }
        });
    }

    private void GuestLogin() {
        // Implement code that adds a bunch of fake games so you can go to the lobby


        ArrayList<Game> fakeGames = new ArrayList<Game>();
        Game game2 = new Game();
        game2.setGameID("002");
        game2.addPlayer(new Player("001", "Michael", PlayerColors.TURQUOISE));
        game2.addPlayer(new Player("002", "Alli", PlayerColors.BLUE));
        game2.addPlayer(new Player("003", "Eric", PlayerColors.RED));
        fakeGames.add(game2);
        Game game1 = new Game();
        game2.setGameID("001");
        game1.addPlayer(new Player("001", "Alli", PlayerColors.BLUE));
        game1.addPlayer(new Player("002", "Michael", PlayerColors.RED));
        fakeGames.add(game1);
        Game game3 = new Game();
        game2.setGameID("003");
        fakeGames.add(game3);
        Game game4 = new Game();
        game2.setGameID("004");
        game4.addPlayer(new Player("001", "Michael", PlayerColors.RED));
        game4.addPlayer(new Player("002", "Alli", PlayerColors.TURQUOISE));
        game4.addPlayer(new Player("003", "Eric", PlayerColors.ORANGE));
        game4.addPlayer(new Player("004", "Hunter", PlayerColors.BLUE));
        game4.addPlayer(new Player("005", "Jonny", PlayerColors.PURPLE));
        fakeGames.add(game4);
        ClientModel.getInstance().setGames(fakeGames);
        transitionToLobbyFromLoginAndRegister();

    }
}
