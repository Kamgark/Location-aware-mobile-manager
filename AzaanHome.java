package app.com.kamgar.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Faysal Ahmed on 01-Jul-17.
 */

public class AzaanHome extends AppCompatActivity {
    public static String RespnoseData="Empty";
    TextView data;

    public static boolean fetched = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.azaan_layout);
        data = (TextView) findViewById(R.id.textView6);


    }

    public void FetchNewData(View v){

        String geoInfo = "";
        Log.e("->>>>>>>>>>>>>>>","999999999999999999");
        if(isNetworkAvailable()){

            Toast.makeText(this, "Fetching data. Please Wait." , Toast.LENGTH_LONG).show();

            Log.e("ASYNC", "ASYNC CALLED");
            new AzaanAsync().execute();
            String ResultData = "Could not connect";
            try {
                Thread.sleep(6000);
            }catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

            }

            try {
                if(this.RespnoseData=="Empty"){
                    data.setText("Could not fetch");
                }else{
                    JSONObject TimingMainObj = new JSONObject(this.RespnoseData);
                    JSONObject TimingdataObject = new JSONObject(TimingMainObj.get("data").toString());
                    JSONObject FinalTimingdataObject = new JSONObject(TimingdataObject.get("timings").toString());
                    String Fajr = FinalTimingdataObject.get("Fajr").toString();
                    String Dhuhr = FinalTimingdataObject.get("Dhuhr").toString();
                    String Asr = FinalTimingdataObject.get("Asr").toString();
                    String Maghrib = FinalTimingdataObject.get("Maghrib").toString();
                    String Isha = FinalTimingdataObject.get("Isha").toString();
                    Log.e("->>>>>>>>>>>>>>>","setting");
                    data.setText("Fajr: "+Fajr+"\n Dhuhr: "+Dhuhr+"\n Asr: "+Asr+"\n Maghrib: "+Maghrib+"\n Isha: "+Isha);
                    Log.e("->>>>>>>>>>>>>>>","done");

                    Log.e("->>>>>>>>>>>>>>>","SETTING DATA IN ARRAY");
                    String[] timings = new String[5];
                    Log.e("->>>>>>>>>>>>>>>","asdsadasdsadsa");
                    //timings[0] = "18:42";
                    timings[0] = Fajr;
                    timings[1] = Dhuhr;
                    timings[2] = Asr;

                    timings[3] = Maghrib;
                    timings[4] = Isha;


                    Log.e("->>>>>>>>>>>>>>>","Arming alarms");
                    for (String item : timings ){
                        String[] timeArray = item.split(":");
                        String Hour = timeArray[0];
                        String Minute = timeArray[1];

                        String weekDay;

                        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
                        //today day
                        java.util.Calendar calendar = java.util.Calendar.getInstance();

                        weekDay = dayFormat.format(calendar.getTime());

                        int DayNum = 3;

                        switch(weekDay) {
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

                        java.util.Calendar calNow = java.util.Calendar.getInstance();

                        java.util.Calendar mCalendar = java.util.Calendar.getInstance();

                        calNow.set(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(Hour));
                        calNow.set(java.util.Calendar.MINUTE, Integer.parseInt(Minute));
                        calNow.set(java.util.Calendar.SECOND, 0);
                        calNow.set(java.util.Calendar.MILLISECOND, 0);
                        calNow.set(java.util.Calendar.DAY_OF_WEEK, DayNum);


                        java.util.Calendar NowTime = java.util.Calendar.getInstance();

                        long diff = NowTime.getTimeInMillis() - calNow.getTimeInMillis();

                        Log.e("->>>>>>>>>>>>","DIFFERENCE : "+diff);
                        long check = 0;

                        if(diff>check){
                            continue;
                        }else{
                            Log.e("->>>>>>>>>>>>","ELSE RUN : "+DayNum);
                            calNow.set(java.util.Calendar.DAY_OF_WEEK, DayNum);
                            long Seconds = calNow.getTimeInMillis();

                            Intent intent = new Intent(getBaseContext(), AzaanBCR.class);
                            int reqCode = Integer.parseInt(Hour)+Integer.parseInt(Minute);
                            intent.putExtra("ReqCode", reqCode);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, reqCode , intent, 0);
                            AlarmManager alarmManager = (AlarmManager)getSystemService(this.ALARM_SERVICE);

                            //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, Seconds, alarmManager.INTERVAL_DAY * 7, pendingIntent);
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, Seconds, pendingIntent);

                            Log.e("->>>>>>>>>>>>>>>","->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Arming alarms in loop");
                        }




                    }
                    Log.e("->>>>>>>>>>>>>>>","Going home");
                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("Status", true);
                    //startActivity(i);

                }

            }catch (Exception e){
                e.fillInStackTrace();
            }



        }else{

            Toast.makeText(this, "No Internet Connection!!" , Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager  connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

}
