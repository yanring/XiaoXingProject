package com.mygps.related_to_device.map.service;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mygps.related_to_device.map.MyPathActivity;
import com.mygps.related_to_device.map.model.Position;
import com.mygps.related_to_device.map.provider.URIList;
import com.mygps.utils.URLUtils;

import java.util.ArrayList;

/**
 * Created by HowieWang on 2016/3/10.
 */
public class LocationService {

    MyPathActivity act;
    RequestQueue reqQue;
    Gson gson;

    public LocationService(MyPathActivity act) {
        this.act = act;
        reqQue = Volley.newRequestQueue(act);
        gson = new Gson();
    }

    public static LatLng getCurrentPosition(String eId,Context context){
        //还没加对eId的判断
        /**
         获取数据库中最新的位置
         **/
        ContentResolver contentResolver = context.getContentResolver();

        Uri CurrentGPSUri = Uri.parse(URIList.GPS_URI);
        Cursor cursor = contentResolver.query(CurrentGPSUri, null, null, null, "time");
        cursor.moveToLast();

        //String time = cursor.getString(cursor.getColumnIndex("time"));
        double lat = Double.parseDouble(cursor.getString(cursor.getColumnIndex("lat")));
        double lng = Double.parseDouble(cursor.getString(cursor.getColumnIndex("lng")));

        //Log.i("TAG2","time:"+time+"坐标:"+lat+","+lng);
        LatLng sourceLatLng = new LatLng(lat,lng);
        cursor.close();
        return sourceLatLng;


    }

    public void getPreviousPostion(String eId, Context context) {
        /**
         *获取数据库中最新历史轨迹并调用MyPathActivity绘图
         **/
        Log.i("LocationService","准备解析数据");
        ContentResolver contentResolver = context.getContentResolver();
        ArrayList<LatLng> latLngs = new ArrayList<>();
        Uri CurrentGPSUri = Uri.parse(URIList.GPS_URI);
        Cursor cursor = contentResolver.query(CurrentGPSUri, null, null, null, "time");
        while (cursor.moveToNext() ){
            String time = cursor.getString(cursor.getColumnIndex("time"));
            double lat = Double.parseDouble(cursor.getString(cursor.getColumnIndex("lat")));
            double lng = Double.parseDouble(cursor.getString(cursor.getColumnIndex("lng")));
            Log.i("TAG2","time:"+time+"坐标:"+lat+","+lng);
            //LatLng sourceLatLng = new LatLng(lat,lng);
            latLngs.add(new LatLng(lat, lng));

        }
        Log.i("latLngs",latLngs.toString());
        act.addOverlay(latLngs);
        cursor.close();



    }
}
