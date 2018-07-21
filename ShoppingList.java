package app.com.kamgar.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by bilalbokharee on 12/20/2017.
 */

public class ShoppingList extends android.support.v7.app.AppCompatActivity{
    String UserIDA;
    String allLists = null;
    Button[] button;
    String[] FriendsID_GLO;
    Intent iintents[];
    TableLayout tableList;
    private String m_Text = "";
    final Context context = this;
    private Button buttonn;
    private EditText result;
    public String call;
   String mamlaat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppinglist);
        tableList = (TableLayout) findViewById(R.id.TableList);
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            UserIDA= null;
        } else {
            UserIDA= extras.getString("UserIDA");
        }
        new ShoppingListAsync().execute(UserIDA);
        // components from main.xml
        buttonn = (Button) findViewById(R.id.button5);

        // add button listener
        buttonn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Create",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        mamlaat = userInput.getText().toString();
                                        new AddShoppingListAsync().execute(UserIDA, mamlaat);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });
    }
    public void SetListsToMainClass(String data){
        this.allLists = data;
        Log.e("in main:::::::::::::::", data.toString());
        setDatainTable();
    }

    public void setDatainTable(){
        try {
            JSONArray jArray = new JSONArray(allLists);
            Log.e("2 TAG", "ithay 1");
            FriendsID_GLO = new String[jArray.length()+1];
            iintents = new Intent[jArray.length()+1];


            Log.e("3 TAG", "ithay 1");
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                Log.e("TAG", "ithay 1");
                TextView FriendID = new TextView(this);
                TextView Name = new TextView(this);
                //Button btn = new Button(this);
                Button btn = new Button(this);
                Button btn2 = new Button(this);
                btn.setId(i+1);
                btn2.setId(i+1);
                btn.setText("View");
                btn2.setText("Delete");
                Log.e("TAG", "ithay 2");
                final int index = i;
                iintents[index] = new Intent(this, ViewList.class);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public  void onClick(View v) {
                        Log.e("TAG", "The index is" + FriendsID_GLO[index]);

                        iintents[index].putExtra("ListID", FriendsID_GLO[index]);
                        iintents[index].putExtra("UserID", UserIDA);

                        startActivity(iintents[index]);
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public  void onClick(View v) {
                        Log.e("TAG", "Del invoked" + FriendsID_GLO[index]);
                        new delAsync().execute(FriendsID_GLO[index]);
                    }
                });
                Log.e("TAG", "ithay 3");

                FriendsID_GLO[index] = json_data.getString("ShoppingListID").toString();
                Log.e("TAG", "ithay 3.5");

                Name.setText(json_data.getString("ListName").toString());
                FriendID.setText(json_data.getString("ShoppingListID").toString());

                TableRow trow = new TableRow(this);

                Log.e("TAG", "ithay 3.5.1");

                trow.addView(FriendID);
                trow.addView(Name);
                trow.addView(btn);
                trow.addView(btn2);

                tableList.addView(trow,i);
                Log.e("TAG", "ithay 4");

            }
        }catch (Exception e){
            Log.e("EXCEPtion->>>>", "ROLA 2: "+e.toString());
        }
    }
    ///////////////sub class
    public class ShoppingListAsync extends AsyncTask<String, String, String> {
        String fullString = "";

        @Override
        protected String doInBackground(String... params) {

            try {
                Log.e(params[0], ". . ");
                // http://localhost/location_aware/API/Friends.php?Action=1&Name=Faysal
                URL url = new URL("http://www.glamdivaz.com/locaware/api/ShoppingList.php?Action=3&UserID=" + params[0] + "");
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
                SetListsToMainClass(data);
            }else{
                Log.e("Error", "Failed to fetch!");
            }

        }

    }
    ///////////////////////////
    ///////////////sub class
    public class AddShoppingListAsync extends AsyncTask<String, String, String> {
        String fullString = "";

        @Override
        protected String doInBackground(String... params) {

            try {
                Log.e(params[0], ". . ");
                // http://localhost/location_aware/API/Friends.php?Action=1&Name=Faysal
                URL url = new URL("http://www.glamdivaz.com/locaware/api/ShoppingList.php?Action=0&UserID=" + params[0] + "&ListName=" + params[1]);
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
                SetListsToMainClass(data);
            }else{
                Log.e("Error", "Failed to fetch!");
            }

        }

    }

    ///////////////sub class
    public class delAsync extends AsyncTask<String, String, String> {
        String fullString = "";

        @Override
        protected String doInBackground(String... params) {

            try {
                Log.e(params[0], ". . ");
                // http://localhost/location_aware/API/Friends.php?Action=1&Name=Faysal
                URL url = new URL("http://www.glamdivaz.com/locaware/api/ShoppingList.php?Action=5&ShoppingListID=" + params[0]);
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

            try {
                JSONObject JSONresponse = new JSONObject(fullString);
                success = JSONresponse.get("success").toString();
                msg = JSONresponse.get("Message").toString();
            } catch (Exception e){
                Log.e("error", e.toString());
            }
            if(success.equals("1")){
                Log.e("inside IF ===========","bich");

            }else{
                Log.e("Error", "Failed to fetch!");
            }

        }

    }

}
