package com.mygps.chiyao;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mygps.R;
import com.mygps.chiyao.Adapter.xiala_adapter;

/**
 * Created by zy on 2016/3/19.
 */
public class chiyao extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout chiyao_xiala;
    private LinearLayout add;
    private int xiala=0;
    private ListView mList;
    private xiala_adapter mAdapter;
    private View view=null;
    chiyao_listfragment chiyaoListfragment=null;
    private ImageView xiala_image;
    private TextView add_chiyao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.juhuang);//通知栏所需颜色
        }*/
        chiyao.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.chiyao);
        init();
        chiyao_xiala.setOnClickListener(this);
        mAdapter=new xiala_adapter(this);
        add_chiyao.setOnClickListener(this);
    }

    private void init() {
        chiyao_xiala= (LinearLayout) findViewById(R.id.chiyao_xiala);
        xiala_image= (ImageView) findViewById(R.id.xiala_image);
        add_chiyao= (TextView) findViewById(R.id.add_chiyao);
        xiala_image.setImageResource(R.mipmap.iconfonticonfonti2copy);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onBackPressed() {
        chiyao.this.finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.chiyao_xiala:{
                FragmentManager fm = getFragmentManager();
                // 开启Fragment事务
                FragmentTransaction transaction = fm.beginTransaction();
                if(xiala==0){
                    xiala=1;
                    xiala_image.setImageResource(R.mipmap.iconfontxiala);
                    chiyaoListfragment=new chiyao_listfragment();
                    transaction.add(R.id.add,chiyaoListfragment);
                    transaction.commit();
                }
                else {
                    xiala=0;
                    xiala_image.setImageResource(R.mipmap.iconfonticonfonti2copy);
                    transaction.remove(chiyaoListfragment);
                    transaction.commit();
                }
                break;
            }
            case R.id.add_chiyao:{
                Intent intent=new Intent(chiyao.this,chiyao_addremind.class);
                startActivity(intent);
                break;
            }
        }
    }
}
