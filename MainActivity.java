package app.com.kamgar.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends android.support.v7.app.AppCompatActivity {
    Button Wifi_btn;
    String UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            UserID= null;
        } else {
            UserID= extras.getString("UserID");
        }

     }

    public void openWifiActivity(View v){

        Intent i = new Intent(getBaseContext(), Wifi_Activity.class);
        startActivity(i);
    }

    public void openAzaanActivity(View v){

        Intent i = new Intent(getBaseContext(), AzaanHome.class);
        startActivity(i);
    }

    public void openFriendsActivity(View v){

        Intent i = new Intent(getBaseContext(), Prefriends.class);
        i.putExtra("UserIDA", this.UserID);
        startActivity(i);
    }

    public void locationAware(View v){

        Intent i = new Intent(getBaseContext(), Premap.class);
        i.putExtra("UserIDA", UserID);
        startActivity(i);
    }

    public void shortestPath(View v){

        Intent i = new Intent(getBaseContext(), ShortestPath1.class);
        i.putExtra("UserIDA", UserID);
        startActivity(i);
    }

    public void shoppingList(View v){

        Intent i = new Intent(getBaseContext(), ShoppingList.class);
        i.putExtra("UserIDA", UserID);
        startActivity(i);
    }

    public void SharedList(View v){

        Intent i = new Intent(getBaseContext(), SharedList.class);
        i.putExtra("UserIDA", UserID);
        startActivity(i);
    }


    public void logOut(View v){

        Intent i = new Intent(getBaseContext(), LogOut.class);
        startActivity(i);
    }

}
