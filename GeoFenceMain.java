package app.com.kamgar.myapplication;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by bilalbokharee on 9/29/2017.
 */

public class GeoFenceMain extends android.support.v7.app.AppCompatActivity {
    public static double lati;
    public static double longi;
    private ReactiveLocationProvider reactiveLocationProvider;

    public GeoFenceMain(){
        reactiveLocationProvider = new ReactiveLocationProvider(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reactiveLocationProvider = new ReactiveLocationProvider(this);
    }

    private PendingIntent createNotificationBroadcastPendingIntent(){
        Log.e("before","Intent intent");

        Log.e("before",".getBroadcast");
        return PendingIntent.getBroadcast(this, 0, new Intent(this, GeofenceBroadcastReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);

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
                            //Toast toast = Toast.makeText(getApplicationContext(), "Geofence added, success: " + addGeofenceResult.isSuccess(), Toast.LENGTH_SHORT);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Error adding geofence.", Toast.LENGTH_SHORT);
                            Log.e("mamlaat", "failure adding geofence.");
                        }
                    });
        }catch (Exception  e){
            Log.e("ExpetUNININ", e.toString());
        }
    }



    private GeofencingRequest createGeofencingRequest() {
        try {
            double longitude = this.longi;
            double latitude = this.lati;
            float radius = 1;
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
            return gfr;

        } catch (NumberFormatException ex) {
            Toast toast = Toast.makeText(getApplicationContext(), "Error parsing input.", Toast.LENGTH_SHORT);
            return null;
        }
    }

}
