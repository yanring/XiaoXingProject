package com.mygps.related_to_device.map.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mygps.R;
import com.mygps.related_to_device.map.model.Equip;

/**
 * Created by 10397 on 2016/5/14.
 */
public class EquipListAdapterViewHolder {
    private TextView equipName;
    private TextView equipPhoneNumber;
    private ImageView equipPen;
    private ImageView equipDelete;
    private LinearLayout parentLayout;
    private Equip equip;
    private Context context;
    private OnThisClickListener listener;
    private int position=0;
    View view;
    public EquipListAdapterViewHolder(Context context, Equip initEquip, final int position) {
        this.context=context;
        this.position=position;
        view= LayoutInflater.from(context).inflate(R.layout.item_equiplist,null);
        equip=initEquip;
        equipName=(TextView)view.findViewById(R.id.item_equiplist_name);
        equipPhoneNumber=(TextView)view.findViewById(R.id.item_equiplist_phone);
        equipPen=(ImageView) view.findViewById(R.id.equiplistItemPen);
        equipDelete=(ImageView)view.findViewById(R.id.equiplistItemDelete);

        parentLayout=(LinearLayout)view.findViewById(R.id.equiplistItemLayout);
        equipName.setText(initEquip.getName());
        equipName.setText(initEquip.getPhoneID());
        equipDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteClick(position);
            }
        });
        equipPen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPenClick(position);
            }
        });
        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
    }

    public interface OnThisClickListener{
        void onDeleteClick(int position);
        void onPenClick(int position);
        void onItemClick(int position);
    }
    public void setOnThisClickListener(OnThisClickListener onThisClickListener){
        listener=onThisClickListener;
    }
    public View getView(){
        return view;
    }
}
