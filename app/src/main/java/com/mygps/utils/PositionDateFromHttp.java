package com.mygps.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.mygps.related_to_device.map.provider.URIList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 10397 on 2016/5/13.
 */
public class PositionDateFromHttp {
    private static String currentPositionUri="http://123.206.30.177/GPSServer/position/current.do?eld=";
    private static String previousPositionUri="http://123.206.30.177/GPSServer/position/previous.do?eld=";
    private String eld=null;
    Context context;

    public PositionDateFromHttp(Context context,String eld) {
        this.eld=eld;
        this.context=context;

    }




    class DataThread extends Thread{
        String url=null;

        public DataThread(String url) {
            this.url=url;
        }

        @Override
        public void run() {
            try {

                parseJson(getJsonString(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    private String getJsonString(String urlPath) throws Exception {
        String jsonString = null;
        HttpURLConnection connection = (HttpURLConnection) new URL(urlPath).openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(false);
        connection.setConnectTimeout(50000);
        InputStream in = connection.getInputStream();
        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                if (null == jsonString) {
                    jsonString = new String(buffer, 0, len);
                } else {
                    jsonString += new String(buffer, 0, len);
                }
            }
        }
        System.out.println(jsonString);
        return jsonString;
    }


    private void parseJson(String jsonString) throws JSONException {
        //
        ContentResolver contentResolver = context.getContentResolver();
        Uri insertUri = Uri.parse(URIList.GPS_URI);
        ContentValues contentValues = new ContentValues();


        //List<Map<String,Object>> data=new ArrayList<>();
        JSONArray jsonArray=new JSONArray(jsonString);
        for (int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=(JSONObject)jsonArray.get(i);
            System.out.println("time"+jsonObject.get("time"));
            //Map<String,Object> map=new HashMap<>();
            contentValues.put("id",jsonObject.get("id").toString());
            contentValues.put("time",jsonObject.get("time").toString());
            contentValues.put("lat",jsonObject.get("lat").toString());
            contentValues.put("lng",jsonObject.get("lng").toString());
            contentValues.put("speed",jsonObject.get("speed").toString());
            contentValues.put("eId",jsonObject.get("eId").toString());
            //Log.i("TAG2",jsonObject.get("eId").toString());
            //Log.i("TAG2",jsonObject.get("time").toString());
            contentResolver.insert(insertUri, contentValues);
        }


    }

    public void getPreviousDate(){
        DataThread dataThread=new DataThread(previousPositionUri+eld);
        dataThread.start();
    }
}
