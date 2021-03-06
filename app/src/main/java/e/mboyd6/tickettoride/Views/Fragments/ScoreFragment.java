package e.mboyd6.tickettoride.Views.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sharedcode.model.Player;

import java.util.ArrayList;

import e.mboyd6.tickettoride.Presenters.GamePresenter;
import e.mboyd6.tickettoride.Presenters.Interfaces.IGamePresenter;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Adapters.ScoreListAdapter;
import e.mboyd6.tickettoride.Views.Interfaces.IGameActivity;
import e.mboyd6.tickettoride.Views.Interfaces.IScoreFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreFragment extends Fragment implements IScoreFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private IGameActivity mListener;
    private View mLayout;
    private ScoreListAdapter mScoreListAdapter;

    private IGamePresenter mGamePresenter = new GamePresenter(this, getActivity());

    public ScoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScoreFragment newInstance(String param1, String param2) {
        ScoreFragment fragment = new ScoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLayout = inflater.inflate(R.layout.fragment_score, container, false);

        mScoreListAdapter = new ScoreListAdapter(getContext(), new ArrayList<Player>(), this);
        ListView listView = mLayout.findViewById(R.id.scores);
        listView.setAdapter(mScoreListAdapter);

        mGamePresenter.updateBoard();

        return mLayout;
    }

    @Override
    public void updateScore(final ArrayList<Player> newList) {
        if(isAdded()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Update current game list with newList
                    if (mScoreListAdapter != null) {
                        mScoreListAdapter.clear();
                        mScoreListAdapter.addAll(newList);
                        mScoreListAdapter.notifyDataSetChanged();
                    }
                }
            });
        } else {
            Exception e = new Exception("Activity is null in the update score method");
            e.printStackTrace();;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
}
