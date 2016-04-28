package com.mygps.related_to_device.map;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.mygps.MyApplication;
import com.mygps.R;
import com.mygps.related_to_device.map.adapter.MyEquipListAdapter;
import com.mygps.related_to_device.map.model.Equip;
import com.mygps.related_to_device.map.service.MyEquipListService;

import java.util.ArrayList;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by HowieWang on 2016/3/9.
 */
public class MyEquipListActivity extends AppCompatActivity {

    MyApplication app;
    MyEquipListService service;


    ListView equipList;
    MyEquipListAdapter adp;

    ProgressDialog pro;
    ArrayList<Equip> equips;

    SwitchCompat functionSwitch;

    FloatingActionButton FABAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equiplist);
        //临时添加一个跳转
      /*  ImageButton button = (ImageButton)findViewById(R.id.service_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyEquipListActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });*/

        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        adp.notifyDataSetChanged();
    }

    private void initView() {

       /* ((TextView)findViewById(R.id.title)).setText("设备列表");*/

        showPro();

        functionSwitch = (SwitchCompat) findViewById(R.id.function_switch);
        FABAdd = (FloatingActionButton) findViewById(R.id.activityEquiplistAdd);

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

        FABAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(MyEquipListActivity.this, AddEquipActivity.class);
                startActivity(intent);*/
                new AddEquipDialog().show(getSupportFragmentManager(),null);
            }
        });

        /*ImageButton add = (ImageButton) findViewById(R.id.function);
        add.setBackgroundResource(R.drawable.add);
        add.setVisibility(View.VISIBLE);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyEquipListActivity.this, AddEquipActivity.class);
                startActivity(intent);

            }
        });*/


    }

    public void notifyDataSetChanged() {
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


    class AddEquipDialog extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("添加设备");
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_equiplist_add_view, null);

            final EditText name = (EditText) view.findViewById(R.id.addequip_name);
            final EditText phone = (EditText) view.findViewById(R.id.addequip_phone);
            builder.setView(view);
            builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String nameStr = name.getText().toString();
                    String phoneStr = phone.getText().toString();

                    /**
                     * 这里做号码和名称的检测
                     */

                    final Equip equip = new Equip(phoneStr, nameStr, app.getUser().getUsername());
                    equip.save(MyEquipListActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {

                            app.getEquips().add(equip);
                            adp.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            new AddEquipDialog().show(getSupportFragmentManager(),null);
                        }
                    });

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            return builder.create();
        }
    }
}
