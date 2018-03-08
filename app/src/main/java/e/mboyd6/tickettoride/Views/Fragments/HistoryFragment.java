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
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Adapters.HistoryListAdapter;
import e.mboyd6.tickettoride.Views.Interfaces.IGameActivity;
import e.mboyd6.tickettoride.Views.Interfaces.IHistoryFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment implements IHistoryFragment {

    private IGameActivity mListener;
    private View mLayout;
    private HistoryListAdapter mHistoryListAdapter;

    private GamePresenter mGamePresenter = new GamePresenter(this);

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLayout = inflater.inflate(R.layout.fragment_history, container, false);
        mHistoryListAdapter = new HistoryListAdapter(getContext(), new ArrayList<String>(), this);
        ListView listView = mLayout.findViewById(R.id.events);
        listView.setAdapter(mHistoryListAdapter);
        mGamePresenter.updateBoard();
        return mLayout;
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

    @Override
    public void updateHistory(ArrayList<String> newList) {
        final ArrayList<String> nl = newList;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Update current game list with newList
                if (mHistoryListAdapter != null) {
                    mHistoryListAdapter.clear();
                    mHistoryListAdapter.addAll(nl);
                    mHistoryListAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
