package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.main_fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.adapters.UserLocationAdapter;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.models_weather.CurrentWeather;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package.BusStop;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package.HorizontalItem;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package.JsonParsedObject;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package.SearchItem;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package.WeatherActivity;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.R;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.route.PathDescription;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.route.Stop;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.route.UserLocation;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.adapters.HorizontalRecyclerClassAdapter;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.models.MyPlaces;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.models.Results;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.remotes.GoogleApiService;
import com.example.csc_8099_mobile_bus_tour_application_jacob_walker.remotes.RetrofitBuilder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// lines 59 to 183 were taken from the following
// https://www.youtube.com/watch?v=FIDeomTlYHI&list=PLxabZQCAe5fgXx8cn2iKOtt0VFJrf5bOd&index=6
// https://www.youtube.com/watch?v=OknMZUnTyds&list=PLgCYzUzKIBE-vInwQhGSdnbyJ62nixHCt
// both were used to create geo location tracking.

public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private Marker marker;
    private MapView mapView;
    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    public static Location mLastLocation;
    private Marker mCurrLocationMarker;
    private LocationRequest mLocationRequest;
    private int radius = 5000;
    public CurrentWeather mWeather;
    private double lat = 0;
    private double lng = 0;
    private List<Stop> stopsList = new ArrayList<>();
    private ArrayList<Stop> tempStopList = new ArrayList<>();
    private Stop stop;
    private List<UserLocation> userLocations = new ArrayList<>();
    private View view;
    private List<SearchItem> searchItems;
    private AutoCompleteTextView autoCompleteTextView;
    private String placeType = "";
    private EditText placeSearch;
    private ImageView search;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    public String weatherType = "";
    public String weatherTemp = "";
    private int markerclicked;
    List<UserLocation> temporaryLocations = new ArrayList<>();
    String miling;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ImageView findBusView;
    List<PathDescription> pathDescriptions = new ArrayList<>();
    ArrayList<HorizontalItem> horizontalItems = new ArrayList<>();
    ArrayList<JsonParsedObject> imageArrayList = new ArrayList<>();
    ArrayList<BusStop> timeList;
    BusStop BusStopApproach;
    BusStop BusStopReturn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //below creates a view within the Map Fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);

        mContext = getContext();

        placeSearch = view.findViewById(R.id.places_search);
        search = view.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeType = placeSearch.getText().toString();
                getNearbyPlaces();
            }
        });
        //Gets all Bus Stop Arrays and uses Polylines to connect them
        populateStopsArray();
        populateUserLoactionsArray();
        populatePathDescription();

        //generalised items placed into Array List, to show that Horizontal Recycler List Changes
        horizontalItems.add(new HorizontalItem(R.drawable.scafelpike, "Line 1", true));
        horizontalItems.add(new HorizontalItem(R.drawable.scafelpike, "Line 1", true));

        //Passing Adapter for Horizintal Recycler View

        mRecyclerView = (RecyclerView) view.findViewById(R.id.map_horizontal_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(horizontalLayoutManagaer);
        mAdapter = new HorizontalRecyclerClassAdapter(horizontalItems);
        mRecyclerView.setAdapter(mAdapter);


        //Hard code Array List for each individual path interest location
        imageArrayList.add(new JsonParsedObject(userLocations.get(0).getName(), "scarfell", "scarfelpike"));
        imageArrayList.add(new JsonParsedObject(userLocations.get(1).getName(), "scafelpike", "scarfell"));
        imageArrayList.add(new JsonParsedObject(userLocations.get(2).getName(), "ullswater", "pic"));
        imageArrayList.add(new JsonParsedObject(userLocations.get(3).getName(), "scarfell", "scarfelpike"));
        imageArrayList.add(new JsonParsedObject(userLocations.get(4).getName(), "scafelpike", "scarfelpike"));
        imageArrayList.add(new JsonParsedObject(userLocations.get(5).getName(), "ullswater", "derwent"));
        imageArrayList.add(new JsonParsedObject(userLocations.get(6).getName(), "scarfell", "scarfelpike"));

        timeList = new ArrayList<BusStop>();

        //Hard code Array List for Time Table Functionality
        timeList.add(new BusStop("Brough Clock", "10:35", "16:15"));
        timeList.add(new BusStop("Winton", "10:41", "16:10"));
        timeList.add(new BusStop("Kirby Stephen", "10:50", "16:00"));
        timeList.add(new BusStop("Kirby Stephen West Station", "10:55", "15:53"));
        timeList.add(new BusStop("Ravenstonedale School", "11:00", "15:45"));
        timeList.add(new BusStop("Newbiggin on Lune Village Hall", "11:05", "15:40"));
        timeList.add(new BusStop("Mount Pleasant, Tepay", "11:18", "15:29"));
        timeList.add(new BusStop("Barnaby Rudge, Tepay", "11:20", "15:27"));
        timeList.add(new BusStop("Grayrigg", "11:34", "15:10"));
        timeList.add(new BusStop("Kendal Morrisons", "11:45", "15:00"));
        timeList.add(new BusStop("Stricklandgate", "21:51", ""));
        timeList.add(new BusStop("Blackhall Road", "23:55", "20:55"));

        findBusView = view.findViewById(R.id.searchBuses);

        //Validation of openTimeDialogue method by checking for NULL
        findBusView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                 getTime();
                if (BusStopApproach == null || BusStopReturn == null) {
                    Toast.makeText(mContext, "NULL", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "NOT NULL", Toast.LENGTH_SHORT).show();
                    openTimeDialogue();
                }


            }
        });



        return view;
    }



// would have use LocalTime but since required SDK is 26 for use and device is only 21,
    //had to find alternative solution
    //uses calendar to iterate through through all Bus Stop Array Locations
    //converts al time objects to String
    //if current Date < Bus time table date, add to Array List

    public void getTime() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String time = format.format(calendar.getTime());

        ArrayList<BusStop> tempBusStopListApproach = new ArrayList<BusStop>();
        ArrayList<BusStop> tempBusStopListReturn = new ArrayList<BusStop>();
        for (int i = 0; i < timeList.size(); i++) {

            BusStop getStop = timeList.get(i);

            if (getStop.getApproach().compareTo(time) >= 0) {
                tempBusStopListApproach.add(getStop);


            }
            if (getStop.getReturning().compareTo(time) >= 0) {
                tempBusStopListReturn.add(getStop);

            }
        }

        if(!(tempBusStopListApproach.isEmpty())){
            BusStopApproach=tempBusStopListApproach.get(0);

        }

        if(!(tempBusStopListReturn.isEmpty())){
            BusStopReturn=tempBusStopListReturn.get(0);

        }

        if(tempBusStopListApproach.isEmpty()){
            Toast.makeText(mContext, "There are no approach buses at this time", Toast.LENGTH_SHORT).show();
        }
        if(tempBusStopListReturn.isEmpty()){
            Toast.makeText(mContext, "There are no buses at this time that will return", Toast.LENGTH_SHORT).show();
        }

    }

    //get Time method combined with Dialog method, which sets objects within its view.

    public void openTimeDialogue(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialogue_layout, null);  // this line
        builder.setView(v);
        builder.setTitle("Next Bus Link");
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        final AlertDialog dialog = builder.create();


        dialog.show();

        if(!(BusStopApproach==null)) {

            TextView textView = dialog.findViewById(R.id.bus_approach_id);
            TextView textView2 = dialog.findViewById(R.id.bus_approach_time_id);
            textView2.setText(BusStopApproach.getApproach());
            textView.setText(BusStopApproach.getBusStopName());

        }

        if(!(BusStopReturn==null)) {

            TextView textView = dialog.findViewById(R.id.bus_return_id);
            TextView textView2 = dialog.findViewById(R.id.bus_return_time_id);
            textView2.setText(BusStopReturn.getReturning());
            textView.setText(BusStopReturn.getBusStopName());


        }
    }



    public void ArrayFill(Marker marker1){
        horizontalItems.clear();

        getMarker(marker1);

        mAdapter.notifyDataSetChanged();

    }

    //used to get any markers which are clicked on and change the information in the Recycler View to it.

    public String getMarker(Marker marker2){
        String markerName ="";

        for(int i=0;i<userLocations.size();i++) {
            if (marker2.getTitle().equals(userLocations.get(i).getName())) {


                String imageName = imageArrayList.get(i).getFirstPicture();
                int resID = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());
                markerName = marker2.getTitle();
                horizontalItems.add(new HorizontalItem(resID, markerName, true));
                if(!(imageArrayList.get(i).getSecondPicture()=="")){
                    String imageName2 = imageArrayList.get(i).getSecondPicture();
                    int resID2 = mContext.getResources().getIdentifier(imageName2, "drawable", mContext.getPackageName());
                    horizontalItems.add(new HorizontalItem(resID2, markerName, true));
                }
            }
        }
        return markerName;
    }

    private GoogleApiService googleApiService;
    private MyPlaces myPlaces;

    List<Results> results;

    //clears the Map and checks if latitude and longitude are correct
    // uses Google Places API,
    // Places API key within URL builder
    //URL builder used for HTTPS Request

    private void getNearbyPlaces() {
        mMap.clear();
        if (lat != 0 && lng != 0) {

            final ProgressDialog dialog = new ProgressDialog(mContext);
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.setIndeterminate(false);
            dialog.show();

            String apiKey = "AIzaSyCwTdI312gF8ppbwjKny768Xy3sPCHdIoU";
            String url = buildUrl(lat, lng,String.valueOf(radius), apiKey);
            Log.d("finalUrl", url);

            // below is typical of a Retrofit Builder call, method
            //used to create a set of protocols in case there is a failure

            googleApiService = RetrofitBuilder.PlacesBuilder().create(GoogleApiService.class);

            Call<MyPlaces> call = googleApiService.getMyNearByPlaces(url);

            call.enqueue(new Callback<MyPlaces>() {
                @Override
                public void onResponse(Call<MyPlaces> call, Response<MyPlaces> response) {
                    //myPlaces represents a class of all POJO's for my Places.
                    // instantiation of the above set to equal all the JSON information taken from the call.
                    myPlaces = response.body();
                    Log.d("MyPlaces", myPlaces.getResults().get(0).toString());
                    results = myPlaces.getResults();
                    //calls the on map ready the display the changes to the View within the Fragment
                    onMapReady(mMap);
                    dialog.dismiss();

                }

                @Override
                public void onFailure(Call<MyPlaces> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void fillSearchList() {
        searchItems = new ArrayList<>();
        searchItems.add(new SearchItem("Bars", R.drawable.ic_action_name));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.map);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    //google api client build, similar to retrofit builder, required in order to make URL connection with Google Maps
    // allows for user location method to be specified later on.

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    //initial Map configurations set to Sydney.
    //Bus Stop locations iterated through and placed upon the map
    //users locations to set to Lat and Lng variables
    //circle is added around the users location

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        drawBusStopsAndRout();
        buildGoogleApiClient();
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (results != null) {
            mMap.addCircle(new CircleOptions()
                    .center(new LatLng(lat ,lng))
                    .radius(radius)
                    .strokeWidth(0)
                    .fillColor(0x66aaaFFF));
            for (int i = 0; i < results.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                Results googlePlace = results.get(i);
                double lat = Double.parseDouble(googlePlace.getGeometry().getLocation().getLat());
                double lng = Double.parseDouble(googlePlace.getGeometry().getLocation().getLng());
                String placeName = googlePlace.getName();
                String vicinity = googlePlace.getVicinity();
                LatLng latLng = new LatLng(lat, lng);
                markerOptions.position(latLng);
                markerOptions.title(placeName);
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                // add marker to map
                googleMap.addMarker(markerOptions).showInfoWindow();
                ;
                // move camera
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                //googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
                googleMap.getUiSettings().setCompassEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
            }
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                ArrayFill(marker);
                return false;
            }
        });

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {

                return null;
            }

            // all information Windows settings, makes sure that information Windows correspond to specific item
            //within Array List.
            @Override
            public View getInfoContents(Marker marker) {

                View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);
                v.setLayoutParams(new RelativeLayout.LayoutParams(500, RelativeLayout.LayoutParams.WRAP_CONTENT));
                LatLng latLng = marker.getPosition();
                ImageView im = (ImageView) v.findViewById(R.id.imageView1);
                TextView tv1 = (TextView) v.findViewById(R.id.textView1);
                TextView tv2 = (TextView) v.findViewById(R.id.textView2);
                TextView tv3 = (TextView) v.findViewById(R.id.textView3);

                String title= marker.getTitle();
                String informations=marker.getSnippet();

                tv1.setText(title);
                tv2.setText(informations);

                  for(int i=0;i<userLocations.size();i++) {
                      if (marker.getTitle().equals(userLocations.get(i).getName())) {
                          tv3.setText("Distance:" + String.valueOf(pathDescriptions.get(i).getMiles()));
                          if (marker.getTitle().equals(userLocations.get(1).getName()))
                          {
                              im.setImageResource(R.drawable.scarfell);
                          }
                          if (marker.getTitle().equals(userLocations.get(2).getName()))
                          {
                              im.setImageResource(R.drawable.scafelpike);
                          }
                      }
                  }
                return v;
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest();
    }

   public void locationRequest() {
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

    //below constantly gets the Users lat and long and sets them upon the map
    //method is called every 10 minutes

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        lat = mLastLocation.getLatitude();
        lng = mLastLocation.getLongitude();

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        LatLng latLng;
        MarkerOptions markerOptions = new MarkerOptions();

        lat = location.getLatitude();
        lng = location.getLongitude();

        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

        mCurrLocationMarker = mMap.addMarker(markerOptions);
        //move map camera
        PlotMarkersOnMap();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        Toast.makeText(mContext, "Your Current Location", Toast.LENGTH_LONG).show();

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }
    }

    // Bus Stop route is iterated through and markers added

    public void drawBusStopsAndRout() {
        for (int i = 0; i < stopsList.size(); i++) {

            MarkerOptions markers = new MarkerOptions();
            LatLng loc = new LatLng(stopsList.get(i).getLat(), stopsList.get(i).getLng());
            markers.position(loc);
            markers.title(stopsList.get(i).getName());
            markers.icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop));
            mMap.addMarker(markers).showInfoWindow();

        }

        //would normally use a for each loop and also get the coordinates out of the database in the same means
        // as the time table but for testing and time considerations below option was used, although will be changed
        // eventually.

        GoogleDirection.withServerKey("AIzaSyCwTdI312gF8ppbwjKny768Xy3sPCHdIoU")
                .from(new LatLng(stopsList.get(0).getLat(), stopsList.get(0).getLng()))
                .and(new LatLng(stopsList.get(1).getLat(), stopsList.get(1).getLng()))
                .and(new LatLng(stopsList.get(2).getLat(), stopsList.get(2).getLng()))
                .and(new LatLng(stopsList.get(3).getLat(), stopsList.get(3).getLng()))
                .and(new LatLng(stopsList.get(4).getLat(), stopsList.get(4).getLng()))
                .and(new LatLng(stopsList.get(5).getLat(), stopsList.get(5).getLng()))
                .and(new LatLng(stopsList.get(6).getLat(), stopsList.get(6).getLng()))
                .and(new LatLng(stopsList.get(7).getLat(), stopsList.get(7).getLng()))
                .and(new LatLng(stopsList.get(8).getLat(), stopsList.get(8).getLng()))
                .and(new LatLng(stopsList.get(9).getLat(), stopsList.get(9).getLng()))
                .to(new LatLng(stopsList.get(10).getLat(), stopsList.get(10).getLng()))
                .transportMode(TransportMode.DRIVING)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if (direction.isOK()) {
                            DrawRouteonmap(direction);

                        } else {
                        }
                    }
                    @Override
                    public void onDirectionFailure(Throwable t) {

                    }
                });
    }

    //additional Polyline spec using Google Directions API

    public void DrawRouteonmap(Direction direction) {
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
                ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(mContext, stepList, 5, Color.RED, 3, Color.BLUE);
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

    //uses a combination of google radius to calculate distance based on GeoLocation and adds from one arraylist to another.
    // if in the second array list a notifcation pops up about directions in the location area.
    // Polygon is added to each user
    // radius log checks if objects are within radius and then adds them to the Dialog


    public void PlotMarkersOnMap() {

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
                    //Double d=getDistanceFromLatLonInKm(lat,lng,locations.get(i).getLat(), locations.get(i).getLng());
                    MarkerOptions markers = new MarkerOptions();
                    LatLng loc = new LatLng(userLocations.get(i).getLat(), userLocations.get(i).getLng());
                    markers.position(loc);
                   int mileageTime = pathDescriptions.indexOf(markers);
                    markers.title(userLocations.get(i).getName());
                    String miles = Double.toString(pathDescriptions.get(i).getMiles());
                    markers.snippet(pathDescriptions.get(i).getDesc());
                    markers.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    mMap.addMarker(markers).showInfoWindow();

                }
            }
            // radius calculator, checks if any items are within the radius and adds them to an Array List

            if (templocations.size() > 0)
                Dialogue(templocations);
            temporaryLocations = templocations;
            //printWeatherType();

            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(2));

        }
    }

    //takes the calculate distance between but converts to metres instead. Makes it easier to hard code distance
    // would also allow user to have full control of radius spectrum in drop down list when combined with Google Directions API.

    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

    //Following taken directly from the following code at Stack Overflow
    // https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula/27943#27943
    //converts the raidus to Kilometers as Radius in the API uses kilos

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

    //creates a pop up notification, takes the user to a weather class so that they can see information concerning
    // weather, additional functionality will make references to what should be visited, however would
    // require full use of database functionality which has only been incorporated very late into the work schedule
    // as such time constraints mean that current functionality has to make do, to compensate in order to compelte 20k word count

    public void Dialogue(List<UserLocation> locations) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(mContext);
        builderSingle.setIcon(R.drawable.ic_my_icon);
        builderSingle.setTitle("Select One Location:-");

        final UserLocationAdapter adapter = new UserLocationAdapter(mContext, R.layout.listitem, locations);

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //intent will take the user to WeatherActivity Class

        builderSingle.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = adapter.getItem(which).getName();
                UserLocation location = adapter.getItem(which);
                Intent intent = new Intent(mContext, WeatherActivity.class);
                intent.putExtra("location", location);

                startActivity(intent);

            }
        });
        builderSingle.show();
    }

    // adds any locations of a bus stop type to an arraylist.
    // time considerations granted, would have used arraylist/adapter/mySQLite database combo as per
    // the time table

    public void populateStopsArray() {

// adds any locations of a specific type to an arraylist.
        // time considerations granted, would have used arraylist/adapter/mySQLite database combo as per
        // the time table

        Stop stop = new Stop("Brough Clock", 54.526212, -2.318561);
        stopsList.add(stop);
        stop = new Stop("Winton", 54.490263, -2.334191);
        stopsList.add(stop);
        stop = new Stop("Kirby Stephen", 54.474413, -2.349169);
        stopsList.add(stop);
        stop = new Stop("Kirby Stephen West Station", 54.430952, -2.427027);
        stopsList.add(stop);
        stop = new Stop("RavenstoneDale", 54.430952, -2.426995);
        stopsList.add(stop);
        stop = new Stop("Newbiggin on Lune Village Hall", 54.441104, -2.456055);
        stopsList.add(stop);
        stop = new Stop("Mount Pleasant", 54.325881, -2.751902);
        stopsList.add(stop);
        stop = new Stop("Barnaby Ridge", 54.426498, -2.596652);
        stopsList.add(stop);
        stop = new Stop("GrayRigg", 54.368972, -2.648730);
        stopsList.add(stop);
        stop = new Stop("Kendal Morrisons", 54.341057, -2.735039);
        stopsList.add(stop);
        stop = new Stop("Blackhall Road", 54.329275, -2.744274);
        stopsList.add(stop);

    }

    // adds any locations of a specific type to an arraylist.
    // time considerations granted, would have used arraylist/adapter/mySQLite database combo as per
    // the time table


    public void populateUserLoactionsArray() {

        UserLocation ulocation = new UserLocation("KirbySide Forest Hike" , 54.529698, -2.314749);
        userLocations.add(ulocation);
        ulocation = new UserLocation("Barn-Side Lake Stroll", 54.490006, -2.321901);
        userLocations.add(ulocation);
        ulocation = new UserLocation("Hill Side Avenue", 54.473471, -2.342455);
        userLocations.add(ulocation);
        ulocation = new UserLocation("Weather Top", 54.431370, -2.424366);
        userLocations.add(ulocation);
        ulocation = new UserLocation("Shireton",54.430952, -2.427027);
        userLocations.add(ulocation);
        ulocation = new UserLocation("Gondor",54.330630, -2.756816);
        userLocations.add(ulocation);
        ulocation = new UserLocation("Narnia", 54.426230, -2.595407);
        userLocations.add(ulocation);

    }

    // add items to path Array List

    public void populatePathDescription(){

        PathDescription path = new PathDescription("Path allows for brisque leisurely stroll", 1.5);
        pathDescriptions.add(path);
        path = new PathDescription("For the more adventurous thrill seeker, offers incredible views, but often precocious on bad weather", 2.5);
        pathDescriptions.add(path);
        path = new PathDescription("A longer distance, but altitude is kept to a minimum", 7.2);
        pathDescriptions.add(path);
        path = new PathDescription("Steapest of all terrain, offers terrific views and ideal at sunset, not for the faint hearted", 9.5);
        pathDescriptions.add(path);
        path = new PathDescription("Recommended for all, enjoy a light leisurely walk, with light winds", 2.5);
        pathDescriptions.add(path);
        path = new PathDescription("For the more experienced walker, bring your hiking boots", 2.5);
        pathDescriptions.add(path);
        path = new PathDescription("Treacherous on ice, overall pleasant", 4.5);
        pathDescriptions.add(path);

    }

    //Passes HTTPS request by taking arguments, within the parameter
    // all are dynamic and used to get places

    public String buildUrl(double latitude, double longitude, String radius, String API_KEY) {

        StringBuilder urlString = new StringBuilder("api/place/search/json?");
        urlString.append("&location=");
        urlString.append(Double.toString(latitude));
        urlString.append(",");
        urlString.append(Double.toString(longitude));
        urlString.append("&radius=" + radius);
        urlString.append("&types=" + placeType.toLowerCase());
        urlString.append("&sensor=false&key=" + API_KEY);
        return urlString.toString();

    }


}