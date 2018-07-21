package app.com.kamgar.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

/**
 * Created by Faysal Ahmed on 02-Jul-17.
 */

public class AzanSilentBCR  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audio.setRingerMode(1);
        //Toast.makeText(context, "Ringer mode enabled.", Toast.LENGTH_LONG).show();

    }
}
