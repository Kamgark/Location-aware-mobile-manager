package app.com.kamgar.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by bilalbokharee on 9/30/2017.
 */

public class Premap extends android.support.v7.app.AppCompatActivity {
    DBHelper mydb;
    String UserIDA = "";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premap);
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            UserIDA= null;
        } else {
            UserIDA= extras.getString("UserIDA");
        }
        mydb = new DBHelper(this);
        LinearLayout LinearL;
        int rows = mydb.numberOfRows("LatLong");

        int foo = Integer.parseInt(UserIDA);
        ArrayList<LatlongData> a = mydb.getlatlongByID(foo);
        LinearL = (LinearLayout) findViewById(R.id.layout_buttons);
        String data = "";
        if(rows>0){
            for(LatlongData item : a){
                LinearLayout row = new LinearLayout(this);
                row.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.FILL_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                data = "";
                //data+=item.id+" - ";Longitude
                data+=item.Latitude+" - ";
                data+=item.Longitude+":";
                Button btnTag = new Button(this);
                btnTag.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.FILL_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                btnTag.setBackgroundColor(Color.WHITE);
                btnTag.setTextColor(Color.BLACK);

                btnTag.setText(data);
               // btnTag.setOnClickListener(handleOnClick(btnTag, item.id, item.ReqCode));
                row.addView(btnTag);

                LinearL.addView(row);
            }
        }else{
            data = "No Lat Long set.";
        }
    }

    public void addNewLocation(View v){

        Intent i = new Intent(getBaseContext(), MapsActivity.class);
        i.putExtra("UserIDA", UserIDA);
        startActivity(i);
    }

}
