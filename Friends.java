package app.com.kamgar.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by bilalbokharee on 9/14/2017.
 */

public class Friends extends android.support.v7.app.AppCompatActivity {
    EditText name;
    TextView data;
    static String response;
    static String UserIDA;
    static String UserIDB;
    static boolean flag = false;
    String allusers = null;
    TableLayout tableList;
    Button[] button;
    String[] FriendsID_GLO;
    Intent iintents[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);
        tableList = (TableLayout) findViewById(R.id.TableList);
        name = (EditText) findViewById(R.id.namee);
        data = (TextView) findViewById(R.id.textView6);
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            UserIDA= null;
        } else {
            UserIDA= extras.getString("UserIDA");
        }

    }



    public void SearchFriends(View v) {
        String Name = this.name.getText().toString();
        if (Name.equals("")) {
            Toast.makeText(this, "All fields required.", Toast.LENGTH_LONG).show();
        } else {
            try {
                Log.e("Name: ", Name.toString());
                new Friends.FriendsAsync().execute(Name);
                Thread.sleep(2000);
            } catch (Exception e) {
                Log.e("error", e.toString());
            }

        }
    }

    public void SetFriendsToMainClass(String data){
        this.allusers = data;
        Log.e("in main:::::::::::::::", data.toString());
        setDatainTable();
    }

    public void setDatainTable(){
        try {
            JSONArray jArray = new JSONArray(allusers);
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
                btn.setText("View");
                Log.e("TAG", "ithay 2");
                final int index = i;
                iintents[index] = new Intent(this, Profile.class);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public  void onClick(View v) {
                        Log.e("TAG", "The index is" + FriendsID_GLO[index]);

                        iintents[index].putExtra("UserIDB", FriendsID_GLO[index]+"-"+UserIDA);
                        startActivity(iintents[index]);
                    }
                });
                Log.e("TAG", "ithay 3");

                FriendsID_GLO[index] = json_data.getString("UserID").toString();
                Log.e("TAG", "ithay 3.5");

                Name.setText(json_data.getString("Name").toString());
                StudentID.setText(json_data.getString("UserID").toString());

                TableRow trow = new TableRow(this);

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


    public void Profile(View v){
        Intent i = new Intent(Friends.this, Profile.class);
        i.putExtra("UserIDB", UserIDB);
        startActivity(i);
    }
    ///////////////sub class
    public class FriendsAsync extends AsyncTask<String, String, String> {
        String fullString = "";

        @Override
        protected String doInBackground(String... params) {

            try {
                Log.e(params[0], ". . ");
               // http://localhost/location_aware/API/Friends.php?Action=1&Name=Faysal
                URL url = new URL("http://www.glamdivaz.com/locaware/api/Friends.php?Action=1&Name=" + params[0] + "");
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
            Friends.response = fullString;
            Friends.flag = true;
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
}
