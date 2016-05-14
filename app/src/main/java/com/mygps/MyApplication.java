package com.mygps;

import android.app.Activity;
import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.mygps.related_to_device.map.model.Equip;

import java.util.ArrayList;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by HowieWang on 2016/2/1.
 */
public class MyApplication extends Application {

    public static final String APPID = "e8f985a4ee26e26e5de22dbcc947a30e";

    ArrayList<Equip> equips = new ArrayList<>();

    Long sleepTime = 60000L;

    BmobUser user;

    @Override
    public void onCreate() {
        super.onCreate();

        Bmob.initialize(this , APPID);
        SDKInitializer.initialize(this);

    }

    public ArrayList<Equip> getEquips() {
        return equips;
    }

    public void setEquips(ArrayList<Equip> equips) {
        this.equips = equips;
    }

    public BmobUser getUser() {
        return user;
    }

    public void setUser(BmobUser user) {
        this.user = user;
    }

    public Long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Long sleepTime) {
        this.sleepTime = sleepTime;
    }
}
