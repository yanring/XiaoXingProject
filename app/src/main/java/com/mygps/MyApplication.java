package com.mygps;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.mygps.related_to_device.map.model.Equip;
import com.mygps.unrelated_to_function.start.model.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by HowieWang on 2016/2/1.
 */
public class MyApplication extends Application {

    public static final String APPID = "e8f985a4ee26e26e5de22dbcc947a30e";

    List<Equip> equips = new ArrayList<>();

    Long sleepTime = 60000L;

    BmobUser user;

    @Override
    public void onCreate() {
        super.onCreate();

        Bmob.initialize(this , APPID);
        SDKInitializer.initialize(this);
    }

    public List<Equip> getEquips() {
        return equips;
    }

    public void setEquips(List<Equip> equips) {
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

    public void removeAllEquips(){
        equips.clear();
    }
}
