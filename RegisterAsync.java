package app.com.kamgar.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class RegisterAsync extends AsyncTask<String, String, String> {
    String fullString = "";
@Override
protected String doInBackground(String... params) {

        try {
            Log.e(params[0],". . ");
            Log.e(params[1],". . ");
            Log.e(params[2],". . ");
            Log.e(params[3],". . ");
            URL url = new URL("http://glamdivaz.com/locaware/api/user.php?Action=1&Name="+params[0]+"&Email="+params[1]+"&Password="+params[2]+"&Phone="+params[3]);
            Log.e("Fetching...", url.toString());
            Log.e("Fetching...", ". . . . .. ");
            Log.e("Fetching 2...", ". . . . .. ");
            BufferedReader reader=null;
            try{

                reader = new BufferedReader(new InputStreamReader(url.openStream()));
            }catch (Exception e){
                Log.e("Exeee", e+". . . . .. ");
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

            app.com.kamgar.myapplication.Register.response = fullString;
            app.com.kamgar.myapplication.Register.flag = true;
            return fullString;
        }catch (Exception e){
             Log.e("ERRRUR", e.toString());

        }

        return "";

        }


    //@Override
    protected void onPostExecute(JSONObject result) {
        // TODO Auto-generated method stub
        app.com.kamgar.myapplication.Register.response = fullString;
        app.com.kamgar.myapplication.Register.flag = true;
    }

 }