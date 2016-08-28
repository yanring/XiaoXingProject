package com.mygps.related_to_device.chiyao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mygps.R;
import com.mygps.related_to_device.map.model.Equipment;

import java.util.List;

/**
 * Created by zy on 2016/8/28.
 */
public class Spiner_adapter extends BaseAdapter{
    private Context context;
    private List<Equipment> Spinner_data;
    private LayoutInflater inflater;
    public Spiner_adapter(){
        super();
    }
    public Spiner_adapter(Context context, List<Equipment> Spinner_data){
        this.context=context;
        this.Spinner_data=Spinner_data;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return Spinner_data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.chiyao_addremind_spinneritem,null,false);
        TextView equip_name= (TextView) view.findViewById(R.id.equip_name);
        TextView equip_id= (TextView) view.findViewById(R.id.equip_id);
        equip_name.setText(Spinner_data.get(i).getName());
        equip_id.setText(Spinner_data.get(i).geteId());
        return view;
    }
}
