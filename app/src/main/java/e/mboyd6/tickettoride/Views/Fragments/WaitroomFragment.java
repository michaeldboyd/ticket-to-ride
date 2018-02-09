package e.mboyd6.tickettoride.Views.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;

import java.util.ArrayList;

import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Interfaces.IWaitroomFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IWaitroomFragment} interface
 * to handle interaction events.
 * Use the {@link WaitroomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaitroomFragment extends Fragment implements IWaitroomFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private IWaitroomFragment mListener;

    public WaitroomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WaitroomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WaitroomFragment newInstance(Game game) {
        WaitroomFragment fragment = new WaitroomFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_waitroom, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IWaitroomFragment) {
            mListener = (IWaitroomFragment) context;
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
    public void updatePlayerList(ArrayList<Player> newList) {
        //Change layout to have updated players
    }

    @Override
    public void updateChat() {
        //Change layout to have updated chat
    }

    @Override
    public void onWaitroomFragmentColorPicked(int color) {
        if (mListener != null) {
            mListener.onWaitroomFragmentColorPicked(color);
        }
    }

    @Override
    public void onWaitroomFragmentBackoutButton() {
        if (mListener != null) {
            mListener.onWaitroomFragmentBackoutButton();
        }
    }
}
