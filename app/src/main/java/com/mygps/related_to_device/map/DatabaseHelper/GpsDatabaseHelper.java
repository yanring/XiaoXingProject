package com.mygps.related_to_device.map.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Yanring on 2016/5/13.
 */
public class GpsDatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "location_history";
    public GpsDatabaseHelper(Context context) {
        super(context,"location_database.db", null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ("
                + "id INTEGER, "
                + "time TEXT, "
                + "lat REAL, "
                + "lng REAL, "
                + "speed REAL, "
                + "eId TEXT,"
                + "battery INTEGER"
                +"inRail BOOLEAN)");
        Log.i("TAG2","CREATED TABLE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
