package com.mygps.related_to_device.map.service;

import android.content.Context;

import com.mygps.MyApplication;

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

/*
        BmobQuery<Equip> query = new BmobQuery<>();
        query.addWhereEqualTo("username" , username);
        query.findObjects(context, new FindListener<Equipment>() {
            @Override
            public void onSuccess(List<Equipment> list) {
                myApplication.setEquips(list);
                equipListener.onSuccess();
            }

            @Override
            public void onError(int i, String s) {
                equipListener.onError(i, s);
            }
        });
*/

    }

    public interface GetEquipListener{
        void onSuccess();
        void onError(int i, String s);
    }
    public void setGetEquipListener(GetEquipListener equipListener){
        this.equipListener=equipListener;
    }

    public void updateList(String username){
        myApplication.removeAllEquips();
        getEquips(username);
    }
}
