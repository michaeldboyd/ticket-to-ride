package e.mboyd6.tickettoride.Views.Fragments;

import android.app.Activity;
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

import e.mboyd6.tickettoride.Presenters.Interfaces.ILobbyPresenter;
import e.mboyd6.tickettoride.Presenters.LobbyPresenter;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Adapters.GameListAdapter;
import e.mboyd6.tickettoride.Views.Interfaces.ILobbyFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IMainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ILobbyFragment} interface
 * to handle interaction events.
 * Use the {@link LobbyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LobbyFragment extends Fragment implements ILobbyFragment, IMainActivity {

    private View mLayout;
    private Button mLogOutButton;
    private Button mStartNewGameButton;
    private GameListAdapter mGameListAdapter;
    private boolean disableInputs = false;

    private Activity activity;
    private IMainActivity mListener;
    private ILobbyPresenter mLobbyPresenter = new LobbyPresenter((ILobbyFragment) this);

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
        mStartNewGameButton = mLayout.findViewById(R.id.lobby_fragment_create_game_button);
        mGameListAdapter = new GameListAdapter(getContext(), new ArrayList<Game>(), this);
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
                onLobbyFragmentCreateGameButton();
            }
        });

        updateGameListForFirstTime();
        return mLayout;
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
        mLobbyPresenter.detachView();
        mLobbyPresenter = null;
    }


    private void onLobbyFragmentLogOutButton() {
        mLobbyPresenter.logOut();
        onLogOutSent();
    }

    private void onLobbyFragmentCreateGameButton() {
        mLobbyPresenter.createGame();
        onCreateGameSent();
    }

    // This is a unique case where the Join Button in the GameListAdapter calls this method directly
    public void onLobbyFragmentJoinGameButton(Game game, Button joinGameButton) {
        mLobbyPresenter.joinGame(game.getGameID());
        onGameJoinedSent();
    }

    private void updateGameListForFirstTime() {
        mLobbyPresenter.updateGameList();
    }

    @Override
    public void updateGameList(ArrayList<Game> newList) {
        final ArrayList<Game> nl = newList;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Update current game list with newList
                if (mGameListAdapter != null) {
                    mGameListAdapter.clear();
                    mGameListAdapter.addAll(nl);
                    mGameListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onLogOutSent() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                disableInputs = true;
                mLogOutButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.waiting_animated,0);
            }
        });
    }

    @Override
    public void onLogOutResponse(String message) {
        final String mess = message;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                disableInputs = false;
                if (!handleError(mess)) {
                    mLogOutButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.back_arrow,0);
                    transitionToLoginFromLobby();
                }
            }
        });
    }

    @Override
    public void onCreateGameSent() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                disableInputs = true;
                mStartNewGameButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.waiting_animated,0);
            }
        });
    }

    @Override
    public void onCreateGameResponse(String message) {
        final String mess = message;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                disableInputs = false;
                if (!handleError(mess)) {
                    mStartNewGameButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.plus, 0);
                }
            }
        });
    }

    @Override
    public void onGameJoinedSent() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                disableInputs = true;
            }
        });
    }

    @Override
    public void onGameJoinedResponse(String message) {
        final String mess = message;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                disableInputs = false;
                if (!handleError(mess)) {
                    transitionToWaitroomFromLobby();
                }
            }
        });
    }


    @Override
    public boolean handleError(String message) {
        return ((IMainActivity) getActivity()).handleError(message);
    }

    @Override
    public void transitionToRegisterFromLogin(String usernameData, String passwordData) {
        return;
    }

    @Override
    public void transitionToLoginFromRegister(String usernameData, String passwordData) {
        return;
    }

    @Override
    public void transitionToLoginFromLobby() {
        mListener.transitionToLoginFromLobby();
    }

    @Override
    public void transitionToWaitroomFromLobby() {
        mListener.transitionToWaitroomFromLobby();
    }

    @Override
    public void transitionToLobbyFromLoginAndRegister() {
        return;
    }

    @Override
    public void transitionToLobbyFromWaitroom() {
        return;
    }
}
