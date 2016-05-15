package com.mygps.related_to_device.map.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.mygps.related_to_device.map.model.Equip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HowieWang on 2016/3/9.
 */
public class MyEquipListAdapter extends BaseAdapter {

    List<Equip> equipList=new ArrayList<>();
    Context context;
    OnViewClickListener onViewClickListener;
    public MyEquipListAdapter(Context context, List<Equip> myDatas) {
        this.context=context;
        this.equipList=myDatas;
    }

    @Override
    public int getCount() {
        return equipList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EquipListAdapterViewHolder holder=null;
        if (null==convertView){
            holder=new EquipListAdapterViewHolder(context,equipList.get(position),position);
            convertView=holder.getView();
            convertView.setTag(holder);
        }else {
            holder = (EquipListAdapterViewHolder) convertView.getTag();
        }

        holder.setOnThisClickListener(new EquipListAdapterViewHolder.OnThisClickListener() {
            @Override
            public void onDeleteClick(int position) {
                onViewClickListener.onDeleteClick(position);
            }

            @Override
            public void onPenClick(int position) {
                onViewClickListener.onPenClick(position);
            }

            @Override
            public void onItemClick(int position) {
                onViewClickListener.onItemClick(position);
            }
        });

        return convertView;
    }

    public interface OnViewClickListener{
        void onItemClick(int postion);
        void onDeleteClick(int position);
        void onPenClick(int position);
    }
    public void setOnViewClickListener(OnViewClickListener onViewClickListener){
        this.onViewClickListener=onViewClickListener;
    }
}

