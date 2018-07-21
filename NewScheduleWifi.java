package app.com.kamgar.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by Faysal Ahmed on 23-Jun-17.
 */

public class NewScheduleWifi extends AppCompatActivity {
    ToggleButton tb;
    Spinner sp;
    TimePicker tp;
    DBHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_wifi_schedule);

        tb = (ToggleButton) findViewById(R.id.toggleButton);
        sp = (Spinner) findViewById(R.id.spinner);
        tp = (TimePicker) findViewById(R.id.timePicker);
    }

    public void SaveData(View v){
        int WifiStatus = 0;
        String Day = sp.getSelectedItem().toString();
        int Hour = tp.getCurrentHour();
        int Minute = tp.getCurrentMinute();
        int DayNum = 1;

        switch(Day) {
            case "Monday":
                DayNum = 2;
                break;
            case "Tuesday":
                DayNum = 3;
                break;
            case "Wednesday":
                DayNum = 4;
                break;
            case "Thursday":
                DayNum = 5;
                break;
            case "Friday":
                DayNum = 6;
                break;
            case "Saturday":
                DayNum = 7;
                break;
            case "Sunday":
                DayNum = 1;
                break;
        }

        if(tb.isChecked()){
            WifiStatus = 1;
        }else{
            WifiStatus = 0;
        }

        Toast.makeText(this, Hour+":"+Minute+" ,"+Day , Toast.LENGTH_SHORT).show();

        mydb = new DBHelper(this);
        int reqCode =(Hour+Minute+DayNum);
        mydb.insertWifi(Day, Hour, Minute, WifiStatus, reqCode);

        //code for alarm
        Calendar calNow = Calendar.getInstance();

        calNow.set(Calendar.HOUR_OF_DAY, Hour);
        calNow.set(Calendar.MINUTE, Minute);
        calNow.set(Calendar.SECOND, 0);
        calNow.set(Calendar.MILLISECOND, 0);
        calNow.set(Calendar.DAY_OF_WEEK, DayNum);


        long Seconds = calNow.getTimeInMillis();

        Toast.makeText(this, getDate(Seconds, "dd/MM/yyyy H:mm:ss.SSS")+"Wifi:"+WifiStatus , Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getBaseContext(), WifiManagerBC.class);
        intent.putExtra("status", WifiStatus+"");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, reqCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(this.ALARM_SERVICE);

        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Seconds, alarmManager.INTERVAL_DAY * 7, pendingIntent);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, Seconds, pendingIntent);

        //code ends

        Intent i = new Intent(this, Wifi_Activity.class);
        i.putExtra("Status", WifiStatus);
        startActivity(i);

    }


    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

}
