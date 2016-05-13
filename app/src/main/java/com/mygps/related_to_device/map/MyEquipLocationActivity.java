package com.mygps.related_to_device.map;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.mygps.MyApplication;
import com.mygps.R;
import com.mygps.related_to_device.map.model.Equip;
import com.mygps.related_to_device.map.model.Location;
import com.mygps.related_to_device.map.service.MyEquipListService;
import com.mygps.utils.material_design.StatusBarUtils;

import java.util.ArrayList;

/**
 * Created by HowieWang on 2016/3/8.
 */
public class MyEquipLocationActivity extends AppCompatActivity implements MyLocationMsgReceiver.ARInteraction {

    MapView mapView = null;
    BaiduMap baiduMap = null;

    ImageButton fresh;

    Equip curEquip;
    SendMsgThread msgThread;
    private MyApplication app;
    private MyLocationMsgReceiver mReceiver;
    Toolbar mToolBar;
    MenuItem menuItemFresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_equiplocation);

        new StatusBarUtils().setStatusBar(this);

        app = (MyApplication) getApplication();

        curEquip = app.getEquips().get(getIntent().getIntExtra("equipPos", -1));

        msgThread = new SendMsgThread(curEquip.getPhoneID(), app.getSleepTime());
        msgThread.start();

        initReceiver();
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

        /*
        ((TextView)findViewById(R.id.title)).setText("实时定位");

        fresh = (ImageButton) findViewById(R.id.function);
        fresh.setVisibility(View.VISIBLE);
        fresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgThread.interrupt();
                msgThread = new SendMsgThread(curEquip.getPhoneID(), app.getSleepTime());
                msgThread.start();
            }
        });
*/

    }

    private void initMap() {

        mapView = (MapView) findViewById(R.id.locationmap);

        //设置是否显示缩放控件
        mapView.showZoomControls(false);

        baiduMap = mapView.getMap();

        /**
         * 定位功能
         * 首先设置地图启用定位模式
         * 然后为定位模式设置定位配置（MyLocationConfiguration）
         */
        baiduMap.setMyLocationEnabled(true);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
        baiduMap.setMyLocationConfigeration(config);

    }

    private void initReceiver() {

        mReceiver = new MyLocationMsgReceiver();
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(1000);
        registerReceiver(mReceiver, intentFilter);
        mReceiver.setARInteraction(this);

    }

    @Override
    public void setLocation(String phoneID, double latitude, double longitude) {

        /**
         * 判断是不是这个设备发来的短信
         */
        if (!phoneID.equals(curEquip.getPhoneID())) {
            return;
        }


        MyLocationData locationData = new MyLocationData.Builder().latitude(latitude).longitude(longitude).build();
        baiduMap.setMyLocationData(locationData);

    }


    @Override
    public void updateLocation(String phoneID, double latitude, double longitude) {

        Location location = new Location(phoneID, System.currentTimeMillis(), latitude, longitude);
        location.save(this);

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
                msgThread.interrupt();
                msgThread = new SendMsgThread(curEquip.getPhoneID(), app.getSleepTime());
                msgThread.start();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        msgThread.setStop(true);
        msgThread.interrupt();
        unregisterReceiver(mReceiver);
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
