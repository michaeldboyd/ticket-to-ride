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
    private ILobbyFragment mListener;
    private GameListAdapter mGameListAdapter;

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
        mGameListAdapter = new GameListAdapter(getContext(), ClientModel.getInstance().getGames());
        ListView listView = mLayout.findViewById(R.id.lobby_fragment_list_view);
        listView.setAdapter(mGameListAdapter);

        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLobbyFragmentLogOutButton();
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
        }
    }

    @Override
    public void onLobbyFragmentLogOutButton() {
        if (mListener != null) {
            mListener.onLobbyFragmentLogOutButton();
        }
    }

    @Override
    public void onLobbyFragmentStartNewGameButton() {

    }

    @Override
    public void onStartNewGameSent() {

    }

    @Override
    public void onLobbyFragmentJoinGameButton(Game game) {
        if (mListener != null) {
            mListener.onLobbyFragmentJoinGameButton(game);
        }
    }

    @Override
    public void onGameJoinedSent() {

    }

    @Override
    public void onGameJoinedResponse() {

    }
}
