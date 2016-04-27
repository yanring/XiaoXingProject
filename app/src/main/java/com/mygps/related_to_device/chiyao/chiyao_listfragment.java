package com.mygps.related_to_device.chiyao;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.mygps.R;
import com.mygps.related_to_device.chiyao.Adapter.xiala_adapter;
import com.mygps.related_to_device.chiyao.Entity.chiyao_addremind_entity;
import com.mygps.related_to_device.chiyao.Service.chiyao_service;

import java.util.List;

/**
 * Created by zy on 2016/4/2.
 */
public class chiyao_listfragment extends android.app.Fragment{

    private ListView listView;
    private xiala_adapter madapter;
    List<chiyao_addremind_entity> chiyao_enity;
    private Handler handler;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.chiyao_fragentlist,container,false);
        listView= (ListView) view.findViewById(R.id.chiyao_fragmentlist);
        downLoadInformation();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:{
                        listView.setAdapter(madapter);
                        setListViewHeightBasedOnChildren(listView);
                        break;
                    }
                }
                super.handleMessage(msg);
            }
        };

        setListViewHeightBasedOnChildren(listView);
        return view;
    }
    /**
     * 计算listView高度
     * */
    private void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    /*
    * 下载数据
    * */
    private void downLoadInformation() {
        new AsyncTask<Void, Void, List<chiyao_addremind_entity>>() {

            @Override
            protected List<chiyao_addremind_entity> doInBackground(Void... arg0) {
                chiyao_service mService=new chiyao_service();
                return chiyao_service.downloadUserInfo("shen", "zheng");
            }

            @Override
            protected void onPostExecute(List<chiyao_addremind_entity> result) {
                chiyao_enity=result;
                madapter=new xiala_adapter(getActivity(),chiyao_enity);
                madapter.notifyDataSetChanged();
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        }.execute();
    }
}
