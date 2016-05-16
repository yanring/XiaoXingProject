package com.mygps.related_to_device.map.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by HowieWang on 2016/3/8.
 */
public class Equip extends BmobObject {

    String phoneID;
    String name;
    String username;

    public Equip(String phoneID, String name ,String username) {
        this.phoneID = phoneID;
        this.name = name;
        this.username = username;
    }

    public String getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(String phoneID) {
        this.phoneID = phoneID;
    }

    public String getName() {
        return name;
    }
    public String getUsername() {
        return username;
    }

    public void setName(String name) {
        this.name = name;
    }
}
