package e.mboyd6.tickettoride.Views.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Interfaces.IGameActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link BoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardFragment extends Fragment implements OnMapReadyCallback,
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

    private LatLng mCenter;
    private CameraPosition mOrigin;

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
        //SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
         //       .findFragmentById(R.id.mapview);
        //mapFragment.getMapAsync(this);

        MapView mapView =  mLayout.findViewById(R.id.mapview);

        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

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
        boolean success = mMap.setMapStyle(new MapStyleOptions(getActivity().getResources()
                .getString(R.string.style_ticket_to_ride)));

        if (!success) {
            Log.e("MAPPROBLEM", "Style parsing failed.");
        }

        // Add a marker in Sydney and move the camer
        // a
        mCenter = new LatLng(39.23f, -111.41f);
        mMap.addMarker(new MarkerOptions().position(mCenter).title("Marker in Sydney"));
        mMap.setMinZoomPreference(6.5f);
        mMap.setMaxZoomPreference(8);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(6.5f));
        mOrigin = CameraPosition.builder().target(mCenter).bearing(90).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mOrigin));
        mMap.setOnCameraIdleListener(this);
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

}
