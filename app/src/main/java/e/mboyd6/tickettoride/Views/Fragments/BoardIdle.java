package e.mboyd6.tickettoride.Views.Fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.sharedcode.model.City;
import com.example.sharedcode.model.Game;
import com.example.sharedcode.model.Player;
import com.example.sharedcode.model.Route;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hunte on 3/4/2018.
 */

public class BoardIdle extends BoardState {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void drawRoutesAndCities(BoardFragment boardFragment, GoogleMap map, Game game, Player currentPlayer) {
        if (game == null) return;
        Map<String, City> cities = game.getCities();

        if (cities == null) return;
        drawCities(map, cities);

        Map<Route, Player> routes = game.getRoutesClaimed();
        if (routes == null) return;

        for(Route route : routes.keySet()) {
            Player playerWhoClaimed = routes.get(route);
            if (playerWhoClaimed != null) {

                drawClaimedRoute(map, cities, route, playerWhoClaimed);

            } else {

                drawNormalRoute(map, cities, route);
            }
        }

        boardFragment.setClickablePolygons(new HashMap<Polygon, Route>());
    }
}
