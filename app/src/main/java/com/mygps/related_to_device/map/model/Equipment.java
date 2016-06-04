package com.mygps.related_to_device.map.model;

import java.io.Serializable;

/**
 * Created by 10397 on 2016/6/1.
 */
public class Equipment implements Serializable {

    private String id;
    private String phone;
    private String name;
    private int uId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

}
