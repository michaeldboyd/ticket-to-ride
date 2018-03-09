package e.mboyd6.tickettoride.Views.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;
import com.example.sharedcode.model.TrainCard;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import e.mboyd6.tickettoride.Model.Autoplayer;
import e.mboyd6.tickettoride.Presenters.GamePresenter;
import e.mboyd6.tickettoride.Presenters.GamePresenterServerOff;
import e.mboyd6.tickettoride.Presenters.GamePresenterServerOn;
import e.mboyd6.tickettoride.Presenters.Interfaces.IGamePresenter;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Activities.GameActivity;
import e.mboyd6.tickettoride.Views.Adapters.CardDrawerDrawTrainCards;
import e.mboyd6.tickettoride.Views.Adapters.CardDrawerIdle;
import e.mboyd6.tickettoride.Views.Adapters.CardDrawerStartGame;
import e.mboyd6.tickettoride.Views.Adapters.CardDrawerState;
import e.mboyd6.tickettoride.Views.Adapters.ClaimRouteButtonIdle;
import e.mboyd6.tickettoride.Views.Adapters.ClaimRouteButtonMissing;
import e.mboyd6.tickettoride.Views.Adapters.ClaimRouteButtonState;
import e.mboyd6.tickettoride.Views.Adapters.ColorSelectionView;
import e.mboyd6.tickettoride.Views.Adapters.DrawerSlider;
import e.mboyd6.tickettoride.Views.Interfaces.IBoardFragment;
import e.mboyd6.tickettoride.Views.Interfaces.IGameActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link BoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class BoardFragment extends Fragment implements
        IBoardFragment,
        OnMapReadyCallback,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.CancelableCallback {

    protected int[] playerColors = {0xFFc92d39, 0xFF19967d, 0xFFef8d22, 0xFF0c7cba, 0xFF834187 };


    private IGameActivity mListener;
    private View mLayout;
    private GoogleMap mMap;
    private Activity activity;

    private LatLng mCenter = new LatLng(39.23f, -111.41f);;
    private CameraPosition mOrigin;

    private boolean successfullyLoaded;

    private Button mServerOnButton;
    private IGamePresenter mGamePresenter;

    private BoardState mBoardState = new BoardIdle();
    private CardDrawerState mCardDrawerState = null;
    private ViewFlipper mViewFlipper;

    private Map<Polygon, Route> clickablePolygons = new HashMap<Polygon, Route>();

    private Button mClaimRouteButton;
    private ClaimRouteButtonState mClaimRouteButtonState = new ClaimRouteButtonMissing();

    private boolean myTurn = false;

    private ArrayList<ColorSelectionView> mColorSelectionViews = new ArrayList<>();
    private Button mAutoplayButton;

    public ArrayList<ImageView> trainCardImages = new ArrayList<>();

    private Game latestLoadedGame = new Game();

    private DrawerSlider mDrawerSlider;

    private boolean uiLocked = false;

    public BoardFragment() {
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
    public static BoardFragment newInstance(String param1, String param2) {
        BoardFragment fragment = new BoardFragment();
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

    public boolean handleError(String message) {
        if (message != null && message.length() > 0) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT)
                    .show();
            return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLayout = inflater.inflate(R.layout.fragment_board, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapview);
        mapFragment.getMapAsync(this);

        mViewFlipper = mLayout.findViewById(R.id.draw_cards_drawer);
        mColorSelectionViews.add((ColorSelectionView) mLayout.findViewById(R.id.player_turn_1));
        mColorSelectionViews.add((ColorSelectionView) mLayout.findViewById(R.id.player_turn_2));
        mColorSelectionViews.add((ColorSelectionView) mLayout.findViewById(R.id.player_turn_3));
        mColorSelectionViews.add((ColorSelectionView) mLayout.findViewById(R.id.player_turn_4));
        mColorSelectionViews.add((ColorSelectionView) mLayout.findViewById(R.id.player_turn_5));

        mClaimRouteButton = mLayout.findViewById(R.id.game_fragment_claim_route_button);
        mClaimRouteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (uiLocked) {
                    handleError("You must finish your action first.");
                    return;
                }
                onBoardFragmentClaimRouteButton();
            }
        });

        mAutoplayButton = mLayout.findViewById(R.id.game_fragment_autoplay_button);
        final BoardFragment boardFragment = this;
        mAutoplayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (uiLocked) {
                    handleError("You must finish your action first.");
                    return;
                }
                autoplay();
                Autoplayer.getInstance().autoplay(getContext(), boardFragment);
            }
        });

        mAutoplayButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (uiLocked) {
                    handleError("You must finish your action first.");
                    return false;
                }
                autoplay();
                Autoplayer.getInstance().autoAutoplay(getContext(), boardFragment);
                return true;
            }
        });

        mServerOnButton = mLayout.findViewById(R.id.game_fragment_server_on_button);
        mServerOnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (uiLocked) {
                    handleError("You must finish your action first.");
                    return;
                }
                onServerOnButton();
            }
        });

        mDrawerSlider = mLayout.findViewById(R.id.drawer_slider);

        setGamePresenterState(new GamePresenterServerOn(this));

        // Run the updates even if Google Maps isn't working
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (mMap == null)
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            initialize();
                        }
                    });
            }
        });
        //thread.start();


        return mLayout;
    }


    private void onBoardFragmentClaimRouteButton() {
        mClaimRouteButtonState.onClick(this);
    }

    private void onServerOnButton() {
        if (mGamePresenter instanceof GamePresenterServerOff) {
            setGamePresenterState(new GamePresenterServerOn(this));
        } else {
            setGamePresenterState(new GamePresenterServerOff(this));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMinZoomPreference(6.5f);
        mMap.setMaxZoomPreference(8);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(6.5f));
        mOrigin = CameraPosition.builder().target(mCenter).bearing(90).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mOrigin));
        mMap.setOnCameraIdleListener(this);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return true;
            }
        });

        if (isAdded()) {
            boolean successfullyLoaded = mMap.setMapStyle(new MapStyleOptions(getResources()
                    .getString(R.string.style_ticket_to_ride)));

            if (!successfullyLoaded) {
                Log.e("MAPPROBLEM", "Style parsing failed.");
            }
        }

        //setCardDrawerState(new CardDrawerStartGame());
        initialize();
    }

    private void initialize() {
        if (getArguments() != null && getArguments().getBoolean("START_GAME", false)) {
            setCardDrawerState(new CardDrawerStartGame());
        }
        mGamePresenter.updateBoard();
        mGamePresenter.onUpdateTurn();
    }

    @Override
    public void onCameraIdle() {
        LatLng cameraPosition = mMap.getCameraPosition().target;
        double bearing = mMap.getCameraPosition().bearing;
        System.out.println(cameraPosition.toString());
        if (cameraPosition.longitude > -109 ||
                cameraPosition.longitude < -114 ||
                cameraPosition.latitude > 42 ||
                cameraPosition.latitude < 37 ||
                bearing != 90)
        {
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(mOrigin), 200, this);
        }
    }

    @Override
    public void onFinish() {
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void updateBoard(Game game) {
        if (game == null || mMap == null || mGamePresenter == null)
            return;
        latestLoadedGame = game;
        ArrayList<Player> players = game.getPlayers();

        mMap.clear();
        mBoardState.drawRoutesAndCities(this, mMap, game, mGamePresenter.getCurrentPlayerObject());

        if (mCardDrawerState != null)
            mCardDrawerState.updateBoard(game);
    }

    @Override
    public void enterGame(ArrayList<TrainCard> trainCardsReceived, ArrayList<DestinationCard> initialDestinationCards) {
        setBoardState(new BoardIdle());
    }

    @Override
    public void completeTurn() {

    }

    @Override
    public void onUpdateTurn(String pT) {
        final String playerTurn = pT == null ? "" : pT;
        if (!isAdded())
            return;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myTurn = mGamePresenter.isMyTurn();
                if (myTurn) {
                   setClaimRouteButtonState(new ClaimRouteButtonIdle());
                   if (!(mCardDrawerState instanceof CardDrawerStartGame))
                       setCardDrawerState(new CardDrawerDrawTrainCards());
                } else {
                    setClaimRouteButtonState(new ClaimRouteButtonMissing());
                    if (!(mCardDrawerState instanceof CardDrawerStartGame))
                        setCardDrawerState(new CardDrawerIdle());
                }

                ArrayList<Player> players = mGamePresenter.getPlayers();
                if (players == null) {
                    return;
                }

                int startIndex = mColorSelectionViews.size() - players.size();
                for (int i = 0; i < mColorSelectionViews.size(); i++) {
                    ColorSelectionView colorSelectionView = mColorSelectionViews.get(i);
                    if (i < startIndex) {
                        colorSelectionView.setVisibility(View.INVISIBLE);
                    } else {
                        Player player = players.get(i - startIndex);
                        colorSelectionView.setVisibility(View.VISIBLE);
                        colorSelectionView.setBackgroundResource(getPlayerColorBackground(player.getColor()));
                        colorSelectionView.setAlpha(playerTurn.equals(player.getPlayerID()) ? 1.0f : 0.25f);
                        colorSelectionView.setText(player.getName());
                    }
                }
            }
        });
    }

    private int getPlayerColorBackground(int playerColor) {
        switch(playerColor) {
            case 1:
                return R.drawable.color_red;
            case 2:
                return R.drawable.color_turquoise;
            case 3:
                return R.drawable.color_orange;
            case 4:
                return R.drawable.color_blue;
            case 5:
                return R.drawable.color_purple;
            default:
                return 0;
        }
    }
    @Override
    public void autoplay() {
        mGamePresenter.autoplay();
    }

    @Override
    public void drawTrainCards(int index1, int index2, int numberFromDeck) {

    }

    @Override
    public void receiveTrainCards(ArrayList<TrainCard> trainCardsReceived) {

    }

    @Override
    public void drawDestinationCards() {

    }

    @Override
    public void receiveDestinationCards(ArrayList<DestinationCard> destinationCards) {

    }

    @Override
    public void chooseDestinationCards(ArrayList<DestinationCard> chosen, ArrayList<DestinationCard> discarded) {

    }

    @Override
    public void claimRoute(Route route) {
        mGamePresenter.claimRoute(route);
    }

    @Override
    public void receiveRouteClaimed(String routeName) {

    }

    public void setClickablePolygons(Map<Polygon, Route> clickablePolygons) {
        this.clickablePolygons = clickablePolygons;
        final Map<Polygon, Route> clickPolys = this.clickablePolygons;
        final BoardFragment boardFragment = this;
        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon polygon) {
                if (polygon.getTag() != null && polygon.getTag().equals("Background_Layer")) {
                    mClaimRouteButtonState.setRoute(boardFragment,null);
                    for(Polygon otherPoly : clickPolys.keySet()) {
                        otherPoly.setFillColor(0xFFFFC0CB);
                    }
                    polygon.setFillColor(0x00000000);
                }
                else if (clickPolys.containsKey(polygon)) {
                    mClaimRouteButtonState.setRoute(boardFragment, clickPolys.get(polygon));
                    for(Polygon otherPoly : clickPolys.keySet()) {
                        otherPoly.setFillColor(0xFFFFC0CB);
                    }
                    polygon.setFillColor(0xFFFF1493);
                }
            }
        });
    }

    public void setGamePresenterState(GamePresenter gamePresenter) {
        if (mGamePresenter != null)
            mGamePresenter.exit();
        mGamePresenter = gamePresenter;
        mGamePresenter.enter(mServerOnButton);
    }

    public void setClaimRouteButtonState(ClaimRouteButtonState claimRouteButtonState) {
        mClaimRouteButtonState = claimRouteButtonState;
        mClaimRouteButtonState.enter(this, mClaimRouteButton);
    }

    public void setBoardState(BoardState boardState) {
        mBoardState = boardState;
    }

    public BoardState getBoardState() {
        return mBoardState;
    }

    //https://stackoverflow.com/questions/25544370/google-maps-api-for-android-v2-how-to-add-text-with-no-background

    public void setCardDrawerState(CardDrawerState cardDrawerState) {
        if (mCardDrawerState != null)
            mCardDrawerState.exit(getContext(),this, mViewFlipper, mDrawerSlider, (GamePresenter) mGamePresenter);
        mCardDrawerState = cardDrawerState;
        mCardDrawerState.enter(getContext(), this, mViewFlipper, mDrawerSlider, (GamePresenter) mGamePresenter);
    }

    public IGamePresenter getmGamePresenter() {
        return mGamePresenter;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public Game getLatestLoadedGame() {
        return latestLoadedGame;
    }

    public void setUILocked(boolean locked) {
        if(getActivity() instanceof GameActivity) {
            ((IGameActivity) getActivity()).setUiLocked(locked);
            uiLocked = locked;
        }
    }
}
