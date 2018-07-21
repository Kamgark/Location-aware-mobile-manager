package app.com.kamgar.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Faysal Ahmed on 24-Jun-17.
 */
public class DBHelper extends SQLiteOpenHelper {
    Cursor res;
    public static final String DATABASE_NAME = "myDatabase14.db";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        //SQLiteDatabase myDatabase14;
        //myDatabase14 = getData.getWritableDatabase();
        //getData.onUpgrade(myDatabase14, 1, 2);
        Log.e("1", "Before 1.1");

        db.execSQL(
                "create table Wifi " +
                        "(id integer primary key, DayName text,Hours integer,Minutes integer, WifiStatus integer,  ReqCode integer);"
        );
        Log.e("1", "Before 1.2");
        db.execSQL(
                "create table LatLong " +
                        "(id integer primary key, latitude String, longitude String)"
        );
        Log.e("1", "Before 1.3");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        Log.e("2", "Before 2.1");
        db.execSQL(
                "create table LatLong " +
                        "(id integer primary key, latitude String, longitude String)"
        );
    }

    public boolean insertWifi (String DayName, int Hours, int Minutes , int WifiStatus, int ReqCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("DayName", DayName);
        contentValues.put("Hours", Hours);
        contentValues.put("Minutes", Minutes);
        contentValues.put("WifiStatus", WifiStatus);
        contentValues.put("ReqCode", ReqCode);
        db.insert("Wifi", null, contentValues);
        return true;
    }

    public boolean insertlatlong (double lat, double longitude, int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("latitude", lat);
        contentValues.put("longitude", longitude);
        contentValues.put("id", userid);
        db.insert("LatLong", null, contentValues);
        return true;
    }

    public Cursor getDataWifiByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Wifi where id="+id+"", null );
        return res;
    }
    public ArrayList<LatlongData> getlatlongByID(int userid) {
        ArrayList<LatlongData> array_list = new ArrayList<LatlongData>();
        SQLiteDatabase db = this.getReadableDatabase();
        res =  db.rawQuery( "select * from Latlong where id="+userid+"", null );
        try {
            res.moveToFirst();
        }catch(Exception e){
            Log.i("Mamlaat"+e, "----------------");
        }
        LatlongData latlong;
        while(res.isAfterLast() == false){

            int id = res.getInt(res.getColumnIndex("id"));
            String latitude = res.getString(res.getColumnIndex("latitude"));
            String Longitude = res.getString(res.getColumnIndex("longitude"));

            latlong = new LatlongData(id, latitude, Longitude);

            array_list.add(latlong);
            res.moveToNext();
        }
        return array_list;
    }



    public int numberOfRows(String TableName){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TableName);
        return numRows;
    }
    public boolean insertlatlong (double lat, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lat", lat);
        contentValues.put("longitude", longitude);
        db.insert("LatLong", null, contentValues);
        return true;
    }

    /*
    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    */

    public Integer deleteWifiRow (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Wifi",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<WifiScheduleData> getAllWifiRows() {
        ArrayList<WifiScheduleData> array_list = new ArrayList<WifiScheduleData>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        res =  db.rawQuery( "select * from Wifi", null );
        try {
            res.moveToFirst();
        }catch(Exception e){
            Log.i("Mamlaat"+e, "----------------");
        }
        WifiScheduleData wifiData;
        while(res.isAfterLast() == false){

            String DayName = res.getString(res.getColumnIndex("DayName"));
            int id = res.getInt(res.getColumnIndex("id"));
            int Hours = res.getInt(res.getColumnIndex("Hours"));
            int Minutes = res.getInt(res.getColumnIndex("Minutes"));
            int ReqCode =  res.getInt(res.getColumnIndex("ReqCode"));
            int WifiStatus = res.getInt(res.getColumnIndex("WifiStatus"));

            wifiData = new WifiScheduleData(id, DayName, Hours,Minutes, WifiStatus, ReqCode);

            array_list.add(wifiData);
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<LatlongData> getAllLatLong() {
        ArrayList<LatlongData> array_list = new ArrayList<LatlongData>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        res =  db.rawQuery( "select * from Latlong", null );
        try {
            res.moveToFirst();
        }catch(Exception e){
            Log.i("Mamlaat"+e, "----------------");
        }
        LatlongData latlong;
        while(res.isAfterLast() == false){

            int id = res.getInt(res.getColumnIndex("id"));
            String latitude = res.getString(res.getColumnIndex("latitude"));
            String Longitude = res.getString(res.getColumnIndex("longitude"));

            latlong = new LatlongData(id, latitude, Longitude);

            array_list.add(latlong);
            res.moveToNext();
        }
        return array_list;
    }

}
