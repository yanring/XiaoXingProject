package com.mygps.related_to_device.map;

import android.content.Context;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.mygps.R;
import com.mygps.related_to_device.map.service.LocationService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10397 on 2016/5/15.
 */
public class EquipPathViewManager {
    BaiduMap baiduMap;
    boolean pathViewIsShow=false;
    LocationService locationService;
    BitmapDescriptor mRedTexture = BitmapDescriptorFactory.fromResource(R.mipmap.icon_road_blue_arrow0000);
    private Overlay startPoint;
    private Overlay stopPoint;
    private Overlay pathOverlay;
    private Context context;
    private String eId;
    public EquipPathViewManager(Context context,String eId,BaiduMap baiduMap) {
        this.eId=eId;
        this.context=context;
        this.baiduMap=baiduMap;
        locationService=new LocationService();
    }

    public boolean getViewVisibility(){
        return pathViewIsShow;
    }
    public void show(){
        remove();
        ArrayList<LatLng> LatLngList = locationService.getPreviousPostion(eId, context);

        if (LatLngList == null)
        {
            return;
        }
        addOverlay(LatLngList);
        pathViewIsShow=true;
    }

    public void remove(){
        if (null!=startPoint) {
            startPoint.remove();
            stopPoint.remove();
            pathOverlay.remove();
            pathViewIsShow = false;
        }
    }
    private void addOverlay(ArrayList<LatLng> points) {

        //构建文字Option对象，用于在地图上添加文字
        OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(60).fontColor
                (0xFFFF00FF).text("终点").position(points.get(points.size()-1));
        startPoint = baiduMap.addOverlay(textOption);

        textOption = new TextOptions().bgColor(0xAAFFFF00).fontSize(60).fontColor
                (0xFFFF00FF).text("起点").position(points.get(0));
        stopPoint = baiduMap.addOverlay(textOption);

        //添加有纹理的路线
        List<BitmapDescriptor> textureList = new ArrayList<BitmapDescriptor>();

        textureList.add(mRedTexture);
        textureList.add(mRedTexture);
        textureList.add(mRedTexture);

        OverlayOptions ooPolyline = new PolylineOptions().width(30)
                .points(points).dottedLine(true).customTexture(mRedTexture).keepScale(false);
        pathOverlay = baiduMap.addOverlay(ooPolyline);
    }
}
