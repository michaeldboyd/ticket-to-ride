package e.mboyd6.tickettoride.Views.Interfaces;

import com.example.sharedcode.model.ChatMessage;

import java.util.ArrayList;

/**
 * Created by jonathanlinford on 2/27/18.
 */

public interface IGameActivity {
    void onIsTypingChanged(boolean isTyping);
    void onChatReceived(ArrayList<ChatMessage> chatMessages, int unreadMessages);
    void setUiLocked(boolean locked);
}
