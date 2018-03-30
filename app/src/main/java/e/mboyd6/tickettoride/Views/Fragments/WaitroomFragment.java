package e.mboyd6.tickettoride.Views.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;

import java.util.ArrayList;

import e.mboyd6.tickettoride.Presenters.Interfaces.IWaitroomPresenter;
import e.mboyd6.tickettoride.Presenters.WaitroomPresenter;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Activities.GameActivity;
import e.mboyd6.tickettoride.Views.Adapters.ColorSelectionView;
import e.mboyd6.tickettoride.Views.Interfaces.IChatFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IMainActivity;
import e.mboyd6.tickettoride.Views.Interfaces.IWaitroomFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IWaitroomFragment} interface
 * to handle interaction events.
 * Use the {@link WaitroomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaitroomFragment extends Fragment implements IWaitroomFragment, IMainActivity {


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

    private Activity activity;
    private IMainActivity mListener;
    private IWaitroomPresenter mWaitroomPresenter = new WaitroomPresenter((IWaitroomFragment) this);

    private ArrayList<ColorSelectionView> colorSelectionViews = new ArrayList<>();
    private TextView playersInLobby;

    private ArrayList<Player> players;

    private Button mBackOutButton;
    private Button mStartGameButton;

    private FrameLayout mChatFragmentContainer;

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

        mChatFragmentContainer = v.findViewById(R.id.chat_fragment_container);
        ChatFragment chatFragment = new ChatFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.chat_fragment_container, chatFragment).commit();
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
        mWaitroomPresenter.detachView();
        mWaitroomPresenter = null;
    }


    private void onWaitroomFragmentColorPicked(int playerColor) {
        //Calls waitroomPresenter
        mWaitroomPresenter.changePlayerColor(playerColor);
    }

    private void onWaitroomFragmentLeaveGameButton() {
        mWaitroomPresenter.leaveGame();
        onLeaveGameSent();
    }

    private void onWaitroomFragmentStartGameButton() {
        //TODO: This is not implemented

        if(mWaitroomPresenter.gameReady()) {
            mWaitroomPresenter.startGame();
            onStartGameSent();
        } else {
            handleError("Not enough players! Find some friends.");
        }
    }

    private void redrawPlayers() {

        selectedColors = refreshSelectedColors();

        int playerCount = players.size();
        for (int i = 0; i < 5; i++) {

            int background = 0;

            int normalText = activity.getColor(R.color.white);
            int fadedText = activity.getColor(R.color.gray);
            int textColor = normalText;
            String colorSelectionText = "CHOOSE";
            int playerColorToSelect = 0;
            boolean chosen = false;

            if (i < playerCount) {
                int playerColor = players.get(i).getColor();
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
                //^^ this previosly was in this conditional: if(player.getColor() != Color.NO_COLOR) {...}
                /*else {   //The player will never not have a color.
                    for (SelectedColor selectedColor : selectedColors) {
                        if (!selectedColor.chosen && !selectedColor.shown) {
                            selectedColor.shown = true;
                            background = selectedColor.background;
                            playerColorToSelect = selectedColor.playerColor;
                            break;
                        }
                    }
                }*/
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

        String playersInLobbyText = playerCount + " " + activity.getString(R.string.players_in_lobby);
        playersInLobby.setText(playersInLobbyText);
    }

    private void updatePlayerListFirstTime() {
        mWaitroomPresenter.updatePlayerList();
    }


    private void disableLeaveGameUI() {
        mBackOutButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.waiting_animated,0);
        mStartGameButton.setEnabled(false);
        mBackOutButton.setEnabled(false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                enableLeaveGameUI();
                //Do something after 100ms
            }
        }, 4000);
    }

    private void enableLeaveGameUI() {
        mBackOutButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        mStartGameButton.setEnabled(true);
        mBackOutButton.setEnabled(true);
    }

    private void disableStartGameUI() {
        mStartGameButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.waiting_animated,0);
        mStartGameButton.setEnabled(false);
        mBackOutButton.setEnabled(false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                enableStartGameUI();
                //Do something after 100ms
            }
        }, 4000);
    }

    private void enableStartGameUI() {
        mStartGameButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        mStartGameButton.setEnabled(true);
        mBackOutButton.setEnabled(true);
    }

    @Override
    public void updatePlayerList(ArrayList<Player> newList) {
        final ArrayList<Player> nL = newList;
        activity.runOnUiThread(new Runnable() {
            // Michael updated this function to match other functions for error andling.
            @Override
        public void run() {
            players = nL;
            redrawPlayers();
            }
        });
    }


    @Override
    public void onStartGameSent() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                disableStartGameUI();
            }
        });
    }

    @Override
    public void onStartGameResponse(String message) {
        final String mess = message;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                    enableStartGameUI();
                    if (!handleError(mess)) {
                        Intent intent = new Intent(getActivity(), GameActivity.class);
                        intent.putExtra("START_GAME", true);
                        getActivity().startActivity(intent);
                    }
                }});
    }

    @Override
    public void onLeaveGameSent() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                disableLeaveGameUI();
            }
        });
    }

    @Override
    public void onLeaveGameResponse(String message) {
        final String mess = message;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    enableLeaveGameUI();
                    if (!handleError(mess)) {
                        transitionToLobbyFromWaitroom();
                    }
                }
            });
    }

    @Override
    public boolean handleError(String message) {
        return mListener.handleError(message);
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
        return;
    }

    @Override
    public void transitionToWaitroomFromLobby() {
        return;
    }

    @Override
    public void transitionToLobbyFromLoginAndRegister() {
        return;
    }

    @Override
    public void transitionToLobbyFromWaitroom() {
        mListener.transitionToLobbyFromWaitroom();
    }
}
