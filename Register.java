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


public class Register extends android.support.v7.app.AppCompatActivity {
    EditText name;
    EditText number;
    EditText email;
    EditText password;
    static String response;
    static boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.number);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

    }

    public void RegisterAction(View v){
        String FullName = this.name.getText().toString();
        String Number = this.number.getText().toString();
        String Email = this.email.getText().toString();
        String Password = this.password.getText().toString();

        if(FullName.equals("") || Number.equals("") || Email.equals("") || Password.equals("")){
            Toast.makeText(this, "All fields required.", Toast.LENGTH_LONG).show();
        }else{

            try{
                new Register.RegisterAsync().execute(FullName, Email, Password, Number);

            }catch(Exception e){
                Log.e("error", e.toString());
            }



        }
    }

    void showMessage(boolean res){
        if(res){
            Toast.makeText(this, "Account registered.", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getBaseContext(), SignIn.class);
            startActivity(i);
        }else{
            Toast.makeText(this, "Failed to register.", Toast.LENGTH_LONG).show();
        }
    }




    //sub class

    public class RegisterAsync extends AsyncTask<String, String, String> {
        String fullString = "";
        @Override
        protected String doInBackground(String... params) {

            try {
                Log.e(params[0],". . ");
                Log.e(params[1],". . ");
                Log.e(params[2],". . ");
                Log.e(params[3],". . ");
                URL url = new URL("http://glamdivaz/locaware/api/user.php?Action=1&Name="+params[0]+"&Email="+params[1]+"&Password="+params[2]+"&Phone="+params[3]);
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

                Register.response = fullString;
                Register.flag = true;
                return fullString;
            }catch (Exception e){
                Log.e("ERRRUR", e.toString());

            }

            return "";

        }


        //@Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Register.response = fullString;
            Register.flag = true;
            try{
                JSONObject JSONresponse = new JSONObject(fullString);
                String success = JSONresponse.get("success").toString();
                String msg = JSONresponse.get("Message").toString();
                if(success.equals("1")){
                    showMessage(true);
                }else{
                    showMessage(false);
                }

            }catch(Exception e){
                Log.e("error", e.toString());
            }

        }

    }


}
