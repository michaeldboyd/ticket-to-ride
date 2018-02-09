package e.mboyd6.tickettoride.Views.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Interfaces.IRegisterFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IRegisterFragment} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements IRegisterFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USERNAME_DATA = "Username Data";
    private static final String PASSWORD_DATA = "Password Data";
    private static final String CONFIRM_DATA = "Confirm Data";

    private String mUsernameData;
    private String mPasswordData;
    private String mConfirmData;

    private IRegisterFragment mListener;

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
        if (context instanceof IRegisterFragment) {
            mListener = (IRegisterFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRegisterFragmentBackButton(String usernameData, String passwordData) {
        if (mListener != null) {
            mListener.onRegisterFragmentBackButton(usernameData, passwordData);
        }
    }

    @Override
    public void onRegisterFragmentSignUpButton(String usernameData, String passwordData) {
        if (mListener != null) {
            mListener.onRegisterFragmentSignUpButton(usernameData, passwordData);
        }
    }

}
