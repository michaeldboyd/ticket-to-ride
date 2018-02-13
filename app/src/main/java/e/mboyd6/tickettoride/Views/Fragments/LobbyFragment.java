package e.mboyd6.tickettoride.Views.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.sharedcode.model.Game;

import java.util.ArrayList;

import e.mboyd6.tickettoride.Model.ClientModel;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Adapters.GameListAdapter;
import e.mboyd6.tickettoride.Views.Interfaces.ILobbyFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ILobbyFragment} interface
 * to handle interaction events.
 * Use the {@link LobbyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LobbyFragment extends Fragment implements ILobbyFragment {

    private View mLayout;
    private Button mLogOutButton;
    private Button mStartNewGameButton;
    private ILobbyFragment mListener;
    private GameListAdapter mGameListAdapter;
    private boolean disableInputs = false;
    private Button currentPressedButton = null;

    public LobbyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LobbyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LobbyFragment newInstance() {
        LobbyFragment fragment = new LobbyFragment();
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
        mLayout = inflater.inflate(R.layout.fragment_lobby, container, false);
        mLogOutButton = mLayout.findViewById(R.id.lobby_fragment_back_button);
        mStartNewGameButton = mLayout.findViewById(R.id.lobby_fragment_start_new_game_button);
        mGameListAdapter = new GameListAdapter(getContext(), ClientModel.getInstance().getGames(), this);
        ListView listView = mLayout.findViewById(R.id.lobby_fragment_list_view);
        listView.setAdapter(mGameListAdapter);

        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLobbyFragmentLogOutButton();
            }
        });
        mStartNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLobbyFragmentStartNewGameButton();
            }
        });
        return mLayout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ILobbyFragment) {
            mListener = (ILobbyFragment) context;
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
    public void updateGameList(ArrayList<Game> newList) {
        //Update current game list with newList
        if (mGameListAdapter != null) {
            mGameListAdapter.clear();
            mGameListAdapter.addAll(newList);
            mGameListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLobbyFragmentLogOutButton() {
        if (mListener != null && !disableInputs) {
            mListener.onLobbyFragmentLogOutButton();
            currentPressedButton = mLogOutButton;
        }
    }

    @Override
    public void onLogOutSent() {
        disableInputs = true;
        mLogOutButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.waiting_animated,0);
    }

    @Override
    public void onLogOutResponse(String message) {
        disableInputs = false;
        mLogOutButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
    }

    public void onGameListAdapterJoinButton(Game game, Button button) {
        onLobbyFragmentJoinGameButton(game);
        currentPressedButton = button;
    }

    @Override
    public void onLobbyFragmentStartNewGameButton() {
        if (mListener != null && !disableInputs) {
            mListener.onLobbyFragmentStartNewGameButton();
            currentPressedButton = mStartNewGameButton;
        }
    }

    @Override
    public void onStartNewGameSent() {
        disableInputs = true;
        mStartNewGameButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.waiting_animated,0);
    }

    @Override
    public void onLobbyFragmentJoinGameButton(Game game) {
        if (mListener != null && !disableInputs) {
            mListener.onLobbyFragmentJoinGameButton(game);
        }
    }

    @Override
    public void onGameJoinedSent() {
        disableInputs = true;
        //currentPressedButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.waiting_animated,0);
    }

    @Override
    public void onGameJoinedResponse(String message) {
        disableInputs = false;
        //currentPressedButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
    }
}
