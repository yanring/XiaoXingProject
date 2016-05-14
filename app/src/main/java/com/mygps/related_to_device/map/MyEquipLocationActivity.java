package com.mygps.related_to_device.map;

import android.content.ContentResolver;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.mygps.MyApplication;
import com.mygps.R;
import com.mygps.related_to_device.map.model.Equip;
import com.mygps.related_to_device.map.model.Location;
import com.mygps.related_to_device.map.provider.URIList;
import com.mygps.related_to_device.map.service.MyEquipListService;
import com.mygps.utils.material_design.StatusBarUtils;

import java.util.ArrayList;

/**
 * Created by HowieWang on 2016/3/8.
 */
public class MyEquipLocationActivity extends AppCompatActivity{

    MapView mapView = null;
    BaiduMap baiduMap = null;

    ImageButton fresh;

    Equip curEquip;
    SendMsgThread msgThread;
    private MyApplication app;
    Toolbar mToolBar;
    MenuItem menuItemFresh;
    private BitmapDescriptor mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_equiplocation);

        new StatusBarUtils().setStatusBar(this);

        app = (MyApplication) getApplication();

        curEquip = app.getEquips().get(getIntent().getIntExtra("equipPos", -1));

       // msgThread = new SendMsgThread(curEquip.getPhoneID(), app.getSleepTime());
        //msgThread.start();

        //initReceiver();
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
        QueryCurrentLocation();
        baiduMap = mapView.getMap();

        /**
         * 定位功能
         * 首先设置地图启用定位模式
         * 然后为定位模式设置定位配置（MyLocationConfiguration）
         */
        baiduMap.setMyLocationEnabled(true);
        //构建Marker图标
        mBitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
        LatLng desLatLng = ConvertGPS2Baidu(QueryCurrentLocation());//通过数据库查询当前坐标并转成百度坐标
        MyLocationData locationData = new MyLocationData.Builder().latitude(desLatLng.latitude).longitude(desLatLng.longitude).build();
        baiduMap.setMyLocationData(locationData);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, mBitmap);
        baiduMap.setMyLocationConfigeration(config);

    }
    public static LatLng ConvertGPS2Baidu(LatLng sourceLatLng)
    {

        // 将GPS设备采集的原始GPS坐标转换成百度坐标
        CoordinateConverter converter  = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
// sourceLatLng待转换坐标
        converter.coord(sourceLatLng);
        LatLng desLatLng = converter.convert();
        return desLatLng;
    }
    public LatLng QueryCurrentLocation(){
        ContentResolver contentResolver = getContentResolver();

        Uri CurrentGPSUri = Uri.parse(URIList.GPS_URI);
        Cursor cursor = contentResolver.query(CurrentGPSUri, null, null, null, "time");
        cursor.moveToLast();

        String time = cursor.getString(cursor.getColumnIndex("time"));
        double lat = Double.parseDouble(cursor.getString(cursor.getColumnIndex("lat")));
        double lng = Double.parseDouble(cursor.getString(cursor.getColumnIndex("lng")));
        Log.i("TAG2","time:"+time+"坐标:"+lat+","+lng);
        LatLng sourceLatLng = new LatLng(lat,lng);
        return sourceLatLng;


//        try {
//            Cursor cursor = contentResolver.query(CurrentGPSUri, null, null, null, "time");
//            cursor.moveToLast();
//            cursor.close();
//            String time = cursor.getString(cursor.getColumnIndex("time"));
//            double lat = Double.parseDouble(cursor.getString(cursor.getColumnIndex("lat")));
//            double lng = Double.parseDouble(cursor.getString(cursor.getColumnIndex("lng")));
//            Log.i("TAG2","time:"+time+"坐标:"+lat+","+lng);
//            LatLng sourceLatLng = new LatLng(lat,lng);
//            return sourceLatLng;
//
//
//        }catch (Exception e){
//            Toast.makeText(MyEquipLocationActivity.this,"无数据",Toast.LENGTH_LONG).show();
//            LatLng sourceLatLng = new LatLng(0,0);
//            return sourceLatLng;
//        }

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
