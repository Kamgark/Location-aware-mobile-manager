package app.com.kamgar.myapplication;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public double lati;
    public double longi;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker marker;
    private LatLng mallPos;
    String UserIDA;
    DBHelper mydb;
    private Subscription lastKnownLocationSubscription;
    private ReactiveLocationProvider reactiveLocationProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            UserIDA= null;
        } else {
            UserIDA= extras.getString("UserIDA");
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        reactiveLocationProvider = new ReactiveLocationProvider(this);
        //getLocation();
    }
    /*public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        } else {

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

        }
    }*/
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this case, we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device.
     * This method will only be triggered once the user has installed
     Google Play services and returned to the app.
     */
    private PendingIntent createNotificationBroadcastPendingIntent(){
        Log.e("before","Intent intent");
        //Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        Log.e("before",".getBroadcast");
        return PendingIntent.getBroadcast(this, 0, new Intent(this, GeofenceBroadcastReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        //return PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Intent intent = new Intent(this, GeoFenceService.class);
        //PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //return pendingIntent;
    }
    public void addGeofence() {
        Log.e("INSIDE AddGeoF", "=>>>>>>>>>>>>>>>>>>>");
        final GeofencingRequest geofencingRequest = createGeofencingRequest();
        if (geofencingRequest == null) return;

        //final PendingIntent pendingIntent = createNotificationBroadcastPendingIntent();
        Log.e("Before","method call");
        //final PendingIntent pendingIntent = createNotificationBroadcastPendingIntent();

        Intent jigar = new Intent(this, GeofenceBroadcastReceiver.class);
        Log.e("after","this");
        try {
            final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 19, jigar, PendingIntent.FLAG_UPDATE_CURRENT);

            Log.e("After", "method call");
            reactiveLocationProvider
                    .removeGeofences(pendingIntent)
                    .flatMap(new Func1<Status, Observable<Status>>() {
                        @Override
                        public Observable<Status> call(Status pendingIntentRemoveGeofenceResult) {
                            return reactiveLocationProvider.addGeofences(pendingIntent, geofencingRequest);
                        }
                    })
                    .subscribe(new Action1<Status>() {
                        @Override
                        public void call(Status addGeofenceResult) {
                            Log.e("mamlaat", "Success adding geofence.");

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {

                            Log.e("mamlaat", "failure adding geofence.");
                        }
                    });
            Toast.makeText(this, "GeoFence Added!.", Toast.LENGTH_LONG).show();
        }catch (Exception  e){
            Log.e("ExpetUNININ", e.toString());
        }
    }
    private GeofencingRequest createGeofencingRequest() {
        try {
            double longitude = this.longi;
            double latitude = this.lati;
            float radius = 30; //in meters
            Log.e("Successfully added ","in class variables!");
            Geofence geofence = new Geofence.Builder()
                    .setRequestId("GEOFENCE")
                    .setCircularRegion(latitude, longitude, radius)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build();
            Log.e("Try block executed: ","createGeofencingRequest()");
            // Toast toast = Toast.makeText(this, "GeoFence Added!", Toast.LENGTH_LONG);

            GeofencingRequest gfr = new GeofencingRequest.Builder().addGeofence(geofence).build();
            mydb = new DBHelper(this);
            mydb.insertlatlong(latitude, longitude, Integer.parseInt(UserIDA));
            Log.e("Database-->>>>","inserted");
            return gfr;

        } catch (NumberFormatException ex) {
            Toast toast = Toast.makeText(getApplicationContext(), "Error parsing input.", Toast.LENGTH_SHORT);
            return null;
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng atd = new LatLng(34.1688, 73.2215);
        marker = mMap.addMarker(new
                MarkerOptions().position(atd).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(atd, 13));
        //mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
          //  @Override
           // public void onMapClick(LatLng position) {
            //    Log.e(position.latitude+" : "+position.longitude, "..");
           // }
        //});
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                if (marker != null) {
                    marker.remove();
                }
                mallPos = point;
                new AlertDialog.Builder(MapsActivity.this)
                        .setMessage("Do you want to add GeoFence around this point?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                marker = mMap.addMarker(new MarkerOptions().position(mallPos).title("Custom location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                                lati = mallPos.latitude;
                                longi = mallPos.longitude;
                                Log.e("Calling-=>", "add geo fence method");
                                addGeofence();
                                Log.e("Calling-=>", "AFTURRR geo fence method");
                                //mydb.insertlatlong(mallPos.latitude, mallPos.longitude, 1);
                                Log.e(mallPos.latitude+", "+mallPos.longitude," entered in database!");
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();

                //marker = mMap.addMarker(new MarkerOptions().position(point).title("Custom location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                Log.e(point.latitude+" : "+point.longitude, "..");
            }
        });

    }
}