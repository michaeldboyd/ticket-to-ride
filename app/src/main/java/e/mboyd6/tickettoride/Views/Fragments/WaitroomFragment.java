package e.mboyd6.tickettoride.Views.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.PlayerColors;

import java.util.ArrayList;

import e.mboyd6.tickettoride.Model.ClientModel;
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
    public class SelectedColor
    {
        PlayerColors playerColor;
        int background;
        int backgroundFaded;
        boolean shown;
        boolean chosen;

        public SelectedColor(PlayerColors playerColor, int background, int backgroundFaded, boolean shown, boolean chosen) {
            this.playerColor = playerColor;
            this.background = background;
            this.backgroundFaded = backgroundFaded;
            this.shown = shown;
            this.chosen = chosen;
        }
    }
    SelectedColor[] selectedColors = {new SelectedColor(PlayerColors.RED, R.drawable.color_red, R.drawable.color_red_faded,false, false),
            new SelectedColor(PlayerColors.TURQUOISE, R.drawable.color_turquoise, R.drawable.color_turquoise_faded, false,false),
            new SelectedColor(PlayerColors.ORANGE, R.drawable.color_orange, R.drawable.color_orange_faded,false, false),
            new SelectedColor(PlayerColors.BLUE, R.drawable.color_blue, R.drawable.color_blue_faded,false, false),
            new SelectedColor(PlayerColors.PURPLE, R.drawable.color_purple, R.drawable.color_purple_faded,false, false)
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private IWaitroomFragment mListener;

    private Button colorSelection1;
    private Button colorSelection2;
    private Button colorSelection3;
    private Button colorSelection4;
    private Button colorSelection5;

    private TextView playersInLobby;

    private ArrayList<Player> players;

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

        Button backOutButton = v.findViewById(R.id.waitroom_fragment_back_out_button);
        backOutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onWaitroomFragmentBackoutButton();
            }
        });

        updatePlayerList(ClientModel.getInstance().getCurrentGame().getPlayers());

        colorSelection1 = v.findViewById(R.id.color_selection_1);
        colorSelection2 = v.findViewById(R.id.color_selection_2);
        colorSelection3 = v.findViewById(R.id.color_selection_3);
        colorSelection4 = v.findViewById(R.id.color_selection_4);
        colorSelection5 = v.findViewById(R.id.color_selection_5);

        int playerCount = players.size();
        for (int i = 0; i < 5; i++) {

            int background = 0;
            if (i < playerCount) {
                PlayerColors playerColor = players.get(i).getColor();
                if (playerColor != PlayerColors.NO_COLOR) {
                    for (SelectedColor selectedColor : selectedColors) {
                        if (playerColor == selectedColor.playerColor) {
                            selectedColor.chosen = true;
                            selectedColor.shown = true;
                            background = selectedColor.backgroundFaded;
                            break;
                        }
                    }
                } else {
                    for (SelectedColor selectedColor : selectedColors) {
                        if (!selectedColor.chosen && !selectedColor.shown) {
                            selectedColor.shown = true;
                            background = selectedColor.background;
                            break;
                        }
                    }
                }
            } else {
                for (SelectedColor selectedColor : selectedColors) {
                    if (!selectedColor.chosen && !selectedColor.shown) {
                        selectedColor.shown = true;
                        background = selectedColor.background;
                        break;
                    }
                }
            }

            switch (i) {
                case 0:
                    colorSelection1.setBackgroundResource(background);
                    break;
                case 1:
                    colorSelection2.setBackgroundResource(background);
                    break;
                case 2:
                    colorSelection3.setBackgroundResource(background);
                    break;
                case 3:
                    colorSelection4.setBackgroundResource(background);
                    break;
                case 4:
                    colorSelection5.setBackgroundResource(background);
                    break;
                default:
                    break;
            }
        }

        playersInLobby = v.findViewById(R.id.players_in_lobby);
        playersInLobby.setText(playerCount + getString(R.string.players_in_lobby));
        return v;
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
