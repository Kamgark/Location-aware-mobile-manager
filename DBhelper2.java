package app.com.kamgar.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by bilalbokharee on 9/30/2017.
 */

public class DBhelper2 extends SQLiteOpenHelper {
    Cursor res;
    public static final String DATABASE_NAME = "myDatabase14.db";

    private HashMap hp;

    public DBhelper2(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table LatLong " +
                        "(id integer primary key, latitude real, longitude real)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS LatLong");
        onCreate(db);
    }

    public boolean insertlatlong (double lat, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lat", lat);
        contentValues.put("longitude", longitude);
        db.insert("LatLong", null, contentValues);
        return true;
    }

    public Cursor getDataWifiByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Wifi where id="+id+"", null );
        return res;
    }



    public int numberOfRows(String TableName){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TableName);
        return numRows;
    }


    /*public ArrayList<WifiScheduleData> getAllWifiRows() {
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
    }*/
}
