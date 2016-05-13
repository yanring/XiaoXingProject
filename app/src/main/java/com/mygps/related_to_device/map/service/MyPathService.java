package com.mygps.related_to_device.map.service;

import com.baidu.mapapi.model.LatLng;
import com.mygps.related_to_device.map.MyPathActivity;
import com.mygps.related_to_device.map.model.Location;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HowieWang on 2016/3/10.
 */
public class MyPathService {

    MyPathActivity act;

    public MyPathService(MyPathActivity act) {
        this.act = act;
    }

    public void getPoints(String phoneID) {

        BmobQuery<Location> query = new BmobQuery<>();
        query.addWhereEqualTo("phoneID", phoneID);
        query.order("time");
        query.findObjects(act, new FindListener<Location>() {
            @Override
            public void onSuccess(List<Location> list) {

                ArrayList<LatLng> points = new ArrayList<>();
                double latitudes = 0;
                double longitudes = 0;
                for (Location location : list) {
                    Double latitude = location.getLatitude();
                    Double longitude = location.getLongitude();
                    points.add(new LatLng(latitude, longitude));
                    latitudes += latitude;
                    longitudes += longitude;

                }

                latitudes /= list.size();
                longitudes /= list.size();
                points.add(new LatLng(39.231403,117.053139));
                points.add(new LatLng(39.231403,118.053139));
                points.add(new LatLng(39.231403,119.053139));
                points.add(new LatLng(39.231403,111.053139));
                act.showArea(latitudes, longitudes);
                act.addOverlay(points);
                act.disPro();

            }

            @Override
            public void onError(int i, String s) {

            }
        });


    }
}
