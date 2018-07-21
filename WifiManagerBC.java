package app.com.kamgar.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Faysal Ahmed on 24-Jun-17.
 */

public class WifiManagerBC extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String flags = intent.getStringExtra("status");
        int flag = Integer.parseInt(flags);
        boolean status = false;
        if(flag==1){
            status = true;
        }
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(status);
        mobiledataenable(context, status);

        if(status){
            Toast.makeText(context, "Wifi/Data turned on", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, "Wifi/Data turned off", Toast.LENGTH_LONG).show();
        }


    }



    public void mobiledataenable(Context context, boolean enable) {

            try {
                final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                final Class<?> conmanClass = Class.forName(conman.getClass().getName());
                final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
                iConnectivityManagerField.setAccessible(true);
                final Object iConnectivityManager = iConnectivityManagerField.get(conman);
                final Class<?> iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
                final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
                setMobileDataEnabledMethod.setAccessible(true);
                setMobileDataEnabledMethod.invoke(iConnectivityManager, enable);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }


}