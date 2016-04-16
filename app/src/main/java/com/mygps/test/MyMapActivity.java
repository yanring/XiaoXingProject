package com.mygps.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.mygps.R;
import com.mygps.model.Location;

import java.util.ArrayList;

/**
 * Created by HowieWang on 2016/2/1.
 */
public class MyMapActivity extends Activity {

    MapView mMapView = null;
    BaiduMap mBaiduMap = null;

    EditText jingdu;
    EditText weidu;

    ArrayList<LatLng> points;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mymap);

        mMapView = (MapView) findViewById(R.id.bmapView);
        //设置是否显示缩放控件
        mMapView.showZoomControls(false);

        mBaiduMap = mMapView.getMap();

        /**
         * 定位功能
         * 首先设置地图启用定位模式
         * 然后为定位模式设置定位配置（MyLocationConfiguration）
         */
        mBaiduMap.setMyLocationEnabled(true);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
        mBaiduMap.setMyLocationConfigeration(config);

        initView();

    }

    private void initView() {

        points = new ArrayList<>();

        jingdu = (EditText) findViewById(R.id.map_jingdu);
        weidu = (EditText) findViewById(R.id.map_weidu);



        //工大经纬度   117.065868,39.243682
        /**
         * 通过经纬度标记在地图上

        findViewById(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double jing = Double.parseDouble(jingdu.getText().toString());
                double wei = Double.parseDouble(weidu.getText().toString());


                MyLocationData locationData = new MyLocationData.Builder().latitude(jing).longitude(wei).build();
                mBaiduMap.setMyLocationData(locationData);


                /*
                //定义Maker坐标点
                LatLng point = new LatLng(jing ,wei);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);



            }
        });

        */
        /**
         * 点击地图获取经纬度
         */
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                points.add(latLng);

                Location location = new Location("13102171390" , System.currentTimeMillis() , latLng.latitude , latLng.longitude);
                location.save(MyMapActivity.this);

                if (points.size()<2){
                    return;
                }
                OverlayOptions ooPolyline = new PolylineOptions().width(15).color(0xAAFF0000).points(points);
                //添加到地图
                mBaiduMap.clear();
                mBaiduMap.addOverlay(ooPolyline);


                /*
                jingdu.setText(""+latLng.latitude);
                weidu.setText(""+latLng.longitude);
                */


            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}

