package com.mygps.related_to_device.map.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
    private Button equipPenButton;
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
        equipPenButton=(Button)view.findViewById(R.id.equipListItemPen);
        parentLayout=(LinearLayout)view.findViewById(R.id.equiplistItemLayout);
        equipName.setText(initEquip.getName());
        equipName.setText(initEquip.getPhoneID());
        equipPenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClick(position);
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
        void onButtonClick(int position);
        void onItemClick(int position);
    }
    public void setOnThisClickListener(OnThisClickListener onThisClickListener){
        listener=onThisClickListener;
    }
    public View getView(){
        return view;
    }
}
