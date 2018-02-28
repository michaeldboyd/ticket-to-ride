package e.mboyd6.tickettoride.Views.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import e.mboyd6.tickettoride.Presenters.Interfaces.IRegisterPresenter;
import e.mboyd6.tickettoride.Presenters.RegisterPresenter;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Interfaces.IMainActivity;
import e.mboyd6.tickettoride.Views.Interfaces.IRegisterFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IRegisterFragment} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements IRegisterFragment, IMainActivity {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USERNAME_DATA = "Username Data";
    private static final String PASSWORD_DATA = "Password Data";
    private static final String CONFIRM_DATA = "Confirm Data";

    private String mUsernameData;
    private String mPasswordData;
    private String mConfirmData;

    private Activity activity;
    private IMainActivity mListener;
    private IRegisterPresenter mRegisterPresenter = new RegisterPresenter((IRegisterFragment) this);

    private Button mSignUpButton;
    private Button mBackButton;
    private EditText mUsernameField;
    private EditText mPasswordField;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param usernameData Parameter 1.
     * @param passwordData Parameter 2.
     * @param confirmData Parameter 3.
     * @return A new instance of fragment RegisterFragment.
     */
    public static RegisterFragment newInstance(String usernameData, String passwordData,
                                               String confirmData) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME_DATA, usernameData);
        args.putString(PASSWORD_DATA, passwordData);
        args.putString(CONFIRM_DATA, confirmData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUsernameData = getArguments().getString(USERNAME_DATA);
            mPasswordData = getArguments().getString(PASSWORD_DATA);
            mConfirmData = getArguments().getString(CONFIRM_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_register, container, false);

        mSignUpButton = layout.findViewById(R.id.register_fragment_sign_up_button);
        mBackButton = layout.findViewById(R.id.register_fragment_back_button);
        mUsernameField = layout.findViewById(R.id.register_fragment_username_field);
        mPasswordField = layout.findViewById(R.id.register_fragment_password_field);
        mUsernameField.setText(mUsernameData);
        mPasswordField.setText(mPasswordData);


        mSignUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            onRegisterFragmentSignUpButton(mUsernameField.getText().toString(), mPasswordField.getText().toString());
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterFragmentBackButton(mUsernameField.getText().toString(),
                        mPasswordField.getText().toString());
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
        mRegisterPresenter.detachView();
        mRegisterPresenter = null;
    }

    public void onRegisterFragmentBackButton(String usernameData, String passwordData) {
        //mDelayedTransactionHandler.post(mRunnableTransitionToLogin);
        transitionToLoginFromRegister(usernameData, passwordData);
    }

    public void onRegisterFragmentSignUpButton(String usernameData, String passwordData) {
        String message = null;
        if(mRegisterPresenter == null)
            message = "Something went wrong on our end.";
        else if(!mRegisterPresenter.validUsername(usernameData)) {
            message = "Invalid username";
        }
        else if(!mRegisterPresenter.validPassword(passwordData)) {
            message = "Invalid password";
        }
        else {
            mRegisterPresenter.register(usernameData, passwordData);
        }

        if (handleError(message)) {
            return;
        } else {
            onRegisterSent();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void disableRegisterUI() {
        mSignUpButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.waiting_animated,0);
        mSignUpButton.setEnabled(false);
        mBackButton.setEnabled(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void enableRegisterUI() {
        mSignUpButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        mSignUpButton.setEnabled(true);
        mBackButton.setEnabled(true);
    }

    @Override
    public boolean handleError(String message) {
        return mListener.handleError(message);
    }

    @Override
    public void transitionToRegisterFromLogin(String usernameData, String passwordData) {return;
    }

    @Override
    public void transitionToLoginFromRegister(String usernameData, String passwordData) {
        mListener.transitionToLoginFromRegister(usernameData, passwordData);
    }

    @Override
    public void transitionToLoginFromLobby() { return;
    }

    @Override
    public void transitionToWaitroomFromLobby() {return;
    }

    @Override
    public void transitionToLobbyFromLoginAndRegister() {
        mListener.transitionToLobbyFromLoginAndRegister();
    }

    @Override
    public void transitionToLobbyFromWaitroom() {
        return;
    }

    // This may be called by outside classes that aren't running on the UI thread, namely the presenter
    @Override
    public void onRegisterSent() {
        activity.runOnUiThread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void run() {
                disableRegisterUI();
            }
        });

    }

    @Override
    public void onRegisterResponse(String message) {
        final String mess = message;
        activity.runOnUiThread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void run() {
                    enableRegisterUI();
                    if (!handleError(mess)) {
                        transitionToLobbyFromLoginAndRegister();
                    }
                }
        });
    }

}
