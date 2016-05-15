package com.mygps.related_to_device.map.service;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.google.gson.Gson;

import com.mygps.related_to_device.map.MyEquipDetailActivity;
import com.mygps.related_to_device.map.provider.URIList;

import java.util.ArrayList;

/**
 * Created by HowieWang on 2016/3/10.
 */
public class LocationService implements OnGetGeoCoderResultListener {

    MyEquipDetailActivity act;
    RequestQueue reqQue;
    Gson gson;
    private String mAddress;
    private GeoCoder mSearch;
    private String mMAddress;

    OnAddress mOnAddress;

    public LocationService(MyEquipDetailActivity act) {
        this.act = act;
        reqQue = Volley.newRequestQueue(act);
        gson = new Gson();

    }

    public LocationService() {

    }

    public LatLng getCurrentPosition(String eId, Context context) {
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
        LatLng sourceLatLng = new LatLng(lat, lng);
        cursor.close();
        return sourceLatLng;
    }

    public ArrayList<LatLng> getPreviousPostion(String eId, Context context) {
        /**
         *获取数据库中最新历史轨迹并调用MyPathActivity绘图
         **/
        Log.i("LocationService", "准备解析数据");
        ContentResolver contentResolver = context.getContentResolver();
        ArrayList<LatLng> latLngs = new ArrayList<>();
        Uri CurrentGPSUri = Uri.parse(URIList.GPS_URI);
        Cursor cursor = contentResolver.query(CurrentGPSUri, null, null, null, "time");
        while (cursor.moveToNext()) {
            String time = cursor.getString(cursor.getColumnIndex("time"));
            double lat = Double.parseDouble(cursor.getString(cursor.getColumnIndex("lat")));
            double lng = Double.parseDouble(cursor.getString(cursor.getColumnIndex("lng")));
            Log.i("TAG2", "time:" + time + "坐标:" + lat + "," + lng);
            //LatLng sourceLatLng = new LatLng(lat,lng);
            latLngs.add(new LatLng(lat, lng));
        }
        Log.i("latLngs", latLngs.toString());

        cursor.close();
        return latLngs;
    }

    public String getAddress(String eId, Context context) {
        //LatLng ptCenter = getCurrentPosition(eId,context);
//        mSearch = GeoCoder.newInstance();
//        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));
//        mSearch.setOnGetGeoCodeResultListener(this);
//        //mAddress = "";
//        Log.i("Address",mAddress+2);

        GeoCoder geoCoder = GeoCoder.newInstance();
        //设置反地理编码位置坐标
        ReverseGeoCodeOption op = new ReverseGeoCodeOption();
        op.location(getCurrentPosition(eId, context));
        geoCoder.reverseGeoCode(op);
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
                //获取点击的坐标地址
                mMAddress = arg0.getAddress();
                mOnAddress.address(mMAddress);
            }

            @Override
            public void onGetGeoCodeResult(GeoCodeResult arg0) {
            }
        });

        return mMAddress;
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {

            return;
        }
        mAddress = result.getAddress();
    }


    public interface OnAddress {
        void address(String address);
    }

    public void setAddressListener(OnAddress onAddress) {
        this.mOnAddress = onAddress;
    }
}
