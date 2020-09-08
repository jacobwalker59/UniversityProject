package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.route;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.adapters.UserLocationAdapter;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.R;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package.WeatherActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
//entire class is a replica of the Map Fragment Class, had to change classes
// in a way to PHP switching as passing an Activity through Google Directions API was much simpler this way
// Other Google Directions prevented static input of lat and lan coordinates into its parameters
//so passing an intent was chosen instead.
public class RouteActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    Context mContext;
    GoogleApiClient mGoogleApiClient;
    public static Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;

    int radius = 500;

    double lat = 0;
    double lng = 0;

    List<Stop> stopsList = new ArrayList<>();
    List<UserLocation> userLocations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        mContext = this;
        populateStopsArray();
        populateUserLoactionsArray();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //easyRouteCalculation = new EasyRouteCalculation(this, mMap);
        drawBusStopsAndRout();
        buildGoogleApiClient();
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest();
    }

    void locationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(60000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //locationChecker(mGoogleApiClient, (Activity) mContext);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        lat = mLastLocation.getLatitude();
        lng = mLastLocation.getLongitude();

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //PlaceModel current location marker
        LatLng latLng;
        MarkerOptions markerOptions = new MarkerOptions();

        lat = location.getLatitude();
        lng = location.getLongitude();
        // StreetViewFragment.NY_TIME_SQUARE = new LatLng(latitude, longitude);
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

        //StreetViewActivity.NY_TIME_SQUARE=latLng;
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        //move map camera
        PlotMarkersOnMap();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        Toast.makeText(mContext, "Your Current Location", Toast.LENGTH_LONG).show();


        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }
    }

    void drawBusStopsAndRout() {
        for (int i = 0; i < stopsList.size(); i++) {

            MarkerOptions markers = new MarkerOptions();
            LatLng loc = new LatLng(stopsList.get(i).getLat(), stopsList.get(i).getLng());
            markers.position(loc);
            markers.title(stopsList.get(i).getName());
            markers.icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop));
            mMap.addMarker(markers).showInfoWindow();

        }


        GoogleDirection.withServerKey("AIzaSyCwTdI312gF8ppbwjKny768Xy3sPCHdIoU")
                .from(new LatLng(stopsList.get(0).getLat(),stopsList.get(0).getLng()))
                .and(new LatLng(stopsList.get(1).getLat(),stopsList.get(1).getLng()))
                .and(new LatLng(stopsList.get(2).getLat(),stopsList.get(2).getLng()))
                .and(new LatLng(stopsList.get(3).getLat(),stopsList.get(3).getLng()))
                .to(new LatLng(stopsList.get(4).getLat(),stopsList.get(4).getLng()))
                .transportMode(TransportMode.DRIVING)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if(direction.isOK()) {
                            DrawRouteonmap(direction);

                        } else {

                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {

                    }
                });
    }

    void DrawRouteonmap(Direction direction){
        if (direction.isOK()) {
            Route route = direction.getRouteList().get(0);
            int legCount = route.getLegList().size();
            for (int index = 0; index < legCount; index++) {
                Leg leg = route.getLegList().get(index);
                mMap.addMarker(new MarkerOptions().position(leg.getStartLocation().getCoordination()));
                if (index == legCount - 1) {
                    mMap.addMarker(new MarkerOptions().position(leg.getEndLocation().getCoordination()));
                }
                List<Step> stepList = leg.getStepList();
                ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(this, stepList, 5, Color.RED, 3, Color.BLUE);
                for (PolylineOptions polylineOption : polylineOptionList) {
                    mMap.addPolyline(polylineOption);
                }
            }
            setCameraWithCoordinationBounds(route);

        }
    }

    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }


    void PlotMarkersOnMap() {

        List<UserLocation> templocations = new ArrayList<>();

        if (userLocations.size() > 0) {

            mMap.addCircle(new CircleOptions()
                    .center(new LatLng(lat, lng))
                    .radius(Double.parseDouble("" + radius))
                    .strokeWidth(0)
                    .fillColor(0x66aaaFFF));
            for (int i = 0; i < userLocations.size(); i++) {
                int d = calculateDistanceInKilometer(lat, lng, userLocations.get(i).getLat(), userLocations.get(i).getLng());
                if (d < radius) {
                    templocations.add(userLocations.get(i));

                    MarkerOptions markers = new MarkerOptions();
                    LatLng loc = new LatLng(userLocations.get(i).getLat(), userLocations.get(i).getLng());
                    markers.position(loc);
                    markers.title(userLocations.get(i).getName());
                    markers.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    mMap.addMarker(markers).showInfoWindow();

                }
            }
            if (templocations.size() > 0)
                Dialogue(templocations);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMap != null) {
            PlotMarkersOnMap();
        }
    }

    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

    public int calculateDistanceInKilometer(double userLat, double userLng,
                                            double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round((AVERAGE_RADIUS_OF_EARTH_KM * c) * 1000));
    }

    public void Dialogue(List<UserLocation> locations) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(RouteActivity.this);
        builderSingle.setIcon(R.drawable.ic_my_icon);
        builderSingle.setTitle("Select One Location:-");

        final UserLocationAdapter adapter = new UserLocationAdapter(mContext, R.layout.listitem, locations);

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = adapter.getItem(which).getName();
                UserLocation location = adapter.getItem(which);
                Intent intent = new Intent(RouteActivity.this, WeatherActivity.class);
                intent.putExtra("location", location);
                startActivity(intent);

            }
        });
        builderSingle.show();
    }

    public void populateStopsArray() {
        Stop stop = new Stop("Bus Stop # 1", 54.976841, -1.615240);
        stopsList.add(stop);
        stop = new Stop("Bus Stop # 2", 54.978029, -1.618319);
        stopsList.add(stop);
        stop = new Stop("Bus Stop # 3", 54.981538, -1.616699);
        stopsList.add(stop);
        stop = new Stop("Bus Stop # 4", 54.979506, -1.612804);
        stopsList.add(stop);
        stop = new Stop("Bus Stop # 5", 54.986771, -1.613877);
        stopsList.add(stop);
    }

    public void populateUserLoactionsArray() {
        UserLocation ulocation = new UserLocation("Location # 1", 54.976403, -1.618619);
        userLocations.add(ulocation);
        ulocation = new UserLocation("Location # 2", 54.979531, -1.622224);
        userLocations.add(ulocation);
        ulocation = new UserLocation("Location # 3", 54.982160, -1.621387);
        userLocations.add(ulocation);
        ulocation = new UserLocation("Location # 4", 54.992459, -1.625164);
        userLocations.add(ulocation);
        ulocation = new UserLocation("Location # 5", 54.990569, -1.602698);

        userLocations.add(ulocation);
        ulocation = new UserLocation("Location # 6", 54.985958, -1.608406);
        userLocations.add(ulocation);
        ulocation = new UserLocation("Location # 7", 54.982287, -1.607444);
        userLocations.add(ulocation);
        ulocation = new UserLocation("Location # 8", 54.980520, -1.607970);
        userLocations.add(ulocation);
        ulocation = new UserLocation("Location # 9", 54.978716, -1.611285);
        userLocations.add(ulocation);
        ulocation = new UserLocation("Location # 10", 54.977084, -1.609440);
        userLocations.add(ulocation);
        ulocation = new UserLocation("Location # 11", 54.976203, -1.616146);
        userLocations.add(ulocation);
    }
}
