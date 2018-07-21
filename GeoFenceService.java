package app.com.kamgar.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

/**
 * Created by bilalbokharee on 9/29/2017.
 */

public class GeoFenceService extends IntentService {
    public static final String TAG = "GeoFence Service";
    public GeoFenceService(){
        super(TAG);
    }
    protected void onHandleIntent(Intent intent){
        GeofencingEvent event = GeofencingEvent.fromIntent(intent);
        int transition = event.getGeofenceTransition();
        List<Geofence> geofences = event.getTriggeringGeofences();
        Geofence geofence = geofences.get(0);
        String requestId = geofence.getRequestId();
        if(transition == Geofence.GEOFENCE_TRANSITION_ENTER){
            Log.d(TAG,String.format("GEOFENCE_TRANSITION_ENTER on %s",requestId));
        }
        else if(transition == Geofence.GEOFENCE_TRANSITION_EXIT){
            Log.d(TAG,String.format("GEOFENCE_TRANSITION_EXIT  on %s",requestId));
        }
    }
}
