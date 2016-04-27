package com.mygps.utils.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class MyViewHolder {

    SparseArray<View> myViews;
    View convertView;
    int myPosition;

    public MyViewHolder(Context context, ViewGroup parents, int itemLayoutId , int position) {
        this.myPosition = position;
        myViews = new SparseArray<View>();
        convertView = LayoutInflater.from(context).inflate(itemLayoutId, parents, false);
        convertView.setTag(this);
    }

    public static MyViewHolder get(Context context, View convertView, ViewGroup parents, int itemLayoutId ,int position) {
        MyViewHolder holder;
        if (convertView == null) {
            holder = new MyViewHolder(context, parents, itemLayoutId,position);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        return holder;
    }

    public View getConvertView() {
        return convertView;
    }

    public <T extends View> T getViewById(int id) {
        View view = myViews.get(id);
        if (view == null) {
            view = convertView.findViewById(id);
            myViews.put(id,view);
        }
        return (T) view;
    }


    /**
     * @return MyViewHolder
     * 采用链式编程，返回自己
     * 异常处理：如果是不可以设置text的控件，则抛出异常
     * 注意如何抛异常！！！
     */
    public MyViewHolder setText(int id , String content) throws Exception {

        View view = this.getViewById(id);
        if (view instanceof TextView) {
            ((TextView)view).setText(content);
        } else if (view instanceof EditText) {
            ((EditText)view).setText(content);
        } else {
            throw new Exception("id为"+id+"的控件不能设置文字内容");
        }

        return this;

    }

    /**
     * 专门为ImageView设置图片写的方法
     * @throws Exception
     */
    public MyViewHolder setBitmapForImageView(int id, Bitmap bitmap) throws Exception {
        View view = this.getViewById(id);
        if (view instanceof ImageView) {
            ((ImageView)view).setImageBitmap(bitmap);
        } else {
            throw new Exception("id为"+id+"的控件不是ImageView或其子类");
        }

        return this;
    }

    public MyViewHolder setImageResourceForImageView(int id, int resId) throws Exception {
        View view = this.getViewById(id);
        if (view instanceof ImageView) {
            ((ImageView)view).setImageResource(resId);
        } else {
            throw new Exception("id为"+id+"的控件不是ImageView或其子类");
        }

        return this;
    }
    
    /**
     * 专门为Switch设置开关状态
     * @throws Exception
     */
    public MyViewHolder setSwitchChecked(int id , boolean power) throws Exception {
    	View view = this.getViewById(id);
        if (view instanceof Switch) {
            ((Switch) view).setChecked(power);
        } else {
            throw new Exception("id为"+id+"的控件不是Switch或其子类");
        }

        return this;
    }

}
