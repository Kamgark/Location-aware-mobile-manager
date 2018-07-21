package app.com.kamgar.myapplication;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by bilalbokharee on 9/25/2017.
 */

public class LocationAware extends android.support.v7.app.AppCompatActivity {
    EditText name;
    TextView data;
    static String response;
    static String UserIDA;
    static String UserIDB;
    static boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_aware);
        name = (EditText) findViewById(R.id.namee);
        data = (TextView) findViewById(R.id.textView6);

    }
}

