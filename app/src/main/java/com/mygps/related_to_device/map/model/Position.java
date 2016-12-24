package com.mygps.related_to_device.map.model;

/**
 * Created by HowieWang on 2016/5/13.
 */
public class Position {


    private int id;
    private String time;
    private double lat;
    private double lng;
    private double speed;
    private String eId;


    @Override
    public String toString() {
        return "Position[id:" + id + "][time:" + time + "][lat:" + lat + "][lng:" + lng + "][speed:" + speed + "][eId:" + eId + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String geteId() {
        return eId;
    }

    public void seteId(String eId) {
        this.eId = eId;
    }


}
