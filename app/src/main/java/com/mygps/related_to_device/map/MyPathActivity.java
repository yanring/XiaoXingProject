package com.mygps.related_to_device.map;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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
import com.mygps.related_to_device.map.service.LocationService;

import java.util.ArrayList;

import static com.mygps.related_to_device.map.MyEquipLocationActivity.ConvertGPS2Baidu;

/**
 * Created by HowieWang on 2016/3/10.
 */
public class MyPathActivity extends AppCompatActivity {

    MapView mapView = null;
    BaiduMap baiduMap = null;

    LocationService service;
    MyApplication app;

    Equip curEquip;

    ProgressDialog pro;

    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);

        app = (MyApplication) getApplication();
        service = new LocationService(this);
        curEquip = app.getEquips().get(getIntent().getIntExtra("equipPos", -1));

        mToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        try {
            getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP, android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP);
        } catch (Exception e) {
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("历史轨迹");

        initMapView();
        initData();
        initOtherView();

    }

    private void initOtherView() {

       // ((TextView)findViewById(R.id.title)).setText("历史轨迹");
    }

    private void initData() {

        //showPro();
        service.getPreviousPostion(curEquip.getPhoneID());


    }

    private void initMapView() {

        mapView = (MapView) findViewById(R.id.pathmap);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        LatLng desLatLng = ConvertGPS2Baidu(new LatLng(112,222));
        MyLocationData locationData = new MyLocationData.Builder().latitude(desLatLng.latitude).longitude(desLatLng.longitude).build();
        baiduMap.setMyLocationData(locationData);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
        baiduMap.setMyLocationConfigeration(config);
        //baiduMap.setMyLocationEnabled(true);


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:finish();break;

        }
        return super.onOptionsItemSelected(item);
    }
}
