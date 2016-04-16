package com.mygps;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mygps.model.Equip;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by HowieWang on 2016/3/9.
 */
public class AddEquipActivity extends Activity {


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

        ((TextView)findViewById(R.id.title)).setText("添加设备");

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


                final Equip equip = new Equip(phoneStr , nameStr ,app.getUser().getUsername());
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
