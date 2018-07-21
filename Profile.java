package app.com.kamgar.myapplication;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Profile extends android.support.v7.app.AppCompatActivity {
    static String userIDA;
    static String userIDB;
    TextView Name;
    TextView Phone;
    TextView Email;
    static String response;
    static boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        Name = (TextView) findViewById(R.id.NameB);
        Phone = (TextView) findViewById(R.id.PhoneB);
        Email = (TextView) findViewById(R.id.EmailB);


            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userIDA= null;
            } else {
                userIDA= extras.getString("UserIDB");
                String[] parts = userIDA.split("-");
                userIDA = parts[1];
                userIDB = parts[0];

            }

        try {
            new Profile.ProfileAsync().execute(userIDB);
            Thread.sleep(3000);
        } catch (Exception e) {
            Log.e("error", e.toString());
        }
        new Profile.GetDetailsAsync().execute(userIDB);

    }

    public void addFriend(View v){
        new Profile.ProfileAsync().execute(userIDA, userIDB);
    }

    ///////////////sub class
    public class ProfileAsync extends AsyncTask<String, String, String> {
        String fullString = "";

        @Override
        protected String doInBackground(String... params) {

            try {
                Log.e(params[0], ". . ");
                // http://localhost/location_aware/API/Friends.php?Action=1&Name=Faysal
                URL url = new URL("http://www.glamdivaz.com/locaware/api/AddFriend.php?Action=1&UserIDA=" + params[0] + "&UserIDB=" + params[1]);
                Log.e("Fetching...", url.toString());
                Log.e("Fetching...", ". . . . .. ");
                Log.e("Fetching 2...", ". . . . .. ");
                BufferedReader reader = null;
                try {

                    reader = new BufferedReader(new InputStreamReader(url.openStream()));
                } catch (Exception e) {
                    Log.e("Exeee", e + ". . . . .. ");
                }
                Log.e("Fetching...after", ". . . . .. ");
                Log.e("Decoding...", ". . . . .. ");
                String line = "";
                while ((line = reader.readLine()) != null) {
                    Log.e("fetch", line);
                    fullString += line;
                }
                Log.e("Total Fetch: ", fullString);
                reader.close();

                Profile.response = fullString;
                Profile.flag = true;
                return fullString;
            } catch (Exception e) {
                Log.e("ERRRUR", e.toString());

            }

            return "";

        }


        //@Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Profile.response = fullString;
            Profile.flag = true;


            try {

                JSONObject JSONresponse = new JSONObject(fullString);
                String success = JSONresponse.get("success").toString();
                String msg = JSONresponse.get("Message").toString();

                JSONObject data = new JSONObject(JSONresponse.get("data").toString());
                if(success.equals("1")){
                    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                }else{
                    Log.e("errror", msg);
                }

            } catch (Exception e){
                Log.e("error", e.toString());
            }

        }

    }

    public class GetDetailsAsync extends AsyncTask<String, String, String> {
        String fullString = "";

        @Override
        protected String doInBackground(String... params) {

            try {
                Log.e(params[0], ". . ");
                // http://localhost/location_aware/API/Friends.php?Action=1&Name=Faysal
                URL url = new URL("http://www.glamdivaz.com/locaware/api/AddFriend.php?Action=2&userIDB=" + params[0] + "");
                Log.e("Fetching...", url.toString());
                Log.e("Fetching...", ". . . . .. ");
                Log.e("Fetching 2...", ". . . . .. ");
                BufferedReader reader = null;
                try {

                    reader = new BufferedReader(new InputStreamReader(url.openStream()));
                } catch (Exception e) {
                    Log.e("Exeee", e + ". . . . .. ");
                }
                Log.e("Fetching...after", ". . . . .. ");
                Log.e("Decoding...", ". . . . .. ");
                String line = "";
                while ((line = reader.readLine()) != null) {
                    Log.e("fetch", line);
                    fullString += line;
                }
                Log.e("Total Fetch: ", fullString);
                reader.close();

                Profile.response = fullString;
                Profile.flag = true;
                return fullString;
            } catch (Exception e) {
                Log.e("ERRRUR", e.toString());

            }

            return "";

        }


        //@Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Profile.response = fullString;
            Profile.flag = true;


            try {

                JSONObject JSONresponse = new JSONObject(fullString);
                String success = JSONresponse.get("success").toString();
                String msg = JSONresponse.get("Message").toString();

                JSONObject data = new JSONObject(JSONresponse.get("data").toString());
                String NameG = data.get("Name").toString();
                String EmailG = data.get("Email").toString();
                String PhoneG = data.get("Phone").toString();

                if(success.equals("1")){
                    Name.setText(NameG);
                    Phone.setText(PhoneG);
                    Email.setText(EmailG);
                }else{
                    Log.e("errror", msg);
                }

            } catch (Exception e){
                Log.e("error", e.toString());
            }

        }

    }

}
