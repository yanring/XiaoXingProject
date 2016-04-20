package com.mygps.chiyao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mygps.R;
import com.mygps.chiyao.Entity.chiyao_addremind_entity;
import com.mygps.chiyao.Entity.user_entity;
import com.mygps.chiyao.chiyao_addremind_additem;
import java.util.List;

/**
 * Created by zy on 2016/3/31.
 */
public class chiyao_addremind_adapter extends BaseAdapter {
    private Context context;
    private List<chiyao_addremind_entity> mEntity;
    private LayoutInflater inflater;
    chiyao_addremind_entity entity;
    int mPosition;
    public chiyao_addremind_adapter(Context context){
        this.context=context;
    }
    public chiyao_addremind_adapter(Context context,List<chiyao_addremind_entity> mEntity){
        this.context=context;
        this.mEntity=mEntity;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mEntity.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mPosition=position;
        entity=new chiyao_addremind_entity();
        entity=mEntity.get(position);
        convertView=inflater.inflate(R.layout.chiyao_addremaind_item,null);
        TextView time= (TextView) convertView.findViewById(R.id.chiyao_addremind_item_text1);
        TextView medision= (TextView) convertView.findViewById(R.id.chiyao_addremind_item_text2);
        TextView id_text= (TextView) convertView.findViewById(R.id.id_text);
        LinearLayout change_information= (LinearLayout) convertView.findViewById(R.id.change_info);
        time.setText(entity.getTime());
        medision.setText(entity.getMedision_name());
        id_text.setText(entity.getId().toString());

        return convertView;
    }
}
