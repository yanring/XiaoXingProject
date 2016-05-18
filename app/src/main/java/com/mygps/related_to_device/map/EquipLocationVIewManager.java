package com.mygps.related_to_device.map;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.mygps.R;
import com.mygps.related_to_device.map.service.LocationService;

/**
 * Created by 10397 on 2016/5/15.
 */
public class EquipLocationViewManager {
    BaiduMap baiduMap;
    TextView locationTextView;
    View view;
    Context context;
    String eId;
    boolean locationViewIsShow=false;
    LocationService locationService;
    private BitmapDescriptor mBitmap= BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
    private BitmapDescriptor dropBitmap=BitmapDescriptorFactory.fromResource(R.mipmap.drop_location_ic);
    public EquipLocationViewManager(Context context, String eId, View view,BaiduMap baiduMap) {
        this.eId=eId;
        this.context=context;
        this.view=view;
        this.baiduMap=baiduMap;
        locationService=new LocationService();
        init();
    }

    private void init(){
        locationTextView=(TextView)view.findViewById(R.id.address_text);
    }
    public boolean getViewVisibility(){
        return locationViewIsShow;
    }
    public void show(){
        locationTextView.setVisibility(View.VISIBLE);
        locationService.getAddress(eId,context);
        locationService.setAddressListener(new LocationService.OnAddress() {
            @Override
            public void address(String address) {
                locationTextView.setText("定位位置:"+address);
            }
        });

        LatLng desLatLng = locationService.getCurrentPosition(eId,context);//通过数据库查询当前坐标并转成百度坐标
        MyLocationData locationData = new MyLocationData.Builder().latitude(desLatLng.latitude).longitude(desLatLng.longitude).build();
        baiduMap.setMyLocationData(locationData);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
        baiduMap.setMyLocationConfigeration(config);
        locationViewIsShow=true;
    }

    public void remove(){
        locationTextView.setVisibility(View.GONE);
        MyLocationConfiguration configuration=new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, dropBitmap);
        baiduMap.setMyLocationConfigeration(configuration);
        locationViewIsShow=false;
    }
}
