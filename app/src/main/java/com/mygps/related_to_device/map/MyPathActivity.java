package com.mygps.related_to_device.map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.mygps.MyApplication;
import com.mygps.R;
import com.mygps.related_to_device.map.model.Equip;
import com.mygps.related_to_device.map.service.MyPathService;

import java.util.ArrayList;

/**
 * Created by HowieWang on 2016/3/10.
 */
public class MyPathActivity extends Activity {

    MapView mapView = null;
    BaiduMap baiduMap = null;

    MyPathService service;
    MyApplication app;

    Equip curEquip;

    ProgressDialog pro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);

        app = (MyApplication) getApplication();
        service = new MyPathService(this);
        curEquip = app.getEquips().get(getIntent().getIntExtra("equipPos", -1));

        initMapView();
        initData();
        initOtherView();


    }

    private void initOtherView() {

        ((TextView)findViewById(R.id.title)).setText("历史轨迹");


    }

    private void initData() {

        showPro();
        service.getPoints(curEquip.getPhoneID());


    }

    private void initMapView() {

        mapView = (MapView) findViewById(R.id.pathmap);
        baiduMap = mapView.getMap();

        baiduMap.setMyLocationEnabled(true);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration
                .LocationMode.FOLLOWING, true, null);
        baiduMap.setMyLocationConfigeration(config);

    }

    public void addOverlay(ArrayList<LatLng> points) {

        //构建文字Option对象，用于在地图上添加文字
        OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(30).fontColor
                (0xFFFF00FF).text("起点").position(points.get(0));
        baiduMap.addOverlay(textOption);

        textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(30).fontColor
                (0xFFFF00FF).text("终点").position(points.get(points.size()-1));
        baiduMap.addOverlay(textOption);

        //添加路线
        OverlayOptions ooPolyline = new PolylineOptions().width(15).color(0xAAFF0000).points
                (points);
        baiduMap.addOverlay(ooPolyline);
    }

    public void showArea(double latitude, double longitude) {
        MyLocationData locationData = new MyLocationData.Builder().latitude(latitude).longitude
                (longitude).build();
        baiduMap.setMyLocationData(locationData);

    }


    public void showPro() {
        if (pro == null) {
            pro = ProgressDialog.show(this, null, "正在加载");
        } else {
            pro.show();
        }
    }

    public void disPro() {
        pro.dismiss();
    }
}
