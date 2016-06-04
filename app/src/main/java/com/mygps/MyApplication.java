package com.mygps;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.mygps.related_to_device.map.model.Equipment;
import com.mygps.unrelated_to_function.start.model.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

/**
 * Created by HowieWang on 2016/2/1.
 */
public class MyApplication extends Application {

    public static final String APPID = "e8f985a4ee26e26e5de22dbcc947a30e";

    List<Equipment> equips = new ArrayList<>();

    Long sleepTime = 60000L;

    User user;

    @Override
    public void onCreate() {
        super.onCreate();

        Bmob.initialize(this , APPID);
        SDKInitializer.initialize(this);
    }

    public List<Equipment> getEquips() {
        return equips;
    }

    public void setEquips(List<Equipment> equips) {
        this.equips = equips;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public void removeAllEquips(){
        equips.clear();
    }
}
