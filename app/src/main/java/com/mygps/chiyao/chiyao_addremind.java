package com.mygps.chiyao;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mygps.R;
import com.mygps.chiyao.Adapter.chiyao_addremind_adapter;
import com.mygps.chiyao.Entity.chiyao_addremind_entity;
import com.mygps.chiyao.Entity.user_entity;
import com.mygps.chiyao.Service.chiyao_service;

import java.util.List;

/**
 * Created by zy on 2016/3/31.
 */
public class chiyao_addremind extends AppCompatActivity implements AdapterView.OnItemLongClickListener,OnItemClickListener{
    private chiyao_addremind_adapter madapter;
    private ListView listview;
    private Handler handler;
    private ImageView imageView;
    private List<chiyao_addremind_entity> chiyao_enity;
    int id_position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chiyao_addremind);
        listview= (ListView) findViewById(R.id.chiyao_addremind_list);
        imageView= (ImageView) findViewById(R.id.chiyao_addremind_addimage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(chiyao_addremind.this,chiyao_addremind_additem.class);
                Bundle bundle=new Bundle();
                bundle.putString("time",null);
                bundle.putString("medision",null);
                bundle.putString("id",null);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        listview.setOnItemLongClickListener(this);
        listview.setOnItemClickListener(this);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                    {
                        listview.setAdapter(madapter);
                        setListViewHeightBasedOnChildren(listview);
                        break;
                    }
                    case 2:
                    {
                        deleteInformation();
                        break;
                    }
                }
                super.handleMessage(msg);
            }
        };
    }

    private void deleteInformation() {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                chiyao_service mService=new chiyao_service();
                return mService.deleteUserInfo(chiyao_enity.get(id_position).getId().toString());

            }

            @Override
            protected void onPostExecute(Boolean result) {
                chiyao_enity.remove(id_position);
                madapter.notifyDataSetChanged();
                listview.setAdapter(madapter);
                if(result==true){
                    Toast.makeText(chiyao_addremind.this,"删除成功！",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(chiyao_addremind.this,"删除失败！",Toast.LENGTH_SHORT).show();
                }
                super.onPostExecute(result);
            }
        }.execute();
    }

    @Override
    protected void onResume() {
        downLoadInformation();
        super.onResume();
    }

    private void downLoadInformation() {
        new AsyncTask<Void, Void, List<chiyao_addremind_entity>>() {

            @Override
            protected List<chiyao_addremind_entity> doInBackground(Void... arg0) {
                chiyao_service mService=new chiyao_service();
                return mService.downloadUserInfo("shen", "zheng");
            }

            @Override
            protected void onPostExecute(List<chiyao_addremind_entity> result) {
                chiyao_enity=result;
                madapter=new chiyao_addremind_adapter(chiyao_addremind.this,chiyao_enity);
                madapter.notifyDataSetChanged();
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        }.execute();
    }

    @Override
    public void onBackPressed() {
        chiyao_addremind.this.finish();
        super.onBackPressed();
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

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        id_position=position;
        showPopup(view);
        return true;
    }
    /*
    * 弹出pupWindows
    * */
    private void showPopup(View view) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.chiyao_deletepopup, null);

        TextView delete= (TextView) contentView.findViewById(R.id.chiyao_popup_delete);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.mipmap.iconfonticon));
        // 设置好参数之后再show
        //popupWindow.showAsDropDown(view);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0] + view.getWidth() / 2, location[1] - view.getHeight());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView time= (TextView) view.findViewById(R.id.chiyao_addremind_item_text1);
        TextView medision= (TextView) view.findViewById(R.id.chiyao_addremind_item_text2);
        TextView id_text= (TextView) view.findViewById(R.id.id_text);
        String mtime=time.getText().toString();
        String mmedision=medision.getText().toString();
        String mid=id_text.getText().toString();
        Intent intent=new Intent(chiyao_addremind.this,chiyao_addremind_additem.class);
        Bundle bundle=new Bundle();
        bundle.putString("id",mid);
        bundle.putString("time",mtime);
        bundle.putString("medision", mmedision);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
