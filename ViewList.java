package app.com.kamgar.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by bilalbokharee on 12/23/2017.
 */

public class ViewList extends android.support.v7.app.AppCompatActivity {
    String ListID;
    String allLists = null;
    EditText showData;
    String updater;
    TableLayout tableList;
    String url;
    String UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewlist);
        showData = (EditText) findViewById(R.id.edit_text);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            ListID = null;
        } else {
            ListID = extras.getString("ListID");
            UserID = extras.getString("UserID");
            Log.e("Fetching.......", UserID);
        }
        new ViewListAsync().execute(ListID);
    }

    public void SetListsToMainClass(String data){
        this.allLists = data;
        Log.e("in main:::::::::::::::", data.toString());
        showData.setText(data);
        setDatainTable();
    }
    public void setDatainTable(){
        try{
                JSONObject json_data = new JSONObject(allLists);

                showData.setText(json_data.getString("Items").toString());
        }catch (Exception e){
            Log.e("EXCEPtion->>>>", "ROLA 2: "+e.toString());
        }
    }

    public void updateList(View v){
        this.updater = showData.getText().toString();
        new UpdateListAsync().execute(updater);

       /* try {
            String query = URLEncoder.encode("Action=2&ShoppingListID="+ListID+"&Items="+updater, "utf-8");
            url = "http://www.glamdivaz.com/locaware/api/ShoppingList.php?" + query;
        }catch(UnsupportedEncodingException e){
            Log.e("Exception", e.toString());
        }*/
    }

    public void shareList(View v){
        Intent i = new Intent(getBaseContext(), ShareWith.class);
        i.putExtra("UserID", UserID);
        i.putExtra("ListID", ListID);
        startActivity(i);
    }

    ///////////////sub class
    public class ViewListAsync extends AsyncTask<String, String, String> {
        String fullString = "";

        @Override
        protected String doInBackground(String... params) {

            try {
                Log.e(params[0], ". . ");
                // http://localhost/location_aware/API/Friends.php?Action=1&Name=Faysal
                URL url = new URL("http://www.glamdivaz.com/locaware/api/ShoppingList.php?Action=2&ShoppingListID=" + params[0] + "");
                Log.e("Fetching...", url.toString());
                Log.e("Fetching...", ". . . . .. ");
                Log.e("Fetching 2...", ". . . . .. ");
                BufferedReader reader = null;
                try{
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

                Friends.response = fullString;
                Friends.flag = true;
                return fullString;
            } catch (Exception e) {
                Log.e("ERRRUR", e.toString());

            }

            return "";

        }


        //@Override
        protected void onPostExecute(String s) {
            // TODO Auto-generated method stub
            super.onPostExecute(s);
            Log.e("::OnPreExecute", "Method invoked");
            Prefriends.response = fullString;
            Prefriends.flag = true;
            String success = "";
            String msg = "";
            String data = "";

            try {
                JSONObject JSONresponse = new JSONObject(fullString);
                success = JSONresponse.get("success").toString();
                msg = JSONresponse.get("Message").toString();
                data = JSONresponse.get("data").toString();
            } catch (Exception e){
                Log.e("error", e.toString());
            }
            if(success.equals("1")){
                Log.e("inside IF ===========","bich");
                SetListsToMainClass(data);
            }else{
                Log.e("Error", "Failed to fetch!");
            }

        }

    }

    ///////////////sub class
    public class UpdateListAsync extends AsyncTask<String, String, String> {
        String fullString = "";

        @Override
        protected String doInBackground(String... params) {

            try {
                Log.e(params[0], ". . ");
                // http://localhost/location_aware/API/Friends.php?Action=1&Name=Faysal
                String query = URLEncoder.encode(params[0], "utf-8");
                URL url = new URL("http://www.glamdivaz.com/locaware/api/ShoppingList.php?Action=1&ShoppingListID="+ListID+"&Items=" +query);
                Log.e("Fetching...", url.toString());
                Log.e("Fetching...", ". . . . .. ");
                Log.e("Fetching 2...", ". . . . .. ");
                BufferedReader reader = null;
                try{
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

                Friends.response = fullString;
                Friends.flag = true;
                return fullString;
            } catch (Exception e) {
                Log.e("ERRRUR", e.toString());

            }

            return "";

        }


        //@Override
        protected void onPostExecute(String s) {
            // TODO Auto-generated method stub
            super.onPostExecute(s);
            Log.e("::OnPreExecute", "Method invoked");
            Prefriends.response = fullString;
            Prefriends.flag = true;
            String success = "";
            String msg = "";
            String data = "";

            try {
                JSONObject JSONresponse = new JSONObject(fullString);
                success = JSONresponse.get("success").toString();
                msg = JSONresponse.get("Message").toString();
                data = JSONresponse.get("data").toString();
            } catch (Exception e){
                Log.e("error", e.toString());
            }
            if(success.equals("1")){
                Log.e("inside IF ===========","bich");
                SetListsToMainClass(data);
            }else{
                Log.e("Error", "Failed to fetch!");
            }

        }

    }

}