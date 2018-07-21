package app.com.kamgar.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class ShareWith extends android.support.v7.app.AppCompatActivity {
    String UserIDA;
    String ListID;
    String allFriends = null;
    Button[] button;
    String[] FriendsID_GLO;
    Intent iintents[];
    LinearLayout LinearL;
    TableLayout tableList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharewith);
        tableList = (TableLayout) findViewById(R.id.TableList);
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            UserIDA= null;
        } else {
            UserIDA= extras.getString("UserID");
            ListID= extras.getString("ListID");

        }
        new ShareWithAsync().execute(UserIDA);
       /* LinearLayout LinearL;

        int foo = Integer.parseInt(UserIDA);
        LinearL = (LinearLayout) findViewById(R.id.layout_buttons);
        String data = "";
           // for(LatlongData item : a){
                LinearLayout row = new LinearLayout(this);
                row.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.FILL_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                data = "";
                //data+=item.id+" - ";Longitude
             //   data+=item.Latitude+" - ";
             //   data+=item.Longitude+":";


                Button btnTag = new Button(this);
                btnTag.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.FILL_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                btnTag.setBackgroundColor(Color.WHITE);
                btnTag.setTextColor(Color.BLACK);

                btnTag.setText(data);
                //btnTag.setOnClickListener(handleOnClick(btnTag, ));
                row.addView(btnTag);

                LinearL.addView(row);

//            }*/
        }

    public void SetFriendsToMainClass(String data){
        this.allFriends = data;
        Log.e("in main:::::::::::::::", data.toString());
        setDatainTable();
    }

    public void setDatainTable(){
        try {
            JSONArray jArray = new JSONArray(allFriends);
            Log.e("2 TAG", "ithay 1");
            FriendsID_GLO = new String[jArray.length()+1];
            iintents = new Intent[jArray.length()+1];


            Log.e("3 TAG", "ithay 1");
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                Log.e("TAG", "ithay 1");
                TextView StudentID = new TextView(this);
                TextView Name = new TextView(this);
                //Button btn = new Button(this);
                Button btn = new Button(this);
                btn.setId(i+1);
                btn.setText("Share");
                Log.e("TAG", "ithay 2");
                final int index = i;
                //iintents[index] = new Intent(this, Profile.class);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public  void onClick(View v) {
                        Log.e("TAG", "The index is" + FriendsID_GLO[index]);
                        new ShareWith.ShareWithAsync2().execute(FriendsID_GLO[index],ListID );
                        //iintents[index].putExtra("UserIDB", FriendsID_GLO[index]+"-"+UserIDA);
                        //startActivity(iintents[index]);
                    }
                });
                Log.e("TAG", "ithay 3");

                FriendsID_GLO[index] = json_data.getString("UserID").toString();
                Log.e("TAG", "ithay 3.5");

                Name.setText(json_data.getString("Name").toString());
                StudentID.setText(json_data.getString("UserID").toString());

                TableRow trow = new TableRow(this);
                //LinearL = (LinearLayout) findViewById(R.id.layout_buttons);
                Log.e("TAG", "ithay 3.5.1");

                trow.addView(StudentID);
                trow.addView(Name);
                trow.addView(btn);

                tableList.addView(trow,i);
                Log.e("TAG", "ithay 4");

            }
        }catch (Exception e){
            Log.e("EXCEPtion->>>>", "ROLA 2: "+e.toString());
        }
    }



///////////////sub class
public class ShareWithAsync extends AsyncTask<String, String, String> {
    String fullString = "";

    @Override
    protected String doInBackground(String... params) {

        try {
            Log.e(params[0], ". . ");
            // http://localhost/location_aware/API/Friends.php?Action=1&Name=Faysal
            URL url = new URL("http://www.glamdivaz.com/locaware/api/Friends.php?Action=2&UserID=" + params[0] + "");
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
                SetFriendsToMainClass(data);
            }else{
                Log.e("Error", "Failed to fetch!");
            }

        }

    }

    ///////////////sub class
    public class ShareWithAsync2 extends AsyncTask<String, String, String> {
        String fullString = "";

        @Override
        protected String doInBackground(String... params) {

            try {
                Log.e(params[0], ". . ");
                // http://localhost/location_aware/API/Friends.php?Action=1&Name=Faysal
                URL url = new URL("http://glamdivaz.com/locaware/api/ShoppingList.php?Action=6&UserID=" + params[0] + "&ShoppingListID="+params[1]);
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
                Toast.makeText(getApplicationContext(), "Shopping List Shared.", Toast.LENGTH_SHORT).show();
            }else{
                Log.e("Error", "Failed to fetch!");
            }

        }

    }
}
