package com.mygps.adapter;

import android.content.Context;

import com.mygps.R;
import com.mygps.adapter.root.MyCommonAdapter;
import com.mygps.adapter.root.MyViewHolder;
import com.mygps.model.Equip;

import java.util.List;

/**
 * Created by HowieWang on 2016/3/9.
 */
public class MyEquipListAdapter extends MyCommonAdapter<Equip> {


    public MyEquipListAdapter(Context context, int itemLayoutId, List<Equip> myDatas) {
        super(context, itemLayoutId, myDatas);
    }

    @Override
    public void setContent(MyViewHolder holder, Equip bean) {

        try {
            holder.setText(R.id.item_equiplist_name , bean.getName()).setText(R.id.item_equiplist_phone , bean.getPhoneID());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
