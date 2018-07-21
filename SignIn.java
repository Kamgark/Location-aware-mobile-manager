package app.com.kamgar.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by bilalbokharee on 9/13/2017.
 */

                public class SignIn extends android.support.v7.app.AppCompatActivity {
                        EditText email;
                        EditText password;
                        static String response;
                        static boolean flag = false;
                        String UserID = null;
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                        setContentView(R.layout.signinn);
                        email = (EditText) findViewById(R.id.email);
                        password = (EditText) findViewById(R.id.password);
                }

                public void RegisterAction2(View v){
                        Intent i = new Intent(this, Register.class);
                        startActivity(i);
                }

                public void SignInAction(View v){
                        String Email = this.email.getText().toString();
                        String Password = this.password.getText().toString();
                        if(Email.equals("") || Password.equals("")){
                                Toast.makeText(this, "All fields required.", Toast.LENGTH_LONG).show();
                        }else{

                                try{
                                        new SignInAsync().execute(Email, Password);
                                        Thread.sleep(2000);
                                }catch(Exception e){
                                        Log.e("error", e.toString());
                                }

                                try{
                                        JSONObject JSONresponse = new JSONObject(this.response);
                                        String success = JSONresponse.get("success").toString();
                                        String msg = JSONresponse.get("Message").toString();

                                        JSONObject data = new JSONObject(JSONresponse.get("data").toString());
                                        String UserID = data.get("UserID").toString();

                                        if(success.equals("1")){
                                                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                                                Intent i = new Intent(getBaseContext(), MainActivity.class);
                                                i.putExtra("UserID", UserID);
                                                startActivity(i);

                                        }else{
                                                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                                        }

                                }catch(Exception e){
                                        Log.e("error", e.toString());
                                }

                        }
                        //Intent i = new Intent(getBaseContext(), AzaanHome.class);
                        //startActivity(i);
                }

        public void SetFriendsToMainClass(String data){
                this.UserID = data;
                Log.e("in main:::::::::::::::", data.toString());

        }


  ///////////////sub class
        public class SignInAsync extends AsyncTask<String, String, String> {
                String fullString = "";
                @Override
                protected String doInBackground(String... params) {

                        try {
                                Log.e(params[0],". . ");
                                Log.e(params[1],". . ");
                                URL url = new URL("http://www.glamdivaz.com/locaware/api/user.php?Action=2&Email="+params[0]+"&Password="+params[1]+"");
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

                                SignIn.response = fullString;
                                SignIn.flag = true;
                                return fullString;
                        }catch (Exception e){
                                Log.e("ERRRUR", e.toString());

                        }

                        return "";

                }


                //@Override
                protected void onPostExecute(String s) {


                }

        }


 }