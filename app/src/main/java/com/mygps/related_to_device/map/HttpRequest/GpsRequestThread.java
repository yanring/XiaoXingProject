package com.mygps.related_to_device.map.HttpRequest;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.nfc.Tag;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.mygps.related_to_device.map.provider.URIList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yanring on 2016/5/14.
 */
public class GpsRequestThread extends Thread {//异步请求GPS数据
    private final Context mContext;
    private final RequestQueue mQueue;
    String mId ;
    private static String previousPositionUri="http://123.206.30.177/GPSServer/position/previous.do?eId=";

    public GpsRequestThread(Context context,String id){
        mContext = context;
        mQueue = Volley.newRequestQueue(context);
        mId = id;
    }

    @Override
    public void run() {
        super.run();

        RequestForJsonData requestForJsonData = new RequestForJsonData(
                previousPositionUri + mId, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("Request", response.toString());
                try {
                    Log.i("RequestSingle", (response.length()-1)+response.get(response.length()-1).toString());
                    Log.i("json",response.toString());
                    parseJson(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RequestError", error.toString(),error);
                    }
                });
        mQueue.add(requestForJsonData);
        synchronized (this){
            try {
                //每隔1分钟自动更新
                this.wait(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    private void parseJson(JSONArray jsonArray) throws JSONException {
        //
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri insertUri = Uri.parse(URIList.GPS_URI);
        ContentValues contentValues = new ContentValues();


        //List<Map<String,Object>> data=new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            System.out.println("time" + jsonObject.get("time"));
            //Map<String,Object> map=new HashMap<>();
            int id = (int) jsonObject.get("id");
            contentValues.put("id", id);
            contentValues.put("time", jsonObject.get("time").toString());
            LatLng BaiduLat = ConvertGPS2Baidu(new LatLng(Double.parseDouble(jsonObject.get("lat").toString()),Double.parseDouble(jsonObject.get("lng").toString())));
            Log.i("Convert",BaiduLat.toString() );
            contentValues.put("lat", BaiduLat.latitude);
            contentValues.put("lng", BaiduLat.longitude);
            contentValues.put("speed", jsonObject.get("speed").toString());
            contentValues.put("eId", jsonObject.get("eId").toString());
            contentValues.put("inRail", jsonObject.get("inRail").toString());
            contentValues.put("battery",jsonObject.get("battery").toString());
            if ((contentResolver.query(insertUri, null, "id=" + id, null, null).getCount() == 0)) {
                contentResolver.insert(insertUri, contentValues);
                Log.i("RequestParseJson", String.valueOf(contentResolver.query(insertUri, null, "id=" + id, null, null).getCount()));

            }
            //contentResolver.insert(insertUri, contentValues);
        }
    }
    public static LatLng ConvertGPS2Baidu(LatLng sourceLatLng)
    {

        // 将GPS设备采集的原始GPS坐标转换成百度坐标
        CoordinateConverter converter  = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
// sourceLatLng待转换坐标
        converter.coord(sourceLatLng);
        LatLng desLatLng = converter.convert();

        return desLatLng;
    }
}
