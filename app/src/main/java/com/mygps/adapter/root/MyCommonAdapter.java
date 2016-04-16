package com.mygps.adapter.root;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 因为每个Adapter都会为某种数据集合服务，所以这里使用泛型
 */
public abstract class MyCommonAdapter<T> extends BaseAdapter {

    Context context;
    List<T> myDatas;
    int itemLayoutId;

    public MyCommonAdapter(Context context, int itemLayoutId, List<T> myDatas) {
        this.context = context;
        this.itemLayoutId = itemLayoutId;
        this.myDatas = myDatas;
    }

    @Override
    public int getCount() {
        return myDatas.size();
    }

    @Override
    public T getItem(int position) {
        return myDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addData(T data) {
    	myDatas.add(data);
    	this.notifyDataSetChanged();
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyViewHolder holder = MyViewHolder.get(context,convertView,parent,itemLayoutId,position);

        T bean= myDatas.get(position);
        setContent(holder,bean);

        return holder.getConvertView();
    }

    public abstract void setContent(MyViewHolder holder , T bean);


}
