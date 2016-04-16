package com.mygps;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.mygps.adapter.MyEquipListAdapter;
import com.mygps.model.Equip;
import com.mygps.service.MyEquipListService;

import java.util.ArrayList;

/**
 * Created by HowieWang on 2016/3/9.
 */
public class MyEquipListActivity extends Activity {

    MyApplication app;
    MyEquipListService service;


    ListView equipList;
    MyEquipListAdapter adp;

    ProgressDialog pro;
    ArrayList<Equip> equips;

    Switch functionSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equiplist);

        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        adp.notifyDataSetChanged();
    }

    private void initView() {

        ((TextView)findViewById(R.id.title)).setText("设备列表");

        showPro();

        functionSwitch = (Switch) findViewById(R.id.function_switch);


        app = (MyApplication) getApplication();
        equips = app.getEquips();
        service = new MyEquipListService(this, equips);

        equipList = (ListView) findViewById(R.id.equiplist);

        adp = new MyEquipListAdapter(this, R.layout.item_equiplist, equips);//yanring:有bug
        equipList.setAdapter(adp);

        service.getEquips(app.getUser().getUsername());
        adp.notifyDataSetChanged();


        equipList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /**
                 * 在这里可以查看实时位置，也可以查看历史轨迹
                 */
                Intent intent;
                if (functionSwitch.isChecked()) {
                    intent = new Intent(MyEquipListActivity.this, MyEquipLocationActivity.class);
                } else {
                    intent = new Intent(MyEquipListActivity.this, MyPathActivity.class);
                }
                intent.putExtra("equipPos", position);
                startActivity(intent);

            }
        });


        ImageButton add = (ImageButton) findViewById(R.id.function);
        add.setBackgroundResource(R.drawable.add);
        add.setVisibility(View.VISIBLE);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyEquipListActivity.this, AddEquipActivity.class);
                startActivity(intent);

            }
        });


    }

    public void notifyDataSetChanged(){
        adp.notifyDataSetChanged();
    }

    public void showPro() {
        if (pro == null) {
            pro = ProgressDialog.show(this, null, "正在加载");
        } else {
            pro.show();
        }


    }

    public void disPro() {
        pro.dismiss();
    }

}
