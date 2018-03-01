package e.mboyd6.tickettoride.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sharedcode.model.ChatMessage;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;

import java.util.ArrayList;

import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Fragments.ChatFragment;
import e.mboyd6.tickettoride.Views.Fragments.LobbyFragment;

/**
 * Created by hunte on 2/8/2018.
 */

public class ChatAdapter extends ArrayAdapter<ChatMessage> {
    private int position;
    private View convertView;
    private ViewGroup parent;
    private Context context;
    private ChatFragment chatFragment;

    public ChatAdapter(Context context, ArrayList<ChatMessage> messages, ChatFragment chatFragment) {
        super(context, 0, messages);
        this.context = context;
        this.chatFragment = chatFragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.position = position;
        this.convertView = convertView;
        this.parent = parent;
        ChatMessage chatMessage = getItem(position);
        String playerID = chatFragment.getPlayerID();

        if (playerID.equals(chatMessage.sender)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_item_right, parent, false);
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_item_left, parent, false);
        }

        TextView chatItemName = convertView.findViewById(R.id.chat_item_name);
        TextView chatItemFullName = convertView.findViewById(R.id.chat_item_fullname);
        TextView chatItemText = convertView.findViewById(R.id.chat_item_text);
        TextView chatItemTimeStamp = convertView.findViewById(R.id.chat_item_timestamp);
        final LinearLayout chatItemExtraInfo = convertView.findViewById(R.id.chat_item_extra_info);

        chatItemName.setText(chatMessage.sender.toUpperCase().charAt(0));
        chatItemFullName.setText(chatMessage.sender);
        chatItemText.setText(chatMessage.message);
        chatItemTimeStamp.setText(chatMessage.timestamp.toString());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chatItemExtraInfo.getVisibility() == View.VISIBLE) {
                    chatItemExtraInfo.setVisibility(View.GONE);
                } else {
                    chatItemExtraInfo.setVisibility(View.VISIBLE);
                }
            }
        });

        return convertView;
    }
}