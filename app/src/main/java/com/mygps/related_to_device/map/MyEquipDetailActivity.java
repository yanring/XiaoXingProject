package com.mygps.related_to_device.map;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.mygps.MyApplication;
import com.mygps.R;
import com.mygps.related_to_device.map.model.Equipment;
import com.mygps.related_to_device.map.service.BatteryService;
import com.mygps.utils.material_design.StatusBarUtils;

/**
 * Created by 10397 on 2016/5/15.
 */
public class MyEquipDetailActivity extends AppCompatActivity {
    MapView mapView = null;
    BaiduMap baiduMap = null;

    MyApplication app;

    Equipment curEquip;

    ProgressDialog pro;

    Toolbar mToolBar;

    MenuItem showPath, showLocation;
    EquipLocationViewManager locationViewManager;
    EquipPathViewManager pathViewManager;
    String eId = "";

    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(this).inflate(R.layout.activity_equiplocation, null);
        setContentView(view);

        new StatusBarUtils().setStatusBar(this);

        app = (MyApplication) getApplication();

        curEquip = app.getEquips().get(getIntent().getIntExtra("equipPos", -1));
        eId=curEquip.geteId();

        mToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        try {
            getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP, android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP);
        } catch (Exception e) {
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("设备");

        initMapView();
        initManager();

        //测试
        locationViewManager.show();
        pathViewManager.show();
    }

    private void initManager() {
        locationViewManager = new EquipLocationViewManager(this, eId, view, baiduMap);
        pathViewManager = new EquipPathViewManager(this, eId, baiduMap);

    }


    private void initMapView() {

        mapView = (MapView) findViewById(R.id.locationmap);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);

        MapStatusUpdate u = MapStatusUpdateFactory.zoomTo((float) 15);
        baiduMap.animateMapStatus(u);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        showLocation = menu.add(0, 0, 0, "电量");
        showLocation.setTitle(BatteryService.getBatteryString(eId,MyEquipDetailActivity.this));
        showLocation.setEnabled(true);
        showLocation.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        showPath = menu.add(0, 1, 1, "历史轨迹");
        showPath.setTitle("关闭轨迹");
        showPath.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case 0:
/*                if (locationViewManager.getViewVisibility()) {
                    locationViewManager.remove();
                    showLocation.setTitle("显示位置");
                } else {
                    locationViewManager.show();
                    showLocation.setTitle("关闭位置");
                }
                */
                break;
            case 1:
                if (pathViewManager.getViewVisibility()) {
                    pathViewManager.remove();
                    showPath.setTitle("显示轨迹");
                } else {
                    pathViewManager.show();
                    showPath.setTitle("关闭轨迹");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
