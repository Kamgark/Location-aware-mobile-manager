package app.com.kamgar.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Faysal Ahmed on 01-Jul-17.
 */

public class AzaanBCR extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("->>>>>>>>>>>>>>>",">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Toast.makeText(context, "AZAAN ALERT!!!!!", Toast.LENGTH_LONG).show();
        MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.azan1);
        mPlayer.start();

        //device silent
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audio.setRingerMode(0);
        Toast.makeText(context, "Device Silent!", Toast.LENGTH_LONG).show();
        Calendar c = Calendar.getInstance();
        int Hour = c.get(Calendar.HOUR);
        int Minutes = c.get(Calendar.MINUTE);
        int Day = c.get(Calendar.DAY_OF_WEEK);

        if(Minutes>55){
            Hour++;
        }

        Calendar ca = Calendar.getInstance();
        Log.e("Minutes before:", Hour+":"+Minutes+"");
        Minutes+=5;
        Log.e("Minutes after:", Hour+":"+Minutes+"");
        ca.set(Calendar.HOUR_OF_DAY, Hour);
        ca.set(Calendar.MINUTE, Minutes);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        ca.set(Calendar.DAY_OF_WEEK, Day);

        long Seconds = ca.getTimeInMillis();
        //Toast.makeText(context, getDate(Seconds, "dd/MM/yyyy H:mm:ss.SSS") , Toast.LENGTH_LONG).show();
        Intent i = new Intent(context, AzanSilentBCR.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 99, i, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, Seconds, pendingIntent);


    }
}
