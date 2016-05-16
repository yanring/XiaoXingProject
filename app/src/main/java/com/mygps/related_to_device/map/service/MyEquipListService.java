package com.mygps.related_to_device.map.service;

import android.content.Context;

import com.mygps.MyApplication;
import com.mygps.related_to_device.map.MyEquipListActivity;
import com.mygps.related_to_device.map.model.Equip;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HowieWang on 2016/3/9.
 */
public class MyEquipListService {
    MyApplication myApplication;
    GetEquipListener equipListener;
    Context context;

    public MyEquipListService(Context context,MyApplication myApplication) {
        this.context=context;
        this.myApplication=myApplication;
    }


    public void getEquips(String username) {

        BmobQuery<Equip> query = new BmobQuery<>();
        query.addWhereEqualTo("username" , username);
        query.findObjects(context, new FindListener<Equip>() {
            @Override
            public void onSuccess(List<Equip> list) {
                myApplication.setEquips(list);
                equipListener.onSuccess();
            }

            @Override
            public void onError(int i, String s) {
                equipListener.onError(i, s);
            }
        });

    }

    public interface GetEquipListener{
        void onSuccess();
        void onError(int i, String s);
    }
    public void setGetEquipListener(GetEquipListener equipListener){
        this.equipListener=equipListener;
    }
}
