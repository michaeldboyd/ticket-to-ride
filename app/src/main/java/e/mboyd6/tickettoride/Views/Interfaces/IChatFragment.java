package e.mboyd6.tickettoride.Views.Interfaces;

/**
 * Created by jonathanlinford on 2/2/18.
 */

import android.widget.Button;

import com.example.sharedcode.model.ChatMessage;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

import java.util.ArrayList;

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

public interface IChatFragment {
    // This allows the mainActivity to inform the presenter of the chatFragment it needs to call
    String getPlayerID();
    void updateChat(ArrayList<ChatMessage> messages);
    void updateTyping(boolean isUpdated, String name);
    void sendMessage(String message);
    void typingChanged(boolean isUpdated);
}