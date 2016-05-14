package com.mygps.related_to_device.map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

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
import com.mygps.R;
import com.mygps.utils.material_design.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10397 on 2016/5/13.
 */
public class MyEquipPen extends AppCompatActivity {
    MapView mapView = null;
    BaiduMap baiduMap = null;

    Toolbar mToolBar;
    MenuItem menuItemFresh;
    private BitmapDescriptor mBitmap;
    SeekBar radiusSeekbar;

    int radius=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equippen);
        new StatusBarUtils().setStatusBar(this);
        initMap();
        initOtherView();

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

        getSupportActionBar().setTitle("实时定位");

        radiusSeekbar=(SeekBar)findViewById(R.id.activityEquipPenSeekbar);

    }

    private void initMap() {

        mapView = (MapView) findViewById(R.id.baiduMapPen);

        //设置是否显示缩放控件
        mapView.showZoomControls(false);

        baiduMap = mapView.getMap();

        MapStatusUpdate u = MapStatusUpdateFactory.zoomTo((float)15.5);
        baiduMap.animateMapStatus(u);
        /**
         * 定位功能
         * 首先设置地图启用定位模式
         * 然后为定位模式设置定位配置（MyLocationConfiguration）
         */
        baiduMap.setMyLocationEnabled(true);
        //构建Marker图标
        mBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.drop_location_ic);
        MyLocationData locationData = new MyLocationData.Builder().latitude(39.231403).longitude(117.053139).build();
        baiduMap.setMyLocationData(locationData);

        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, mBitmap);
        baiduMap.setMyLocationConfigeration(config);


        final LatLng centerPoint = new LatLng(39.231403, 117.053139);
//构建Marker图标
        BitmapDescriptor centerBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.center_position_icon);

        final MarkerOptions centerOptions = new MarkerOptions()
                .position(centerPoint)  //设置marker的位置
                .icon(centerBitmap)  //设置marker图标
                .zIndex(9)  //设置marker所在层级
                .draggable(true);  //设置手势拖拽
//将marker添加到地图上

        final Marker centerMarker = (Marker) (baiduMap.addOverlay(centerOptions));

        LatLng radiusPoint = new LatLng(centerMarker.getPosition().latitude, centerMarker.getPosition().longitude + 0.01);
//构建Marker图标
        BitmapDescriptor radiusBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.radius_position_icon);

        final MarkerOptions radiusOptions = new MarkerOptions()
                .position(radiusPoint)  //设置marker的位置
                .icon(radiusBitmap)  //设置marker图标
                .zIndex(9)  //设置marker所在层级
                .draggable(true);  //设置手势拖拽
//将marker添加到地图上

        final Marker radiusMacker = (Marker) (baiduMap.addOverlay(radiusOptions));
        final CircleOptions circleOptions = new CircleOptions().center(centerMarker.getPosition()).stroke(new Stroke(2, 0x44000000)).fillColor(0x15000000).radius((int) DistanceUtil.getDistance(centerMarker.getPosition(), radiusMacker.getPosition()));
        final Overlay[] circleOverlay = {baiduMap.addOverlay(circleOptions)};
        final List<LatLng> list = new ArrayList<>();
        list.add(centerMarker.getPosition());
        list.add(new LatLng((centerMarker.getPosition().latitude + radiusMacker.getPosition().latitude) / 2, (centerMarker.getPosition().longitude + radiusMacker.getPosition().longitude) / 2));
        list.add(radiusMacker.getPosition());

        final PolygonOptions lineOption = new PolygonOptions()
                .points(list)
                .stroke(new Stroke(3, 0xAA00FF00))
                .fillColor(0xAAFFFF00);
        final Overlay[] lineOverlay = {baiduMap.addOverlay(lineOption)};

        baiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker) {
                circleOptions.center(centerMarker.getPosition());
                circleOptions.radius((int) DistanceUtil.getDistance(centerMarker.getPosition(), radiusMacker.getPosition()));
                circleOverlay[0].remove();
                circleOverlay[0] = baiduMap.addOverlay(circleOptions);

                list.clear();
                list.add(centerMarker.getPosition());
                list.add(new LatLng((centerMarker.getPosition().latitude + radiusMacker.getPosition().latitude) / 2, (centerMarker.getPosition().longitude + radiusMacker.getPosition().longitude) / 2));
                list.add(radiusMacker.getPosition());
                lineOption.points(list);
                lineOverlay[0].remove();
                lineOverlay[0] = baiduMap.addOverlay(lineOption);
            }

            public void onMarkerDragEnd(Marker marker) {
                //拖拽结束
            }

            public void onMarkerDragStart(Marker marker) {
                //开始拖拽
            }
        });

       /* radiusSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                radius=(int)Math.pow(1.1,progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/
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
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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

