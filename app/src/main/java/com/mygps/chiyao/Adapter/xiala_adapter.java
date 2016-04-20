package com.mygps.chiyao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.mygps.R;
import com.mygps.chiyao.Entity.chiyao_addremind_entity;
import java.util.List;

/**
 * Created by zy on 2016/3/20.
 */
public class xiala_adapter extends BaseAdapter {
    private Context context;
    List<chiyao_addremind_entity> mEntity;
    public xiala_adapter(Context context){
        this.context=context;
    }
    public xiala_adapter(Context context,List<chiyao_addremind_entity> mEntity){
        this.mEntity=mEntity;
        this.context=context;
    }
    @Override
    public int getCount() {
        return mEntity.size() ;
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
        if(position==0){
            convertView= LayoutInflater.from(context).inflate(R.layout.chiyao_list_item1,null);
            TextView time= (TextView) convertView.findViewById(R.id.chiyao_list_time);
            TextView medision= (TextView) convertView.findViewById(R.id.chiyao_list_medision);
            medision.setText(mEntity.get(0).getMedision_name());
            time.setText(mEntity.get(0).getTime());
            return convertView;
        }
        else {
            convertView= LayoutInflater.from(context).inflate(R.layout.chiyao_list_item2,null);
            TextView time= (TextView) convertView.findViewById(R.id.chiyao_list_time2);
            TextView medision= (TextView) convertView.findViewById(R.id.chiyao_list_medision2);
            medision.setText(mEntity.get(position).getMedision_name());
            time.setText(mEntity.get(position).getTime());
            return convertView;
        }
    }
}
