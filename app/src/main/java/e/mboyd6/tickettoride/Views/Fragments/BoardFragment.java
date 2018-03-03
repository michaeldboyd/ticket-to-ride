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
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharedcode.model.City;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Map;

import e.mboyd6.tickettoride.Presenters.GamePresenter;
import e.mboyd6.tickettoride.Presenters.Interfaces.IGamePresenter;
import e.mboyd6.tickettoride.R;
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

    private int[] trainColors = {0xffD7350A, 0xffE8E4E1, 0xffaa4609, 0xff9EBF34, 0xff0E6CB1, 0xffF8DA20, 0xff68605E, 0xffBC9CC5};
    private int circleColor = 0xfff26b55;
            /*
            Color.valueOf(0xffD7350A).toArgb(),
            Color.valueOf(0xffE8E4E1).toArgb(),
            Color.valueOf(0xffaa4609).toArgb(),
            Color.valueOf(0xff9EBF34).toArgb(),
            Color.valueOf(0xff0E6CB1).toArgb(),
            Color.valueOf(0xffF8DA20).toArgb(),
            Color.valueOf(0xff68605E).toArgb(),
            Color.valueOf(0xffBC9CC5).toArgb()};
           */

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

        /*
        MapView mapView =  mLayout.findViewById(R.id.mapview);

        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
*/
        return mLayout;
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

        // Customise the styling of the base map using a JSON object defined
        // in a string resource file. First create a MapStyleOptions object
        // from the JSON styles string, then pass this to the setMapStyle
        // method of the GoogleMap object.


        // Add a marker in Sydney and move the camer
        // a
        mMap.setMinZoomPreference(6.5f);
        mMap.setMaxZoomPreference(8);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(6.5f));
        mOrigin = CameraPosition.builder().target(mCenter).bearing(90).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mOrigin));
        mMap.setOnCameraIdleListener(this);

        if (isAdded()) {
            boolean successfullyLoaded = mMap.setMapStyle(new MapStyleOptions(getResources()
                    .getString(R.string.style_ticket_to_ride)));

            if (!successfullyLoaded) {
                Log.e("MAPPROBLEM", "Style parsing failed.");
            }

            mGamePresenter.updateBoard();
        }
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
        if (game == null) return;
        Map<String, City> cities = game.getCities();
        if (cities == null) return;
        for(String cityName : cities.keySet()) {
            City city = cities.get(cityName);
            LatLng cityPosition = new LatLng(city.getLat(), city.getLon());
            LatLng cityLabelPosition = new LatLng(city.getLat() + 0.15, city.getLon() + 0.15);
            mMap.addCircle(new CircleOptions().center(cityPosition).radius(6000f).fillColor(circleColor).strokeWidth(3f));
            mMap.addMarker(new MarkerOptions().position(cityLabelPosition).icon(createPureTextIcon(cityName)));
        }
        Map<Route, Player> routes = game.getRoutesClaimed();
        if (cities == null) return;
        for(Route route : routes.keySet()) {
            City city1 = cities.get(route.getCity1());
            LatLng city1Position = new LatLng(city1.getLat(), city1.getLon());
            City city2 = cities.get(route.getCity2());
            LatLng city2Position = new LatLng(city2.getLat(), city2.getLon());


            int segments = route.getNumberTrains();

            double segmentsDivider = (double) segments;

            double latDistance = (city2.getLat() - city1.getLat()) / segmentsDivider;
            double lonDistance = (city2.getLon() - city1.getLon()) / segmentsDivider;

            //double distance = Math.sqrt(Math.pow(latDistance, 2) + Math.pow(lonDistance, 2));
            double padding = 0.2;


            double gapBeforePercentage = 0.25;
            double gapAfterPercentage = 0.25;
            double segmentLengthPercentage = 1 - gapAfterPercentage - gapBeforePercentage;
            double latitude = city1Position.latitude;
            double longitude = city1Position.longitude;
            for(int i = 0; i < segments; i++) {
                System.out.println("Loop " + i);

                latitude += latDistance * gapBeforePercentage;
                longitude += lonDistance * gapBeforePercentage;
                LatLng point1 = new LatLng(latitude, longitude);
                latitude += latDistance * segmentLengthPercentage;
                longitude += lonDistance * segmentLengthPercentage;
                LatLng point2 = new LatLng(latitude, longitude);
                latitude += latDistance * gapAfterPercentage;
                longitude += lonDistance * gapAfterPercentage;

                mMap.addPolyline(new PolylineOptions()
                        .add(point1, point2)
                        .color(trainColors[route.getTrainType()]));
            }


        }
    }

    //https://stackoverflow.com/questions/25544370/google-maps-api-for-android-v2-how-to-add-text-with-no-background

    public BitmapDescriptor createPureTextIcon(String text) {

        Paint backgroundTextPaint = new Paint(); // Adapt to your needs
        backgroundTextPaint.setTextSize(36f);
        backgroundTextPaint.setStyle(Paint.Style.STROKE);
        backgroundTextPaint.setStrokeWidth(5f);
        backgroundTextPaint.setColor(0xFF000000);

        Paint textPaint = new Paint(); // Adapt to your needs
        textPaint.setTextSize(36f);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(0xFFFFFFFF);

        float textWidth = textPaint.measureText(text);
        float textHeight = textPaint.getTextSize() + 10f;
        int width = (int) (textWidth);
        int height = (int) (textHeight);

        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);

        canvas.translate(0, height);

        // For development only:
        // Set a background in order to see the
        // full size and positioning of the bitmap.
        // Remove that for a fully transparent icon.
        canvas.drawText(text, 0, -10, backgroundTextPaint);

        canvas.drawText(text, 0, -10, textPaint);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(image);
        return icon;
    }
}
