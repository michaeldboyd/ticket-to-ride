package e.mboyd6.tickettoride.Views.Fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

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
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by hunte on 3/4/2018.
 */

public class BoardState {
    protected int[] playerColors = {0xFFc92d39, 0xFF19967d, 0xFFef8d22, 0xFF0c7cba, 0xFF834187 };
    protected int[] trainColors = {0xffD7350A, 0xffE8E4E1, 0xffaa4609, 0xff9EBF34, 0xff0E6CB1, 0xffF8DA20, 0xff68605E, 0xffBC9CC5};
    protected int circleColor = 0xfff26b55;

    protected void drawRoutesAndCities(BoardFragment boardFragment, GoogleMap map, Game game, Player currentPlayer) { }

    protected void drawCities(GoogleMap map, Map<String, City> cities) {
        for(String cityName : cities.keySet()) {
            City city = cities.get(cityName);
            LatLng cityPosition = new LatLng(city.getLat(), city.getLon());
            LatLng cityLabelPosition = new LatLng(city.getLat() + 0.15, city.getLon() + 0.15);
            map.addCircle(new CircleOptions()
                    .center(cityPosition)
                    .radius(6000f)
                    .fillColor(circleColor)
                    .strokeWidth(3f)
                    .clickable(false));
            map.addMarker(new MarkerOptions()
                    .position(cityLabelPosition)
                    .icon(createPureTextIcon(cityName)));
        }
    }

    protected void drawNormalRoute(GoogleMap map, Map<String, City> cities, Route route) {
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

        double offset = 0;
        if (route.isDuplicate()) {
            if (city1Position.longitude > city2Position.longitude) {
                offset = -0.05;
            } else {
                offset = 0.05;
            }
        }
        double gapBeforePercentage = 0.25;
        double gapAfterPercentage = 0.25;
        double segmentLengthPercentage = 1 - gapAfterPercentage - gapBeforePercentage;
        double latitude = city1Position.latitude;
        double longitude = city1Position.longitude;

        ArrayList<Polyline> polyLinesDrawn = new ArrayList<>();
        for(int i = 0; i < segments; i++) {

            latitude += latDistance * gapBeforePercentage;
            longitude += lonDistance * gapBeforePercentage;
            LatLng point1 = new LatLng(latitude + offset, longitude + offset);
            latitude += latDistance * segmentLengthPercentage;
            longitude += lonDistance * segmentLengthPercentage;
            LatLng point2 = new LatLng(latitude + offset, longitude + offset);
            latitude += latDistance * gapAfterPercentage;
            longitude += lonDistance * gapAfterPercentage;

            polyLinesDrawn.add(map.addPolyline(new PolylineOptions()
                    .add(point1, point2)
                    .color(trainColors[route.getTrainType()])));
        }
    }

    protected void drawClaimedRoute(GoogleMap map, Map<String, City> cities, Route route, Player playerWhoClaimed) {
        City city1 = cities.get(route.getCity1());
        double x1 = city1.getLat();
        double y1 = city1.getLon();
        City city2 = cities.get(route.getCity2());
        double x2 = city2.getLat();
        double y2 = city2.getLon();

        double[] polyCoords = getPolyCoords(x1, y1, x2, y2, 0.04);

        Polygon backgroundPolygon = map.addPolygon(new PolygonOptions()
                .add(new LatLng(polyCoords[0], polyCoords[1]))
                .add(new LatLng(polyCoords[2], polyCoords[3]))
                .add(new LatLng(polyCoords[4], polyCoords[5]))
                .add(new LatLng(polyCoords[6], polyCoords[7]))
                .strokeWidth(0)
                .fillColor(playerColors[playerWhoClaimed.getColor()]));

        drawNormalRoute(map, cities, route);
        // Extra stuff here
    }

    protected Polygon drawBackgroundListener(GoogleMap map, Map<String, City> cities) {
        Polygon backgroundPolygon = map.addPolygon(new PolygonOptions()
                .add(new LatLng(32, -105))
                .add(new LatLng(45, -105))
                .add(new LatLng(45, -120))
                .add(new LatLng(32, -120))
                .strokeWidth(0));

        backgroundPolygon.setClickable(true);
        backgroundPolygon.setTag("Background_Layer");

        return backgroundPolygon;
    }

    protected Polygon drawAvailableRoute(BoardFragment boardFragment, GoogleMap map, Map<String, City> cities, Route route) {
        // Draw a background polygon for click listening
        City city1 = cities.get(route.getCity1());
        double x1 = city1.getLat();
        double y1 = city1.getLon();
        City city2 = cities.get(route.getCity2());
        double x2 = city2.getLat();
        double y2 = city2.getLon();

        double[] polyCoords = getPolyCoords(x1, y1, x2, y2, 0.04);

        Polygon linePolygon = map.addPolygon(new PolygonOptions()
                .add(new LatLng(polyCoords[0], polyCoords[1]))
                .add(new LatLng(polyCoords[2], polyCoords[3]))
                .add(new LatLng(polyCoords[4], polyCoords[5]))
                .add(new LatLng(polyCoords[6], polyCoords[7]))
                .strokeWidth(0)
                .fillColor(0xFFFFC0CB));

        linePolygon.setClickable(true);
        drawNormalRoute(map, cities, route);

        return linePolygon;
    }


    protected double[] getPolyCoords(double x1, double y1, double x2, double y2, double thickness) {
        /**
         * https://stackoverflow.com/questions/1222713/how-do-i-create-a-line-of-arbitrary-thickness-using-bresenham
         * Draw a background polygon
         *   angle = atan2(y2-y1,x2-x1);
         p[0].x = x1 + thickness*cos(angle+PI/2);
         p[0].y = y1 + thickness*sin(angle+PI/2);
         p[1].x = x1 + thickness*cos(angle-PI/2);
         p[1].y = y1 + thickness*sin(angle-PI/2);
         */

        double pointCoordinates[] = new double[]{0,0,0,0,0,0,0,0};
        double angle = Math.atan2(y2-y1,x2-x1);
        pointCoordinates[0] = x1 + thickness*Math.cos(angle + (Math.PI / 2));
        pointCoordinates[1] = y1 + thickness*Math.sin(angle + (Math.PI / 2));
        pointCoordinates[2] = x1 + thickness*Math.cos(angle - (Math.PI / 2));
        pointCoordinates[3] = y1 + thickness*Math.sin(angle - (Math.PI / 2));
        pointCoordinates[4] = x2 + thickness*Math.cos(angle - (Math.PI / 2));
        pointCoordinates[5] = y2 + thickness*Math.sin(angle - (Math.PI / 2));
        pointCoordinates[6] = x2 + thickness*Math.cos(angle + (Math.PI / 2));
        pointCoordinates[7] = y2 + thickness*Math.sin(angle + (Math.PI / 2));
        return pointCoordinates;
    }


    protected BitmapDescriptor createPureTextIcon(String text) {

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
