package com.mygps.related_to_device.chiyao;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mygps.MyApplication;
import com.mygps.R;
import com.mygps.related_to_device.chiyao.Adapter.xiala_adapter;
import com.mygps.related_to_device.map.service.LocationService;

/**
 * Created by zy on 2016/3/19.
 */
public class chiyao extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout chiyao_xiala;
    private LinearLayout add;
    private int xiala = 0;
    private ListView mList;
    private xiala_adapter mAdapter;
    private View view = null;
    chiyao_listfragment chiyaoListfragment = null;
    private ImageView xiala_image;
    private TextView add_chiyao;
    private TextView positionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chiyao.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.chiyao);
        init();
        chiyao_xiala.setOnClickListener(this);
        mAdapter = new xiala_adapter(this);
        add_chiyao.setOnClickListener(this);
    }

    private void init() {
        chiyao_xiala = (LinearLayout) findViewById(R.id.chiyao_xiala);
        xiala_image = (ImageView) findViewById(R.id.xiala_image);
        add_chiyao = (TextView) findViewById(R.id.add_chiyao);
        xiala_image.setImageResource(R.mipmap.iconfonticonfonti2copy);
        positionTextView = (TextView) findViewById(R.id.takeMedicinePositionTextView);
        positionTextView.setText("无位置信息");
        LocationService locationService = new LocationService();
        if (((MyApplication) getApplication()).getEquips().size()>0) {
            locationService.getAddress(((MyApplication) getApplication()).getEquips().get(0).getName(), this);
        }
        locationService.setAddressListener(new LocationService.OnAddress() {
            @Override
            public void address(String address) {
                positionTextView.setText(address);
            }
        });

    }

    @Override
    public void onBackPressed() {
        chiyao.this.finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.chiyao_xiala: {
                FragmentManager fm = getFragmentManager();
                // 开启Fragment事务
                FragmentTransaction transaction = fm.beginTransaction();
                if (xiala == 0) {
                    xiala = 1;
                    xiala_image.setImageResource(R.mipmap.iconfontxiala);
                    chiyaoListfragment = new chiyao_listfragment();
                    transaction.add(R.id.add, chiyaoListfragment);
                    transaction.commit();
                } else {
                    xiala = 0;
                    xiala_image.setImageResource(R.mipmap.iconfonticonfonti2copy);
                    transaction.remove(chiyaoListfragment);
                    transaction.commit();
                }
                break;
            }
            case R.id.add_chiyao: {
                Intent intent = new Intent(chiyao.this, chiyao_addremind.class);
                startActivity(intent);
                break;
            }
        }
    }
}
