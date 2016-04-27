package com.mygps.related_to_device.map.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by HowieWang on 2016/3/8.
 */
public class Location extends BmobObject {


    String phoneID;
    Long time;
    Double latitude;
    Double longitude;

    public Location(String phoneID, Long time, Double latitude, Double longitude) {
        this.phoneID = phoneID;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(String phoneID) {
        this.phoneID = phoneID;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
