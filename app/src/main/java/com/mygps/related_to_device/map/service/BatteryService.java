package com.mygps.related_to_device.map.service;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.mygps.related_to_device.map.provider.URIList;

/**
 * Created by 10397 on 2016/6/14.
 */

public class BatteryService {

    public static int getBatteryInteger(String eId, Context context) {

        ContentResolver contentResolver = context.getContentResolver();

        Uri CurrentGPSUri = Uri.parse(URIList.GPS_URI);
        Cursor cursor = contentResolver.query(CurrentGPSUri, null, "eId="+eId, null, "time");
        if(cursor.getCount()<=0){
            return 0;
        }
        Log.i("CursorCount",cursor.getCount()+"");
        cursor.moveToLast();
        int battery=Integer.parseInt(cursor.getString(cursor.getColumnIndex("battery")));
        cursor.close();
        return battery;
    }
    public static String getBatteryString(String eId,Context context){
        return getBatteryInteger(eId,context)+"%";
    }
}
