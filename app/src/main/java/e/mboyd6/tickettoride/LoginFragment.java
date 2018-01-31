package e.mboyd6.tickettoride;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USERNAME_DATA = "Username Data";
    private static final String PASSWORD_DATA = "Password Data";

    private String mUsernameData;
    private String mPasswordData;

    private Button mSignUpButton;
    private EditText mUsernameField;
    private EditText mPasswordField;

    private OnFragmentInteractionListener mListener;

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
        mSignUpButton = layout.findViewById(R.id.login_fragment_sign_up_button);
        mUsernameField = layout.findViewById(R.id.login_fragment_username_field);
        mPasswordField = layout.findViewById(R.id.login_fragment_password_field);
        mUsernameField.setText(mUsernameData);
        mPasswordField.setText(mPasswordData);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                onSignUpButtonPressed(mUsernameField.getText().toString(), mPasswordField.getText().toString());
            }
        });
        return layout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onSignUpButtonPressed(String usernameData, String passwordData) {
        if (mListener != null) {
            mListener.onLoginFragmentSignUpButton(usernameData, passwordData);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onLoginFragmentSignUpButton(String usernameData, String passwordData);
    }
}
