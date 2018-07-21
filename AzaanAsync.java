package app.com.kamgar.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;



public class  AzaanAsync extends AsyncTask<String, Void, String>{

    @Override
    protected String doInBackground(String... params) {


        String fullString = "";

        try {

            URL url = new URL("http://api.aladhan.com/timingsByCity?city=Abbottabad&country=PK&method=2");
            Log.e("Fetching...", ". . . . .. ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            Log.e("Decoding...", ". . . . .. ");
            String line;
            while ((line = reader.readLine()) != null) {
                Log.e("fetch", line);
                fullString += line;
            }
            Log.e("Total Fetch: ", fullString);
            reader.close();

                app.com.kamgar.myapplication.AzaanHome.RespnoseData = fullString;
                app.com.kamgar.myapplication.AzaanHome.fetched = true;


            return fullString;
        }catch (Exception e){
            Log.e("ERRRUR", e.toString());

        }

        return "";

    }







}
