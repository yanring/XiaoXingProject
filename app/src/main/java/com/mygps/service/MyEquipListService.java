package com.mygps.service;

import com.mygps.MyEquipListActivity;
import com.mygps.model.Equip;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HowieWang on 2016/3/9.
 */
public class MyEquipListService {
    ArrayList<Equip> equips;

    MyEquipListActivity act;

    public MyEquipListService(MyEquipListActivity act, ArrayList<Equip> equips) {
        this.act = act;
        this.equips = equips;
    }


    public ArrayList<Equip> getEquips(String username) {

        BmobQuery<Equip> query = new BmobQuery<>();
        query.addWhereEqualTo("username" , username);
        query.findObjects(act, new FindListener<Equip>() {
            @Override
            public void onSuccess(List<Equip> list) {
                equips.addAll(list);
                act.notifyDataSetChanged();
                act.disPro();
            }

            @Override
            public void onError(int i, String s) {

            }
        });

        return equips;
    }
}
