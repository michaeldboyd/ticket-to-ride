package e.mboyd6.tickettoride.Views.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;

import java.util.ArrayList;

import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Adapters.ColorSelectionView;
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
    public class SelectedColor
    {
        int playerColor;
        int background;
        int backgroundFaded;
        boolean shown;
        boolean chosen;

        public SelectedColor(int playerColor, int background, int backgroundFaded, boolean shown, boolean chosen) {
            this.playerColor = playerColor;
            this.background = background;
            this.backgroundFaded = backgroundFaded;
            this.shown = shown;
            this.chosen = chosen;
        }
    }
    SelectedColor[] selectedColors;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private IWaitroomFragment mListener;

    private ArrayList<ColorSelectionView> colorSelectionViews = new ArrayList<>();
    private TextView playersInLobby;

    private ArrayList<Player> players;

    private Button mBackOutButton;
    private Button mStartGameButton;

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
    public static WaitroomFragment newInstance() {
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
        View v = inflater.inflate(R.layout.fragment_waitroom, container, false);

        mBackOutButton = v.findViewById(R.id.waitroom_fragment_leave_game_button);
        mBackOutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onWaitroomFragmentLeaveGameButton();
            }
        });

        mStartGameButton = v.findViewById(R.id.waitroom_fragment_start_game_button);
        mStartGameButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onWaitroomFragmentStartGameButton();
            }
        });

        colorSelectionViews.clear();
        colorSelectionViews.add((ColorSelectionView) v.findViewById(R.id.color_selection_1));
        colorSelectionViews.add((ColorSelectionView) v.findViewById(R.id.color_selection_2));
        colorSelectionViews.add((ColorSelectionView) v.findViewById(R.id.color_selection_3));
        colorSelectionViews.add((ColorSelectionView) v.findViewById(R.id.color_selection_4));
        colorSelectionViews.add((ColorSelectionView) v.findViewById(R.id.color_selection_5));

        playersInLobby = v.findViewById(R.id.players_in_lobby);

        updatePlayerListFirstTime();
        return v;
    }

    public SelectedColor[] refreshSelectedColors() {
        return new SelectedColor[]{new SelectedColor(PlayerColors.RED, R.drawable.color_red, R.drawable.color_red_faded,false, false),
                new SelectedColor(PlayerColors.TURQUOISE, R.drawable.color_turquoise, R.drawable.color_turquoise_faded, false,false),
                new SelectedColor(PlayerColors.ORANGE, R.drawable.color_orange, R.drawable.color_orange_faded,false, false),
                new SelectedColor(PlayerColors.BLUE, R.drawable.color_blue, R.drawable.color_blue_faded,false, false),
                new SelectedColor(PlayerColors.PURPLE, R.drawable.color_purple, R.drawable.color_purple_faded,false, false)};
    }

    //TODO: The player name shouldn't appear unless they have chosen that color
    //TODO: Make the colorSelectionViews into an arrayList that is iterated over
    //TODO: Implement the onClickListeners (each one will call one function that takes in the index of the colorSelectionView, and can do the logic to figure out whether it should send a color change or not)
    public void redrawPlayers() {

        selectedColors = refreshSelectedColors();

        int playerCount = players.size();
        for (int i = 0; i < 5; i++) {

            int background = 0;

            int normalText = ContextCompat.getColor((Context) mListener, R.color.white);
            int fadedText = ContextCompat.getColor((Context) mListener, R.color.gray);
            int textColor = normalText;
            String colorSelectionText = "CHOOSE";
            int playerColorToSelect = 0;
            boolean chosen = false;

            if (i < playerCount) {
                int playerColor = players.get(i).getColor();
                if (playerColor != PlayerColors.NO_COLOR) {
                    for (SelectedColor selectedColor : selectedColors) {
                        if (playerColor == selectedColor.playerColor) {
                            selectedColor.chosen = true;
                            chosen = true;
                            colorSelectionText = players.get(i).getName();
                            selectedColor.shown = true;
                            background = selectedColor.backgroundFaded;
                            textColor = fadedText;
                            playerColorToSelect = selectedColor.playerColor;
                            break;
                        }
                    }
                } else {
                    for (SelectedColor selectedColor : selectedColors) {
                        if (!selectedColor.chosen && !selectedColor.shown) {
                            selectedColor.shown = true;
                            background = selectedColor.background;
                            playerColorToSelect = selectedColor.playerColor;
                            break;
                        }
                    }
                }
            } else {
                for (SelectedColor selectedColor : selectedColors) {
                    if (!selectedColor.chosen && !selectedColor.shown) {
                        selectedColor.shown = true;
                        background = selectedColor.background;
                        playerColorToSelect = selectedColor.playerColor;
                        break;
                    }
                }
            }

            colorSelectionViews.get(i).setBackgroundResource(background);
            colorSelectionViews.get(i).setTextColor(textColor);
            colorSelectionViews.get(i).setText(colorSelectionText);

            final int playerIndex = i;
            final int playerColorToSelectFinal = playerColorToSelect;
            final boolean chosenFinal = chosen;
            colorSelectionViews.get(i).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (!chosenFinal) {
                        onWaitroomFragmentColorPicked(playerColorToSelectFinal);
                    }
                }
            });
            //colorSelectionViews.get(i).setOnClickListener(null);
        }

        String playersInLobbyText = playerCount + " " + getString(R.string.players_in_lobby);
        playersInLobby.setText(playersInLobbyText);
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
        players = newList;
        redrawPlayers();
        //Change layout to have updated players
    }

    @Override
    public void onWaitroomFragmentStartGameButton() {
        if (mListener != null) {
            mListener.onWaitroomFragmentStartGameButton();
        }
    }

    @Override
    public void onStartGameSent() {
        mStartGameButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.waiting_animated,0);
        mStartGameButton.setEnabled(false);
        mBackOutButton.setEnabled(false);
    }

    @Override
    public void onStartGameResponse(String message) {
        mStartGameButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        mStartGameButton.setEnabled(true);
        mBackOutButton.setEnabled(true);
    }

    @Override
    public void updateChat() {
        //Change layout to have updated chat
    }

    @Override
    public void updatePlayerListFirstTime() {
        if (mListener != null) {
            mListener.updatePlayerListFirstTime();
        }
    }

    @Override
    public void onWaitroomFragmentColorPicked(int playerColor) {
        if (mListener != null) {
            mListener.onWaitroomFragmentColorPicked(playerColor);
        }
    }

    @Override
    public void onWaitroomFragmentLeaveGameButton() {
        if (mListener != null) {
            mListener.onWaitroomFragmentLeaveGameButton();
        }
    }

    @Override
    public void onLeaveGameSent() {
        mBackOutButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.waiting_animated,0);
        mStartGameButton.setEnabled(false);
        mBackOutButton.setEnabled(false);
    }

    @Override
    public void onLeaveGameResponse(String message) {
        mBackOutButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        mStartGameButton.setEnabled(true);
        mBackOutButton.setEnabled(true);
    }

}
