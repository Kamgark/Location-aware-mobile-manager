package app.com.kamgar.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat.LayoutParams;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Faysal Ahmed on 23-Jun-17.
 */

public class Wifi_Activity extends AppCompatActivity {
    DBHelper mydb;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_layout);

        mydb = new DBHelper(this);
        LinearLayout LinearL;
        int rows = mydb.numberOfRows("Wifi");


        ArrayList<WifiScheduleData> a = mydb.getAllWifiRows();
        LinearL = (LinearLayout) findViewById(R.id.layout_buttons);
        String data = "";
        if(rows>0){
            for(WifiScheduleData item : a){
                LinearLayout row = new LinearLayout(this);
                row.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                data = "";
                //data+=item.id+" - ";
                data+=item.DayName+" - ";
                data+=item.Hours+":";
                data+=item.Minutes+" - ";
                if(item.WifiStatus==1){
                    data+="On";
                }else if(item.WifiStatus==0){
                    data+="Off";
                }
                Button btnTag = new Button(this);
                btnTag.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                btnTag.setBackgroundColor(Color.RED);
                btnTag.setTextColor(Color.WHITE);

                btnTag.setText(data);
                btnTag.setOnClickListener(handleOnClick(btnTag, item.id, item.ReqCode));
                row.addView(btnTag);

                LinearL.addView(row);
            }
        }else{
            data = "No schedules set.";
        }

        //tv = (TextView) findViewById(R.id.textView2);
        //tv.setText(data);
    }

    public void AddSchedule(View view){
        Intent i = new Intent(this, NewScheduleWifi.class);
        startActivity(i);
    }

    View.OnClickListener handleOnClick(final Button button, final int id, final int ReqCode) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                int a = ReqCode;
                CancelAlarm(a);
                 mydb.deleteWifiRow(id);
                finish();
                startActivity(getIntent());
            }
        };
    }

    public void CancelAlarm(int ReqCode){
        Intent intent = new Intent(this, Wifi_Activity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, ReqCode, intent ,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);


    }
}
