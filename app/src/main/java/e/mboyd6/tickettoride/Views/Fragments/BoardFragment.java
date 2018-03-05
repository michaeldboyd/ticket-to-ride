package e.mboyd6.tickettoride.Views.Fragments;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.sharedcode.model.City;
import com.example.sharedcode.model.DestinationCard;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;
import com.example.sharedcode.model.TrainCard;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import e.mboyd6.tickettoride.Presenters.GamePresenter;
import e.mboyd6.tickettoride.Presenters.Interfaces.IGamePresenter;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Adapters.CardDrawerAdapter;
import e.mboyd6.tickettoride.Views.Adapters.ClaimRouteButtonIdle;
import e.mboyd6.tickettoride.Views.Adapters.ClaimRouteButtonState;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private IGameActivity mListener;
    private View mLayout;
    private GoogleMap mMap;
    private Activity activity;

    private LatLng mCenter = new LatLng(39.23f, -111.41f);;
    private CameraPosition mOrigin;

    private boolean successfullyLoaded;
    private IGamePresenter mGamePresenter = new GamePresenter(this);

    private BoardState mBoardState = new BoardIdle();
    private CardDrawerAdapter mCardDrawerAdapter;
    private ViewFlipper mViewFlipper;

    private Map<Polygon, Route> clickablePolygons = new HashMap<Polygon, Route>();

    private Button mClaimRouteButton;
    private ClaimRouteButtonState mClaimRouteButtonState = new ClaimRouteButtonIdle();

    private boolean myTurn = false;

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
        mLayout = inflater.inflate(R.layout.fragment_board, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapview);
        mapFragment.getMapAsync(this);

        mClaimRouteButton = mLayout.findViewById(R.id.game_fragment_claim_route_button);

        mClaimRouteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBoardFragmentClaimRouteButton();
            }
        });

        return mLayout;
    }


    private void onBoardFragmentClaimRouteButton() {
        mClaimRouteButtonState.onClick(this);
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

        mGamePresenter.updateBoard();
        mGamePresenter.onNewTurn();
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
        ArrayList<Player> players = game.getPlayers();
        mMap.clear();
        mBoardState.drawRoutesAndCities(this, mMap, game, mGamePresenter.getCurrentPlayer());
    }

    @Override
    public void enterGame(ArrayList<TrainCard> trainCardsReceived, ArrayList<DestinationCard> initialDestinationCards) {
        setBoardState(new BoardIdle());
    }

    @Override
    public void completeTurn() {

    }

    @Override
    public void onNewTurn(String playerID) {
        myTurn = (playerID == mGamePresenter.getCurrentPlayer().getPlayerID());
    }

    @Override
    public void autoplay() {

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
    public void claimRoute(String routeName) {

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

    public void setClaimRouteButtonState(ClaimRouteButtonState claimRouteButtonState) {
        mClaimRouteButtonState = claimRouteButtonState;
        mClaimRouteButtonState.enter(this, mClaimRouteButton);
    }

    public void setBoardState(BoardState boardState) {
        mBoardState = boardState;
    }

    public BoardState getBoardState() { return mBoardState; }

    //https://stackoverflow.com/questions/25544370/google-maps-api-for-android-v2-how-to-add-text-with-no-background

    public void setCardDrawerState(CardDrawerAdapter cardDrawerState) {
        mCardDrawerAdapter = cardDrawerState;
    }

    public IGamePresenter getmGamePresenter() {
        return mGamePresenter;
    }

    public boolean isMyTurn() {
        return myTurn;
    }
}
