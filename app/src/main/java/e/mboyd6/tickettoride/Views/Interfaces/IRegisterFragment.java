package e.mboyd6.tickettoride.Views.Interfaces;

/**
 * Created by hunte on 2/7/2018.
 */

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

public interface IRegisterFragment {
    void onRegisterFragmentBackButton(String usernameData, String passwordData);
    void onRegisterFragmentSignUpButton(String usernameData, String passwordData);
    void onRegisterSent();
    void onRegisterResponse(String message);
}
