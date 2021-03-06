package e.mboyd6.tickettoride.Views.Fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sharedcode.model.ChatMessage;

import java.util.ArrayList;

import e.mboyd6.tickettoride.Presenters.ChatPresenter;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Adapters.ChatAdapter;
import e.mboyd6.tickettoride.Views.Interfaces.IChatFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IGameActivity;
import e.mboyd6.tickettoride.Views.Interfaces.IMainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link GameChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameChatFragment extends Fragment implements IChatFragment {

    private IGameActivity mListener;
    private ChatPresenter mChatPresenter = new ChatPresenter( this);
    private Activity activity;

    private View mLayout;
    private Button mSendButton;
    private EditText mMessageField;
    private TextView mSomeoneTypingField;
    private ChatAdapter mChatAdapter;

    private ArrayList<String> mPlayersTyping = new ArrayList<>();

    private boolean mTypingStarted;


    public GameChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameChatFragment newInstance(String param1, String param2) {
        GameChatFragment fragment = new GameChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mLayout = inflater.inflate(R.layout.fragment_chat, container, false);
        mSendButton = mLayout.findViewById(R.id.chat_fragment_send_button);
        mMessageField = mLayout.findViewById(R.id.chat_fragment_chat_edit_text);
        mSomeoneTypingField = mLayout.findViewById(R.id.chat_fragment_someone_typing_field);
        mChatAdapter = new ChatAdapter(activity, new ArrayList<ChatMessage>(), this);
        ListView listView = mLayout.findViewById(R.id.chat_fragment_list);
        listView.setAdapter(mChatAdapter);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMessageField.getText().length() > 0) {
                    sendMessage(mMessageField.getText().toString());
                    mMessageField.setText("");
                }
            }
        });

        mMessageField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString()) && s.toString().trim().length() == 1) {
                    typingChanged(true);
                    mTypingStarted = true;

                } else if (s.toString().trim().length() == 0 && mTypingStarted) {
                    //Log.i(TAG, “typing stopped event…”);
                    typingChanged(true);
                    mTypingStarted = false;
                }
            }
        });

        updateChatFirstTime();
        return mLayout;
    }

    public String getPlayerID() {
        return mChatPresenter.getPlayerID();
    }

    private void updateChatFirstTime() {
        mChatPresenter.chatReceived();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
        if (context instanceof IGameActivity) {
            mListener = (IGameActivity) context;
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
    public void updateChat(ArrayList<ChatMessage> messages) {
        final ArrayList<ChatMessage> msgs = messages;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Update current game list with newList
                if (mChatAdapter != null) {
                    mChatAdapter.clear();
                    if (msgs != null)
                        mChatAdapter.addAll(msgs);
                    mChatAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void updateTyping(boolean iT, String s) {
        final boolean isTyping = iT;
        final String name = s;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isTyping && mPlayersTyping.contains(name)) {
                    mPlayersTyping.remove(name);
                } else if (isTyping && !mPlayersTyping.contains(name)) {
                    mPlayersTyping.add(name);
                }
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < mPlayersTyping.size(); i++) {
                    // Add the player who is typing
                    sb.append(mPlayersTyping.get(i));
                    // Add 'and' if it's the second to last person and there's more than one player typing
                    if (mPlayersTyping.size() > 1 && i == mPlayersTyping.size() - 2) {
                        sb.append(" and");
                    }
                    // Add ',' if there's more than two people and the current person is before the second to last
                    else if (mPlayersTyping.size() > 2 && i < mPlayersTyping.size() - 2) {
                        sb.append(",");
                    }
                }
                if (mPlayersTyping.size() > 0) {
                    if (mPlayersTyping.size() == 1)
                        sb.append("is typing...");
                    else
                        sb.append("are typing...");
                } else {
                    mSomeoneTypingField.setText(sb.toString());
                }
            }
        });

    }

    @Override
    public void sendMessage(String message) {
        mChatPresenter.sendMessage(message);
    }

    @Override
    public void typingChanged(boolean isUpdated) {
        mChatPresenter.isTypingChanged(isUpdated);
    }
}
