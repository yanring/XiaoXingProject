package com.mygps.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;

import com.mygps.R;

public class SendMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        initView();


    }

    private void initView() {

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SmsManager manager = SmsManager.getDefault();

                /**
                 * 这样发送出去的短信，本地保留的内容是
                 * ss[MyGPS]
                 * 对方收到的是
                 * ss
                 */
                manager.sendTextMessage("13012024128" , null , "ss" ,null , null);


            }
        });


    }
}
