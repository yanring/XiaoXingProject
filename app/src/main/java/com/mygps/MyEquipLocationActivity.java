package com.mygps;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.mygps.model.Equip;
import com.mygps.model.Location;

/**
 * Created by HowieWang on 2016/3/8.
 */
public class MyEquipLocationActivity extends Activity implements MyLocationMsgReceiver.ARInteraction {

    MapView mapView = null;
    BaiduMap baiduMap = null;

    ImageButton fresh;

    Equip curEquip;
    SendMsgThread msgThread;
    private MyApplication app;
    private MyLocationMsgReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equiplocation);

        app = (MyApplication) getApplication();
        curEquip = app.getEquips().get(getIntent().getIntExtra("equipPos" , -1));

        msgThread = new SendMsgThread(curEquip.getPhoneID(), app.getSleepTime());
        msgThread.start();

        initReceiver();
        initMap();
        initOtherView();
    }

    private void initOtherView() {

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
    public void setLocation(String phoneID , double latitude, double longitude) {

        /**
         * 判断是不是这个设备发来的短信
         */
        if (!phoneID.equals(curEquip.getPhoneID())){
            return;
        }


        MyLocationData locationData = new MyLocationData.Builder().latitude(latitude).longitude(longitude).build();
        baiduMap.setMyLocationData(locationData);

    }


    @Override
    public void updateLocation(String phoneID , double latitude, double longitude) {

        Location location = new Location(phoneID, System.currentTimeMillis() ,latitude , longitude);
        location.save(this);

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
