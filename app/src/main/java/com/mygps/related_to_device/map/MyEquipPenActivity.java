package com.mygps.related_to_device.map;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.mygps.MyApplication;
import com.mygps.R;
import com.mygps.related_to_device.map.HttpRequest.PenLocationPost;
import com.mygps.related_to_device.map.service.LocationService;
import com.mygps.utils.material_design.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10397 on 2016/5/13.
 */
public class MyEquipPenActivity extends AppCompatActivity {
    MapView mapView = null;
    BaiduMap baiduMap = null;

    Toolbar mToolBar;
    MenuItem menuItemFresh;
    private BitmapDescriptor mBitmap;
    SeekBar radiusSeekbar;

    Marker centerMarker;
    MarkerOptions centerOptions;
    Marker radiusMacker;
    MarkerOptions radiusOptions;
    CircleOptions circleOptions;
    Overlay circleOverlay;
    PolygonOptions lineOption;
    Overlay lineOverlay;
    List<LatLng> linePointsist = new ArrayList<>();

    boolean isTouchAlarmByPerson = false;
    private TextView mRaiusTextView;

    SharedPreferences sharedPreferences;

    String equipID = "867967020452449";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equippen);
        new StatusBarUtils().setStatusBar(this);

        sharedPreferences = getSharedPreferences("equipPen", MODE_PRIVATE);

        equipID = ((MyApplication) getApplication()).getEquips().get(getIntent().getIntExtra("equipPos", -1)).getName();

        initOtherView();
        initMap();
    }

    private void initOtherView() {

        mToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        try {
            getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP, android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP);
        } catch (Exception e) {
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("地理围栏");

        radiusSeekbar = (SeekBar) findViewById(R.id.activityEquipPenSeekbar);
        mRaiusTextView = (TextView) findViewById(R.id.radius_textview);
    }

    private void initMap() {

        mapView = (MapView) findViewById(R.id.baiduMapPen);

        //设置是否显示缩放控件
        mapView.showZoomControls(false);

        baiduMap = mapView.getMap();

        MapStatusUpdate u = MapStatusUpdateFactory.zoomTo((float) 15.5);
        baiduMap.animateMapStatus(u);
        /**
         * 定位功能
         * 首先设置地图启用定位模式
         * 然后为定位模式设置定位配置（MyLocationConfiguration）
         */
        baiduMap.setMyLocationEnabled(true);
        //构建Marker图标
        mBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.drop_location_ic);
        MyLocationData locationData = new MyLocationData.Builder().latitude(sharedPreferences.getFloat(equipID + "CenterLat", (float) (new LocationService()).getCurrentPosition(equipID, this).latitude)).longitude(sharedPreferences.getFloat(equipID + "CenterLng", (float) (new LocationService()).getCurrentPosition(equipID, this).longitude)).build();
        baiduMap.setMyLocationData(locationData);

        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, mBitmap);
        baiduMap.setMyLocationConfigeration(config);

        final LatLng centerPoint = new LatLng(locationData.latitude, locationData.longitude);
        //构建Marker图标

        BitmapDescriptor centerBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.center_position_icon);

        centerOptions = new MarkerOptions()
                .position(centerPoint)  //设置marker的位置
                .icon(centerBitmap)  //设置marker图标
                .zIndex(9)  //设置marker所在层级
                .draggable(true);  //设置手势拖拽
        //将marker添加到地图上

        centerMarker = (Marker) (baiduMap.addOverlay(centerOptions));

        LatLng radiusPoint = new LatLng(sharedPreferences.getFloat(equipID + "RadiusLat", (float) centerMarker.getPosition().latitude), sharedPreferences.getFloat(equipID + "RadiusLng", (float) (centerMarker.getPosition().longitude + 0.01)));
        //构建Marker图标
        BitmapDescriptor radiusBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.radius_position_icon);

        radiusOptions = new MarkerOptions()
                .position(radiusPoint)  //设置marker的位置
                .icon(radiusBitmap)  //设置marker图标
                .zIndex(9)  //设置marker所在层级
                .draggable(true);  //设置手势拖拽
        //将marker添加到地图上

        radiusMacker = (Marker) (baiduMap.addOverlay(radiusOptions));
        circleOptions = new CircleOptions().center(centerMarker.getPosition()).stroke(new Stroke(2, 0x44000000)).fillColor(0x15000000).radius((int) DistanceUtil.getDistance(centerMarker.getPosition(), radiusMacker.getPosition()));
        circleOverlay = baiduMap.addOverlay(circleOptions);

        linePointsist.add(centerMarker.getPosition());
        linePointsist.add(new LatLng((centerMarker.getPosition().latitude + radiusMacker.getPosition().latitude) / 2, (centerMarker.getPosition().longitude + radiusMacker.getPosition().longitude) / 2));
        linePointsist.add(radiusMacker.getPosition());

        lineOption = new PolygonOptions()
                .points(linePointsist)
                .stroke(new Stroke(3, 0xAA00FF00))
                .fillColor(0xAAFFFF00);
        lineOverlay = baiduMap.addOverlay(lineOption);


        setSeekBarProgressByDistance();
        mRaiusTextView.setText("当前围栏半径为:" + getDistanceString());

        baiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker) {
                freshView();
                setSeekBarProgressByDistance();
            }

            public void onMarkerDragEnd(Marker marker) {
                isTouchAlarmByPerson = false;

            }

            public void onMarkerDragStart(Marker marker) {
                isTouchAlarmByPerson = true;
            }
        });

        radiusSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!isTouchAlarmByPerson) {
                    freshView((int) Math.pow(1.05, progress + 100));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void freshView() {
        circleOptions.center(centerMarker.getPosition());
        circleOptions.radius((int) getDistance());
        circleOverlay.remove();
        circleOverlay = baiduMap.addOverlay(circleOptions);

        linePointsist.clear();
        linePointsist.add(centerMarker.getPosition());
        linePointsist.add(new LatLng((centerMarker.getPosition().latitude + radiusMacker.getPosition().latitude) / 2, (centerMarker.getPosition().longitude + radiusMacker.getPosition().longitude) / 2));
        linePointsist.add(radiusMacker.getPosition());
        lineOption.points(linePointsist);
        lineOverlay.remove();
        lineOverlay = baiduMap.addOverlay(lineOption);

        mRaiusTextView.setText("当前围栏半径为:" + getDistanceString());

        Log.i("distance", getDistanceString());
    }

    private void freshView(int radius) {
        Log.i("Seekbar", radius + "");
        radiusOptions.position(new LatLng(centerMarker.getPosition().latitude, centerMarker.getPosition().longitude + radius / (double) 111000));
        radiusMacker.remove();
        radiusMacker = (Marker) (baiduMap.addOverlay(radiusOptions));
        freshView();
    }

    private double getDistance() {
        return DistanceUtil.getDistance(centerMarker.getPosition(), radiusMacker.getPosition());
    }

    private String getDistanceString() {
        double distance = getDistance();
        if ((int) distance / 1000 > 0) {
            return ((double) ((int) (distance / 10))) / 100 + "千米";
        } else {
            return (int) distance + "米";
        }
    }

    private void setSeekBarProgressByDistance() {
        radiusSeekbar.setProgress((int) (Math.log(getDistance()) / Math.log(1.05) - 100));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuItemFresh = menu.add(0, 0, 0, "刷新");
        menuItemFresh.setIcon(R.mipmap.ic_refresh_white_36dp);
        menuItemFresh.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                break;
            case android.R.id.home:
                saveData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveData();
    }

    private void saveData() {
        new PenLocationPost(this, this, getSupportFragmentManager(), sharedPreferences).start(equipID, centerMarker.getPosition(), radiusMacker.getPosition());

    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }
}

