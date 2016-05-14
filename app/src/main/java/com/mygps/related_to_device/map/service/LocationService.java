package com.mygps.related_to_device.map.service;

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

    public void getCurrentPosition(String eId){

        StringRequest req=new StringRequest(URLUtils.getCurrentURL(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Position p =gson.fromJson(response , Position.class);

                /**
                 * 把实时位置显示到界面上
                 */
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(act , error.toString() , Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void getPreviousPostion(String eId) {


        StringRequest req = new StringRequest(URLUtils.getPreviousURL(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Position> positions = gson.fromJson(response , new TypeToken<ArrayList<Position>>(){}.getType());

                ArrayList<LatLng> latLngs = new ArrayList<>();
                /**
                 * 将Position对象转换为LatLng对象
                 */
                for (Position p:positions) {
                    latLngs.add(new LatLng(p.getLat() , p.getLng()));
                }
                act.addOverlay(latLngs);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(act , error.toString() , Toast.LENGTH_SHORT).show();
            }
        });


    }
}
