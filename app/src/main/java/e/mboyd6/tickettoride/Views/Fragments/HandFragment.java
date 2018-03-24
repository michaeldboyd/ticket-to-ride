package e.mboyd6.tickettoride.Views.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Player;

import java.util.ArrayList;
import java.util.Map;

import e.mboyd6.tickettoride.Presenters.GamePresenter;
import e.mboyd6.tickettoride.Presenters.Interfaces.IGamePresenter;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Adapters.DestinationCardAdapter;
import e.mboyd6.tickettoride.Views.Interfaces.IGameActivity;
import e.mboyd6.tickettoride.Views.Interfaces.IHandFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HandFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HandFragment extends Fragment implements IHandFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private IGameActivity mListener;
    private View mLayout;

    private IGamePresenter mGamePresenter = new GamePresenter(this, getActivity());
    private DestinationCardAdapter mDestinationCardAdapter;
    private ArrayList<ImageView> mTrainCardsImages = new ArrayList<>();
    private ArrayList<TextView> mTrainCardsText = new ArrayList<>();
    private TextView mTrainsText;

    public HandFragment() {
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
    public static HandFragment newInstance(String param1, String param2) {
        HandFragment fragment = new HandFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLayout = inflater.inflate(R.layout.fragment_hand, container, false);

        mDestinationCardAdapter = new DestinationCardAdapter(getContext(), new ArrayList<DestinationCard>(), this);
        ListView destinationCardList = mLayout.findViewById(R.id.destination_card_list);
        destinationCardList.setAdapter(mDestinationCardAdapter);

        mTrainCardsImages.add((ImageView) mLayout.findViewById(R.id.hand_fragment_train_card_1_image));
        mTrainCardsImages.add((ImageView) mLayout.findViewById(R.id.hand_fragment_train_card_2_image));
        mTrainCardsImages.add((ImageView) mLayout.findViewById(R.id.hand_fragment_train_card_3_image));
        mTrainCardsImages.add((ImageView) mLayout.findViewById(R.id.hand_fragment_train_card_4_image));
        mTrainCardsImages.add((ImageView) mLayout.findViewById(R.id.hand_fragment_train_card_5_image));
        mTrainCardsImages.add((ImageView) mLayout.findViewById(R.id.hand_fragment_train_card_6_image));
        mTrainCardsImages.add((ImageView) mLayout.findViewById(R.id.hand_fragment_train_card_7_image));
        mTrainCardsImages.add((ImageView) mLayout.findViewById(R.id.hand_fragment_train_card_8_image));
        mTrainCardsImages.add((ImageView) mLayout.findViewById(R.id.hand_fragment_train_card_9_image));

        for(ImageView imageView : mTrainCardsImages) {
            imageView.setAlpha(0.5f);
        }

        mTrainCardsText.add((TextView) mLayout.findViewById(R.id.hand_fragment_train_card_1_text));
        mTrainCardsText.add((TextView) mLayout.findViewById(R.id.hand_fragment_train_card_2_text));
        mTrainCardsText.add((TextView) mLayout.findViewById(R.id.hand_fragment_train_card_3_text));
        mTrainCardsText.add((TextView) mLayout.findViewById(R.id.hand_fragment_train_card_4_text));
        mTrainCardsText.add((TextView) mLayout.findViewById(R.id.hand_fragment_train_card_5_text));
        mTrainCardsText.add((TextView) mLayout.findViewById(R.id.hand_fragment_train_card_6_text));
        mTrainCardsText.add((TextView) mLayout.findViewById(R.id.hand_fragment_train_card_7_text));
        mTrainCardsText.add((TextView) mLayout.findViewById(R.id.hand_fragment_train_card_8_text));
        mTrainCardsText.add((TextView) mLayout.findViewById(R.id.hand_fragment_train_card_9_text));

        for(TextView textView : mTrainCardsText) {
            textView.setVisibility(View.INVISIBLE);
            String textViewText = "x 0";
            textView.setText(textViewText);
        }

        mTrainsText = mLayout.findViewById(R.id.hand_fragment_trains_text);
        mTrainsText.setText("");
        mTrainsText.setVisibility(View.VISIBLE);

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
    public void updateHand(final Player player) {
        if(isAdded() && getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDestinationCardAdapter.clear();
                    mDestinationCardAdapter.addAll(player.getDestinationCards());

                    for(ImageView imageView : mTrainCardsImages) {
                        imageView.setAlpha(0.5f);
                    }

                    for(TextView textView : mTrainCardsText) {
                        textView.setVisibility(View.INVISIBLE);
                    }

                    Map<Integer, Integer> hand = player.getHand();

                    for(int i : hand.keySet()) {
                        if (hand.get(i) != null & hand.get(i) > 0) {
                            mTrainCardsImages.get(i).setAlpha(1f);
                            String trainCardText = "x " + hand.get(i);
                            mTrainCardsText.get(i).setText(trainCardText);
                            mTrainCardsText.get(i).setVisibility(View.VISIBLE);
                        }
                    }

                    String trainsText = "x " + player.getTrains();
                    mTrainsText.setText(trainsText);
                    mTrainsText.setVisibility(View.VISIBLE);
                }
            });
        }

    }
}
