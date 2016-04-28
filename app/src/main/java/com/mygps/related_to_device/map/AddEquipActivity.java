package com.mygps.related_to_device.map;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mygps.MyApplication;
import com.mygps.R;
import com.mygps.related_to_device.map.model.Equip;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by HowieWang on 2016/3/9.
 */
public class AddEquipActivity extends AppCompatActivity {

    EditText name;
    EditText phone;

    Button add;

    MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addequip);

        initView();

    }

    private void initView() {

        ((TextView) findViewById(R.id.title)).setText("添加设备");

        app = (MyApplication) getApplication();

        name = (EditText) findViewById(R.id.addequip_name);
        phone = (EditText) findViewById(R.id.addequip_phone);

        add = (Button) findViewById(R.id.addequip_add);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameStr = name.getText().toString();
                String phoneStr = phone.getText().toString();

                /**
                 * 这里做号码和名称的检测
                 */

                final Equip equip = new Equip(phoneStr, nameStr, app.getUser().getUsername());
                equip.save(AddEquipActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {

                        app.getEquips().add(equip);
                        AddEquipActivity.this.finish();

                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });


            }
        });
    }

}
